package zybo_client;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        String ip;
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
                System.out.println("\nEnter IP-adress: ");
                key.nextLine();
                ip = key.nextLine();
                if (!ip.equals(""))
                {
                    System.out.println("\nEnter username: ");
                    user = key.nextLine();
                    if (!user.equals(""))
                    {
                        System.out.println("\nEnter password: ");
                        pass = key.nextLine();
                        if (!pass.equals(""))
                        {
                            try
                            {
                                System.out.println("\n" + sdataSocket.format(new Date()) + " - Connecting to server...");

                                if (FTP.connect(ip, user, pass))
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
                                System.out.println("\n" + sdataSocket.format(new Date()) + " - Connection timed out. Exiting!");
                                System.exit(-1);
                            }
                        }
                    }
                    while (connected)
                    {
                        System.out.println("\nType '0' to return to main menu\n\nType '1' to list files:\n\nType '2' to retrieve file:\n\nType '3' to delete file:");
                        type = key.nextInt();
                        if (type == 1)
                        {
                            String answer = FTP.getData("LIST");
                            if (answer.length() > 1)
                            {
                                System.out.println("\nFiles: " + answer);
                            }
                            else
                            {
                                System.out.println("\nNo files on Zybo!");
                            }
                        }
                        else if (type == 2)
                        {
                            System.out.println("\nEnter filename:\n");
                            key.nextLine();
                            String name = key.nextLine();
                            String answer = FTP.getData("RETR " + name);
                            if (!answer.equals("File not found"))
                            {
                                try
                                {
                                    FileWriter file = new FileWriter(name);
                                    PrintWriter out = new PrintWriter(file);
                                    out.write(answer);
                                    out.close();
                                    System.out.println("\n" + sdataSocket.format(new Date()) + " - (" + name + ") has been downloaded succesfully!");
                                }
                                catch (FileNotFoundException e)
                                {
                                    //e.printStackTrace();
                                    System.out.println("\n" + sdataSocket.format(new Date()) + " - File not writable on local disk!");
                                }
                            }
                            else
                            {
                                System.out.println("\n" + sdataSocket.format(new Date()) + " - 550 " + answer + ".");
                            }
                        }
                        else if (type == 3)
                        {
                            System.out.println("\nEnter filename:\n");
                            key.nextLine();
                            String name = key.nextLine();
                            System.out.println("\n" + sdataSocket.format(new Date()) + " - " + FTP.send("DELE " + name));

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
}
