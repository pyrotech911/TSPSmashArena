package game;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.file.Paths;

import com.google.gson.*;

public class Arena {
	
	private static LobyView mainView;
	private View clientView;
	
	/*
	public static void main(String[] args){
		mainView = new LobyView(new Arena());
	}
	*/
	public Arena(){
		this.clientView = null;
	}
	
	public void host(int numberOfPlayers, int port){
		Process serverProcess = null;
		String currentPath = Paths.get("").toAbsolutePath().toString();
		String[] commandArgs = {"java","-cp",currentPath + File.pathSeparator + currentPath + 
				"/lib/gson-2.2.4.jar" + File.pathSeparator + currentPath + "/bin" + File.pathSeparator + 
				currentPath + "/arena.jar","game.Server",String.valueOf(numberOfPlayers),Integer.toString(port)};
		
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(commandArgs);
			processBuilder.redirectErrorStream(true);
			processBuilder.redirectOutput(new File("server_output.txt"));
			serverProcess = processBuilder.start();
			System.out.println("Server started!");
		}
		catch (IOException ioe) {
			System.err.println("IOException attempting to start the server.");
			System.exit(1);
		}
		
		if (serverProcess == null) {
			System.err.println("Failed to execute the server.");
			System.exit(1);
		}
		
		join("localhost", port);
		
		System.out.println("Terminating the server.");
		serverProcess.destroy();
	}
	
	public void join(String ip, int port){
		InetAddress serverAddr = null;
		try {
			serverAddr = InetAddress.getByName(ip);
		}
		catch (UnknownHostException uhe) {
			System.out.println("Could not resolve the server address.  Unknown host.");
			System.exit(1);
		}
		catch (Exception e) {
			System.out.println("Could not aquire the server address.  Unspecified error.");
			System.exit(1);
		}
		if(clientView == null){
			//clientView = new View();
		}
		else{
			//clientView.setVisible(true);
		}
		Client theClient = null;
		
		try {
			theClient = new Client(serverAddr, port);
		}
		catch (IOException e) {
			System.err.println("Failed to create game client. " + e.getMessage());
			System.exit(1);
		}
		System.out.println("Clent Started");
		try {
			theClient.play();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		theClient.getView().setVisible(false);
		mainView.setVisible(true);
	}
	
	public static void main(String[] args){
		int port = 5379;
		String selection = "0";
		int choice = -1;
		Scanner inputScanner = new Scanner(System.in);

		System.out.println("Welcome. " +
				"Enter 0 if you wish to host a game; otherwise, enter the IP address of the game you wish to join.");
		selection = inputScanner.nextLine();
		try {
			choice = Integer.parseInt(selection);
		}
		catch (NumberFormatException nfe) {
			choice = -1;
		}
		
		Process serverProcess = null;
		if (choice == 0) { // Host and connect on loopback

			inputScanner = new Scanner(System.in); // Crudely reset scanner
			
			int numberOfPlayers = 0;
			while (numberOfPlayers < 1 || numberOfPlayers > 4) {
				System.out.println("Please enter the number of players (maximum 4) in the game:");
				numberOfPlayers = inputScanner.nextInt();
			}
			
			String currentPath = Paths.get("").toAbsolutePath().toString();
			String[] commandArgs = {"java","-cp",currentPath + File.pathSeparator + currentPath + 
					"/lib/gson-2.2.4.jar" + File.pathSeparator + currentPath + "/bin" + File.pathSeparator + 
					currentPath + "/arena.jar","game.Server",String.valueOf(numberOfPlayers),Integer.toString(port)};
			
			try {
				ProcessBuilder processBuilder = new ProcessBuilder(commandArgs);
				processBuilder.redirectErrorStream(true);
				processBuilder.redirectOutput(new File("server_output.txt"));
				serverProcess = processBuilder.start();
				System.out.println("Server started!");
			}
			catch (IOException ioe) {
				System.err.println("IOException attempting to start the server.");
				System.exit(1);
			}
			
			if (serverProcess == null) {
				System.err.println("Failed to execute the server.");
				System.exit(1);
			}
			else{//get ip address of server
				Enumeration<NetworkInterface> nets = null;
				try {
					nets = NetworkInterface.getNetworkInterfaces();//Get list of all network interfaces
				} catch (SocketException e) {
					nets = null;
				}
				if(nets != null){
					for (NetworkInterface netint : Collections.list(nets)){
						try {//Find the interface that is active and host is communicating on
							if(netint.isUp() && !netint.isPointToPoint() && !netint.isVirtual() && !netint.isLoopback()){
								Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
								for (InetAddress inetAddress : Collections.list(inetAddresses)) {
									String inet = inetAddress.toString();
									inet = inet.substring(1);//Find the address on the interface we want that is "real" and IPv4.
									if(inet.substring(0,7).compareTo("169.254") != 0 && !inet.contains(":")){
										System.out.println("Listening on: "+inet+":"+port);
									}
								}
							}
						} catch (SocketException e) {
							System.err.println("Unable to get local address server is utilizing.");
						}
					}
				}
				else{
					System.err.println("Unable to get local interface server is utilizing.");
				}
			}
		}
		inputScanner.close();
		
		InetAddress serverAddr = InetAddress.getLoopbackAddress(); // default IP address
		int serverPort = port;
	
		Client theClient = null;
		if (choice != 0) {
			try {
				serverAddr = InetAddress.getByName(selection);
			}
			catch (UnknownHostException uhe) {
			System.out.println("Could not connect to server.  Unknown host.");
			System.exit(1);
			}
			catch (Exception e) {
			System.out.println("Could not connect to server.  Unspecified error.");
			System.exit(1);
			}
		}

		try {
			theClient = new Client(serverAddr,serverPort);
		}
		catch (IOException e) {
			System.err.println("Failed to create game client. " + e.getMessage());
			System.exit(1);
		}
		
		try {
			theClient.play();
		}
		catch (Exception e) {
			
		}
		
		theClient.getView().setVisible(false);
		if (serverProcess != null) {
			System.out.println("Terminating the server.");
			serverProcess.destroy();
		}
			
		System.out.println("Game over. Thanks for playing!");
		System.exit(0); //super quit
	}
	
	
}