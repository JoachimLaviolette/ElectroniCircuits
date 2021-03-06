package Fichier_circuit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

	public ChargerFichier()
	{
		this.setScanner(new Scanner(System.in));
		System.out.println("Saisissez votre url de fichier circuit : ");
		this.setUrl_fichier_circuit(this.getScanner().nextLine()); 
		this.setFichier_circuit(new FichierCircuit(null, this.getUrl_fichier_circuit(),null));
		this.getFichier_circuit().lire();
	}
	
	public void charger_circuit()
	{
		String[][] tdv = {{""},{""}};
		ArrayList<Composant> liste_c = new ArrayList<Composant>();
		ArrayList<Liaison> liste_l = new ArrayList<Liaison>();
		this.setListe_noms_composants(new ArrayList<String>());
		try
		{
			File fichier_circuit = new File(this.getUrl_fichier_circuit());
		    FileReader lecteur = new FileReader(fichier_circuit);
		    BufferedReader buffer = new BufferedReader(lecteur);
		    try
		    {
		        String ligne = buffer.readLine();
		        String debut_chaine = ligne.substring(0,9);
		        while(ligne != null)
		        {
		            debut_chaine = ligne.substring(0,9);
			        if(debut_chaine.equals("composant"))
			        	if(compter_occurences(ligne, " ") == 2)
		        			traitement_composant(ligne, liste_c);
			        	else
			        		System.out.println("[ERREUR] Le nombre d'espaces attendu est diff�rent de 2 !\n Ligne : " + ligne + "\n [Nombre d'espaces : " + compter_occurences(ligne, " ") + "]");
			        else
			        {
			        	debut_chaine = ligne.substring(0,7);
			        	if(debut_chaine.equals("liaison"))
			        		if((compter_occurences(ligne, " ") == 4  && !ligne.contains("out")) || (compter_occurences(ligne, " ") == 3  && ligne.contains("out")))
			        			traitement_liaison(ligne, liste_l);
			        		else
			        			System.out.println("[ERREUR] Le nombre d'espaces attendu est diff�rent de 2 !\n Ligne : " + ligne + "\n [Nombre d'espaces : " + compter_occurences(ligne, " ") + "]");
			        	else
			        		System.out.println("[ERREUR] Le fichier contient une ligne d�pr�ci�e ! [Debut de chaine : " + debut_chaine + "]");
			        }
			        ligne = buffer.readLine();
		        }
		        buffer.close();
		        lecteur.close();
		    }
			catch(IOException e)
			{
				System.out.print("[ERREUR] Le fichier n'a pas pu �tre ouvert... (" + e + ")");
			}
		    Circuit c = new Circuit(liste_c, liste_l);
		    c.mise_a_jour();
			this.setCircuit_charge(c);
	    }
		catch(FileNotFoundException e)
		{
			System.out.println("[ERREUR] Le fichier n'a pas �t� trouv� ! (" + e + ")");
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
	
	public void traitement_composant(String ligne, ArrayList<Composant> liste_c)
	{ 	
		String nom_composant = supprimer_espaces(ligne.substring(10, 14));	
		String type_composant = supprimer_espaces(ligne.substring(ligne.length()-3)); 	
		if(!type_composant.equals("IN") && !type_composant.equals("OR") && !type_composant.equals("AND") && !type_composant.equals("XOR") && !type_composant.equals("NOT") && !type_composant.equals("OUT"))
			System.out.println("[ERREUR] Le fichier contient un composant non-accept� ! [Composant : " + type_composant + "]");
		else
		{
			String nom_tmp = new String();
			if(type_composant.equals("IN") || type_composant.equals("OR"))
				nom_tmp = nom_composant.substring(0, 2);
			else
				nom_tmp = nom_composant.substring(0, 3);
			if(!nom_tmp.equals(type_composant.toLowerCase()))
				System.out.println("[ERREUR] Il y a un probl�me entre le nom du composant (" + nom_composant +") et le type du composant (" + type_composant + ")");
			else
			{
				liste_c.add(new Composant(nom_composant, type_composant)); //le substr passe de 2 � 3 car on ajoute le chiffre dans le nom du composant
			}
		}
	}
	
	public void traitement_liaison(String ligne, ArrayList<Liaison> liste_l)
	{ 
		String log = new String("\n               :::::::::: LOG TRAITEMENT LIAISON ::::::::::\n\n");
		String nom_composant_1 = supprimer_espaces(ligne.substring(8, 12));
		String nom_composant_2 = supprimer_espaces(ligne.substring(14, 18));
		Composant c1 = new Composant(nom_composant_1, "");
		Composant c2 = new Composant(nom_composant_2, "");
		String entree_c2 = new String(); //par defaut, premi�re entr�e (pour composant OUT)
		String sortie_c1 = new String();
		entree_c2 = supprimer_espaces(ligne.substring(ligne.length()-1));
		sortie_c1 = supprimer_espaces(ligne.substring((ligne.substring(0, 13).lastIndexOf(" ")), (ligne.substring(0, 13).lastIndexOf(" ")) + 2));
		boolean verifie = false;
		boolean controle_effectue = false;
		
		//on regarde si il n'y a pas qqch li� � un IN et si il n'y a pas un OUT li� � qqch
		if(nom_composant_1.contains("out"))
			controle_effectue = true; //PROBLEME! Un out ne peut pas etre li� � qqch!
		if(nom_composant_2.contains("in"))
			controle_effectue = true; //PROBLEME! qqch ne peut pas etre li� � un IN!
		
		//si pas de controle encore effectu� a ce stade, alors c'est qu'il n'y a pas de mauvaise ordonnance de portes dans le fichier circuit, on controle donc les entrees et sorties connect�es
		//on controle alors les ports d'entree et de sortie en fonction du composant
		if(!controle_effectue) 
		{
			log += "               [Ctrl pas encore effectu�]";
			verifie = controle_entree(nom_composant_2, entree_c2) && controle_sortie(nom_composant_1, sortie_c1);
			if(verifie)
				log += "\n               controle_sortie : true \n               controle_sortie : true";
		}
		else
			log += "               [Ctrl d�j� effectu�] : OUT en premier composant ou IN en deuxi�me composant.";
		
		//on remplit le log
		log += "\n               c1 : " + nom_composant_1 + "\n               c2 : " + nom_composant_2 + "\n               e_c2 : " + entree_c2 + "\n               s_c1 : " + sortie_c1 + "\n               verifie ? " + verifie + "\n";
		
		//verifie que le composant qui est lu ne possede pas le meme nom qu'un composant ayant deja �t� lu
		verifie = verifie && verifier_disponibilite_nom_composant(nom_composant_1);
		if(verifie) //si la bonne ordonnance a �t� v�rifi�e et que les noms sont disponibles, on les ajoute � la liste de noms
			this.getListe_noms_composants().add(nom_composant_1);
			
		//on commence par regarder le premier composant de la liaison
		if(!nom_composant_1.contains("in") && !nom_composant_1.contains("or") && !nom_composant_1.contains("and") && !nom_composant_1.contains("xor") && !nom_composant_1.contains("not") && !nom_composant_1.contains("out"))
			System.out.println("[ERREUR] Le fichier contient un nom de premier composant non-accept� ! [Composant : " + nom_composant_1 + "]");
		else
		{
			if(nom_composant_1.contains("in"))
				c1.setType("IN");
			else if(nom_composant_1.contains("or"))
				c1.setType("OR");
			else if(nom_composant_1.contains("and") || nom_composant_1.contains("xor") || !nom_composant_1.contains("not")) 
			{
				if(nom_composant_2.contains("and"))
					c1.setType("AND");
				else if(nom_composant_2.contains("xor"))
					c1.setType("XOR");
				else
					c1.setType("NOT");
			}
			else //si le deuxi�me composant est une sortie OUT
			{
				sortie_c1 = null;
				c1.setType("OUT");
			}
		}
		
		//on regarde ensuite le deuxi�me composant de la liaison
		if(!nom_composant_2.contains("in") && !nom_composant_2.contains("or") && !nom_composant_2.contains("and") && !nom_composant_2.contains("xor") && !nom_composant_2.contains("not") && !nom_composant_2.contains("out"))
			System.out.println("[ERREUR] Le fichier contient un nom de deuxi�me composant non-accept� ! [Composant : " + nom_composant_2 + "]");
		else
		{
			if(nom_composant_2.contains("in"))
			{
				entree_c2 = null;
				c2.setType("IN");
			}
			else if(nom_composant_2.contains("or"))
				c2.setType("OR");
			else if(nom_composant_2.contains("and") || nom_composant_2.contains("xor") || !nom_composant_2.contains("not")) //si le deuxi�me composant est une porte AND, XOR ou NOT
			{
				if(nom_composant_2.contains("and"))
					c2.setType("AND");
				else if(nom_composant_2.contains("xor"))
					c2.setType("XOR");
				else
					c2.setType("NOT");
			}
			else //si le deuxi�me composant est une sortie OUT
			{
				entree_c2 = null;
				c2.setType("OUT");
			}
			//si en outre, les entrees et sorties ont �t� v�rifi�es, on cr�e la nouvelle liaison et on l'ajoute � la liste des liaisons du circuit
			if(verifie)
			{	
				Liaison li = new Liaison(c1, c2, sortie_c1, entree_c2);
				liste_l.add(li);
			}
		}
		afficher_log(log);
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
		if(nom_c.contains("and") || nom_c.contains("or") || nom_c.contains("xor"))
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
		
	public void afficher_resultats()
	{
		this.getCircuit_charge().afficher_informations();
		System.out.println("\n               :::::::::: LISTE DES NOMS DE COMPOSANTS ENREGISTRES ::::::::::\n");
		for(int a = 0; a < this.getListe_noms_composants().size(); a++)
			System.out.println("               - " + this.getListe_noms_composants().get(a));
		this.getCircuit_charge().afficher_tdv();
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
}
