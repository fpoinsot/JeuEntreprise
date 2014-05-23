import java.util.*;
import java.sql.*;
import java.io.*;
public class Bdd {

	private Connection connection; 
	private String driver ;
	private String url;
	private String login;
	private String pass;
	private ResultSet resultat =null;
	public  Bdd(String driver, String url,String login,String pass)
	{
		this.driver=driver;
		this.url=url;
		this.login=login;
		this.pass=pass;
		
		try {
			this.seConnecter();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("impossible de se co ");
			e.printStackTrace();
		}
		
	}
	public  void  seConnecter( ) throws ClassNotFoundException
	{
		try
		{
			
			
		 connection= DriverManager.getConnection(this.url,this.login,this.pass);
			System.out.println("connection reussi ");
			
		}
		catch(SQLException sqle){
			   System.out.println("Erreur SQL : "+sqle);
			   //Cf. Comment gérer les erreurs ?
			}
			catch(Exception e){
			   System.out.println("Autre erreur : ");
			   e.printStackTrace();
			}
			
	}

	// connection valide
public static boolean estValid(Connection connection){
	if(connection==null){
      return false;
		}
   ResultSet ping = null;
   try{
      if(connection.isClosed()){return false;}
      ping = connection.createStatement().executeQuery("SELECT 1");
      return ping.next();
   }catch(SQLException sqle){
      return false;
   }
   finally{
      if(ping!=null){try{ping.close();}catch(Exception e){}}
   }  
}
	

// executer requete

public ResultSet executerRequete()
{
	

	Statement statement;
	try {
		statement =this.connection.createStatement();
		boolean trouve = statement.execute("SELECT * FROM personne");
		if(trouve)
		{
			resultat=statement.executeQuery("SELECT * FROM personne");
		}else
		{
			
			System.out.println("La requete est vide  : ");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return resultat;
	

}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//version oracle 11g pour le parametre
		String driver="oracle.jdbc.OracleDriver" ;
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String login="franck";
		String pass="franck";
		ResultSet resultat =null;
		Bdd base =new Bdd(driver,url,login,pass);
		resultat=base.executerRequete();
		try {
			while(resultat.next())
			{
				System.out.println("id : "+resultat.getString(1)+" nom : "+resultat.getString(2));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
