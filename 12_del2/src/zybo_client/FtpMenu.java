package zybo_client;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FtpMenu
{
    private String pass;
    private String user;
    private int type;
    private final SimpleDateFormat date;
    private final java.util.Scanner key;
    private final java.util.Scanner ints;
    private final FTP_Client FTP;

    public FtpMenu()
    {
        date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        key = new java.util.Scanner(System.in);
        ints = new java.util.Scanner(System.in);
        FTP = new FTP_Client();
    }

    public boolean connectFTP(String ip) throws IOException
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
                        System.out.println("\n" + date.format(new Date()) + " - Connecting to server on port 21...");

                        if (FTP.connect(ip, user, pass))
                        {
                            return true;
                        }
                        else
                        {
                            System.out.println("\nCredentials denied!\n");
                            return false;
                        }
                    }
                    catch (ConnectException e)
                    {
                        // e.printStackTrace();
                        System.out.println("\n" + date.format(new Date()) + " - Connection timed out.");
                    }
                    catch (UnknownHostException e)
                    {
                        // e.printStackTrace();
                        System.out.println("\n" + date.format(new Date()) + " - Unknown host.");
                    }
                    catch (SocketException e)
                    {
                        // e.printStackTrace();
                        System.out.println("\n" + date.format(new Date()) + " - Network is unreachable.");
                    }

                }
            }
        return false;
        }

    

    public void getMenu()
    {
        System.out.println("Type '0' to return to main menu\n\nType '1' to list files:\n\nType '2' to retrieve file:\n\nType '3' to delete file:");
    }

    public void getOptions() throws IOException
    {
        while (true)
        {
            type = ints.nextInt();
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
                String name = key.nextLine();
                String answer = FTP.getData("RETR " + name);
                System.out.println(answer);
                if (!answer.equals("\nFile not found"))
                {
                    try
                    {
                        FileWriter file = new FileWriter("ftp-files/" + name.split("/")[name.split("/").length - 1]);
                        PrintWriter out = new PrintWriter(file);
                        out.write(answer);
                        out.close();
                        System.out.println("\n" + date.format(new Date()) + " - <" + name + "> has been downloaded succesfully!");
                    }
                    catch (FileNotFoundException e)
                    {
                        // e.printStackTrace();
                        System.out.println("\n" + date.format(new Date()) + " - File not writable on local disk!");
                    }
                }
                else
                {
                    System.out.println("\n" + date.format(new Date()) + " - 550 " + answer + ".");
                }
            }
            else if (type == 3)
            {
                System.out.println("\nEnter filename:\n");
                String name = key.nextLine();
                System.out.println("\n" + date.format(new Date()) + " - " + FTP.send("DELE " + name));
            }
            else if (type == 0)
            {
                System.out.println("\n" + date.format(new Date()) + " - Disconnected");
                break;
            }
        }
    }
}
