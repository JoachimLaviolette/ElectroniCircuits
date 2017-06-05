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

	public ChargerFichier()
	{
		this.setScanner(new Scanner(System.in));
		System.out.println("Saisissez votre url de fichier circuit : ");
		this.setUrl_fichier_circuit(this.getScanner().nextLine()); 
		this.setFichier_circuit(new FichierCircuit(null, this.getUrl_fichier_circuit(),null));
		this.getFichier_circuit().lire();
	}
	
	public void ChargerCircuit()
	{
		String[][] tdv = {{""},{""}};
		Circuit c = new Circuit(new ArrayList<Composant>(), new ArrayList<Liaison>(), tdv);
		try
		{
			File fichier_circuit = new File(this.getUrl_fichier_circuit());
		    FileReader lecteur = new FileReader(fichier_circuit);
		    BufferedReader buffer = new BufferedReader(lecteur);
		    try
		    {
		        String ligne = buffer.readLine();
		        String debut_chaine = new String("");
		        debut_chaine = ligne.substring(0,9);
		        if(debut_chaine.equals("composant"))
		        	if(compter_occurences(ligne, " ") == 2)
	        			traitement_composant(ligne, c);
		        	else
		        		System.out.println("[ERREUR] Le nombre d'espaces attendu est différent de 2 !");
		        else
		        {
		        	debut_chaine = ligne.substring(0,7);
		        	if(debut_chaine.equals("liaison"))
		        		if(compter_occurences(ligne, " ") == 2)
		        			traitement_liaison(ligne, c);
		        		else
		        			System.out.println("[ERREUR] Le nombre d'espaces attendu est différent de 2 !");
		        	else
		        		System.out.println("[ERREUR] Le fichier contient une ligne dépréciée !");
		        }
		        while(ligne != null)
		        {
		            ligne = buffer.readLine();	
		            debut_chaine = ligne.substring(0,9);
			        if(debut_chaine.equals("composant"))
			        	if(compter_occurences(ligne, " ") == 2)
		        			traitement_composant(ligne, c);
			        	else
			        		System.out.println("[ERREUR] Le nombre d'espaces attendu est différent de 2 !");
			        else
			        {
			        	debut_chaine = ligne.substring(0,7);
			        	if(debut_chaine.equals("liaison"))
			        		if(compter_occurences(ligne, " ") == 2)
			        			traitement_liaison(ligne, c);
			        		else
			        			System.out.println("[ERREUR] Le nombre d'espaces attendu est différent de 2 !");
			        	else
			        		System.out.println("[ERREUR] Le fichier contient une ligne dépréciée !");
			        }
		        }
		        buffer.close();
		        lecteur.close();
		    }
			catch(IOException e)
			{
				System.out.print("[ERREUR] Le fichier n'a pas pu être ouvert... (" + e + ")");
			}
	    }
		catch(FileNotFoundException e)
		{
			System.out.println("[ERREUR] Le fichier n'a pas été trouvé ! (" + e + ")");
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
	
	public void traitement_composant(String ligne, Circuit c)
	{
		String fin_chaine = new String("");
		String nom_composant = new String("");
		fin_chaine = ligne.substring(0,-4);
		if(fin_chaine.contains(" "))
		{
			fin_chaine = ligne.substring(0, -3);
			if(!fin_chaine.equals("IN") && !fin_chaine.equals("OR"))
				System.out.println("[ERREUR] Le fichier contient un composant non-accepté [2 lettres] !");
			else
				if(fin_chaine.equals("IN") || fin_chaine.equals("OR"))
				{
					nom_composant = ligne.substring(10, 13);
					if(!nom_composant.substring(0, 2).equals(fin_chaine.toLowerCase()))
						System.out.println("[ERREUR] Il y a un problème entre le nom du composant (" + nom_composant.substring(0,  2) +") et le type du composant (" + fin_chaine + ")");
					else
						c.getListe_composants().add(new Composant(nom_composant.substring(0, 3), fin_chaine)); //le substr passe de 2 à 3 car on ajoute le chiffre dans le nom du composant
				}
		}
		else
		{
			if(!fin_chaine.equals("AND") && !fin_chaine.equals("XOR") && !fin_chaine.equals("NOT") && !fin_chaine.equals("OUT"))
				System.out.println("[ERREUR] Le fichier contient un composant non-accepté [3 lettres] !");
			else
				if(fin_chaine.equals("AND") || fin_chaine.equals("XOR") || fin_chaine.equals("NOT") || fin_chaine.equals("OUT"))
				{
					nom_composant = ligne.substring(10, 13);
					if(!nom_composant.substring(0, 3).equals(fin_chaine.toLowerCase()))
						System.out.println("[ERREUR] Il y a un problème entre le nom du composant (" + nom_composant.substring(0,  3) +") et le type du composant (" + fin_chaine + ")");
					else
						c.getListe_composants().add(new Composant(nom_composant.substring(0, 4), fin_chaine)); //le substr passe de 3 à 4 car on ajoute le chiffre dans le nom du composant
				}
		}
	}
	
	public void traitement_liaison(String ligne, Circuit c)
	{
		
	}
	
	//-------------------------------------------------- accesseurs et mutateurs --------------------------------------------------//
	
	public Scanner getScanner() 
	{
		return scanner;
	}
		
	public void setScanner(Scanner scanner) 
	{
		this.scanner = scanner;
	}
	
	public String getUrl_fichier_circuit() 
	{
		return url_fichier_circuit;
	}

	public void setUrl_fichier_circuit(String url_fichier_circuit) 
	{
		this.url_fichier_circuit = url_fichier_circuit;
	}

	public FichierCircuit getFichier_circuit() 
	{
		return fichier_circuit;
	}

	public void setFichier_circuit(FichierCircuit fichier_circuit)
	{
		this.fichier_circuit = fichier_circuit;
	}
}
