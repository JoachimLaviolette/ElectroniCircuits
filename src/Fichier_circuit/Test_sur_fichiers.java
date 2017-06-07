package Fichier_circuit;

public class Test_sur_fichiers 
{
	public static void main(String[] args)
	{
		/*Composant c1 = new Composant("and1", "AND");
		System.out.println(c1.getValTdv(0,0));
		System.out.println(c1.getValTdv(1,1));
		System.out.println(c1.getValTdv(1,2));
		System.out.println(c1.getValTdv(2,1));
		System.out.println(c1.getValTdv(2,2));*/
		ChargerFichier c = new ChargerFichier();
		c.charger_circuit();
		c.afficher_resultats();
		//SauvegarderFichier s = new SauvegarderFichier("Blabla");
	}
}