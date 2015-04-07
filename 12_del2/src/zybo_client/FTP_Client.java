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
        Date rawDate = new Date();
        SimpleDateFormat sdataSocket = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss - ");
        String date = sdataSocket.format(rawDate);

    public String send(String in) throws IOException
    {
        System.out.println(date + "MSG: " + in);
        out.println(in);
        out.flush();
        return getAnswer();
    }

    private String getAnswer() throws IOException
    {
        while (true)
        {
            String s = in.readLine();
            System.out.println(date + "MSG: " + s);
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
        String adress = send("PASV");
        StringTokenizer st = new StringTokenizer(adress, "(,)");
        if (st.countTokens() < 7)
        {
            throw new IOException("Message recieved does not follow the regular 7 token syntax (MSG.IP.IP.IP.IP.PORT.PORT");
        }
        for (int i = 0; i < 5; i++)
        {
            st.nextToken();
        }
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
        String s = dataIn.readLine();
        sb.append(date + "\n");
        while (s != null)
        {
            System.out.println("data: " + s);
            sb.append(s + "\n");
            s = dataIn.readLine();
        }
        dataIn.close();
        dataSocket.close();
        getAnswer();
        return sb.toString();
    }
}
