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
//import static java.awt.SystemColor.text;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*
Création d'une classe qui affichera la liste des médicaments.
Objectif :
1. arriver à afficher les médicaments en stock. [ok]
2. arriver à afficher les professionnels de santé (vendeurs et médecins) liés à un médicament (requête SQL + autre ouverture classe). OK
3. arriver à modifier un ou plusieurs médicaments affiché(s). [En cours]
4. 


PS: A un moment, je me suis emmelé en nommant les variables qui me permettent d'afficher ou retourner les informations supplémentaires des vendeurs et des médecins.
Au lieu de corriger ces variables, ce qui me prendrait beaucoup de temps, j'ai préferé garder ces noms qui néanmoins, retourner des résultats cohérents.
Cela, je l'espère, me permettra d'avancer sur d'autres détails du projet.
*/

public class ListeMedicaments 
{
    //Création d'un JFrame nommé origin.
    public JFrame origin;
    public ListeMedicaments(JFrame origin)
    {
        this.origin = origin;
    }
    private ArrayList<String[]> getMedicaments(){
        try{
//Charger le driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

//Créer un objet de connection
            Connection con= DriverManager.getConnection(
                    "jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1"," p2112423","627105");

//Statement
            Statement stmt=con.createStatement();

//Requête à exécuter
            /*
               La requête restitue toute la table medicament en ordonant la première colonne par ordre croissant.
               Je part du principe que ma table a 5 colonnes.
               J'enregistre le résultat dans un objet appelé rs
               
            */
            ResultSet rs=stmt.executeQuery("select * from medicament order by 1");
            
            /*
            Je veux mettre le résultat de la requête dans un tableau à 5 colonnes.
            Si j'avais fait une requête qui me retourne 4 colonnes, j'aurais fait un tableau à 4 colonnes.
            */
            
            ArrayList <String[]> result = new ArrayList<String[]>();
            /*
            La boucle while va me permettre de charger le résultat de ma requête dans un tableau.
            La boucle reste ouverte tant qu'elle n'a pas parcouru tous les éléments résultats de ma requête.
            */
            while(rs.next())
            {
                String[] entry = new String[5];
                
                //La première colonne au format int
                entry[0] = ""+rs.getInt(1);
                entry[1] = rs.getString(2);
                entry[2] = rs.getString(3);
                entry[3] = rs.getString(4);
                entry[4] = rs.getString(5);
                //Je remplis le tableau avec les différentes colonnes de entry
                result.add(entry);
            }

            stmt.close();
//Fermer connection
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
    
    public void launch()
    {
        //crée un JFrame appelé frame et dont le titre est entre guillemets.
        final JFrame frame = new JFrame("Tableau des médicaments en stock.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Noms de colonnes 
        String[] columns = new String[]
        {
                "ID Medicament", "Désignation", "Effets", "Posologie", "Prix Laboratoire en €"
        };
        
        //Création d'un tableau dont sera remplit les médicaments de la BDD.
        ArrayList<String[]> result_all_table = getMedicaments();
        
        String[][] medicaments = result_all_table.toArray(new String[0][0]);


        //crée un JTable avec des données
        JTable table = new JTable(medicaments, columns)
        {
            public boolean editCellAt(int row, int column, java.util.EventObject e)
            {
                return false;
            }
        };
        
                table.setAutoCreateRowSorter(true);
                
                
        /*
        Menu contextuel si au click droit.
        Ce JPopupMenu donne deux choix à l'utilisateur.
        Son choix permettra de lancer une méthode de la classe InfoSupplémentaires.
        Cette seule et même méthode retourne deux résultats différents en fontions du choix fait par l'utilisateur. 
        */
        JPopupMenu contextualMenu = new JPopupMenu();
        
        //Option Voir les médecins qui vendent ce médicament
        JMenuItem informations_medecins = new JMenuItem("Voir les médecins qui le prescrivent.");
        
        //J'ajoute un écouteur 
        informations_medecins.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Je récupère le numéro de la ligne.
                int rowIndex = table.getSelectedRow();
                
                //Je déclare la variable qui va contenir l'ID du médecin
                int id_med;
                if(rowIndex >= 0)
                {
                    id_med = Integer.valueOf((String) table.getValueAt(rowIndex, 0));
                    InfoSupplementaires info = new InfoSupplementaires();
                    info.launch(id_med,1);
                }
            }
        });
        
        
        /*
        
        */
        JMenuItem informations_vendeurs = new JMenuItem("Voir les vendeurs qui le proposent.");
        //
        informations_vendeurs.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int rowIndex = table.getSelectedRow();
                int id_med = -1;
                if(rowIndex >= 0)
                {
                    id_med = Integer.valueOf((String) table.getValueAt(rowIndex, 0));
                    InfoSupplementaires info = new InfoSupplementaires();
                    info.launch(id_med,2);
                }
            }
        });
        contextualMenu.add(informations_medecins);
        contextualMenu.add(informations_vendeurs);
        table.add(contextualMenu);
        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent me)
            {
                //Vérifier si clique droit
                if(SwingUtilities.isRightMouseButton(me))
                {
                    int r = table.rowAtPoint(me.getPoint());
                    if (r >= 0 && r < table.getRowCount())
                    {
                        table.setRowSelectionInterval(r, r);
                    }
                    else
                    {
                        table.clearSelection();
                    }
                    contextualMenu.show(me.getComponent(),me.getX(),me.getY());
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JLabel labelHead = new JLabel("Liste des médicaments en stock. Click droit sur le médicament de votre choix pour plus de détails.");
        
        //Je m'amuse à changer les polices de caractères.
        labelHead.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
        
        //Je n'arrive pas à centrer le titre.
        //labelHead.area.(text, BorderLayout.CENTER);

        //J'ajoute
        frame.getContentPane().add(labelHead, BorderLayout.PAGE_START);
        //ajouter la table au frame
        frame.getContentPane().add(scroll, BorderLayout.CENTER);

        
        
        //Création d'un bouton retour au menu principal
        JButton backButton = new JButton("Retour au menu principal");
        //Action pour le bouton Back
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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        //ajouter le bouton back au frame
        frame.getContentPane().add(backButton,BorderLayout.PAGE_END);
        frame.setSize(900, 600);
        frame.setVisible(true);
    }
}