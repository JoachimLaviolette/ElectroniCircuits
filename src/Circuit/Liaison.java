package Circuit;
public class Liaison 
{
	private Composant c1;
	private Composant c2;
	private String sortie_c1;
	private String entree_c2;
	private String chaine; //"liaison in1 1 and1 1"
	
	public Liaison(Composant c1, Composant c2, String s1, String e2)
	{
		this.setC1(c1);
		this.setC2(c2);
		this.setSortie_c1(s1);
		this.setEntree_c2(e2);
	}
	
	public void generer_chaine()
	{
		String str = this.getC1().getType() + " - " + this.getC1().getNom() + " - " + this.getSortie_c1() + " --> " + this.getC2().getType() + " - " + this.getC2().getNom();
		if(!this.getC2().getType().equals("OUT"))
				str += " - " + this.getEntree_c2();
		this.setChaine(str);
	}
	
	//-------------------------------------------------- accesseurs et mutateurs --------------------------------------------------//
	
	public Composant getC1() 
	{
		return this.c1;
	}

	public void setC1(Composant c1)
	{
		this.c1 = c1;
	}

	public Composant getC2()
	{
		return this.c2;
	}

	public void setC2(Composant c2) 
	{
		this.c2 = c2;
	}

	public String getSortie_c1() 
	{
		return this.sortie_c1;
	}

	public void setSortie_c1(String sortie_c1) 
	{
		this.sortie_c1 = sortie_c1;
	}

	public String getEntree_c2()
	{
		return this.entree_c2;
	}

	public void setEntree_c2(String entree_c2)
	{
		this.entree_c2 = entree_c2;
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
