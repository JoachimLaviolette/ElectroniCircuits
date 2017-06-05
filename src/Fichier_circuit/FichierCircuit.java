package Fichier_circuit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FichierCircuit
{
	private String nom;
	private String url;
	private String contenu;
	
	public FichierCircuit(String nom, String url_fichier_circuit, String contenu)
	{
		this.setNom(nom);
		this.setUrl(url_fichier_circuit);
		this.setContenu(contenu);
	}

	public String chargerContenu()
	{
		String contenu = new String("");
		try
	    {
			File fichier_circuit = new File(this.getUrl());
		    FileReader lecteur = new FileReader(fichier_circuit);
		    BufferedReader buffer = new BufferedReader(lecteur);
		    try
		    {
		        String ligne = buffer.readLine();
		        contenu += ligne + "\n";
		        while(ligne != null)
		        {
		            ligne = buffer.readLine();
		            contenu += ligne + "\n";
		        }
		        buffer.close();
		        lecteur.close();
		        System.out.println("[SUCCES] Le contenu du fichier a bien été chargé !");
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
		return contenu;
	}
	
	public void lire()
	{
		this.setContenu(this.chargerContenu());
		System.out.println(this.getContenu());
	}
	
	public void sauvegarder(String url_fichier_circuit, String nom_fichier_circuit)
	{
		try
	    {
			File fichier_circuit = new File(this.getUrl());
			FileWriter ecriveur = new FileWriter(url_fichier_circuit + "/" + nom_fichier_circuit + ".txt");
			BufferedWriter buffer = new BufferedWriter(ecriveur);
			try
			{
				ecriveur.write(this.getContenu());
				buffer.close();
				ecriveur.close();
				System.out.println("[SUCCES] Votre fichier a été sauvegardé !");
			}
			catch (IOException e)
			{
				System.out.print("[ERREUR] Le fichier n'a pas pu être ouvert... (" + e + ")");
			}	
	    }
		catch(IOException e)
		{
			System.out.println("[ERREUR] Le fichier n'a pas été trouvé ! (" + e + ")");
		}
	}
	
	public void editer()
	{
		//TODO
	}
	
	/*
	 * FONCTIONS A IMPLEMENTER PLUS TARD
	
		public void ajouterComposant(Composant c)
		{
			//TODO
		}
		
		public void supprimerComposant(Composant c)
		{
			//TODO
		}
		
		public void ajouterAmperemetre(Amperemetre a)
		{
			//TODO
		}
		
		public void supprimerAmperemetre(Amperemetre a)
		{
			//TODO
		}
		
		public void ajouterVoltmetre(Voltmetre v)
		{
			//TODO
		}
		
		public void supprimerVoltmetre(Voltmetre v)
		{
			//TODO
		}
	*/
	
	//-------------------------------------------------- accesseurs et mutateurs --------------------------------------------------//

	public String getNom()
	{
		return nom;
	}
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getContenu()
	{
		return contenu;
	}

	public void setContenu(String contenu) 
	{
		this.contenu = contenu;
	}	
}
