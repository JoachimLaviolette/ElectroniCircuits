package GUI;

import Circuit.*;
import Fichier_circuit.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_CreerFichier extends GUI
{	
	private JTextField champ_saisie_url;
	private JTextField champ_saisie_nom_fichier;
	private JTextArea zone_texte;
	
	public GUI_CreerFichier()
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		double h = initialiser_hauteur(toolkit);
		double l = initialiser_largeur(toolkit);
		
		this.setTitle("ElectronicCircuits - Créer un fichier circuit");		
		this.setHauteur((int)h);
		this.setLargeur((int)l);
		this.setSize(this.getLargeur(), this.getHauteur());
		this.initialiserLocation(toolkit);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initialiser_creer_fichier();
		this.setVisible(true);
	}
	
	public void initialiser_creer_fichier()
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
		JLabel sous_titre = new JLabel("CREER UN FICHIER CIRCUIT");
		sous_titre.setFont(new Font(Font.DIALOG, Font.BOLD, 30));	
		sous_titre.setForeground(Color.GREEN);
		sous_titre.setHorizontalAlignment(SwingConstants.CENTER);
		
		//creation de la zone de texte
		zone_texte = new JTextArea();
		zone_texte.setBorder(null);
		zone_texte.setBackground(Color.WHITE);
		zone_texte.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		zone_texte.setRows(5);
	    JScrollPane scroll_p = new JScrollPane(zone_texte, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scroll_p.setBorder(null);
	    scroll_p.setBounds(new Rectangle(-4, 1, 397, 198));
		
		//creation du label et du champ de saisie pour le chemin du fichier a enregistrer et du paneau qui les contient
		JLabel label_champ_saisie_url = new JLabel("Chemin du dossier de sauvegarde   ");
		label_champ_saisie_url.setForeground(Color.GREEN);
		champ_saisie_url = new JTextField();
		champ_saisie_url.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		champ_saisie_url.setForeground(Color.WHITE);
		champ_saisie_url.setBackground(Color.WHITE);
		champ_saisie_url.setOpaque(false);
		champ_saisie_url.setHorizontalAlignment(SwingConstants.CENTER);
		
		//creation du label et du champ de saisie pour le chemin du fichier a enregistrer et du paneau qui les contient
		JLabel label_champ_saisie_nom_fichier = new JLabel("                       Nom du fichier circuit");
		label_champ_saisie_nom_fichier.setForeground(Color.GREEN);
		champ_saisie_nom_fichier = new JTextField();
		champ_saisie_nom_fichier.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		champ_saisie_nom_fichier.setForeground(Color.WHITE);
		champ_saisie_nom_fichier.setBackground(Color.WHITE);
		champ_saisie_nom_fichier.setOpaque(false);
		champ_saisie_nom_fichier.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel ps_items = new JPanel();
		ps_items.setBackground(Color.BLACK);
		ps_items.setLayout(new GridLayout(1, 2));
		ps_items.add(label_champ_saisie_url);
		ps_items.add(champ_saisie_url);		
		ps_items.add(label_champ_saisie_nom_fichier);
		ps_items.add(champ_saisie_nom_fichier);		
				
		//creation des boutons
		JButton bouton_1 = new JButton("SAUVEGARDER LE FICHIER CIRCUIT");
		bouton_1.setBackground(Color.GREEN);
		bouton_1.setBorderPainted(false);
		bouton_1.setFocusPainted(false);
		JButton bouton_2 = new JButton("REVENIR AU MENU PRINCIPAL");
		bouton_2.setBackground(Color.ORANGE);
		bouton_2.setBorderPainted(false);
		bouton_2.setFocusPainted(false);
		
		//boutons invisibles
		JButton bouton_i1 = new JButton("");
		bouton_i1.setVisible(false);
		JButton bouton_i2 = new JButton("");
		bouton_i2.setVisible(false);	
		JButton bouton_i3 = new JButton("");
		bouton_i3.setVisible(false);
				
		//creation panneau actions
		JPanel pa = new JPanel(new GridLayout(5, 1));
		pa.setBackground(Color.BLACK);
		pa.add(ps_items);
		pa.add(bouton_i2);
		pa.add(bouton_1);
		pa.add(bouton_i3);
		pa.add(bouton_2);
		
		//ajout des boutons au panneau
		sous_panneau_1.add(bouton_i1);
		sous_panneau_1.add(sous_titre);	
		sous_panneau_1.add(scroll_p);
		sous_panneau_1.add(pa);
		
		//ajout des événements aux boutons
		bouton_1.addActionListener(new SauvegarderFichierListener());
		bouton_2.addActionListener(new RevenirMenuPrincipalListener());
		
		//ajout du sous panneau au panneau principal
		panneau.add(sous_panneau_1);
		
		//ajout du panneau principal à la fenetre
		this.setContentPane(panneau);	
	}	
	
	class SauvegarderFichierListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			String url = champ_saisie_url.getText();
			String nom = champ_saisie_nom_fichier.getText();
			String contenu = zone_texte.getText();
			if(!url.equals("") && url != null && !nom.equals("") && nom != null)
			{
				if(nom.length() > 3)
				{
					String ext = nom.substring(nom.length() - 4);
					if(ext.equals(".txt"))
						nom = nom.substring(0, nom.indexOf("."));
				}
				while(url.substring(url.length() - 1).equals("/"))
					url = url.substring(0, url.length() - 1);
				SauvegarderFichier s = new SauvegarderFichier(url, nom, contenu);
				JOptionPane erreur = new JOptionPane();
				erreur.showMessageDialog(null, "Votre fichier circuit a bien été sauvegardé !", "Succès de sauvegarde", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				new GUI();
			}
			else
			{
				JOptionPane erreur = new JOptionPane();
				erreur.showMessageDialog(null, "Votre fichier n'a pas pu être sauvegardé. Vérifez le nom et le chemin du dossier de sauvegarde !", "Erreur de sauvegarde", JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
}
