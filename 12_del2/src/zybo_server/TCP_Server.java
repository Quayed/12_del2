package zybo_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP_Server
{

    public static void main(String argv[]) throws Exception
    {
        Sensors sensor = new Sensors();
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(8001);

        while (true)
        {
            System.out.println("Server running on port 8001");
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            System.out.println("Received: " + clientSentence);
            
            if (clientSentence.contains("INCR"))
                {
                int sensorNumber = (clientSentence.charAt(5)-'0');  // Get the sensorNumber
                sensor.increase(sensorNumber);
            }
            
            if (clientSentence.contains("DECR"))
                {
                int sensorNumber = (clientSentence.charAt(5)-'0');  // Get the sensorNumber
                sensor.decrease(sensorNumber);
            }
            
            if (clientSentence.contains("STOP"))
                {
                int sensorNumber = (clientSentence.charAt(5)-'0');  // Get the sensorNumber
                sensor.stop(sensorNumber);
            }
            
            if (clientSentence.contains("STAR"))
                {
                int sensorNumber = (clientSentence.charAt(5)-'0');  // Get the sensorNumber
                sensor.start(sensorNumber);
            }
            
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
        }
    }
}
