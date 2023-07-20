
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author i.magagi
 */

/*

Classe de médecins.
Copié depuis la classe des vendeurs.
Certains commentaires résiduels doivents évoquer les éléments de la classe vendeur.
Je n'ai pas eu le temps de tout nettoyer.
Je propose un formulaire de modifications des informations.
Les modifications ne sont pas encore integrées dans la BDD à la date du 9 mars.

*/
public class ListeMedecins
{

    //Création d'un JFrame
    public JFrame origin ;       
    
    public ListeMedecins(JFrame origin)
    {
        this.origin = origin;
    }
    
    private ArrayList<String[]> getMedecins()
    {

//Création d'un try en vue de se connecter à la BDD        
        try
        {
//Je charge le driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

//Je crée la connexion que je nomme con
//Le nom con sera souvent utilisé tout le long de ce projet.
            Connection con= DriverManager.getConnection
            (
                    "jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1"," p2112423","627105"
            );

//création de l'objet statement
            Statement stmt=con.createStatement();

//Executer la requête qui affiche tous les médicaments en stock pour les enregistrer dans l'objet rs
            ResultSet rs=stmt.executeQuery("select * from medecin order by 1");
            
//Création d'un tableau de chaîne de caractères.
            ArrayList <String[]> result = new ArrayList<>();
            
            //Le résultat de la requête est enregistré dans une table à 4 colonnes.
            //La boucle pour va permettre de remplir une à une les cases du tableau avec le résultat de la requête.
            while(rs.next())
            {
                //Le 4, c'est le nombre de colonnes de la table medecin dans ma BDD.
                String[] entry = new String[4];
                
                /*
                Gestion particulière de la première colonne pour garder la permière colonne au format int.
                Interêt: la première colonne comporte l'id des médecins.
                Cet ID pourra être utilisé dans les requêtes ultérieures.
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
    


    public void launch()
    {
        //crée un frame
        final JFrame frame = new JFrame("Tableau des médecins.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //En-têtes pour JTable
        String[] columns = new String[]
        {
                "ID Médecin", "Nom du médecin", "Spécialité", "Adresse"
        };
        
        ArrayList<String[]> result_all_table = getMedecins();
        String[][] Medecins = result_all_table.toArray(new String[0][0]);

        //crée un JTable avec des données
        JTable table = new JTable(Medecins, columns)
        {
            @Override
            public boolean editCellAt(int row, int column, java.util.EventObject e)
            {
                return false;
            }

        };

        //Menu contextuel
        JPopupMenu contextualMenu = new JPopupMenu();

        //Option editer
        JMenuItem edit = new JMenuItem("Editer");

        //Action quand on clique sur éditer

        edit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int rowIndex = table.getSelectedRow();
                String[] elements = columns;
                String[] valeurs = new String[4];

                if(rowIndex >= 0)
                {
                    for (int i = 0; i < 4; i++){
                        valeurs[i] = (String) table.getValueAt(rowIndex, i);
                    }
                    FormulaireMedecin changerMedecin = new FormulaireMedecin(frame);
                    changerMedecin.launch(elements,valeurs);
                }

            }
        });
        contextualMenu.add(edit);
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
        
        
        //Tableau des médecins
        JLabel labelHead = new JLabel("Liste des médecins. Click droit sur le médecin de votre choix pour toute modification");
        //Je m'amuse à d
        labelHead.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));

        frame.getContentPane().add(labelHead, BorderLayout.PAGE_START);
        //ajouter la table au frame
        frame.getContentPane().add(scroll, BorderLayout.CENTER);
        
        //Vréation d'un bouton retour au menu principal
        JButton backButton = new JButton("Retour au menu principal");
        
        //J'ajoute un écouteur au bouton.
        backButton.addActionListener
        (
                new ActionListener()
        {            
            @Override
            //au click sur le bouton
            public void actionPerformed(ActionEvent e)
            {
                //La fenêtre se ferme.
                frame.setVisible(false);
                //La fenêtre principale que j'ai appelé origin s'ouvre.
                origin.setVisible(true);
            }
        }
        );
        
        
        //La fermeture de la fenêtre ferme le programme.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //J'ajoute le bouton retour au frame
        frame.getContentPane().add(backButton,BorderLayout.PAGE_END);
        
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
    //Fin de la méthode qui affiche le tableau des  médecins
}