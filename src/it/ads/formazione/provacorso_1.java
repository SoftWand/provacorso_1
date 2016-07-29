package it.ads.formazione;

import java.io.*;
import java.util.List;



public class provacorso_1 {

	public static void main(String[] args) {
		
		Cliente cliente = new Cliente("DB_Clienti" , "Clients");
		
		cliente.creaDBClients();
		cliente.creaTabellaClients();
		
		try{
			FileReader fileClienti = new FileReader("client.txt");
			BufferedReader bufferFile = new BufferedReader (fileClienti);
			
			int i=0;
			String rigaFile;
			while ((rigaFile = bufferFile.readLine()) != null){
				cliente.estraiCliente(i++,rigaFile);
				cliente.inserisciCliente();
			}
		}
		catch(Exception e){
			System.out.println("il File non esiste!");
		}
		
		
		List<Cliente> clienti = cliente.findAll();
		cliente.stampaRisultati(clienti);
	}

}
