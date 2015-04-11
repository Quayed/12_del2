package zybo_server;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class SampleHandler implements Runnable
{
    private final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    public SampleHandler()
    {
        
    }

    public void run()
    {
        
        FileWriter file = new FileWriter("/Home/FTP/" + name);
        PrintWriter out = new PrintWriter(file);
        out.write(answer);
        out.close();
    }

}
