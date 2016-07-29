package it.ads.formazione;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
	
	private static String nomeDB;
	private static String nomeTabella;
	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	
	
	
	public Cliente(){
		
	}
	
	
	public Cliente(String nomeDB, String nomeTabella){
		this.nomeDB = nomeDB;
		this.nomeTabella = nomeTabella;
	}
	

	
	public static String getNomeDB() {
		return nomeDB;
	}



	public static String getNomeTabella() {
		return nomeTabella;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}

	
	
	public void creaDBClients(){
		Connection connDB = null;
		Statement stmtDB = null;
		
	    try {
	      connDB = new ConnessioneDB("sqlite",nomeDB).getConnDB();
	    } catch ( Exception e ) {
	      System.err.println("impossibile Creare il DB");
	    }
	    System.out.println("DB " + nomeDB + "Aperto Correttamente");
	}


	
	public void creaTabellaClients(){
		Statement stmtDB = null;
		try {
			Connection connDB = new ConnessioneDB ("sqlite", nomeDB).getConnDB();
	    	stmtDB = connDB.createStatement();
	        String sql = "CREATE TABLE IF NOT EXISTS " + nomeTabella +
	                     "(id INT PRIMARY KEY     NOT NULL," +
	                     " first_name     TEXT    NOT NULL, " + 
	                     " last_name      TEXT    NOT NULL, " + 
	                     " address         TEXT, " + 
	                     " city           TEXT);"; 
	        stmtDB.executeUpdate(sql);
	        stmtDB.close();
	        connDB.close();
	      } catch ( Exception e ) {
	        System.err.println("Errore nella creazione della tabella '" + nomeTabella + "'");
	      }
	}
	
	
	
	public void estraiCliente(int id, String rigaFile){
		String[] tmp = rigaFile.split(";");
		this.id = id; 
		firstName = tmp[0];
		lastName = tmp[1];
		address = tmp[2];
		city = tmp[3];
	}
	
	
	
	public void inserisciCliente(){
		try{
			Connection connDB = new ConnessioneDB ("sqlite", nomeDB).getConnDB();
			connDB.setAutoCommit(false);
			
			Statement stmtDB = connDB.createStatement();
			
			String sql = "INSERT INTO " + nomeTabella + " (id, first_name, last_name, address, city) " +
	                     "VALUES (" + 
	                     id + ", '" + 
	                     firstName + "', '" + 
	                     lastName + "', '" + 
	                     address + "', " + "'" + 
	                     city + "');";
			stmtDB.executeUpdate(sql);
		    stmtDB.close();
		    connDB.commit();
		    connDB.close();
		} catch ( Exception e ) {
		    System.err.println("ID Cliente già esistent o è impossibile inserire il Cliente nella Tabella " + nomeTabella + "!");
		}
			
	}
	
	
	public List<Cliente> findAll(){
		List<Cliente> clienti = new ArrayList();
		
		Connection connDB = null;
	    Statement stmtDB = null;
	    try {
	      connDB = new ConnessioneDB("sqlite",nomeDB).getConnDB();
	      connDB.setAutoCommit(false);

	      stmtDB = connDB.createStatement();
	      ResultSet rs = stmtDB.executeQuery( "SELECT * FROM " + this.nomeTabella + " ;" );
	      Cliente cliente = null;
	      while ( rs.next() ) {
	    	  cliente = new Cliente();
	    	  cliente.setId(rs.getInt("id"));
	    	  cliente.setFirstName(rs.getString("first_name"));
	    	  cliente.setLastName(rs.getString("last_name"));
	    	  cliente.setAddress(rs.getString("address"));
	    	  cliente.setCity(rs.getString("city"));

	    	  clienti.add(cliente);
	      }
	      rs.close();
	      stmtDB.close();
	      connDB.close();
	    } catch ( Exception e ) {
	      System.err.println("Impossibile estrarre Dati Clienti");
	    }
		return clienti;
	}

	
	public void stampaRisultati(List<Cliente> clienti){
		for(Cliente cliente : clienti){
	    	  System.out.println("id: " + cliente.getId());
	    	  System.out.println("Name: " + cliente.getFirstName());
	    	  System.out.println("Age: " + cliente.getLastName());
	    	  System.out.println("Address: " + cliente.getAddress());
	    	  System.out.println("City: " + cliente.getCity());
	    	  System.out.println("<----------------------------------->");
	      }
	}
}
