package GUI;

import javax.swing.*;

import Fichier_circuit.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_AfficherFichierCharge extends GUI
{	
	private JTextArea zone_texte;
	private JButton bouton_0, bouton_1;
	private String url, nom, contenu;
	
	public GUI_AfficherFichierCharge(String url, String nom, String contenu)
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		double h = initialiser_hauteur(toolkit);
		double l = initialiser_largeur(toolkit);
		
		this.setTitle("ElectronicCircuits - Affichage du fichier circuit chargé");		
		this.setHauteur((int)h);
		this.setLargeur((int)l);
		this.setSize(this.getLargeur(), this.getHauteur());
		this.initialiserLocation(toolkit);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.url = url;
		this.nom = nom;
		this.contenu = contenu;
		this.initialiser_afficher_fichier_charge(this.contenu);
		this.setVisible(true);
	}
	
	public void initialiser_afficher_fichier_charge(String contenu)
	{
		//creation du panneau principal
		JPanel panneau = new JPanel();
		panneau.setBackground(Color.BLACK);
				
		//creation du grid layout du sous panneau
		GridLayout conteneur_boutons = new GridLayout(4, 1); 
		conteneur_boutons.setVgap(30);
		conteneur_boutons.setHgap(10);
		
		//affectation du grid layout au sous panneau
		JPanel sous_panneau_1 = new JPanel(conteneur_boutons);
		sous_panneau_1.setBackground(Color.BLACK);
		
		//creation du sous-titre
		JLabel sous_titre = new JLabel("APERCU DU FICHIER CIRCUIT CHARGE");
		sous_titre.setFont(new Font(Font.DIALOG, Font.BOLD, 30));	
		sous_titre.setForeground(Color.GREEN);
		sous_titre.setHorizontalAlignment(SwingConstants.CENTER);
		
		//creation de la zone de texte
		zone_texte = new JTextArea(contenu);
		zone_texte.setBorder(null);
		zone_texte.setBackground(Color.BLACK);
		zone_texte.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		zone_texte.setForeground(Color.GREEN);
		zone_texte.setRows(5);
		zone_texte.setEditable(false);
	    JScrollPane scroll_p = new JScrollPane(zone_texte, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scroll_p.setBorder(null);
	    scroll_p.setBounds(new Rectangle(-4, 1, 397, 198));	   
				
		//creation des boutons
	    bouton_0 = new JButton("EDITER LE CONTENU CHARGE");
	    bouton_0.setBackground(Color.RED);
	    bouton_0.setBorderPainted(false);
	    bouton_0.setFocusPainted(false);
		bouton_1 = new JButton("PRODUIRE LE CIRCUIT");
		bouton_1.setBackground(Color.GREEN);
		bouton_1.setBorderPainted(false);
		bouton_1.setFocusPainted(false);
		JButton bouton_2 = new JButton("REVENIR AU MENU PRINCIPAL");
		bouton_2.setBackground(Color.ORANGE);
		bouton_2.setBorderPainted(false);
		bouton_2.setFocusPainted(false);
		
		//boutons invisibles
		JButton bouton_i0 = new JButton("");
		bouton_i0.setVisible(false);
		JButton bouton_i1 = new JButton("");
		bouton_i1.setVisible(false);
		JButton bouton_i2 = new JButton("");
		bouton_i2.setVisible(false);	
		JButton bouton_i3 = new JButton("");
		bouton_i3.setVisible(false);
				
		//creation panneau actions
		JPanel pa = new JPanel(new GridLayout(5, 1));
		pa.setBackground(Color.BLACK);
		pa.add(bouton_0);
		pa.add(bouton_i2);
		pa.add(bouton_1);
		pa.add(bouton_i3);
		pa.add(bouton_2);
		
		//ajout des boutons au panneau
		sous_panneau_1.add(bouton_i0);
		sous_panneau_1.add(sous_titre);	
		sous_panneau_1.add(scroll_p);
		sous_panneau_1.add(pa);
		
		//ajout des événements aux boutons
		bouton_0.addActionListener(new EditerContenuFichierListener());
		bouton_1.addActionListener(new ProduireCircuitListener_contenu());
		bouton_2.addActionListener(new RevenirMenuPrincipalListener());
		
		//ajout du sous panneau au panneau principal
		panneau.add(sous_panneau_1);
		
		//ajout du panneau principal à la fenetre
		this.setContentPane(panneau);	
	}
	
	class EditerContenuFichierListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			zone_texte.setBackground(Color.WHITE);
			zone_texte.setForeground(Color.BLACK);	
			zone_texte.setEditable(true);
			bouton_0.setText("TERMINER EDITION");
			bouton_0.setBackground(Color.YELLOW);
			bouton_0.removeActionListener(this);
			bouton_0.addActionListener(new TerminerEditionListener());
		}		
	}
	
	class TerminerEditionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			contenu = zone_texte.getText();
			zone_texte.setBackground(Color.BLACK);
			zone_texte.setForeground(Color.GREEN);		
			zone_texte.setEditable(false);
			bouton_0.setText("EDITER LE CONTENU MODIFIE");
			bouton_0.setBackground(Color.RED);
			bouton_0.removeActionListener(this);
			bouton_0.addActionListener(new EditerContenuFichierListener());
		}
	}
	
	class ProduireCircuitListener_contenu implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			ChargerFichier c = new ChargerFichier(url);
			c.charger_circuit_graphique(contenu);
			//appelle le manager ChargerFichier
			//recupere une reponse
			//genere boite de commande information ou erreur
			//si correcte
			dispose();
			//puis
			//ferme la fenetre et en ouvre une nouvelle avec les informations circuit et sa table de vérité		
			new GUI_AfficherCircuitProduit(c);
		}		
	}
	
}
