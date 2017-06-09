package Fichier_circuit;

import GUI.*;

public class ElectroniCircuitsApplication 
{
	public static void main(String[] args)
	{
		//Mode graphique
		//GUI gui = new GUI();
		//Mode console
		ChargerFichier c = new ChargerFichier();
		c.charger_circuit();
		c.afficher_resultats();
		//sauvegarder fichier
		/*SauvegarderFichier s = new SauvegarderFichier(c.getCircuit_charge().produire_tdv());*/
	}
}