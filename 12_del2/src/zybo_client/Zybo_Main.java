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
import java.util.InputMismatchException;

public class Zybo_Main
{

    public static void main(String[] args) throws IOException, InterruptedException, ConnectException, UnknownHostException, SocketException
    {
        boolean connected;
        String pass;
        String user;
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Scanner key = new java.util.Scanner(System.in);
        java.util.Scanner ints = new java.util.Scanner(System.in);
        FTP_Client FTP = new FTP_Client();
        while (true)
        {
            connected = false;
            System.out.println("\nZybo FTP/TCP-Connecter v0.1");
            System.out.println("\nType '1' for FTP:\n\nType '2' for TCP:");
            try
            {
                int type = ints.nextInt();
                if (type == 1)
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
                                System.out.println("\n" + date.format(new Date()) + " - Connecting to server...");

                                if (FTP.connect(user, pass))
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
                                System.out.println("\n" + date.format(new Date()) + " - Connection timed out.");
                            }
                            catch (UnknownHostException e)
                            {
                                //e.printStackTrace();
                                System.out.println("\n" + date.format(new Date()) + " - Unknown host.");
                            }
                            catch (SocketException e)
                            {
                                //e.printStackTrace();
                                System.out.println("\n" + date.format(new Date()) + " - Network is unreachable.");
                            }

                        }
                    }
                    while (connected)
                    {
                        System.out.println("Type '0' to return to main menu\n\nType '1' to list files:\n\nType '2' to retrieve file:\n\nType '3' to delete file:");
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
                                    //e.printStackTrace();
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
                            System.out.println("\n" + date.format(new Date()) + " - " + FTP.ftpHandler.send("DELE " + name));
                        }
                        else if (type == 0)
                        {
                            System.out.println("\n" + date.format(new Date()) + " - Disconnected");
                            break;
                        }
                    }
                }
                else if (type == 2)
                {
                    try
                    {
                        TCP_Client tcp = new TCP_Client();
                        System.out.println("\nConnected on port 8001.");
                        
                        getTcpMenu();
                        
                        while (true)
                        {
                            try
                            {
                                String cmd = key.nextLine();
                                if (cmd.equals("?"))
                                {
                                    getTcpMenu();
                                }

                                else if (cmd.equals("0"))
                                {
                                    System.out.println("\n" + date.format(new Date()) + " - Disconnected.");
                                    break;
                                }

                                else if (cmd.equals("1"))
                                {
                                    tcp.send("LIST");
                                }

                                else if (Integer.parseInt(cmd) > 1 && Integer.parseInt(cmd) < 6)
                                {
                                    System.out.println("\nEnter sensor-number:");

                                    int sensorNumber = ints.nextInt();
                                    int answer = Integer.parseInt(cmd) - 1;
                                    String output = null;
                                    switch (answer)
                                    {
                                        case 1:
                                            output = "INCR";
                                            break;
                                        case 2:
                                            output = "DECR";
                                            break;
                                        case 3:
                                            output = "STAR";
                                            break;
                                        case 4:
                                            output = "STOP";
                                            break;
                                    }
                                    tcp.send(output + "_" + sensorNumber);
                                }

                                else if (cmd.equals("6"))
                                {
                                    tcp.send("DELE");
                                }

                                else
                                {
                                    System.out.println("\n" + date.format(new Date()) + " - Unknown menu.");
                                }
                            }

                            catch (NumberFormatException | InputMismatchException e)
                            {
                                //e.printStackTrace();
                                System.out.println("\n" + date.format(new Date()) + " - Unknown menu.");
                            }
                        }
                    }

                    catch (UnknownHostException | SocketException e)
                    {
                        //e.printStackTrace();
                        System.out.println("\n" + date.format(new Date()) + " - Network is unreachable.");
                    }
                }
            }

            catch (NumberFormatException | InputMismatchException e)
            {
                //e.printStackTrace();
                System.out.println("\n" + date.format(new Date()) + " - Wrong Input.");
            }
        }
    }

    private static void getTcpMenu()
    {
        System.out.println("\nType '1' to list sensors:\n\nType '2' to increase sample rate:\n\nType '3' to decrease sampling rate");
        System.out.println("\nType '4' to start logging:\n\nType '5' to stop logging:\n\nType '6' to delete sensor-log:");
        System.out.println("\nType '0' to return to main menu:\n\nType '?' to display help");
    }

}
