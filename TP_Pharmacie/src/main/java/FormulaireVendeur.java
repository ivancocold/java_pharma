
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author i.magagi
 */
public class FormulaireVendeur 
{
    
    //Variables qui seront utilisées pour insérer des données dans la BDD.
    static int a;
    static double e;
    static String b,c,d;
    
    //Déaclaration d'un Jframe nommé origin.
    public JFrame origin;
    
    //JFrame 
    public FormulaireVendeur(JFrame origin)
    {
        this.origin = origin;
    }
    
    /*
    La méthode launch de cette classe prend en paramètres une liste de noms de valonnes et une liste de valeurs pour créer un formulaire.
    Dans un premier temps, je teste sur des listes de chaînes de caractères.
    Par la suite, je ferai un distinguo sur les ID et les prix qui sont respectivement en int et double.
    */
    
    public void launch(String[] elements, String[] valeurs)
    {
        //Création d'un JPanel
        //Il s'agit d'une grille d'une colonne.
        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        //Je crée une boucle afin de créer autant de zones de saisies que nécessaires.
        //Si la liste elements fait une taille 5, on aura 5 JTextField ajoutés au JPanel.
        for (int i = 0; i<elements.length; i++)
        {
            //JTextField = zone de saisie.
            JTextField field = new JTextField(valeurs[i]);
            panel.add(new JLabel(elements[i]));
            panel.add(field);
        }
        
        /*
        L'avantage de la boucle précédente est qu'elle s'adapte aux différentes méthodes launch (tout en fin de la classe).
        Le soucis est que je n'arrive pas à récupérer le contenu de toutes les zones de texte.
        */
        
        
        //Méthode qui permet de valider ou annuler les saisies avec des boutons OK et cancel.
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter/modifier un vendeur",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
          
          //Suite du programme en fonction du choix après saisi
          if (result == JOptionPane.OK_OPTION)
          {
              
              
              //Si on choisit "ok", la fenêtre principale réaparaît.
              origin.setVisible(true);
              
              //Ici result vaut 0. Vérifications personnelles dans la console
              System.out.println(result);
              
          }
          
          else 
          {
            //Si non, la fenêtre principale réaparaît aussi.
            origin.setVisible(true);
            
            //Ici result vaut 2. Vérifications personnelles dans la console
            System.out.println(result);
          }
    }
    
    //Méthode de saisi de nouveaux vendeurs
    public void launchVendeurs()
    //Libellé des différents zones de saisi
    {
        launch(new String[]{"ID Vendeur:", "Nom du vendeur:", "Adresse:", "Téléphone:"}
                , new String[]{"","","",""});
    }


    
}
