package Circuit;
import java.util.ArrayList;

public class Circuit 
{
	private Courant courant;
	private int nb_composants;
	private int nb_liaisons;
	private ArrayList<Composant> liste_composants;
	private ArrayList<Liaison> liste_liaisons;
	private String[][] tdv; //table de verite d'un circuit
		
	public Circuit(ArrayList<Composant> liste_composants, ArrayList<Liaison> liste_liaisons)
	{
		this.setListe_composants(liste_composants);
		this.setListe_liaisons(liste_liaisons);
		this.setNb_composants(calculer_nb_composants());
		this.setNb_liaisons(calculer_nb_liaisons());
		this.setTdv(new String[(int)Math.pow(2, calculer_nb_entrees())+1][2]);
	}
	
	public void mise_a_jour() //appel�e syst�matiquement apr�s �dition d'un circuit
	{
		//met � jour le nombre de composants et de liaisons du circuit
		this.setNb_composants(calculer_nb_composants());
		this.setNb_liaisons(calculer_nb_liaisons());		
	}
	
	public int calculer_nb_composants()
	{
		return(this.getListe_composants().size());
	}
	
	public int calculer_nb_liaisons()
	{
		return(this.getListe_liaisons().size());
	}
	
	public int calculer_nb_entrees()
	{
		int somme = 0;
		for(int i = 0; i < this.getNb_composants(); i++)
			if(this.getListe_composants().get(i).getType().equals("IN"))
				somme++;
		return somme;
	}
	
	public void remplir_noms_entrees(String[] entrees)
	{
		int a = 0;
		for(int i = 0; i < this.getListe_composants().size(); i++)
			if(this.getListe_composants().get(i).getType().equals("IN"))
			{
				entrees[a] = this.getListe_composants().get(i).getNom(); //enregistre le nom de chaque composant IN
				a++;
			}
	}
		
	public void generer_tdv()
	{
		int nb_entrees = calculer_nb_entrees();
		String[] entrees = new String[nb_entrees];
		this.remplir_noms_entrees(entrees);
		for(int i = 0; i < (int)Math.pow(2, nb_entrees); i++) //g�n�re des nombres en binaire allant de 0 � 2^k entr�es
		{
			String binaire = Integer.toBinaryString(i); //convertit le nombre en chaine binaire
			while(binaire.length() != nb_entrees) //la chaine doit �tre compos�e d'autant de bits qu'il y a d'entr�es
				binaire = "0" + binaire; 
			this.setValTdv(0, i+1, binaire); //0 0 0
			this.setValTdv(i+1, 1, calculer_tdv_sortie(binaire)); //1
		}
	}
	
	public void afficher_tdv()
	{
		this.generer_tdv();
		String tdv = new String(" ");
		int nb_entrees = calculer_nb_entrees();
		String[] entrees = new String[nb_entrees];
		this.remplir_noms_entrees(entrees);
		tdv += " ";
		for(int i = 0; i < nb_entrees; i++)
			tdv += entrees[i] + " ";
		tdv += "| Sortie\n";	
		for(int i = 0; i < (int)Math.pow(2, nb_entrees); i++) //g�n�re des nombres en binaire allant de 0 � 2^k entr�es
		{
			tdv += " ";
			for(int a = 0; a < this.getValTdv(0, i+1).length(); a++) //parcourt la chaine binaire du nombre , ex : 01011 pour g�n�rer : "0 1 0 1 1 "
				tdv += this.getValTdv(0, i+1).charAt(a) + " ";	//ajoute chaque caract�re de la chaine binaire avec un espace entre eux
			tdv += "| " + this.getValTdv(1, i+1) + "\n"; //affiche la valeur de v�rit� au nombre binaire (chaine) 
		}
		System.out.println(tdv);
	}
	
	public String calculer_tdv_sortie(String binaire)
	{
		String str = new String("");
		for(int m = 0; m < binaire.length(); m++)
			for(int x = 0; x < this.getNb_composants(); x++)
				if(this.getListe_composants().get(x).getNom().contains("in" + (m + 1)))
					this.getListe_composants().get(x).setBit_sortie(binaire.charAt(m) + ""); //on affecte � la porte IN regard� son bit de sortie
		for(int i = 0; i < this.getNb_liaisons(); i++)
		{
			Liaison li = this.getListe_liaisons().get(i);
			String c1_nom = li.getC1().getNom();
			if(li.getC1().getType().equals("AND") || li.getC1().getType().equals("OR") || li.getC1().getType().equals("XOR")) //2 entr�es
			{
				String c_pred_bit0_s = "";
				String c_pred_nom0 = "";
				String c_pred_bit1_s = "";
				String c_pred_nom1 = "";
				int bit_0 = 0; //bit entr�e 1
				int bit_1 = 0; //bit entr�e 2
				int a = 0;
				for(int j = 0; j < this.getNb_liaisons(); j++)
				{
					if(this.getListe_liaisons().get(j).getC2().getNom().equals(c1_nom) && a == 0)
					{
						c_pred_nom0 = this.getListe_liaisons().get(j).getC1().getNom();
						for(int x = 0; x < this.getNb_composants(); x++)
							if(this.getListe_composants().get(x).getNom().equals(c_pred_nom0))
								c_pred_bit0_s = this.getListe_composants().get(x).getBit_sortie();
						System.out.println("Res : " + c_pred_bit0_s);
						bit_0 = Integer.parseInt(c_pred_bit0_s);
						a++;
					}					
					else if(this.getListe_liaisons().get(j).getC2().getNom().equals(c1_nom) && a == 1)
					{
						c_pred_nom1 = this.getListe_liaisons().get(j).getC1().getNom();
						for(int x = 0; x < this.getNb_composants(); x++)
							if(this.getListe_composants().get(x).getNom().equals(c_pred_nom1))
								c_pred_bit1_s = this.getListe_composants().get(x).getBit_sortie();
						System.out.println("Res : " + c_pred_bit1_s);
						bit_1 = Integer.parseInt(c_pred_bit1_s);
						a++;
					}
				}				
				for(int x = 0; x < this.getNb_composants(); x++)
				{
					if(this.getListe_composants().get(x).getNom().equals(c1_nom))
					{
						if(this.getListe_composants().get(x).getListeBits_entree().get(0).equals(""))
							this.getListe_composants().get(x).setBit_entree(0, bit_0 + ""); //on affecte au composant regard� son bit d'entr�e (0)
						else if(this.getListe_composants().get(x).getListeBits_entree().get(1).equals(""))
							this.getListe_composants().get(x).setBit_entree(1, bit_1 + ""); //on affecte au composant regard� son bit d'entr�e (1)
						else
							System.out.println("[ERREUR] Ce composant (" + c1_nom + ") poss�de tous ses ports d'entr�e occup�s.");
						if(!this.getListe_composants().get(x).getListeBits_entree().get(0).equals("") && !this.getListe_composants().get(x).getListeBits_entree().get(1).equals(""))
							this.getListe_composants().get(x).setBit_sortie(this.getListe_composants().get(x).getValTdv(bit_0, bit_1));
					}	
				}
			}
			else //NOT et OUT
			{
				if(!li.getC1().getType().equals("IN")) 
				{
					String c_pred_bit_s = ""; //bit entr�e
					String c_pred_nom = "";
					int bit = 0; //bit entr�e
					int a = 0;
					for(int j = 0; j < this.getNb_liaisons(); j++)
					{
						if(this.getListe_liaisons().get(j).getC2().getNom().equals(c1_nom) && a == 0)
						{
							c_pred_nom = this.getListe_liaisons().get(j).getC1().getNom();
							for(int x = 0; x < this.getNb_composants(); x++)
								if(this.getListe_composants().get(x).getNom().equals(c_pred_nom))
									c_pred_bit_s = this.getListe_composants().get(x).getBit_sortie();
							System.out.println("Res : " + c_pred_bit_s);
							bit = Integer.parseInt(c_pred_bit_s);
							a++;
						}
					}				
					for(int x = 0; x < this.getNb_composants(); x++)
					{
						if(this.getListe_composants().get(x).getNom().equals(c1_nom))
						{
							if(this.getListe_composants().get(x).getListeBits_entree().get(0).equals(""))
								this.getListe_composants().get(x).setBit_entree(0, bit + ""); //on affecte au composant regard� son bit d'entr�e						
							else
								System.out.println("[ERREUR] Ce composant (" + c1_nom + ") poss�de tous ses ports d'entr�e occup�s.");
							if(!li.getC1().getType().equals("OUT")) 
								if(!this.getListe_composants().get(x).getListeBits_entree().get(0).equals(""))
									this.getListe_composants().get(x).setBit_sortie(this.getListe_composants().get(x).getValTdv(bit));
						}	
					}	
				}
			}
			if(li.getC2().getType().equals("OUT"))
				li.getC2().setBit_entree(0, li.getC1().getBit_sortie());
		}
		for(int x = 0; x < this.getNb_composants(); x++)
			if(this.getListe_composants().get(x).getType().equals("OUT"))
				return this.getListe_composants().get(x).getListeBits_entree().get(0);
		return null;
	}
	
	public String getValTdv(int x, int y)
	{
		return this.getTdv()[y][x];
	}
	
	public void setValTdv(int x, int y, String val)
	{
		this.getTdv()[y][x] = val;
	}
	
	public void afficher_informations()
	{
		System.out.println("\n               :::::::::: INFORMATIONS SUR LE CIRCUIT CHARGE ::::::::::\n\n" 
				+ "               Ce circuit comporte : \n"
				+ "               " + this.calculer_nb_entrees() + " entr�es \n"
				+ "               " + this.getNb_composants() + " composants \n"
				+ "               " + this.getNb_liaisons() + " liaisons \n\n"
				+ "               Ses composants sont : \n");
		for(int i = 0; i < this.getNb_composants(); i++)
			System.out.println("               " + this.getListe_composants().get(i).getChaine());
		System.out.println("\n               Ses liaisons sont : \n");
		for(int i = 0; i < this.getNb_liaisons(); i++)
			System.out.println("               " + this.getListe_liaisons().get(i).getChaine());
	}
		
	//-------------------------------------------------- accesseurs et mutateurs --------------------------------------------------//

	public Courant getCourant() 
	{
		return courant;
	}

	public void setCourant(Courant courant) 
	{
		this.courant = courant;
	}

	public int getNb_composants() 
	{
		return nb_composants;
	}

	public void setNb_composants(int nb_composants) 
	{
		this.nb_composants = nb_composants;
	}
	
	public int getNb_liaisons() 
	{
		return nb_liaisons;
	}
	
	public void setNb_liaisons(int nb_liaisons)
	{
		this.nb_liaisons = nb_liaisons;
	}

	public ArrayList<Composant> getListe_composants() 
	{
		return liste_composants;
	}

	public void setListe_composants(ArrayList<Composant> liste_composants) 
	{
		this.liste_composants = liste_composants;
	}
	
	public ArrayList<Liaison> getListe_liaisons() 
	{
		return liste_liaisons;
	}

	public void setListe_liaisons(ArrayList<Liaison> liste_liaisons) 
	{
		this.liste_liaisons = liste_liaisons;
	}

	public String[][] getTdv() 
	{
		return this.tdv;
	}

	public void setTdv(String[][] tdv) 
	{
		this.tdv = tdv;
	}
}
