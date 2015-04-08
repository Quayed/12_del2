package zybo_client;

import java.util.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class FTP_Client
{

    private Socket socket;
    private BufferedReader in;
    private PrintStream out;
    private Date rawDate;
    private SimpleDateFormat sdataSocket;

    void FTP_Client()
    {
        sdataSocket = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss - ");

    }

    public String send(String in) throws IOException
    {
        rawDate = new Date();
        System.out.println(sdataSocket.format(rawDate) + "MSG: " + in);
        out.println(in);
        out.flush();
        return getAnswer();
    }

    private String getAnswer() throws IOException
    {
        while (true)
        {
            String s = in.readLine();
            rawDate = new Date();
            System.out.println(sdataSocket.format(rawDate) + "MSG: " + s);
            if (s.length() >= 3 && s.charAt(3) != '-' && Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(1)) && Character.isDigit(s.charAt(2)))
            {
                return s;
            }
        }
    }

    public void connect(String ip, String user, String pass) throws IOException, InterruptedException
    {
        socket = new Socket(ip, 21);
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        getAnswer();
        send("USER " + user);
        send("PASS " + pass);
    }

    private Socket getData() throws IOException
    {
        // Enabling passive to recieve data:
        String adress = send("PASV");
        // Append tokens with ',':
        StringTokenizer st = new StringTokenizer(adress, "(,)");
        if (st.countTokens() < 7)
        {
            rawDate = new Date();
            throw new IOException(sdataSocket.format(rawDate) + "Message receved does not follow the regular 7-token syntax (MSG.IP.IP.IP.IP.PORT.PORT");
        }
        // Saving the first five tokens (Message + ip-adress):
        for (int i = 0; i < 5; i++)
        {
            st.nextToken();
        }
        // Parsing the last two tokens as integers to calculate port number, by multiplying the sixth byte by 256 and adding the seventh byte (256 * token6 + token7):
        int portNr = 256 * Integer.parseInt(st.nextToken()) + Integer.parseInt(st.nextToken());
        return new Socket(socket.getInetAddress(), portNr);
    }

    public void sendData(String out, String data) throws IOException
    {
        Socket dataSocket = getData();
        PrintStream dataOut = new PrintStream(dataSocket.getOutputStream());
        send(out);
        dataOut.print(data);
        dataOut.close();
        dataSocket.close();
        getAnswer();
    }

    public String getMSG(String out) throws IOException
    {
        Socket dataSocket = getData();
        BufferedReader dataIn = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
        send(out);
        StringBuilder sb = new StringBuilder();
        String input = dataIn.readLine();
        rawDate = new Date();
        sb.append(sdataSocket.format(rawDate)).append("\n");
        while (input != null)
        {
            rawDate = new Date();
            System.out.println(sdataSocket.format(rawDate) + "data: " + input);
            sb.append(input).append("\n");
            input = dataIn.readLine();
        }
        dataIn.close();
        dataSocket.close();
        getAnswer();
        return sb.toString();
    }
}
