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

    public String send(String in) throws IOException
    {
        out.println(in);
        out.flush();
        return getAnswer();
    }

    private String getAnswer() throws IOException
    {
        while (true)
        {
            String input = in.readLine();
            if (input.length() >= 3 && input.charAt(3) != '-' && Character.isDigit(input.charAt(0)) && Character.isDigit(input.charAt(1)) && Character.isDigit(input.charAt(2)))
            {
                return input;
            }
        }
    }

    public boolean connect(String ip, String user, String pass) throws IOException, InterruptedException
    {
        socket = new Socket(ip, 21);
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        getAnswer();
        send("USER " + user);
        if (send("PASS " + pass).contains("230"))
        {
            return true;
        }
        return false;
    }

    private Socket getData() throws IOException
    {
        // Enabling passive to recieve data:
        String adress = send("PASV");
        // Append tokens with ',':
        StringTokenizer st = new StringTokenizer(adress, "(,)");
        if (st.countTokens() < 7)
        {
            SimpleDateFormat sdataSocket = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss - ");
            Date rawDate = new Date();
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
        //rawDate = new Date();
        sb.append("\n");
        while (input != null)
        {
            sb.append(input).append("\n");
            input = dataIn.readLine();
        }
        dataIn.close();
        dataSocket.close();
        getAnswer();
        return sb.toString();
    }
}
