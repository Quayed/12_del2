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

                            if (FTP.connect("192.168.0.38", user, pass))
                            {
                                connected = true;
                            }
                            else
                            {
                                System.out.println("\nCredentials denied!\n");
                            }
                        }
                        catch (ConnectException e)
                        {
                            //e.printStackTrace();
                            rawDate = new Date();
                            System.out.println("\n" + sdataSocket.format(rawDate) + " - Connection timeout!");
                            System.exit(-1);
                        }
                    }
                }
                while (connected)
                {
                    System.out.println("\nType '0' to return to main menu\n\nType '1' to list files:\n\nType '2' to retrieve file:");
                    type = key.nextInt();
                    if (type == 1)
                    {
                        System.out.println("\nFiles: " + FTP.getMSG("LIST"));
                    }
                    else if (type == 2)
                    {
                        System.out.println(FTP.getMSG("RETR examples.desktop"));
                        
                    }
                    else if (type == 0)
                    {
                        break;
                    }
                }
            }
        }
    }
}
