
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author i.magagi
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
Création d'un écran de menu principal.
Objectif :
- Proposer un menu en mode liste déroulante. OK
- La séléction d'un choix de ce menu devrait se faire valider par un bouton OK. OK
- Un choix doit nous renvoyer vers d'autres écrans : médicaments, vendeurs et médecins. OK
- Par défaut, la fermeture de la fenêtre devrait fermer le programme. OK
- Idéalement, je voudrais proposer un bouton exit qui ferme le programme (et ferme l'écran par ricochet). OK
- L'écran principal se ferme lorqu'on choisit une action. OK

*/

class Menu extends JFrame implements ItemListener 
{

    // Déclare JFrame 
    static JFrame frame;
    
    // Déclare une zone de texte associée à une liste 
    static JComboBox combobox;
    
    // Déclare label 
    static JLabel l1;


    public static void main(String[] args)
    {
        // Créer un nouveau frame
        
        //Titre de la JFrame créée
        frame = new JFrame("Fenêtre d'accueil");
        
        //Si besoin de mettre une image en fond.
        frame.setContentPane(new JLabel(new ImageIcon("F:\\Dossier personnel de Ivan-Corneille\\Etudes\\SID\\Java\\TestASupp\\TestASupp\\src\\main\\java\\colorful design.jpg")));
        
        
        
        //Le programme se ferme en fermant la fenêtre.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // créer un  menu
        Menu obj = new Menu();

        // définir la disposition du JFrame.
        //composants disposés ligne par ligne, les uns après les autres (dans un flux).
        frame.setLayout(new FlowLayout());

        /*
        Liste de chaînes contenant les options proposée.
        La liste, je la nomme s1.
        */
        
        String s1[] = 
        {
            "Voir les médicaments existants",
            "Voir les vendeurs existants",
            "Voir les médecins existants",
            "Ajouter un médicament",
            "Ajouter un médecin",
            "Ajouter un vendeur",
        };

        // créer un menu contextuel avec la liste nommée s1
        combobox = new JComboBox(s1);
        
        //Couleur des caractères
        combobox.setForeground(Color.black);
        
        //Couleur de fond
        combobox.setBackground(Color.white);

        // ajouter ItemListener (écouteur) sur le combobox.
        combobox.addItemListener(obj);

        // créer un JLabel l1 qui annoncera ce qu'il faut faire.
        l1 = new JLabel("Que souhaitez-vous faire?\n ");

        // créer un nouveau panneau nommé p
        JPanel p = new JPanel();
        
        //Couleur de fond du JPanel p
        p.setBackground(Color.white);

        /*
        A ce panneau JPanel j'ajoute dans l'ordre :
        -mon JLabel
        -mon combobox
        */
        
        p.add(l1);
        p.add(combobox);




        //Création d'un bouton ok
        JButton ok = new JButton("Valider votre choix");
        ok.setBackground(Color.white);
        
        
        //Ajout du bouton ok à la fenêtre
        p.add(ok);

        // ajouter le panneau JPanel au JFrame
        frame.add(p);

        // définir la taille du frame 
        frame.setSize(800, 600);
        
        //Couleur de fond du JFrame
        frame.getContentPane().setBackground( Color.LIGHT_GRAY );
        
        
        //Afficher le frame
        frame.setVisible(true);
        
        

        //Déterminer la suite du programme après validation d'un choix.
        //Ajout d'un écouteur au bouton OK.
        ok.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //à la validation d'un choix, l'écran JPanel d'acceuil disparaît.
                frame.setVisible(false);
                
                //La suite du programme varie en fonction du choix grâce à la boucle si qui suit.
                
                
                if(combobox.getSelectedItem().equals("Ajouter un médicament"))
                    
                {
                    /*
                    Si on choisit d'ajouter un médicament, un formulaire nous est proposé.
                    Les requêtes insertions seront développées dans un dernier temps.
!!! Repasser par ici!!!
                    */
                    
                    FormulaireMedicament ajouterMedecin = new FormulaireMedicament(frame);
                    ajouterMedecin.launchMedicaments();
                }
                
                
                else if(combobox.getSelectedItem().equals("Ajouter un médecin"))
                    
                {
                    /*
                    Si on choisit d'ajouter un médicament, un formulaire nous est proposé.
                    Les requêtes insertions seront développées dans un dernier temps.
!!! Repasser par ici!!!
                    */
                    
                    FormulaireMedecin ajouterMedicament = new FormulaireMedecin(frame);
                    ajouterMedicament.launchMedecins();
                }
                
                
                else if(combobox.getSelectedItem().equals("Ajouter un vendeur"))
                    
                {
                    /*
                    Si on choisit d'ajouter un médicament, un formulaire nous est proposé.
                    Les requêtes insertions seront développées dans un dernier temps.
!!! Repasser par ici!!!
                    
                    */
                    FormulaireVendeur ajouterVendeur = new FormulaireVendeur(frame);
                    ajouterVendeur.launchVendeurs();
                }
                
                
                //Si on a choisit de voir les vendeurs.
                else if (combobox.getSelectedItem().equals("Voir les vendeurs existants"))
                {
                    ListeVendeurs tableVendeurs = new ListeVendeurs(frame);
                    tableVendeurs.launch();
                    //Voir la méthode dans la classe ListeVendeurs
                }
                
                
                else if (combobox.getSelectedItem().equals("Voir les médecins existants"))
                {
                    ListeMedecins tableMedecins = new ListeMedecins(frame);
                    tableMedecins.launch();
                }
                
                //Pour le seul autre choix qui reste, on choisit de voir les médicaments en stock.
                else
                {
                    ListeMedicaments ourTable = new ListeMedicaments(frame);
                    ourTable.launch();
                }
            }
        });
        
        /*
        Création d'un bouton exit pour fermer le programme.
        */
        
        //Le bouton qui ferme le programme s'appelle exit et il a le libellé "Fermer le programme".
        JButton exit = new JButton("Fermer le programme");
        exit.setBackground(Color.white);
        
        //Je détermine son action
        exit.addActionListener((event) -> System.exit(0));
        
        //Ajout du bouton au JFrame
        frame.add(exit);
        
    }
    

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        // si l'état du combobox est modifiée
        if (e.getSource() == combobox)
        {
        }
    }


}