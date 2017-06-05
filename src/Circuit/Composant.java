package Circuit;
import java.util.ArrayList;

public class Composant 
{
	private String nom;
	private String type; //AND, OR, XOR, NOT, IN, OUT
	private float resistance;
	private ArrayList<String> liste_bits_entree;
	private int nb_entrees;
	private int nb_sorties;
	private String bit_sortie;
	private String tdv[][]; //table de verite d'un composant
	private String chaine;
	
	public Composant(String nom_c, String type_c)
	{
		this.setNom(nom_c);
		this.setType(type_c);
		initialiser_nb_entrees_sorties();
		initialiser_tdv();
	}	
	
	public void initialiser_nb_entrees_sorties()
	{
		//entrees
		if(this.getType().equals("IN"))
			this.setNb_entrees(0);
		else if(this.getType().equals("NOT") || this.getType().equals("OUT"))
			this.setNb_entrees(1);
		else
			this.setNb_entrees(2);
		//sorties (0 pour out
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
			case "OR" : 
				this.setValTdv(1,1,"0");
				this.setValTdv(2,1,"1");
				this.setValTdv(1,2,"1");
				this.setValTdv(2,2,"1");
				break;
			case "XOR" :
				this.setValTdv(1,1,"0");
				this.setValTdv(2,1,"1");
				this.setValTdv(1,2,"1");
				this.setValTdv(2,2,"0");
				break;
			case "NOT" : 
				this.setValTdv(1,1,"1");
				this.setValTdv(2,1,"0");
				break;
		}
	}
	
	public String getValTdv(int x)
	{
		return (this.getTdv()[x][1]);
	}
	
	public String getValTdv(int x, int y)
	{
		return (this.getTdv()[x][y]);
	}
	
	public void setValTdv(int x, int y, String val)
	{
		this.getTdv()[x][y] = val;
	}
	
	public String getBitEntree(int i) //premier bit : 0, deuxième bit : 1
	{
		return this.getListeBits_entree().get(i);
	}
	
	public void setBitEntree(int i, String val)
	{
		this.getListeBits_entree().set(i, val);
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
	
	public ArrayList<String> getListeBits_entree() 
	{
		return this.liste_bits_entree;
	}

	public void setListeBits_entree(ArrayList<String> bits_entree)
	{
		this.liste_bits_entree = bits_entree;
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

	public String getBit_sortie() 
	{
		return this.bit_sortie;
	}

	public void setBit_sortie(String bit_sortie) 
	{
		this.bit_sortie = bit_sortie;
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
