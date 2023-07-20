/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author i.magagi
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Cette classe va me permettre de gérer l'insertion et l'actualisation des médicaments.


Objectifs:
1. Insérer un nouveau médicament.
2. Ma BDD devrait avoir un ID médicament autoincrémental. L'idéal serait dont que je propose à l'utilisateur de saisir .

Au fur et à mesure, je pourrais créer des méthodes d'insertion (insert)/ actualisation(update) de vendeurs et de médecins.
*/
public class InsertUpdate 
{
    //String b,c,d;
    //double e;
    
    
    Scanner sc;
    int choix;
    //Va enregistrer le résutat d'une requête des idmedicaments connus de notre BDD.
    ArrayList<Integer> list;
    
    
    private static void insert (int a,String b, String c, String d, double e)
            {
                /*
                J'utiliserai le resultet pour comparer et voir si l'ID saisi dans le formulaire est connu de ma base données.
                Ainsi je saurais par la suite (boucle si) si je sois faire insert ou update.
                */
                ResultSet rs;
                
                //Enregistrer le driver
                Connection con;
                
                //Statement
                Statement statement;
                
                //PreparedStatement
                PreparedStatement preparedstatement;
                /*try
                    {
                   
                    con = DriverManager.getConnection("jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1","p2112423","627105");
                    preparedstatement = con.prepareStatement("select idmedicament from minibus");
                    rs = preparedstatement.executeQuery("select * from minibus");
                    
                    }
                catch(SQLException g)
                    {
                        
                    }
                
                //Tester que l'id est coonnu de la BDD. Si connu, actualisation, sinon, insertion.
                if ()*/
                    
                    try
                        {
                            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());            
                        }
                    catch (SQLException ex)
                        {
                            Logger.getLogger(InsertUpdate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                /*
                        Connection con;
                        Statement statement;
                    PreparedStatement preparedstatement;
                */
                    try
                        {
                   
                        con = DriverManager.getConnection("jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1","p2112423","627105");
                        con.setAutoCommit(true);
                        preparedstatement = con.prepareStatement("insert into medicament (idmedicament, designationmed,effetmed,posologiemed,prixlaboratoire) values (?,?,?,?,?)");
                    
                        preparedstatement.setInt(1, a);
                        preparedstatement.setString(2, b);
                        preparedstatement.setString(3, c);
                        preparedstatement.setString(4, d);
                        preparedstatement.setDouble(5, e);
                        
                        //Execution de la requête
                        preparedstatement.execute();
                        
                        //Fermeture de la connextion
                        con.close();
                    
                        }
                    //Fin du try
                
                    //Gestion des exceptions
                    catch (SQLException p)
                        {
                            //Affichage du message d'erreur dans la console.
                            System.out.println(p);
                            
                            //Sortir du programme en cas d'échec de l'insertion.
                            System.exit(0);
                            
                            
                            
                        }
                    //Fin du catch
                
                
    
    
            }
    //Fin de la méthode insert

    public InsertUpdate() 
    {
        this.list = new ArrayList();
    }
}
    


