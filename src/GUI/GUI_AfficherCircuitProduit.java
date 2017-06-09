package GUI;

import Fichier_circuit.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class GUI_AfficherCircuitProduit extends GUI
{	
	public GUI_AfficherCircuitProduit(ChargerFichier c)
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
		this.initialiser_afficher_circuit_produit(c);
		this.setVisible(true);
	}
	
	public void initialiser_afficher_circuit_produit(ChargerFichier c)
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
		JLabel sous_titre = new JLabel("INFORMATIONS DU CIRCUIT PRODUIT        ");
		sous_titre.setFont(new Font(Font.DIALOG, Font.BOLD, 30));	
		sous_titre.setForeground(Color.GREEN);
		sous_titre.setHorizontalAlignment(SwingConstants.CENTER);
		
		//creation de la zone de texte
		JTextArea zone_texte = new JTextArea(c.recuperer_resultats());
		zone_texte.setBorder(null);
		zone_texte.setBackground(Color.BLACK);
		zone_texte.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		zone_texte.setForeground(Color.GREEN);
		zone_texte.setColumns(20);
		zone_texte.setRows(15);
		zone_texte.setEditable(false);
		JScrollPane scroll_p = new JScrollPane(zone_texte, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll_p.setBorder(null);
		scroll_p.setBounds(new Rectangle(-4, 1, 397, 198));
		
		JPanel panneau_infos = new JPanel();
		panneau_infos.setLayout(new GridLayout(1, 1));
		panneau_infos.add(scroll_p);
				
		//creation des boutons
		JButton bouton_1 = new JButton("REVENIR AU MENU PRINCIPAL");
		bouton_1.setBackground(Color.ORANGE);
		bouton_1.setBorderPainted(false);
		bouton_1.setFocusPainted(false);
		
		//boutons invisibles
		JButton bouton_i0 = new JButton("");
		bouton_i0.setVisible(false);
		JButton bouton_i1 = new JButton("");
		bouton_i1.setVisible(false);
		JButton bouton_i2 = new JButton("");
		bouton_i2.setVisible(false);
		JButton bouton_i3 = new JButton("");
		bouton_i3.setVisible(false);	
		JButton bouton_i4 = new JButton("");
		bouton_i4.setVisible(false);	
		JButton bouton_i5 = new JButton("");
		bouton_i5.setVisible(false);	
				
		//creation panneau actions
		JPanel pa = new JPanel(new GridLayout(4, 1));
		pa.setBackground(Color.BLACK);
		pa.add(bouton_i0);
		pa.add(bouton_1);	
		pa.add(bouton_i1);
		pa.add(bouton_i2);	
		
		//ajout des boutons au panneau
		sous_panneau_1.add(sous_titre);	
		sous_panneau_1.add(panneau_infos);
		sous_panneau_1.add(pa);
		sous_panneau_1.add(bouton_i3);
		
		//ajout des événements aux boutons
		bouton_1.addActionListener(new RevenirMenuPrincipalListener());
		
		//ajout du sous panneau au panneau principal
		panneau.add(sous_panneau_1);
		
		//ajout du panneau principal à la fenetre
		this.setContentPane(panneau);	
	}	
}
