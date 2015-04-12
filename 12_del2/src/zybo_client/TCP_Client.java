package zybo_client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import shared.SocketHandler;

public class TCP_Client
{

    private String modifiedSentence;
    private SocketHandler socketHandler;

    public boolean connect() throws IOException
    {
        try
        {
            socketHandler = new SocketHandler("2.108.207.65", 8001);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public void send(String output) throws IOException
    {
        socketHandler.println(output);
        modifiedSentence = socketHandler.readLine();
        System.out.println("\n" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + " - FROM SERVER: " + modifiedSentence);
    }
}
