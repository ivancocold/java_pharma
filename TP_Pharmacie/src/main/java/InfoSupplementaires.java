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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;


/*
Classe de gestion des professionnels de santé liés à un médicament.
Cette classe va nous permettre d'afficher 

*/
public class InfoSupplementaires
{

    
    //Gestion des médecins liés à un médicament par la prescription
    private ArrayList<String[]> getMedecins(int id_med)
    {
        try
        {
//Chargement de la connection
            Class.forName("oracle.jdbc.driver.OracleDriver");

//Création de la connection
            Connection con= DriverManager.getConnection
        (
                    "jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1"," p2112423","627105"
        );

//Création du statement
            Statement stmt=con.createStatement();

            //Requête sql.
            //Je ne veux pas de doublons dans les résultats, j'ai donc utilisé la clause distinct.
            
            ResultSet rs=stmt.executeQuery
//J'ai décidé d'afficher 4 colonnes.
//Si on en veut plus et qu'on modifie la requête, il faudra modifier la boucle while qui est plus bas.
//La boucle me sert à constuire un tableau à la taille de mon resultset
        (
                    "select distinct(d.idmedecin), d.nommedecin, d.specialitemedecin, d.adrmedecin \n"+
                    "from medicament m\n" +
                        "right join prescrire p on m.idmedicament =p.idmedicament\n" +
                        "left join medecin d on p.idmedecin = d.idmedecin\n" +
                    "where m.idmedicament = "+ id_med + "\n" +
                    "order by 1"
        );
            
            //Je déclare une liste nommée resultat.
            //J'enregistre le résultat de notre requête dans une liste de chaînes de caractères.
            ArrayList <String[]> result = new ArrayList<String[]>();
            
            //Cette boucle va me servir dans la coonstuction d'un tableau à la taille de mon résultat.
            while(rs.next())
            {
                String[] entry = new String[4];
                entry[0] = ""+rs.getInt(1);
                entry[1] = rs.getString(2);
                entry[2] = rs.getString(3);
                entry[3] = rs.getString(4);
                result.add(entry);
            }
            //Fermeture du statement
            stmt.close();
            //Fermeture de la connection
            con.close();
            //Ma méthode retourne le résultat de la requête dans un tableau.
            return result;

        }
        //Fin du try
        
        //Début du catch
        catch(Exception e)
            //Gestion des exceptions
        {
            //Affichage du message d'erreur dans la console.
            System.out.println(e);
            
            //Sortir du program en cas d'échec de connection.
            System.exit(0);
            return null;
        }
        //Fin du catch
    }
    //Fin de la gestion des médecins liés à un médicament par la prescription
    

    
    /*Gestion des vendeurs liés à un médicament par la proposition à la vente.
    Si jamais des commentaires manquent, se référer à la méthode précédente.
    La méthode est similaire à celle de la gestion des médecins.
    La seule différence réside dans la requête et dans le nombre de colonnes retournées.
    */
    private ArrayList<String[]> getVendeurs(int id_med)
    {
        try
        {
//
            Class.forName("oracle.jdbc.driver.OracleDriver");

//
            Connection con= DriverManager.getConnection(
                    "jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1"," p2112423","627105");

//
            Statement stmt=con.createStatement();

//
            ResultSet rs=stmt.executeQuery("select distinct(p.idvendeur), p.nomvendeur, p.adrvendeur, p.telvendeur\n" +
                    "from medicament m\n" +
                    "    right join vends v on m.idmedicament=v.idmedicament\n" +
                    "    left join vendeur p on v.idvendeur=p.idvendeur\n" +
                    "where m.idmedicament = " +id_med+" \n"+
                    "order by 1");
            ArrayList <String[]> result = new ArrayList<String[]>();
            while(rs.next()){
                String[] entry = new String[4];
                entry[0] = ""+rs.getInt(1);
                entry[1] = rs.getString(2);
                entry[2] = rs.getString(3);
                entry[3] = rs.getString(4);
                result.add(entry);
            }

            stmt.close();
            con.close();

            return result;

        }
        
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }
    /*
    Fin de l'affichage des médicaments
    */
    
    
    /*
    Développement d'une méthode de lancement de l'affichage.
    */
    
    //La méthode prend en paramètre deux réels
    public void launch(int id, int choice)
    {
        //crée un JFrame labelisé Professionnels [***]
        //Il pourra afficher les médecins ou les vendeurs selon le choix de l'utilisateur.
        final JFrame frame = new JFrame("Professionnels de santé liés au médicament que vous avez choisi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        //Noms des colonnes si l'utilisateur choisi les vendeurs associés
        String[] columns_vendeurs = new String[]
        {
                "ID Vendeur", "Nom vendeur", "Adresse", "Téléphone"
        };
        ArrayList<String[]> result_all_table_vendeurs = getVendeurs(id);
        String[][] vendeurs = result_all_table_vendeurs.toArray(new String[0][0]);

        //crée un JTable avec des données
        JTable table_vendeurs = new JTable(vendeurs, columns_vendeurs)
        {
            public boolean editCellAt(int row, int column, java.util.EventObject e)
            {
                return false;
            }

        };


        ////Noms des colonnes si l'utilisateur choisi les médecins associés
        String[] columns_medecins = new String[]
        {
                "ID medecin", "Nom medecin", "Specialité", "Adresse"
        };
        
        ArrayList<String[]> result_all_table_medecins = getMedecins(id);
        String[][] medecins = result_all_table_medecins.toArray(new String[0][0]);

        //crée un JTable avec des données
        JTable table_medecins = new JTable(medecins, columns_medecins)
        {
            public boolean editCellAt(int row, int column, java.util.EventObject e)
            {
                return false;
            }

        };
        //Tri par défaut des deux tables
        table_vendeurs.setAutoCreateRowSorter(true);
        table_medecins.setAutoCreateRowSorter(true);

        
        JScrollPane scroll_vendeurs = new JScrollPane(table_vendeurs);
        table_vendeurs.setFillsViewportHeight(true);

        JScrollPane scroll_medecins = new JScrollPane(table_medecins);
        table_medecins.setFillsViewportHeight(true);

        /*
        
        */

        JLabel labelHead_vendeurs = new JLabel("Liste des vendeurs qui proposent le médicament de votre choix");
        JLabel labelHead_medecins= new JLabel("Liste des médecins qui prescrivent le médicament de votre choix");
        labelHead_vendeurs.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
        labelHead_medecins.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));

        if (choice == 1)
        {
            frame.getContentPane().add(labelHead_medecins, BorderLayout.PAGE_START);
            frame.getContentPane().add(scroll_medecins,BorderLayout.CENTER);

        }

        else
        {
            frame.getContentPane().add(labelHead_vendeurs, BorderLayout.PAGE_START);
            frame.getContentPane().add(scroll_vendeurs, BorderLayout.CENTER);
        }
        JButton backButton = new JButton("Exit");
        //Action pour le bouton Back

        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.setVisible(false);
            }
        });


        //ajouter le bouton back au frame
        frame.getContentPane().add(backButton,BorderLayout.PAGE_END);
        frame.setSize(900, 200);
        frame.setVisible(true);
    }
}
