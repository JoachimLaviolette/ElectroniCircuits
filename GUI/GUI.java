package GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame
{
	private int hauteur;
	private int largeur;
	
	public GUI()
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		double h = initialiser_hauteur(toolkit);
		double l = initialiser_largeur(toolkit);
		
		this.setTitle("ElectronicCircuits");		
		this.setHauteur((int)h);
		this.setLargeur((int)l);
		this.setSize(this.getLargeur(), this.getHauteur());
		this.initialiserLocation(toolkit);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initialiser_menu();
		this.setVisible(true);
	}
	
	public void initialiser_menu()
	{
		//creation du panneau principal
		JPanel panneau = new JPanel();
		panneau.setBackground(Color.BLACK);
				
		//creation du grid layout du sous panneau
		GridLayout conteneur_boutons = new GridLayout(8, 1); 
		conteneur_boutons.setVgap(30);
		conteneur_boutons.setHgap(10);
		
		//affectation du grid layout au sous panneau
		JPanel sous_panneau_1 = new JPanel(conteneur_boutons);
		sous_panneau_1.setBackground(Color.BLACK);
		
		//création du logo et du sous-titre
		JLabel logo = new JLabel("ElectroniCircuits");
		logo.setFont(new Font(Font.DIALOG, Font.BOLD, 35));	
		logo.setForeground(Color.GREEN);
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel sous_titre = new JLabel("Logiciel de simulation de circuits électroniques");
		sous_titre.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));	
		sous_titre.setForeground(Color.GREEN);
		
		//creation des boutons
		JButton bouton_1 = new JButton("CREER UN FICHIER CIRCUIT");
		bouton_1.setBackground(Color.WHITE);
		bouton_1.setBorderPainted(false);
		bouton_1.setFocusPainted(false);
		JButton bouton_2 = new JButton("CHARGER UN CIRCUIT DEPUIS UN FICHIER");
		bouton_2.setBackground(Color.WHITE);
		bouton_2.setBorderPainted(false);
		bouton_2.setFocusPainted(false);
		JButton bouton_3 = new JButton("EDITER UN FICHIER CIRCUIT");
		bouton_3.setBackground(Color.WHITE);
		bouton_3.setBorderPainted(false);
		bouton_3.setFocusPainted(false);
		JButton bouton_4 = new JButton("QUITTER");
		bouton_4.setBackground(Color.WHITE);
		bouton_4.setBorderPainted(false);
		bouton_4.setFocusPainted(false);
		
		//modification des tailles des boutons
		/*bouton_1.setSize(new Dimension(30, 5));
		bouton_2.setSize(new Dimension(30, 10));
		bouton_3.setSize(new Dimension(30, 10));
		bouton_4.setSize(new Dimension(30, 10));
		bouton_5.setSize(new Dimension(30, 10));*/
		
		//boutons invisibles
		JButton bouton_i1 = new JButton("");
		bouton_i1.setVisible(false);
		JButton bouton_i2 = new JButton("");
		bouton_i2.setVisible(false);	
				
		//ajout des boutons au panneau
		sous_panneau_1.add(bouton_i1);
		sous_panneau_1.add(logo);	
		sous_panneau_1.add(sous_titre);
		sous_panneau_1.add(bouton_i2);
		sous_panneau_1.add(bouton_1);
		sous_panneau_1.add(bouton_2);
		sous_panneau_1.add(bouton_3);
		sous_panneau_1.add(bouton_4);
		
		//ajout des événements aux boutons
		bouton_1.addActionListener(new CreerFichierListener());
		bouton_2.addActionListener(new ChargerFichierListener());
		bouton_3.addActionListener(new EditerFichierListener());
		bouton_4.addActionListener(new QuitterApplicationListener());
		
		//ajout du sous panneau au panneau principal
		panneau.add(sous_panneau_1);
		
		//ajout du panneau principal à la fenetre
		this.setContentPane(panneau);	
	}
	
	class CreerFichierListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			dispose();
			new GUI_CreerFichier();
		}		
	}
	
	class ChargerFichierListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			dispose();
			new GUI_ChargerFichier();
		}		
	}
	
	class EditerFichierListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			dispose();
			new GUI_EditerFichier();
		}
	}
	
	class QuitterApplicationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			System.exit(0);
		}
	}
	
	//pour les classes qui descendent de GUI
	class RevenirMenuPrincipalListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			dispose();
			new GUI();
		}
	}
	
	public double initialiser_hauteur(Toolkit toolkit)
	{
		double h = toolkit.getScreenSize().getHeight();
		/*double h2 = toolkit.getScreenSize().getHeight()/4;	
		h = h - h2;
		System.out.println("Hauteur GUI : " + h);*/
		return h;
	}
	
	public double initialiser_largeur(Toolkit toolkit)
	{
		double l = toolkit.getScreenSize().getWidth();
		/*double l2 = toolkit.getScreenSize().getWidth()/4;	
		l = l - l2;
		System.out.println("Largeur GUI : " + l);*/
		return l;
	}
	
	public void initialiserLocation(Toolkit toolkit)
	{
		this.pack();
		this.setExtendedState(this.MAXIMIZED_BOTH);
		//this.setLocation((int)toolkit.getScreenSize().getWidth()/8, (int)toolkit.getScreenSize().getHeight()/8);		
	}
	
	//-------------------------------------------------- accesseurs et mutateurs --------------------------------------------------//

	public int getHauteur()
	{
		return this.hauteur;
	}

	public void setHauteur(int hauteur) 
	{
		this.hauteur = hauteur;
	}

	public int getLargeur()
	{
		return this.largeur;
	}

	public void setLargeur(int largeur) 
	{
		this.largeur = largeur;
	}
}
