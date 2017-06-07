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
		//System.out.println("taille --> " + this.getTdv()[0][1]);
	}
	
	public void mise_a_jour() //appelée systématiquement après édition d'un circuit
	{
		//met à jour le nombre de composants et de liaisons du circuit
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
		for(int i = 0; i < (int)Math.pow(2, nb_entrees); i++) //génère des nombres en binaire allant de 0 à 2^k entrées
		{
			//on vide les bits d'entrées et de sortie de chaque porte avant chaque nouveau calcul [IMPORTANT!]
			for(int x = 0; x < this.getNb_composants(); x++)
			{
				this.getListe_composants().get(x).setBit_sortie("");
				this.getListe_composants().get(x).getListe_bits_entree().clear();
			}
			String binaire = Integer.toBinaryString(i); //convertit le nombre en chaine binaire
			while(binaire.length() != nb_entrees) //la chaine doit être composée d'autant de bits qu'il y a d'entrées
				binaire = "0" + binaire;
			this.setValTdv(0, i+1, binaire); //0 0 0
			String valeur_de_verite_sortie = calculer_tdv_sortie(binaire);
			if(valeur_de_verite_sortie.equals("[ERREUR]"))
					System.out.println("Une erreur s'est produite durant le calcul de la valeur de vérité du circuit pour l'entrée : " + binaire);
			else
				this.setValTdv(1, i+1, valeur_de_verite_sortie); //1
		}
	}
	
	public void afficher_tdv()
	{
		this.generer_tdv();
		String tdv = new String("\n               :::::::::: TABLE DE VERITE DU CIRCUIT ::::::::::\n\n"); 
		int nb_entrees = calculer_nb_entrees();
		String[] entrees = new String[nb_entrees];
		this.remplir_noms_entrees(entrees);
		tdv += "               ";
		for(int i = 0; i < nb_entrees; i++)
			tdv += entrees[i] + "    ";
		tdv += " |      Sortie\n";	
		for(int i = 0; i < (int)Math.pow(2, nb_entrees); i++) //génère des nombres en binaire allant de 0 à 2^k entrées
		{
			tdv += "                ";
			for(int a = 0; a < this.getValTdv(0, i+1).length(); a++) //parcourt la chaine binaire du nombre , ex : 01011 pour générer : "0 1 0 1 1 "
				tdv += this.getValTdv(0, i+1).charAt(a) + "      ";	//ajoute chaque caractère de la chaine binaire avec un espace entre eux
			tdv += "|        " + this.getValTdv(1, i+1) + "\n"; //affiche la valeur de vérité au nombre binaire (chaine) 
		}
		System.out.println(tdv);
	}
	
	public String calculer_tdv_sortie(String binaire)
	{
		for(int m = 0; m < binaire.length(); m++)
			for(int x = 0; x < this.getNb_composants(); x++)
				if(this.getListe_composants().get(x).getNom().contains("in" + (m + 1)))
				{
					this.getListe_composants().get(x).setBit_sortie(binaire.charAt(m) + ""); //on affecte à la porte IN regardé son bit de sortie
					this.getListe_composants().get(x).getC_successeur().getListe_bits_entree().add(binaire.charAt(m) + ""); //on ajoute à la liste de bits d'entrée du successeur la sortie de l'entrée 
				}
		for(int i = 0; i < this.getNb_composants(); i++)
		{
			if(!this.getListe_composants().get(i).getType().equals("IN") && !this.getListe_composants().get(i).getType().equals("OUT"))
			{
				if(this.getListe_composants().get(i).getType().equals("AND") || this.getListe_composants().get(i).getType().equals("OR") || this.getListe_composants().get(i).getType().equals("XOR"))
				{
					if(this.getListe_composants().get(i).getListe_bits_entree().size() > this.getListe_composants().get(i).getNb_entrees())
						System.out.println("[ERREUR] Composant [AND/OR/XOR] à deux entrées. Nombre d'entrées différent de 2 !");
					else
					{
						int bit_entree_1 = Integer.parseInt(this.getListe_composants().get(i).getBitEntree(0));
						int bit_entree_2 = Integer.parseInt(this.getListe_composants().get(i).getBitEntree(1));
						String valeur_de_verite = this.getListe_composants().get(i).getValTdv(bit_entree_1 + 1, bit_entree_2 + 1);
						this.getListe_composants().get(i).setBit_sortie(valeur_de_verite);
						String bit_sortie = this.getListe_composants().get(i).getBit_sortie();
						this.getListe_composants().get(i).getC_successeur().getListe_bits_entree().add(bit_sortie);
					}					
				}
				else if(this.getListe_composants().get(i).getType().equals("NOT"))
				{
					if(this.getListe_composants().get(i).getListe_bits_entree().size() > this.getListe_composants().get(i).getNb_entrees())
						System.out.println("[ERREUR] Composant [NOT] à une entrée. Nombre d'entrées différent de 1 !");
					else
					{
						int bit_entree_1 = Integer.parseInt(this.getListe_composants().get(i).getBitEntree(0));
						String valeur_de_verite = this.getListe_composants().get(i).getValTdv(bit_entree_1 + 1, 1);
						this.getListe_composants().get(i).setBit_sortie(valeur_de_verite);
						String bit_sortie = this.getListe_composants().get(i).getBit_sortie();
						this.getListe_composants().get(i).getC_successeur().getListe_bits_entree().add(bit_sortie);
					}
				}
				if(this.getListe_composants().get(i).getC_successeur().getType().equals("OUT"))
				{
					if(this.getListe_composants().get(i).getC_successeur().getListe_bits_entree().size() > this.getListe_composants().get(i).getC_successeur().getNb_entrees())
						System.out.println("[ERREUR] Composant [OUT] à une entrée. Nombre d'entrées différent de 1 !");
					else
						return this.getListe_composants().get(i).getC_successeur().getBitEntree(0);				
				}
			}
		}		
		return "[ERREUR]";
		/*
			for(int i = 0; i < this.getNb_liaisons(); i++)
			{
				Liaison li = this.getListe_liaisons().get(i);
				String c1_nom = li.getC1().getNom();
				if(li.getC1().getType().equals("AND") || li.getC1().getType().equals("OR") || li.getC1().getType().equals("XOR")) //2 entrées
				{
					String c_pred_bit0_s = "";
					String c_pred_nom0 = "";
					String c_pred_bit1_s = "";
					String c_pred_nom1 = "";
					int bit_0 = 0; //bit entrée 1
					int bit_1 = 0; //bit entrée 2
					int a = 0;
					for(int j = 0; j < this.getNb_liaisons(); j++)
					{
						if(this.getListe_liaisons().get(j).getC2().getNom().equals(c1_nom) && a == 0)
						{
							c_pred_nom0 = this.getListe_liaisons().get(j).getC1().getNom();
							for(int x = 0; x < this.getNb_composants(); x++)
								if(this.getListe_composants().get(x).getNom().equals(c_pred_nom0))
									c_pred_bit0_s = this.getListe_composants().get(x).getBit_sortie();
							bit_0 = Integer.parseInt(c_pred_bit0_s);
							a++;
						}					
						else if(this.getListe_liaisons().get(j).getC2().getNom().equals(c1_nom) && a == 1)
						{
							c_pred_nom1 = this.getListe_liaisons().get(j).getC1().getNom();
							for(int x = 0; x < this.getNb_composants(); x++)
								if(this.getListe_composants().get(x).getNom().equals(c_pred_nom1))
									c_pred_bit1_s = this.getListe_composants().get(x).getBit_sortie();
							bit_1 = Integer.parseInt(c_pred_bit1_s);
							a++;
						}
					}				
					for(int x = 0; x < this.getNb_composants(); x++)
					{
						if(this.getListe_composants().get(x).getNom().equals(c1_nom))
						{
							if(this.getListe_composants().get(x).getListe_bits_entree().get(0).equals(""))
								this.getListe_composants().get(x).setBit_entree(0, bit_0 + ""); //on affecte au composant regardé son bit d'entrée (0)
							else if(this.getListe_composants().get(x).getListe_bits_entree().get(1).equals(""))
								this.getListe_composants().get(x).setBit_entree(1, bit_1 + ""); //on affecte au composant regardé son bit d'entrée (1)
							else
								System.out.println("\n1) [ERREUR] Ce composant (" + c1_nom + ") possède tous ses ports d'entrée occupés.");
							if(!this.getListe_composants().get(x).getListe_bits_entree().get(0).equals("") && !this.getListe_composants().get(x).getListe_bits_entree().get(1).equals(""))
								this.getListe_composants().get(x).setBit_sortie(this.getListe_composants().get(x).getValTdv(bit_0, bit_1));
						}	
					}
				}
				else //NOT et OUT
				{
					if(!li.getC1().getType().equals("IN")) 
					{
						String c_pred_bit_s = ""; //bit entrée
						String c_pred_nom = "";
						int bit = 0; //bit entrée
						int a = 0;
						for(int j = 0; j < this.getNb_liaisons(); j++)
						{
							if(this.getListe_liaisons().get(j).getC2().getNom().equals(c1_nom) && a == 0)
							{
								c_pred_nom = this.getListe_liaisons().get(j).getC1().getNom();
								for(int x = 0; x < this.getNb_composants(); x++)
									if(this.getListe_composants().get(x).getNom().equals(c_pred_nom))
										c_pred_bit_s = this.getListe_composants().get(x).getBit_sortie();
								bit = Integer.parseInt(c_pred_bit_s);
								a++;
							}
						}				
						for(int x = 0; x < this.getNb_composants(); x++)
						{
							if(this.getListe_composants().get(x).getNom().equals(c1_nom))
							{
								if(this.getListe_composants().get(x).getListe_bits_entree().get(0).equals(""))
									this.getListe_composants().get(x).setBit_entree(0, bit + ""); //on affecte au composant regardé son bit d'entrée						
								else
									System.out.println("2) [ERREUR] Ce composant (" + c1_nom + ") possède tous ses ports d'entrée occupés.");
								if(!li.getC1().getType().equals("OUT")) 
									if(!this.getListe_composants().get(x).getListe_bits_entree().get(0).equals(""))
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
					return this.getListe_composants().get(x).getListe_bits_entree().get(0);
			return null;
		*/
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
				+ "               Ce circuit comporte : \n\n"
				+ "               " + this.calculer_nb_entrees() + " entrées \n"
				+ "               " + this.getNb_composants() + " composants \n"
				+ "               " + this.getNb_liaisons() + " liaisons \n\n"
				+ this.afficher_informations_composants()
				+ "               Ses composants sont : \n");
		for(int i = 0; i < this.getNb_composants(); i++)
			System.out.println("               " + this.getListe_composants().get(i).getChaine());
		System.out.println("\n               Ses liaisons sont : \n");
		for(int i = 0; i < this.getNb_liaisons(); i++)
			System.out.println("               " + this.getListe_liaisons().get(i).getChaine());
	}
	
	public String afficher_informations_composants()
	{
		String str = "               Les informations concernant chaque composant sont les suivantes : \n\n";
		for(int i = 0; i < this.getNb_composants(); i++)
		{
			str += "               Le composant : " + this.getListe_composants().get(i).getNom() + " a pour ";
			if(this.getListe_composants().get(i).getListe_c_predecesseurs().size() > 0)
			{
				str += "prédecesseur(s) : ";
				for(int a = 0; a < this.getListe_composants().get(i).getListe_c_predecesseurs().size(); a++)
				{	
					if(a != 0)
						str += ", ";
					str += this.getListe_composants().get(i).getListe_c_predecesseurs().get(a).getNom();
				}
				if(!this.getListe_composants().get(i).getType().equals("OUT"))
					str += " et pour ";
			}
			if(!this.getListe_composants().get(i).getType().equals("OUT"))
			{
				System.out.println("succ " + this.getListe_composants().get(i).getNom());
				str += "successeur : " + this.getListe_composants().get(i).getC_successeur().getNom(); 
			}
			str += "\n";
		}
		str += "\n";
		return str;
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
