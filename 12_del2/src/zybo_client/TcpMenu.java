package zybo_client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpMenu
{
    private String answer;
    private final SimpleDateFormat date;
    private final java.util.Scanner key;
    private final java.util.Scanner ints;
    private final TCP_Client tcp;

    public TcpMenu()
    {
        date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        key = new java.util.Scanner(System.in);
        ints = new java.util.Scanner(System.in);
        tcp = new TCP_Client();

    }

    public boolean connectTCP(String ip) throws IOException
    {
        try
        {
            tcp.connect(ip);
            return true;
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
        return false;
    }

    public void getTcpMenu()
    {
        System.out.println("\nType '1' to list sensors:\n\nType '2' to increase sample rate:\n\nType '3' to decrease sampling rate");
        System.out.println("\nType '4' to start logging:\n\nType '5' to stop logging:\n\nType '6' to delete sensor-log:");
        System.out.println("\nType '0' to return to main menu:\n\nType '?' to display help");
    }

    public void getOptions() throws IOException
    {
        while (true)
        {
            answer = key.nextLine();
            if (answer.equals("?"))
            {
                getTcpMenu();
            }

            else if (answer.equals("0"))
            {
                tcp.disconnect();
                System.out.println("\n" + date.format(new Date()) + " - Disconnected TCP.");
                break;
            }

            else if (answer.equals("1"))
            {
                tcp.send("LIST");
            }

            else if (Integer.parseInt(answer) > 1 && Integer.parseInt(answer) < 6)
            {
                System.out.println("\nEnter sensor-number:");
                int sensorNumber = ints.nextInt();
                int answerInt = Integer.parseInt(answer) - 1;
                String output = null;
                switch (answerInt)
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

            else if (answer.equals("6"))
            {
                tcp.send("DELE");
            }

            else
            {
                System.out.println("\n" + date.format(new Date()) + " - Unknown menu.");
            }
        }
    }
}
