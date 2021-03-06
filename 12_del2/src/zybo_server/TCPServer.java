package zybo_server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import shared.SocketHandler;

public class TCPServer {

	private final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private SocketHandler socketHandler;

	public TCPServer() throws FileNotFoundException, IOException, InterruptedException, SocketException {
		SensorHandler sensor = new SensorHandler();
		String clientSentence;
		ServerSocket welcomeSocket = new ServerSocket(8001);
		System.out.println("\n" + date.format(new Date()) + " - Ready for connections on port 8001");
		while (true) {
			try {
				Socket connectionSocket = welcomeSocket.accept();
				socketHandler = new SocketHandler(connectionSocket);
				System.out.println("\n" + date.format(new Date()) + " - Client connected on port 8001");

				while (true) {

					clientSentence = socketHandler.readLine();
					if (clientSentence == null) {
						System.out.println("\n" + date.format(new Date()) + " - Client has disconnected.");
						break;
					}
					System.out.println("\n" + date.format(new Date()) + " - Received: " + clientSentence);

					serverCommand(sensor, clientSentence);
				}
                                
			} catch (BindException e) {
				System.out.println("\n" + date.format(new Date()) + " - Address already in use. Exiting.");
                                welcomeSocket.close();
				System.exit(-1);
			} catch (FileNotFoundException e) {
				System.out.println("\n" + date.format(new Date()) + " - Cannot read sensor-file. Exiting.");
				welcomeSocket.close();
                                System.exit(-1);
			} catch (SocketException e) {
				System.out.println("\n" + date.format(new Date()) + " - Client disconnected.");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("\n" + date.format(new Date()) + " - Sensor doesn't exist.");
				socketHandler.println("Unsuccessful, no sensor with that value. Try to print list of sensors.");
			}
		}
	}

	private void serverCommand(SensorHandler sensor, String clientSentence)
			throws IOException, InterruptedException {
		if (clientSentence.length() == 6) {
			if (clientSentence.startsWith("INCR")) {
				int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
				socketHandler.println(sensor.increase(sensorNumber));
			}

			else if (clientSentence.startsWith("DECR")) {
				int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
				socketHandler.println(sensor.decrease(sensorNumber));
			}

			else if (clientSentence.startsWith("STOP")) {
				int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
				socketHandler.println(sensor.stop(sensorNumber));
			}

			else if (clientSentence.startsWith("STAR")) {
				int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
				socketHandler.println(sensor.start(sensorNumber));
			}

			else {
				String answer = "\n" + date.format(new Date()) + " - Unknown command!";
				System.out.println(answer);
				socketHandler.println("Unknown command.");
			}

		} else if (clientSentence.equals("LIST")) {
			socketHandler.println(sensor.list());
		}

		else if (clientSentence.contains("DELE")) {
			socketHandler.println(sensor.deleteLog());
		}

		else {
			String answer = "\n" + date.format(new Date()) + " - Unknown command!";
			System.out.println(answer);
			socketHandler.println("Unknown command.");
		}
	}

	public static void main(String argv[]) throws Exception {
		new TCPServer();
	}
}
