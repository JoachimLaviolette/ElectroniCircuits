public class Courant 
{
	private float tension; //en volts (V)
	private float intensite; //en ampères (A)
	private float puissance; //en watt (W)
	
	public Courant(float t, float i, float p) 
	{
		this.setTension(t);
		this.setIntensite(i);
		this.setPuissance(p);
	}

	public float getTension() 
	{
		return tension;
	}
	
	public void setTension(float tension) 
	{
		this.tension = tension;
	}
	
	public float getIntensite() 
	{
		return intensite;
	}
	
	public void setIntensite(float intensite) 
	{
		this.intensite = intensite;
	}
	
	public float getPuissance() 
	{
		return puissance;
	}
	
	public void setPuissance(float puissance) 
	{
		this.puissance = puissance;
	}	
}


