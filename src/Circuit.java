import java.util.ArrayList;

public class Circuit 
{
	private Courant courant;
	private int nb_composants;
	private int nb_liaisons;
	private ArrayList<Composant> liste_composants;
	private ArrayList<Liaison> liste_liaisons;
	private String[][] tdv; //table de verite d'un circuit
	
	public Circuit(Courant courant, ArrayList<Composant> liste_composants, ArrayList<Liaison> liste_liaisons, String[][] tdv)
	{
		this.setCourant(courant);
		this.setNb_composants(calculer_nb_composants());
		this.setNb_liaisons(calculer_nb_liaisons());
		this.setListe_composants(liste_composants);
		this.setListe_liaisons(liste_liaisons);
		this.setTdv(tdv);
	}
	
	public void update()
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
	
	public int calculer_nb_entrees(String[] entrees)
	{
		int a = 0;
		int somme = 0;
		for(int i = 0; i < this.getListe_composants().size(); i++)
			if(this.getListe_composants().get(i).getType().equals("IN"))
			{
				somme++;
				entrees[a] = this.getListe_composants().get(i).getNom(); //enregistre le nom de chaque composant IN
				a++;
			}
		return somme;
	}
		
	public void generer_tdv()
	{
		String[] entrees = null;
		int nb_entrees = calculer_nb_entrees(entrees);
		for(int i = 0; i < (int)Math.pow(2,nb_entrees); i++) //génère des nombres en binaire allant de 0 à 2^k entrées
		{
			String binaire = Integer.toBinaryString(i); //convertit le nombre en binaire
			this.getTdv()[i+1][0] = binaire;
			this.getTdv()[i+1][1] = calculer_tdv_sortie(binaire);
		}
	}
	
	public void afficher_tdv()
	{
		String[] entrees = {};
		String tdv = new String("  ");
		int nb_entrees = calculer_nb_entrees(entrees); //passe en paramètre le tableau "entrees" qui va etre rempli avec le nom de chaque composant IN
		for(int i = 0; i < nb_entrees; i++)
			tdv += entrees[i] + " ";
		tdv += "| Sortie\n";	
		for(int i = 0; i < (int)Math.pow(2,nb_entrees); i++) //génère des nombres en binaire allant de 0 à 2^k entrées
		{
			String binaire = Integer.toBinaryString(i); //convertit le nombre en binaire
			for(int a = 0; a < binaire.length(); a++) //parcourt la chaine binaire
				tdv += binaire.charAt(a) + " ";	//ajoute chaque caractère de la chaine binaire
			tdv += "| " + this.getTdv()[i+1][1] + "\n"; //afficher la ligne de la tdv correspondant à la chaine binaire (nombre)
		}
		System.out.println(tdv);
	}
	
	public String calculer_tdv_sortie(String binaire)
	{
		String str = new String("");
		for(int m = 0; m < binaire.length(); m++)
			this.getListe_composants().get(this.getListe_composants().indexOf("in" + (m + 1))).setBit_sortie(binaire.charAt(m) + ""); //on affecte à chaque porte IN son bit de sortie
		for(int i = 0; i < this.getNb_liaisons(); i++)
		{
			Liaison li = this.getListe_liaisons().get(i);
			String li_nom = li.getC1().getNom();
			if(li.getC1().getType().equals("AND") || li.getC1().getType().equals("OR") || li.getC1().getType().equals("XOR")) 
			{
				int bit_0 = 0;
				int bit_1 = 0;
				int a = 0;
				for(int j = 0; j < this.getNb_liaisons(); j++)
				{
					if(this.getListe_liaisons().get(j).getC2().getNom().equals(li_nom) && a == 0)
					{
						bit_0 = Integer.parseInt(this.getListe_liaisons().get(j).getC1().getBit_sortie());
						a++;
					}					
					else if(this.getListe_liaisons().get(j).getC2().getNom().equals(li_nom) && a == 1)
						bit_1 = Integer.parseInt(this.getListe_liaisons().get(j).getC1().getBit_sortie());					
				}
				str = li.getC1().getValTdv(bit_0, bit_1);
			}
			else if(li.getC1().getType().equals("NOT"))
			{
				int bit = 0;
				for(int j = 0; j < this.getNb_liaisons(); j++)
					if(this.getListe_liaisons().get(j).getC2().getNom().equals(li_nom))
						bit = Integer.parseInt(this.getListe_liaisons().get(j).getC1().getBit_sortie());
				str = li.getC1().getValTdv(bit);
			}
			li.getC1().setBit_sortie(str);
		}
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
		return tdv;
	}

	public void setTdv(String[][] tdv) 
	{
		this.tdv = tdv;
	}
}
