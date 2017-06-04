package Creation_Edition;

public class FichierCircuit
{
	private String url;
	private String contenu;
	/*
	 * EXEMPLE DE CONTENU:
	 * composant in1 IN
	 * composant in2 IN
	 * composant in3 IN
	 * composant out1 OUT
	 * composant and1 AND
	 * composant or1 OR
	 * liaison in1 1 and1 1
	 * liaison in2 1 and1 2
	 * liaison and1 1 or1 1
	 * liaison in3 1 or1 2
	 * liaison or1 1 out1
	 */
	
	public FichierCircuit(String u, String c)
	{
		this.setUrl(u);
		this.setContenu(c);
	}

	public void lire()
	{
		//TODO
	}
	
	public void editer()
	{
		//TODO
	}
	
	/*
	 * FONCTIONS A IMPLEMENTER PLUS TARD
	
		public void ajouterComposant(Composant c)
		{
			//TODO
		}
		
		public void supprimerComposant(Composant c)
		{
			//TODO
		}
		
		public void ajouterAmperemetre(Amperemetre a)
		{
			//TODO
		}
		
		public void supprimerAmperemetre(Amperemetre a)
		{
			//TODO
		}
		
		public void ajouterVoltmetre(Voltmetre v)
		{
			//TODO
		}
		
		public void supprimerVoltmetre(Voltmetre v)
		{
			//TODO
		}
	
	*/
	
	//-------------------------------------------------- accesseurs et mutateurs --------------------------------------------------//

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getContenu()
	{
		return contenu;
	}

	public void setContenu(String contenu) 
	{
		this.contenu = contenu;
	}	
}
