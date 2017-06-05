package Fichier_circuit;

import java.util.Scanner;

public class ChargerFichier 
{
	private Scanner scanner;
	private String url_fichier_circuit;
	private FichierCircuit fichier_circuit;

	public ChargerFichier()
	{
		this.setScanner(new Scanner(System.in));
		System.out.println("Saisissez votre url de fichier circuit : ");
		this.setUrl_fichier_circuit(this.getScanner().nextLine()); 
		this.setFichier_circuit(new FichierCircuit(null, this.getUrl_fichier_circuit(),null));
		this.getFichier_circuit().lire();
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
