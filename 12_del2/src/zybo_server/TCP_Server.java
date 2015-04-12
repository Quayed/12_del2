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

public class TCP_Server
{

    private final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    SocketHandler socketHandler;

    public TCP_Server() throws FileNotFoundException, IOException, InterruptedException, SocketException
    {
        SensorHandler sensor = new SensorHandler();
        String clientSentence;
        ServerSocket welcomeSocket = new ServerSocket(8001);
        
        while (true)
        {
            System.out.println("\n" + date.format(new Date()) + " - Ready for connections on port 8001");
            try
            {

                Socket connectionSocket = welcomeSocket.accept();

                socketHandler = new SocketHandler(connectionSocket);

                System.out.println("\n" + date.format(new Date()) + " - Client connected on port 8001");

                clientSentence = socketHandler.readLine();

                System.out.println("\n" + date.format(new Date()) + " - Received: " + clientSentence);

                if (clientSentence.length() == 6)
                {
                    if (clientSentence.startsWith("INCR"))
                    {
                        int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
                        socketHandler.println(sensor.increase(sensorNumber));
                    }

                    else if (clientSentence.startsWith("DECR"))
                    {
                        int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
                        socketHandler.println(sensor.decrease(sensorNumber));
                    }

                    else if (clientSentence.startsWith("STOP"))
                    {
                        int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
                        socketHandler.println(sensor.stop(sensorNumber));
                    }

                    else if (clientSentence.startsWith("STAR"))
                    {
                        int sensorNumber = (clientSentence.charAt(5) - '0'); // Get the sensorNumber
                        socketHandler.println(sensor.start(sensorNumber));
                    }

                    else
                    {
                        String answer = "\n" + date.format(new Date()) + " - Unknown command!";
                        System.out.println(answer);
                        socketHandler.println("Unknown command.");
                    }

                }
                else if (clientSentence.equals("LIST"))
                {
                    socketHandler.println(sensor.list());
                }

                else if (clientSentence.contains("DELE"))
                {
                    socketHandler.println(sensor.deleteLog());
                }

                else
                {
                    String answer = "\n" + date.format(new Date()) + " - Unknown command!";
                    System.out.println(answer);
                    socketHandler.println("Unknown command.");
                }
            }
            catch (BindException e)
            {
                System.out.println("\n" + date.format(new Date()) + " - Address already in use. Exiting.");
                System.exit(-1);
                //e.printStackTrace();                
            }
            catch (FileNotFoundException e)
            {
                System.out.println("\n" + date.format(new Date()) + " - Cannot read sensor-file. Exiting.");
                System.exit(-1);
                //e.printStackTrace();                
            }
            catch (SocketException e)
            {
                //e.printStackTrace();
                System.out.println("\n" + date.format(new Date()) + " - Client disconnected.");
            }
            catch (IndexOutOfBoundsException e)
            {
                System.out.println("\n" + date.format(new Date()) + " - Sensor doesn't exist.");
                socketHandler.println("Unsuccessful, no sensor with that value. Try to print list of sensors.");
                //e.printStackTrace();                
            }
        }
    }

    public static void main(String argv[]) throws Exception
    {
        new TCP_Server();
    }
}
