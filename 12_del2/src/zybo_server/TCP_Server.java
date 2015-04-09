package zybo_server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.SocketHandler;

public class TCP_Server {

	SocketHandler socketHandler;
	
	public TCP_Server() throws FileNotFoundException, IOException {
		Sensors sensor = new Sensors();
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(8001);
		
		System.out.println("Ready for connection");

		Socket connectionSocket = welcomeSocket.accept();

		socketHandler = new SocketHandler(connectionSocket);

		while (true) {
			
			System.out.println("Server running on port 8001");

			clientSentence = socketHandler.readLine();
			
			System.out.println("Received: " + clientSentence);

			if(clientSentence.length() == 6){
				if (clientSentence.startsWith("INCR ")) {
					int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
					sensor.increase(sensorNumber);
					// returns the same command
					socketHandler.println(clientSentence);
				}

				else if (clientSentence.contains("DECR")) {
					int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
					sensor.decrease(sensorNumber);
					// returns the same command
					socketHandler.println(clientSentence);
				}

				else if (clientSentence.contains("STOP")) {
					int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
					sensor.stop(sensorNumber);
					// returns the same command
					socketHandler.println(clientSentence);
				}

				else if (clientSentence.contains("STAR")) {
					int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
					capitalizedSentence = sensor.start(sensorNumber).toUpperCase();
					socketHandler.println(capitalizedSentence);
				} else 
					unknownCommand();
				
			} else 
				unknownCommand();
			
		}
	}
	
	private void unknownCommand() throws IOException {
		String capitalizedSentence;
		String answer = "Unknown command!";
		System.out.println(answer);
		capitalizedSentence = answer.toUpperCase();
		socketHandler.println(capitalizedSentence);
	}
	
	public static void main(String argv[]) throws Exception {
		new TCP_Server();
	}

}
