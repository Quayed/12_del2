package zybo_client;

import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Zybo_Client
{

    public static void main(String[] args) throws IOException, InterruptedException, ConnectException
    {
        boolean connected = false;
        Date rawDate = new Date();
        SimpleDateFormat sdataSocket = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = sdataSocket.format(rawDate);
        java.util.Scanner key = new java.util.Scanner(System.in);
        FTP_Client FTP = new FTP_Client();
        System.out.println(date);
        System.out.println("Zybo FTP/TCP-Connecter v0.1");
        System.out.println("\nEnter username: ");
        String pass;
        String user = key.nextLine();
        if (user != null)
        {
            System.out.println("\nEnter password: ");
            pass = key.nextLine();
            if (pass != null)
            {
                try
                {
                    System.out.println("\nConnecting to server...");
                    FTP.connect("192.168.0.38", user, pass);
                    pass = null;
                    user = null;
                }
                catch (ConnectException e)
                {
                    //e.printStackTrace();
                    System.out.println("Connection timeout!");
                    System.exit(-1);
                }
                connected = true;
            }
        }
        if (connected)
        {
            FTP.send("HELP");
        }

        //FTP.getMSG("LIST");
    }

}
