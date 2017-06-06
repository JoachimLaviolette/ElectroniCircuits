package Fichier_circuit;

import java.util.Scanner;

public class SauvegarderFichier 
{
	private Scanner scanner;
	private String url_fichier_circuit;
	private String nom_fichier_circuit;
	private FichierCircuit fichier_circuit;
	private String contenu;

	public SauvegarderFichier(String contenu)
	{
		this.setContenu(contenu);
		this.setScanner(new Scanner(System.in));
		System.out.println("Saisissez où vous souhaitez enregistrer votre fichier de circuit : ");
		this.setUrl_fichier_circuit(this.getScanner().nextLine());
		System.out.println("Saisissez le nom de votre fichier de circuit : ");
		this.setNom_fichier_circuit(this.getScanner().nextLine());
		this.setFichier_circuit(new FichierCircuit(this.getNom_fichier_circuit(), this.getUrl_fichier_circuit(), contenu));
		this.getFichier_circuit().sauvegarder(this.getUrl_fichier_circuit(), this.getNom_fichier_circuit());
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
		return url_fichier_circuit + "/";
	}

	public void setUrl_fichier_circuit(String url_fichier_circuit) 
	{
		this.url_fichier_circuit = url_fichier_circuit;
	}
	
	public String getNom_fichier_circuit() 
	{
		return nom_fichier_circuit;
	}

	public void setNom_fichier_circuit(String nom_fichier_circuit) 
	{
		this.nom_fichier_circuit = nom_fichier_circuit;
	}
	
	public FichierCircuit getFichier_circuit() 
	{
		return fichier_circuit;
	}

	public void setFichier_circuit(FichierCircuit fichier_circuit)
	{
		this.fichier_circuit = fichier_circuit;
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
