package zybo_client;

import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Zybo_Main
{

    public static void main(String[] args) throws IOException, InterruptedException, ConnectException
    {
        boolean connected;
        String pass;
        String user;
        Date rawDate;
        SimpleDateFormat sdataSocket = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Scanner key = new java.util.Scanner(System.in);
        FTP_Client FTP = new FTP_Client();
        while (true)
        {
            connected = false;
            System.out.println("\nZybo FTP/TCP-Connecter v0.1");
            System.out.println("\nType '1' for FTP:\n\nType '2' for TCP:");
            int type = key.nextInt();
            if (type == 1)
            {
                System.out.println("\nEnter username: ");
                key.nextLine();
                user = key.nextLine();
                if (!user.equals(""))
                {
                    System.out.println("\nEnter password: ");
                    pass = key.nextLine();
                    if (!pass.equals(""))
                    {
                        try
                        {
                            rawDate = new Date();
                            System.out.println("\n" + sdataSocket.format(rawDate) + " - Connecting to server...");
                            FTP.connect("192.168.0.38", user, pass);
                            pass = null;
                            user = null;
                        }
                        catch (ConnectException e)
                        {
                            //e.printStackTrace();
                            rawDate = new Date();
                            System.out.println("\n" + sdataSocket.format(rawDate) + " - Connection timeout!");
                            System.exit(-1);
                        }
                        connected = true;
                    }
                }
                if (connected)
                {
                    FTP.send("HELP");

                    key.nextLine();
                }

                //FTP.getMSG("LIST");
            }
        }
    }
}
