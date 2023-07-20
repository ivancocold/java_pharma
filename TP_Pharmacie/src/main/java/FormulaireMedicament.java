/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author i.magagi
 */

import java.awt.GridLayout;
import javax.swing.*;
//import java.text.Normalizer;
//import java.util.ArrayList;

/*
Objectifs:
1. Proposer un formulaire de saisi d'un nouveau médicament [ok]
2. Proposer un bouton de validation de la saisie. [OK]
3. i. L'ID médicaments sont auto-incremental. je ne propose pas sa saisie. [OK]
   ii. Enregistrer le médicament saisie dans la BDD. [PLS]


4. Bouton de retour au menu principal [OK]
5. On close exit. [OK]

*/


/*
Classe Formulaire pour saisir des insert.
Classe intelligente qui s'adapte aux besoins grâces aux différentes boucles while.

Idéalement, je souhaite utiliser cette classe pour la saisi de nouvelles entrées dans ma BDD (médicaments, médecins, vendeurs).
Mais le temps...

Priorité : saisi et insertions des médicalents.
*/

class FormulaireMedicament
{
    //Variables qui seront utilisées pour insérer des données dans la BDD.
    static int a;
    static double e;
    static String b,c,d;
    
    //Déaclaration d'un Jframe nommé origin.
    public JFrame origin;
    
    //JFrame 
    public FormulaireMedicament(JFrame origin)
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
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter/modifier un médicament",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
          
          //Suite du programme en fonction du choix après saisi
          if (result == JOptionPane.OK_OPTION)
          {
              
              
              //Si on choisit "ok", la fenêtre principale réaparaît.
              origin.setVisible(true);
              
              //Ici result vaut 0
              System.out.println(result);
              
          }
          
          else 
          {
            //Si non, la fenêtre principale réaparaît aussi.
            origin.setVisible(true);
            
            //Ici result vaut 2
            System.out.println(result);
          }
    }

    //Méthode de saisi de nouveaux médicaments
    public void launchMedicaments()
    /*
            Libellé des différents zones de saisie.
            Ce sont les deux listes ici définies qui permettront de créer le bon nombre de JTextField.
            Ce sont aussi les méthodes ci-après qui définissent les libellés des différents JTextField en fonction de ce qui choisit sur le menu d'acceuil 
   */
            
    {
        launch(new String[]{"ID Médicament:","Désignation du médicament:","Effets du médicament:","Posologie du médicament:","Prix en chiffre et en €:"}
                , new String[]{"","","","",""});
    }
    

}

/*
RESTE A FAIRE : création des insert/update dans la base de données. Arriver à identifier qu'on saisi un médecin, un médicamenent ou un vendeur.

Je ne suis pas arrivé à gérer le cas où l'utiisateur saisirait un id médicament déjà existant dans la BDD ou
s'il saisit du texte à la place d'un nombre.

Si vous lisez jusqu'ici, vous allez vous rendre compte d'une des limites de mon projet.
Je n'ai pas encore fini, mais je sais quand même bien commenter mon projet.
ça s'équilibre un peu. :-)
*/