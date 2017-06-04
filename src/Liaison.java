public class Liaison 
{
	private Composant c1;
	private Composant c2;
	private int sortie_c1;
	private int entree_c2;
	private String chaine; //"liaison in1 1 and1 1"
	
	public Liaison(Composant c1, Composant c2, int s1, int e2)
	{
		this.setC1(c1);
		this.setC2(c2);
		this.setSortie_c1(s1);
		this.setEntree_c2(e2);
		this.setChaine(generer_chaine());
	}
	
	public String generer_chaine()
	{
		String str = new String("liaison ");
		str += this.getC1().getNom() + " " + this.getSortie_c1() + " " + this.getC2().getNom() + " " + this.getEntree_c2();
		return str;
	}
	
	//-------------------------------------------------- accesseurs et mutateurs --------------------------------------------------//
	
	public Composant getC1() 
	{
		return c1;
	}

	public void setC1(Composant c1)
	{
		this.c1 = c1;
	}

	public Composant getC2()
	{
		return c2;
	}

	public void setC2(Composant c2) 
	{
		this.c2 = c2;
	}

	public int getSortie_c1() 
	{
		return sortie_c1;
	}

	public void setSortie_c1(int sortie_c1) 
	{
		this.sortie_c1 = sortie_c1;
	}

	public int getEntree_c2()
	{
		return entree_c2;
	}

	public void setEntree_c2(int entree_c2)
	{
		this.entree_c2 = entree_c2;
	}

	public String getChaine() 
	{
		return chaine;
	}

	public void setChaine(String chaine) 
	{
		this.chaine = chaine;
	}
}
