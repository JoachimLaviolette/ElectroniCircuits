package Circuit;
import java.util.ArrayList;

public class Composant 
{
	private String nom;
	private String type; //AND, OR, XOR, NOT, IN, OUT        Nouveaux composants : NOR, NAND, XNOR
	private float resistance;
	private ArrayList<String> liste_bits_entree;
	private String bit_sortie;
	private int nb_entrees;
	private int nb_sorties;
	private ArrayList<Composant> liste_c_predecesseurs;
	private Composant c_successeur;
	private String tdv[][]; //table de verite d'un composant
	private String chaine;
	
	public Composant(String nom_c, String type_c)
	{
		this.setNom(nom_c);
		this.setType(type_c);
		this.setListe_bits_entree(new ArrayList<String>());
		this.initialiser_nb_entrees_sorties();	
		this.setListe_c_predecesseurs(new ArrayList<Composant>());
		this.initialiser_tdv();
	}	
	
	public void initialiser_nb_entrees_sorties()
	{
		//entrees (0 pour in)
		if(this.getType().equals("IN"))
			this.setNb_entrees(0);
		else if(this.getType().equals("NOT") || this.getType().equals("OUT"))
			this.setNb_entrees(1);
		else
			this.setNb_entrees(2);
		//sorties (0 pour out)
		if(this.getType().equals("OUT"))
			this.setNb_sorties(0);
		else
			this.setNb_sorties(1);
	}
	
	public void initialiser_tdv()
	{
		String[][] tdv = new String[3][3];
		this.setTdv(tdv);
		//en-têtes de la tdv
		this.setValTdv(0,0,this.getType());
		if(!this.getType().equals("NOT") && !this.getType().equals("IN") && !this.getType().equals("OUT"))
		{
			this.setValTdv(0,1,"0");
			this.setValTdv(0,2,"1");
			this.setValTdv(1,0,"0");
			this.setValTdv(2,0,"1");
		}
		else if(this.getType().equals("NOT"))
		{
			this.setValTdv(0,1," ");
			this.setValTdv(1,0,"0");
			this.setValTdv(2,0,"1");
		}
		//valeurs de la tdv
		switch(this.getType())
		{
			case "AND" : 
				this.setValTdv(1,1,"0"); //0 0
				this.setValTdv(2,1,"0"); //0 1
				this.setValTdv(1,2,"0"); //1 0
				this.setValTdv(2,2,"1"); //1 1
				break;
			case "NAND" :
				this.setValTdv(1,1,"1");
				this.setValTdv(2,1,"0");
				this.setValTdv(1,2,"0");
				this.setValTdv(2,2,"0");
			case "OR" : 
				this.setValTdv(1,1,"0");
				this.setValTdv(2,1,"1");
				this.setValTdv(1,2,"1");
				this.setValTdv(2,2,"1");
				break;
			case "NOR" :
				this.setValTdv(1,1,"1");
				this.setValTdv(2,1,"0");
				this.setValTdv(1,2,"0");
				this.setValTdv(2,2,"0");
			case "XOR" :
				this.setValTdv(1,1,"0");
				this.setValTdv(2,1,"1");
				this.setValTdv(1,2,"1");
				this.setValTdv(2,2,"0");
				break;
			case "XNOR" :
				this.setValTdv(1,1,"1");
				this.setValTdv(2,1,"0");
				this.setValTdv(1,2,"0");
				this.setValTdv(2,2,"1");
			case "NOT" : 
				this.setValTdv(1,1,"1");
				this.setValTdv(2,1,"0");
				break;
		}
	}
	
	public String getValTdv(int x)
	{
		return (this.getTdv()[1][x]);
	}
	
	public String getValTdv(int x, int y)
	{
		return (this.getTdv()[y][x]);
	}
	
	public void setValTdv(int x, int y, String val)
	{
		this.getTdv()[y][x] = val;
	}
	
	public String getBitEntree(int i) //premier bit : 0, deuxième bit : 1
	{
		return this.getListe_bits_entree().get(i);
	}
	
	public void generer_chaine()
	{
		String str = this.getType() + " - " + this.getNom();
		this.setChaine(str);
	}
	
	//-------------------------------------------------- accesseurs et mutateurs --------------------------------------------------//
	
	public String getNom() 
	{
		return this.nom;
	}

	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	public String getType() 
	{
		return this.type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}

	public float getResistance() 
	{
		return this.resistance;
	}

	public void setResistance(float resistance) 
	{
		this.resistance = resistance;
	}
	
	public ArrayList<String> getListe_bits_entree() 
	{
		return this.liste_bits_entree;
	}

	public void setListe_bits_entree(ArrayList<String> liste_bits_entree) 
	{
		this.liste_bits_entree = liste_bits_entree;
	}

	public String getBit_sortie() 
	{
		return this.bit_sortie;
	}

	public void setBit_sortie(String bit_sortie) 
	{
		this.bit_sortie = bit_sortie;
	}

	public int getNb_entrees()
	{
		return this.nb_entrees;
	}

	public void setNb_entrees(int nb_entrees)
	{
		this.nb_entrees = nb_entrees;
	}
	
	public int getNb_sorties()
	{
		return this.nb_sorties;
	}

	public void setNb_sorties(int nb_sorties)
	{
		this.nb_sorties = nb_sorties;
	}
	
	public ArrayList<Composant> getListe_c_predecesseurs() 
	{
		return this.liste_c_predecesseurs;
	}

	public void setListe_c_predecesseurs(ArrayList<Composant> liste_c_predecesseurs) 
	{
		this.liste_c_predecesseurs = liste_c_predecesseurs;
	}

	public Composant getC_successeur()
	{
		return this.c_successeur;
	}

	public void setC_successeur(Composant c_successeur)
	{
		this.c_successeur = c_successeur;
	}

	public String[][] getTdv() 
	{
		return this.tdv;
	}

	public void setTdv(String[][] tdv) 
	{
		this.tdv = tdv;
	}
	
	public String getChaine() 
	{
		this.generer_chaine();
		return this.chaine;
	}

	public void setChaine(String chaine) 
	{
		this.chaine = chaine;
	}
}
