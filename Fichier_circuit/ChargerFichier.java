package Fichier_circuit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

import Circuit.*;

public class ChargerFichier 
{
	private Scanner scanner;
	private String url_fichier_circuit;
	private FichierCircuit fichier_circuit;
	private Circuit circuit_charge;
	private ArrayList<String> liste_noms_composants;
	private String resultat = new String();

	public ChargerFichier() //mode console
	{
		this.setScanner(new Scanner(System.in));
		System.out.println("Saisissez votre url de fichier circuit : ");
		String url = this.getScanner().nextLine();
		String nom = url.substring(url.lastIndexOf("\\"), url.lastIndexOf("."));
		this.setUrl_fichier_circuit(url); 
		this.setFichier_circuit(new FichierCircuit(nom, this.getUrl_fichier_circuit(), null));
		this.getFichier_circuit().lire();
	}
	
	public ChargerFichier(String url) //mode graphique
	{
		String nom = url.substring(url.lastIndexOf("\\"), url.lastIndexOf("."));
		this.setUrl_fichier_circuit(url); 
		this.setFichier_circuit(new FichierCircuit(nom, this.getUrl_fichier_circuit(), null));
		this.getFichier_circuit().lire();
	}
	
	public void charger_circuit()
	{
		ArrayList<Composant> liste_c = new ArrayList<Composant>();
		ArrayList<Liaison> liste_l = new ArrayList<Liaison>();
		this.setListe_noms_composants(new ArrayList<String>());
		try
		{
			File fichier_circuit = new File(this.getUrl_fichier_circuit());
		    FileReader lecteur = new FileReader(fichier_circuit);
		    BufferedReader buffer = new BufferedReader(lecteur);
		    int compteur_ligne = 0;
		    try
		    {
		        String ligne = buffer.readLine();
		        int taille_ligne;
		        String debut_chaine = new String();
		        compteur_ligne++;
		        while(ligne != null)
		        {
		        	taille_ligne = ligne.length();
		        	if(taille_ligne < 7)
		        	{
		        		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Syntaxe incorrecte !\n");
		        		return;
		        	}
		        	else
		        	{	
		        		debut_chaine = ligne.substring(0,9);
			            if(debut_chaine.equals("composant"))
				        {
				        	if(!ligne.contains("MUX") && !ligne.contains("mux"))
				        	{	
				        		if(compter_occurences(ligne, " ") == 2)
				        		{
				        			if(traitement_composant(ligne, liste_c, compteur_ligne) == false)
				        				return;
				        		}
					        	else
					        	{
					        		System.out.println("[ERREUR] Le nombre d'espaces attendu est différent de 2 !\n Ligne : " + ligne + "\n [Nombre d'espaces : " + compter_occurences(ligne, " ") + "]");
					        		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Le nombre d'espaces attendu est différent de 2 !\n[Nombre d'espaces : " + compter_occurences(ligne, " ") + "] La syntaxe est incorrecte !\n");
					        		return;
					        	}
				        	}
				        	else
				        	{
				        		if(compter_occurences(ligne, " ") == 3)
				        		{
				        			if(traitement_composant(ligne, liste_c, compteur_ligne) == false)
				        				return;
				        		}
					        	else
					        	{
					        		System.out.println("[ERREUR] Le nombre d'espaces attendu est différent de 3 !\n Ligne : " + ligne + "\n [Nombre d'espaces : " + compter_occurences(ligne, " ") + "]");
					        		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Le nombre d'espaces attendu est différent de 3 !\n[Nombre d'espaces : " + compter_occurences(ligne, " ") + "] La syntaxe est incorrecte !\n");
					        		return;
					        	}
				        	}
				        }
				        else
				        {
				        	debut_chaine = ligne.substring(0,7);
				        	if(debut_chaine.equals("liaison"))
				        	{
				        		if((compter_occurences(ligne, " ") == 4  && !ligne.contains("out")) || (compter_occurences(ligne, " ") == 3  && ligne.contains("out")))
				        		{
				        			if(traitement_liaison(ligne, liste_l, liste_c, compteur_ligne) == false)
				        				return;
				        		}
				        		else
				        		{
				        			System.out.println("[ERREUR] Le nombre d'espaces attendu est différent de 2 !\n Ligne : " + ligne + "\n [Nombre d'espaces : " + compter_occurences(ligne, " ") + "]");
				        			this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Le nombre d'espaces attendu est différent de 2 !\n[Nombre d'espaces : " + compter_occurences(ligne, " ") + "] La syntaxe est incorrecte !\n");
					        		return;
				        		}
				        	}
				        	else
				        	{
				        		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Syntaxe incorrecte !\n");
				        		return;
				        	}
				        }
		        	}
			        ligne = buffer.readLine();
			        compteur_ligne++;			        
		        }
		        buffer.close();
		        lecteur.close();
		    }
			catch(IOException e)
			{
				System.out.print("[ERREUR] Le fichier n'a pas pu être ouvert... (" + e + ")");
				this.setResultat(this.getResultat() + "[ERREUR] Une erreur s'est produite lors de l'ouverture du fichier\n");
	        	return;
			}
		  //on vérifie qu'il ne manque pas de ligne 'liaison' (càd que chaque composant est connecté à autant de composants qu'il a d'entrées et à autant de composants qu'il a de sorties
		    if(verifier_nb_predecesseurs_entree(liste_c) && verifier_successeur_sortie(liste_c))
		    {
		    	Circuit c = new Circuit(liste_c, liste_l);
				c.mise_a_jour();
				this.setCircuit_charge(c);
				this.setResultat("[SUCCES] Chargement du circuit effectué !");
		    }
		    else
		    {		    	
		    	if(!verifier_nb_predecesseurs_entree(liste_c))
		    		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier manque de ligne 'liaison', le composant " + recuperer_nom_composant_entree_manquante(liste_c) + " ne possède pas toutes ses entrées connectées.\n");
		    	if(!verifier_successeur_sortie(liste_c))
		    		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier manque de ligne 'liaison', le composant " + recuperer_nom_composant_sortie_manquante(liste_c) + " ne possède pas sa sortie connectée.\n");
		    	return;
		    }	   
	    }
		catch(FileNotFoundException e)
		{
			System.out.println("[ERREUR] Le fichier n'a pas été trouvé ! (" + e + ")");
			this.setResultat(this.getResultat() + "[ERREUR] Une erreur s'est produite lors de l'accès au fichier circuit\n");
		    return;
		}
	}
	
	public void charger_circuit_graphique(String contenu)
	{
		
		ArrayList<Composant> liste_c = new ArrayList<Composant>();
		ArrayList<Liaison> liste_l = new ArrayList<Liaison>();
		this.setListe_noms_composants(new ArrayList<String>());
		try
		{
			File fichier_circuit = new File(this.getUrl_fichier_circuit());
		    FileReader lecteur = new FileReader(fichier_circuit);
		    BufferedReader buffer = new BufferedReader(new StringReader(contenu));
		    int compteur_ligne = 0;
		    try
		    {
		        String ligne = buffer.readLine();
		        int taille_ligne;
		        String debut_chaine = new String();
		        compteur_ligne++;
		        while(ligne != null)
		        {
		        	taille_ligne = ligne.length();
		        	if(taille_ligne < 7)
		        	{
		        		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Syntaxe incorrecte !\n");
		        		return;
		        	}
		        	else
		        	{	
		        		debut_chaine = ligne.substring(0,9);
			            if(debut_chaine.equals("composant"))
				        {
				        	if(!ligne.contains("MUX") && !ligne.contains("mux"))
				        	{	
				        		if(compter_occurences(ligne, " ") == 2)
				        		{
				        			if(traitement_composant(ligne, liste_c, compteur_ligne) == false)
				        				return;
				        		}
					        	else
					        	{
					        		System.out.println("[ERREUR] Le nombre d'espaces attendu est différent de 2 !\n Ligne : " + ligne + "\n [Nombre d'espaces : " + compter_occurences(ligne, " ") + "]");
					        		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Le nombre d'espaces attendu est différent de 2 !\n[Nombre d'espaces : " + compter_occurences(ligne, " ") + "] La syntaxe est incorrecte !\n");
					        		return;
					        	}
				        	}
				        	else
				        	{
				        		if(compter_occurences(ligne, " ") == 3)
				        		{
				        			if(traitement_composant(ligne, liste_c, compteur_ligne) == false)
				        				return;
				        		}
					        	else
					        	{
					        		System.out.println("[ERREUR] Le nombre d'espaces attendu est différent de 3 !\n Ligne : " + ligne + "\n [Nombre d'espaces : " + compter_occurences(ligne, " ") + "]");
					        		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Le nombre d'espaces attendu est différent de 3 !\n[Nombre d'espaces : " + compter_occurences(ligne, " ") + "] La syntaxe est incorrecte !\n");
					        		return;
					        	}
				        	}
				        }
				        else
				        {
				        	debut_chaine = ligne.substring(0,7);
				        	if(debut_chaine.equals("liaison"))
				        	{
				        		if((compter_occurences(ligne, " ") == 4  && !ligne.contains("out")) || (compter_occurences(ligne, " ") == 3  && ligne.contains("out")))
				        		{
				        			if(traitement_liaison(ligne, liste_l, liste_c, compteur_ligne) == false)
				        				return;
				        		}
				        		else
				        		{
				        			System.out.println("[ERREUR] Le nombre d'espaces attendu est différent de 2 !\n Ligne : " + ligne + "\n [Nombre d'espaces : " + compter_occurences(ligne, " ") + "]");
				        			this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Le nombre d'espaces attendu est différent de 2 !\n[Nombre d'espaces : " + compter_occurences(ligne, " ") + "] La syntaxe est incorrecte !\n");
					        		return;
				        		}
				        	}
				        	else
				        	{
				        		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Syntaxe incorrecte !\n");
				        		return;
				        	}
				        }
		        	}
			        ligne = buffer.readLine();
			        compteur_ligne++;			        
		        }
		        buffer.close();
		        lecteur.close();
		    }
			catch(IOException e)
			{
				System.out.print("[ERREUR] Le fichier n'a pas pu être ouvert... (" + e + ")");
				this.setResultat(this.getResultat() + "[ERREUR] Une erreur s'est produite lors de l'ouverture du fichier\n");
	        	return;
			}
		  //on vérifie qu'il ne manque pas de ligne 'liaison' (càd que chaque composant est connecté à autant de composants qu'il a d'entrées et à autant de composants qu'il a de sorties
		    if(verifier_nb_predecesseurs_entree(liste_c) && verifier_successeur_sortie(liste_c))
		    {
		    	Circuit c = new Circuit(liste_c, liste_l);
				c.mise_a_jour();
				this.setCircuit_charge(c);
				this.setResultat("[SUCCES] Chargement du circuit effectué !");
		    }
		    else
		    {		    	
		    	if(!verifier_nb_predecesseurs_entree(liste_c))
		    		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier manque de ligne 'liaison', le composant " + recuperer_nom_composant_entree_manquante(liste_c) + " ne possède pas toutes ses entrées connectées.\n");
		    	if(!verifier_successeur_sortie(liste_c))
		    		this.setResultat(this.getResultat() + "[ERREUR] Votre fichier manque de ligne 'liaison', le composant " + recuperer_nom_composant_sortie_manquante(liste_c) + " ne possède pas sa sortie connectée.\n");
		    	return;
		    }	   
	    }
		catch(FileNotFoundException e)
		{
			System.out.println("[ERREUR] Le fichier n'a pas été trouvé ! (" + e + ")");
			this.setResultat(this.getResultat() + "[ERREUR] Une erreur s'est produite lors de l'accès au fichier circuit\n");
		    return;
		}
	}

	public int compter_occurences(String chaine, String caractere)
	{
		int somme = 0;
		for(int i = 0; i < chaine.length(); i++)
			if((chaine.charAt(i) + "").equals(caractere))
				somme++;
		return somme;
	}
	
	public boolean traitement_composant(String ligne, ArrayList<Composant> liste_c, int compteur_ligne)
	{ 	
		String nom_composant;
		String type_composant;
		String commande = null;
		if(!ligne.contains("MUX"))
		{
			nom_composant = ligne.substring(ligne.indexOf(" ") + 1, ligne.lastIndexOf(" "));
			type_composant = ligne.substring(ligne.lastIndexOf(" ") + 1);
		}
		else
		{
			nom_composant = ligne.substring(ligne.indexOf(" ") + 1);
			nom_composant = nom_composant.substring(0, nom_composant.indexOf(" "));
			type_composant = ligne.substring(ligne.indexOf(" ") + 1);
			type_composant = type_composant.substring(type_composant.indexOf(" ") + 1);
			type_composant = type_composant.substring(0, type_composant.indexOf(" "));	
			commande = ligne.substring(ligne.lastIndexOf(" ") + 1);
		}
		if(!type_composant.equals("IN") && !type_composant.equals("OR") && !type_composant.equals("NOR") && !type_composant.equals("AND") && !type_composant.equals("NAND") && !type_composant.equals("XOR") && !type_composant.equals("XNOR") && !type_composant.equals("NOT") && !type_composant.equals("OUT") && !type_composant.equals("MUX"))
		{
			System.out.println("[ERREUR] Le fichier contient un composant non-accepté ! [Composant : " + type_composant + "]");
			this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Composant non-accepté ! [Composant : " + type_composant + "]\n");
    		return false;
		}
		else
		{
			String nom_tmp = new String();
			if(type_composant.equals("IN") || type_composant.equals("OR"))
				nom_tmp = nom_composant.substring(0, 2);
			else
			{
				if(type_composant.equals("AND") || type_composant.equals("XOR") || type_composant.equals("NOT") || type_composant.equals("NOR") || type_composant.equals("OUT") || type_composant.equals("MUX"))
					nom_tmp = nom_composant.substring(0, 3);
				else
					nom_tmp = nom_composant.substring(0, 4);
			}
			if(!nom_tmp.equals(type_composant.toLowerCase()))
			{
				System.out.println("[ERREUR] Il y a un problème entre le nom du composant (" + nom_composant + ") et le type du composant (" + type_composant + ")");
				this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Il y a un problème entre le nom du composant (" + nom_composant + ") et le type du composant (" + type_composant + ")\n");
        		return false;
			}
			else
			{
				System.out.println("aaa");
				liste_c.add(new Composant(nom_composant, type_composant, commande)); //le substr passe de 2 à 3 car on ajoute le chiffre dans le nom du composant
				return true;
			}
		}
	}
	
	public boolean traitement_liaison(String ligne, ArrayList<Liaison> liste_l, ArrayList<Composant> liste_c, int compteur_ligne)
	{ 
		String log = new String("\n               :::::::::: LOG TRAITEMENT LIAISON ::::::::::\n\n");
		//parse les lignes "liaison" pour récupérer les noms de composants
		String nom_composant_1 = ligne.substring(ligne.indexOf(" ") + 1);
		nom_composant_1 = nom_composant_1.substring(0, nom_composant_1.indexOf(" "));
		String nom_composant_2 = ligne.substring(ligne.indexOf(" ") + 1); 
		nom_composant_2 = nom_composant_2.substring(nom_composant_2.indexOf(" ") + 1); 
		nom_composant_2 = nom_composant_2.substring(nom_composant_2.indexOf(" ") + 1); 
		if(!nom_composant_2.contains("out"))
			nom_composant_2 = nom_composant_2.substring(0, nom_composant_2.indexOf(" "));
		boolean nom_c1_trouve = false, nom_c2_trouve = false;
		Composant c1 = null;
		Composant c2 = null;
		for(int i = 0; i < liste_c.size(); i++)
		{	
			if(liste_c.get(i).getNom().equals(nom_composant_1))
			{
				c1 = liste_c.get(i);
				nom_c1_trouve = true;
			}
			if(liste_c.get(i).getNom().equals(nom_composant_2))
			{
				c2 = liste_c.get(i);
				nom_c2_trouve = true;
			}
		}
		if(!nom_c1_trouve || !nom_c2_trouve)
		{
			if(!nom_c1_trouve)
				this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n Le premier composant possède un nom inconnu !\n");
			if(!nom_c2_trouve)
				this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n Le deuxième composant possède un nom inconnu !\n");
			return false;
		}
		String entree_c2 = new String(); //par defaut, première entrée (pour composant OUT)
		String sortie_c1 = new String();
		if(!nom_composant_2.contains("out"))
			entree_c2 = ligne.substring(ligne.lastIndexOf(" ") + 1);
		else
			entree_c2 = "";
		sortie_c1 = ligne.substring(ligne.indexOf(" ") + 1);
		sortie_c1 = sortie_c1.substring(sortie_c1.indexOf(" ") + 1);
		sortie_c1 = sortie_c1.substring(0, sortie_c1.indexOf(" "));
		boolean verifie = false;
		boolean controle_effectue = false;
		
		//on verifie que le port/sortie d'entree est bien un nombre
		try
		{
			Integer.parseInt(sortie_c1);
		}
		catch(NumberFormatException e)
		{
			this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n Le port de sortie indiqué pour le premier composant n'est pas un chiffre/nombre\n");
    		return false;
		}
		if(!nom_composant_2.contains("out"))
		{
			try
			{
				Integer.parseInt(entree_c2);
			}
			catch(NumberFormatException e)
			{
				this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n Le port d'entrée indiqué pour le deuxième composant n'est pas un chiffre/nombre\n");
	    		return false;
			}
		}
				
		//on regarde si il n'y a pas qqch lié à un IN et si il n'y a pas un OUT lié à qqch
		if(nom_composant_1.contains("out"))
		{
			controle_effectue = true; //PROBLEME! Un out ne peut pas etre lié à qqch!
			this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n OUT en premier composant\n");
    		return false;
		}
		if(nom_composant_2.contains("in"))
		{
			controle_effectue = true; //PROBLEME! qqch ne peut pas etre lié à un IN!
			this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n IN en deuxième composant\n");
    		return false;
		}
		
		//si pas de controle encore effectué a ce stade, alors c'est qu'il n'y a pas de mauvaise ordonnance de portes dans le fichier circuit, on controle donc les entrees et sorties connectées
		//on controle alors les ports d'entree et de sortie en fonction du composant
		if(!controle_effectue) 
		{
			log += "               [Ctrl pas encore effectué]";
			verifie = controle_entree(nom_composant_2, entree_c2) && controle_sortie(nom_composant_1, sortie_c1);
			if(verifie)
				log += "\n               controle_entree : true \n               controle_sortie : true";
		}
		else
			log += "               [Ctrl déjà effectué] : OUT en premier composant ou IN en deuxième composant.";
		
		//on remplit le log
		log += "\n               c1 : " + nom_composant_1 + "\n               c2 : " + nom_composant_2 + "\n               e_c2 : " + entree_c2 + "\n               s_c1 : " + sortie_c1 + "\n               verifie ? " + verifie + "\n";
		
		//verifie que le composant qui est lu ne possede pas le meme nom qu'un composant ayant deja été lu
		verifie = verifie && verifier_disponibilite_nom_composant(nom_composant_1);
		if(verifie) //si la bonne ordonnance a été vérifiée et que les noms sont disponibles, on les ajoute à la liste de noms
			this.getListe_noms_composants().add(nom_composant_1);
			
		//on commence par regarder le premier composant de la liaison
		if(!nom_composant_1.contains("in") && !nom_composant_1.contains("or") && !nom_composant_1.contains("nor") && !nom_composant_1.contains("and") && !nom_composant_1.contains("nand") && !nom_composant_1.contains("xor") && !nom_composant_1.contains("xnor") && !nom_composant_1.contains("not") && !nom_composant_1.contains("out") && !nom_composant_1.contains("mux"))
		{
			System.out.println("[ERREUR] Le fichier contient un nom de premier composant non-accepté ! [Composant : " + nom_composant_1 + "]");
			this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Nom de premier composant non-accepté ! [Composant : " + nom_composant_1 + "]\n");
    		return false;
		}
		
		//on regarde ensuite le deuxième composant de la liaison
		if(!nom_composant_2.contains("in") && !nom_composant_2.contains("or") && !nom_composant_2.contains("nor") && !nom_composant_2.contains("and") && !nom_composant_2.contains("nand") && !nom_composant_2.contains("xor") && !nom_composant_2.contains("xnor") && !nom_composant_2.contains("not") && !nom_composant_2.contains("out") && !nom_composant_2.contains("mux"))
		{
			System.out.println("[ERREUR] Le fichier contient un nom de deuxième composant non-accepté ! [Composant : " + nom_composant_2 + "]");
			this.setResultat(this.getResultat() + "[ERREUR] Votre fichier contient une ligne dépréciée :\n >> Ligne " + compteur_ligne + " : " + ligne + "\n" + "Nom de deuxième composant non-accepté ! [Composant : " + nom_composant_2 + "]\n");
    		return false;
		}
		
		//si en outre, les entrees et sorties ont été vérifiées, on crée la nouvelle liaison et on l'ajoute à la liste des liaisons du circuit
		if(verifie)
		{	
			c1.setC_successeur(c2);
			c2.getListe_c_predecesseurs().add(c1);
			Liaison li = new Liaison(c1, c2, sortie_c1, entree_c2);
			liste_l.add(li);
			afficher_log(log);
			return true;
		}
		return false;
	}
	
	public boolean verifier_nb_predecesseurs_entree(ArrayList<Composant> liste_c)
	{
		for(int i = 0; i < liste_c.size(); i++)
			if(!liste_c.get(i).getType().equals("IN"))
				if(liste_c.get(i).getListe_c_predecesseurs().size() != liste_c.get(i).getNb_entrees())
					return false;
		return true;
	}
	
	public boolean verifier_successeur_sortie(ArrayList<Composant> liste_c)
	{
		for(int i = 0; i < liste_c.size(); i++)
			if(!liste_c.get(i).getType().equals("OUT"))
				if(liste_c.get(i).getC_successeur() == null)
					return false;
		return true;
	}
	
	public String recuperer_nom_composant_entree_manquante(ArrayList<Composant> liste_c)
	{
		String nom_c = new String();
		for(int i = 0; i < liste_c.size(); i++)
			if(!liste_c.get(i).getType().equals("IN"))
				if(liste_c.get(i).getC_successeur() == null)
					return liste_c.get(i).getNom();
		return nom_c;
	}
	
	public String recuperer_nom_composant_sortie_manquante(ArrayList<Composant> liste_c)
	{
		String nom_c = new String();
		for(int i = 0; i < liste_c.size(); i++)
			if(!liste_c.get(i).getType().equals("IN"))
				if(liste_c.get(i).getListe_c_predecesseurs().size() != liste_c.get(i).getNb_entrees())
					return liste_c.get(i).getNom();
		return nom_c;
	}
	
	public String supprimer_espaces(String nom_c)
	{
		String str = new String();
		for(int i = 0; i < nom_c.length(); i++)
			if(!((nom_c.charAt(i) + "").equals(" ")))
				str += nom_c.charAt(i);
		return str;
	}
	
	public boolean controle_entree(String nom_c, String n_e)
	{
		if(nom_c.contains("and") || nom_c.contains("or") || nom_c.contains("xor") || nom_c.contains("nor") || nom_c.contains("nand") || nom_c.contains("xnor")) 
			if(!n_e.equals("1") && !n_e.equals("2"))
				return false;
		else if(nom_c.contains("out"))
			if(!n_e.equals("1"))
				return false;
		else if(nom_c.contains("in"))
			if(n_e != null)
				return false;
		return true;
	}

	public boolean controle_sortie(String nom_c, String n_s)
	{
		if(!nom_c.contains("out"))
			if(!n_s.equals("1"))
				return false;
		else if(nom_c.contains("out"))
			if(n_s != null)
				return false;
		return true;
	}
	
	public boolean verifier_disponibilite_nom_composant(String nom_c1)
	{
		if(this.getListe_noms_composants().contains(nom_c1))
			return false;
		return true;
	}
	
	public void afficher_log(String log)
	{
		System.out.println(log);
	}
		
	public void afficher_resultats() //mode console
	{
		if(this.getResultat().equals("[SUCCES] Chargement du circuit effectué !"))
		{
			this.getCircuit_charge().afficher_informations();
			System.out.println("\n:::::::::: LISTE DES NOMS DE COMPOSANTS ENREGISTRES ::::::::::\n");
			for(int a = 0; a < this.getListe_noms_composants().size(); a++)
				System.out.println("- " + this.getListe_noms_composants().get(a));
			this.getCircuit_charge().afficher_tdv();
		}
		else
			System.out.println(this.getResultat());
	}
	
	public String recuperer_resultats() //mode graphique
	{
		if(this.getResultat().equals("[SUCCES] Chargement du circuit effectué !"))
		{
			String str = new String();
			str += this.getCircuit_charge().recuperer_informations();
			str += "\n:::::::::: LISTE DES NOMS DE COMPOSANTS ENREGISTRES ::::::::::\n\n";
			for(int a = 0; a < this.getListe_noms_composants().size(); a++)
				str += "- " + this.getListe_noms_composants().get(a) + "\n";
			str += this.getCircuit_charge().recuperer_tdv();
			return str;
		}
		else
			return this.getResultat();
	}
	
	//-------------------------------------------------- accesseurs et mutateurs --------------------------------------------------//
	
	public Scanner getScanner() 
	{
		return this.scanner;
	}
		
	public void setScanner(Scanner scanner) 
	{
		this.scanner = scanner;
	}
	
	public String getUrl_fichier_circuit() 
	{
		return this.url_fichier_circuit;
	}

	public void setUrl_fichier_circuit(String url_fichier_circuit) 
	{
		this.url_fichier_circuit = url_fichier_circuit;
	}

	public FichierCircuit getFichier_circuit() 
	{
		return this.fichier_circuit;
	}

	public void setFichier_circuit(FichierCircuit fichier_circuit)
	{
		this.fichier_circuit = fichier_circuit;
	}
	
	public Circuit getCircuit_charge() 
	{
		return this.circuit_charge;
	}

	public void setCircuit_charge(Circuit circuit_charge)
	{
		this.circuit_charge = circuit_charge;
	}
	
	public ArrayList<String> getListe_noms_composants() 
	{
		return this.liste_noms_composants;
	}

	public void setListe_noms_composants(ArrayList<String> liste_noms_composants) 
	{
		this.liste_noms_composants = liste_noms_composants;
	}
	
	public String getResultat() 
	{
		return this.resultat;
	}

	public void setResultat(String resultat)
	{
		this.resultat = resultat;
	}
}
