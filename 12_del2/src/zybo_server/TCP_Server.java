package zybo_server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import shared.SocketHandler;

public class TCP_Server
{

    SocketHandler socketHandler;

    public TCP_Server() throws FileNotFoundException, IOException
    {
        try
        {
            Sensors sensor = new Sensors();
            String clientSentence;
            ServerSocket welcomeSocket = new ServerSocket(8001);

            System.out.println("\nReady for connection on port 8001");

            Socket connectionSocket = welcomeSocket.accept();

            socketHandler = new SocketHandler(connectionSocket);

            System.out.println("\nClient connected on port 8001");

            while (true)
            {
                clientSentence = socketHandler.readLine();

                System.out.println("Received: " + clientSentence);

                if (clientSentence.length() == 6)
                {
                    try
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
                            unknownCommand();
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println("Sensor doesn't exist.");
                        socketHandler.println("Unsuccessful, no sensor with that value. Try to print list of sensors.");
                        //e.printStackTrace();                
                    }
                }
                else if (clientSentence.equals("LIST"))
                {
                    socketHandler.println(sensor.list());
                }
                else
                {
                    unknownCommand();
                }
            }
        }
        catch (BindException e)
        {
            System.out.println("Address already in use. Exiting.");
            System.exit(-1);
            //e.printStackTrace();                
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Cannot read sensor-file. Exiting.");
            System.exit(-1);
            //e.printStackTrace();                
        }
    }

    private void unknownCommand() throws IOException
    {
        String answer = "Unknown command!";
        System.out.println(answer);
        socketHandler.println(answer);
    }

    public static void main(String argv[]) throws Exception
    {
        new TCP_Server();
    }

}
