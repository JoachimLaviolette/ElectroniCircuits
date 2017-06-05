package Fichier_circuit;

public class Test_sur_fichiers 
{
	public static void main(String[] args)
	{
		ChargerFichier c = new ChargerFichier();
		c.charger_circuit();
		c.afficher_resultats();
		//SauvegarderFichier s = new SauvegarderFichier("Blabla");
	}
}