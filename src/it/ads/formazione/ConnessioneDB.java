package it.ads.formazione;


import java.sql.Connection;
import java.sql.DriverManager;

public class ConnessioneDB {
	
	private String tipoConn;
	private String nomeDBConn;
	private Connection connDB;
	
	
	public String getTipoConn() {
		return tipoConn;
	}


	public String getNomeDBConn() {
		return nomeDBConn;
	}


	public Connection getConnDB() {
		return connDB;
	}


	public ConnessioneDB(String tipoConn, String nomeDBConn) throws Exception{		
		switch (tipoConn.toLowerCase()){
			case "sqlite":
				try {
					Class.forName("org.sqlite.JDBC");
				    connDB = DriverManager.getConnection("jdbc:sqlite:" + nomeDBConn + ".db");
				    this.tipoConn=tipoConn;
				    this.nomeDBConn=nomeDBConn;
				}
				catch ( Exception e ) {
					System.err.println( e.getClass().getName() + ": " + e.getMessage() );
					throw new Exception("Impossibile connettersi al DB!");
				}
				break;
			default:
				throw new Exception("Tipo Connessione non corretta!");
		}
	}
	
}
