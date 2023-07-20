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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;


/*
Création de l'écran de la liste des vendeurs.
Objectif :
- Ecran qui s'ouvre lorsqu'on veut voir les pharmaciens /vendeurs. OK
- Affiche le résultat d'une requête SQL. OK
- Affiche le résultat d'une requête SQL dans un liste. OK+amélioration Tableau
- Affiche le résultat d'une requête SQL dans un tableau. OK
- Proposer de modifier un ou plusieurs vendeurs. OK (formulaire)
- Update et de la BDD en fonction des mofications réalisées. PLS :'(
*/
public class ListeVendeurs
{
    //Création d'un JFrame
    public JFrame origin ;       
    
    public ListeVendeurs(JFrame origin)
    {
        
        this.origin = origin;
    }

    /*
    ListeVendeurs()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */
    
    //Création d'une méthode qui retourne les médicaments dans un tableau
    private ArrayList<String[]> getMedicaments()
    {

        /*
        Création d'un try en vue de se connecter à la BDD et executer les requêtes opportunes.
        J'ai fait un gros try catch plutôt que plusieurs petits try catch par défaut de temps.
        Avantage : gain de temps.
        Inconvenient : l'échec d'un petit bout de 
        
        */
                
        try
        {
            //Je charge le driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            /*
            Je crée la connexion que je nomme con.
            Le nom con sera souvent utilisé tout le long de ce projet.
            */
                
            Connection con= DriverManager.getConnection
            (
                    "jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1"," p2112423","627105"
            );

//création de l'objet statement
            Statement stmt=con.createStatement();

//Executer la requête qui affiche tous les médicaments en stock pour les enregistrer dans l'objet rs
            ResultSet rs=stmt.executeQuery("select * from vendeur order by 1");
            
//Création d'un tableau de chaîne de caractères.
            ArrayList <String[]> result = new ArrayList<>();
            
            //Le résultat de la requête est enregistré dans une table à 4 colonnes.
            //La boucle pour va permettre de remplir une à une les cases du tableau avec le résultat de la requête.
            while(rs.next())
            {
                //Le 4, c'est le nombre de colonnes de la table vendeurs dans ma BDD.
                String[] entry = new String[4];
                
                /*
                Gestion particulière de la première colonne pour garder la permière colonne au format int.
                Interêt: la première colonne comporte l'id des vendeurs.
                Cet ID pourra être utilisé dans les requêtes ultérieures (actualisation/modification).
                */
                entry[0] = ""+rs.getInt(1);
                /*
                Les autres colonnes peuvent garder le format string.
                */
                entry[1] = rs.getString(2);
                entry[2] = rs.getString(3);
                entry[3] = rs.getString(4);
                result.add(entry);
            }
            
//Fermeture du statement
            stmt.close();
            
            
//Fermeture de la connection
            con.close();

            return result;

        }
        
        catch(ClassNotFoundException | SQLException e)
        {
            //Affichage du message d'erreur dans la console.
            System.out.println(e);
            
            //Sortir du program en cas d'échec de connection.
            System.exit(0);
            return null;
            
        }
    }
    
    
    //Création d'une méthode qui affiche les différents vendeurs répertoriés
    public void launch()
    {
        //crée un frame
        final JFrame frame = new JFrame("Tableau des médicaments.");
        
        //Fermer le frame met fin a programme.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //En-têtes pour JTable
        String[] columns = new String[]
        {
                "ID Vendeur", "Nom", "Adresse", "Téléphone"
        };
        ArrayList<String[]> result_all_table = getMedicaments();
        String[][] vendeurs = result_all_table.toArray(new String[0][0]);

        //crée un JTable avec des données
        JTable table = new JTable(vendeurs, columns) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;}

        };

        //Menu contextuel sous format JPopupMenu
        JPopupMenu contextualMenu = new JPopupMenu();

        //Option editer
        JMenuItem edit = new JMenuItem("Editer");

        //Action quand on clique sur éditer

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowIndex = table.getSelectedRow();
                String[] elements = columns;
                String[] valeurs = new String[4];

                if(rowIndex >= 0){
                    for (int i = 0; i < 4; i++){
                        valeurs[i] = (String) table.getValueAt(rowIndex, i);
                    }
                    FormulaireVendeur changerVendeur = new FormulaireVendeur(frame);
                    changerVendeur.launch(elements,valeurs);
                }

            }
        });
        
        //Je rajoute l'option éditer au menu contextuel
        contextualMenu.add(edit);
        
        //Je rajoute le menu contectuel à la JTable.
        table.add(contextualMenu);
        
        //J'ajoute un écouteur à ma JTable.
        table.addMouseListener(new MouseAdapter()
        {
            //Méthode au click de la souris
            public void mouseClicked(MouseEvent me)
            {
                /*
                Vérifier le click droit.
                J'utilise le click droit car, instinctivement, le click gauche est utilisé pour valider un choix.
                Cela n'est pas le cas des fonctionnalités que je compte proposer.
                */
                if(SwingUtilities.isRightMouseButton(me))
                    {
                        //la variable r enregistre le numéro de la ligne sur laquelle on a cliqué.
                        int r = table.rowAtPoint(me.getPoint());
                        
                        //Si 
                        if (r >= 0 && r < table.getRowCount()) 
                            {
                                //
                                table.setRowSelectionInterval(r, r);
                            }
                        //
                        else 
                            {
                                table.clearSelection();
                            }
                        
                        contextualMenu.show(me.getComponent(),me.getX(),me.getY());
                    }
                
                /*
                Fin de la boucle if qui vérifie si le click est droit ou gauche.
                */

            }
        });
        
        
        //Ajout d'un JScrollPane à ma JTable.
        
        JScrollPane scroll = new JScrollPane(table);
        
        
        table.setFillsViewportHeight(true);
        
        //Je crée un JLabel qui me servira de titre à mon JFrame
        JLabel labelHead = new JLabel("Liste des Vendeurs. Click droit sur le vendeur de votre choix pour toute modification.");
        
        //Je m'amuse à changer la police de caractère de mon titre
        labelHead.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
        
        //J'ajoute le titre en haut de mon JFrame
        frame.getContentPane().add(labelHead, BorderLayout.PAGE_START);
        
        //ajouter la JScrollPane au JFrame
        frame.getContentPane().add(scroll, BorderLayout.CENTER);
        
        //Création d'un JButton appelé "backButton" de retour au menu principal.
        JButton backButton = new JButton("Retour au menu principal");
        
        /*
        Action pour le bouton backButton:
        -J'ajoute un écouteur.
        -En clickant sur le bouton, on ouvre la JFrame d'acceuil et ferme la JFrame d'affichage des médicaments.        
        */       
        backButton.addActionListener
        (
                new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            frame.setVisible(false);
                            origin.setVisible(true);
                        }
                    }
        );
        //Fin de l'écouteur du bouton retour.

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //ajouter le bouton back au JFrame
        frame.getContentPane().add(backButton,BorderLayout.PAGE_END);
        
        //Taille du JFrame
        frame.setSize(800, 600);
        
        //Affichage du JFrame
        frame.setVisible(true);
    }
    //Fin de la méthode qui affiche le tableau des  vendeurs
}