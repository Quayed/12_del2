package zybo_client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.InputMismatchException;

public class Zybo_Main
{

    private int answer;
    private final java.util.Scanner ints;

    private Zybo_Main(String ip) throws IOException
    {
        ints = new java.util.Scanner(System.in);
        while (true)
        {
            try
            {
                getMenu();
                answer = ints.nextInt();
                if (answer == 1)
                {
                    FtpMenu ftp = new FtpMenu();
                    if (ftp.connectFTP(ip))
                    {
                        ftp.getMenu();
                        ftp.getOptions();
                    }
                }

                else if (answer == 2) // TCP CONNECTION
                {
                    TcpMenu tcp = new TcpMenu();
                    if (tcp.connectTCP(ip))
                    {
                        tcp.getTcpMenu();
                        tcp.getOptions();
                    }
                }
            }
            catch (NumberFormatException | InputMismatchException e)
            {
                // e.printStackTrace();
                System.out.println(" - Wrong Input.");
            }
        }
    }
    
    public void getMenu()
    {
        System.out.println("Press '1' for FTP");
        System.out.println("Press '2' for TCPP");
    }
    
    public static void main(String[] args) throws IOException, InterruptedException, ConnectException, UnknownHostException, SocketException
    {
        if (args.length == 1)
        {
            new Zybo_Main(args[0]);
        }
        else
        {
            new Zybo_Main("localhost");
        }
    }
}
