package zybo_server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleHandler implements Runnable
{

    private final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final String sensorName;
    private final int sampleRate;
    private final int sampleValue;

    public SampleHandler(String name, int rate, int value)
    {
        sensorName = name;
        sampleRate = rate;
        sampleValue = value;
    }

    public void saveToFile()
    {
        try
        {
            FileWriter file = new FileWriter("tmp/SensorData.log", true);
            PrintWriter out = new PrintWriter(file);
            out.println(date.format(new Date()) + " - Value of " + sensorName + " = " + sampleValue + " (" + sampleRate + " sec. sample rate)");
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("\n" + date.format(new Date()) + " - Cannot write sensor-data.");
        }
    }

    public void run()
    {
        while (true)
        {
            try
            {
                Thread.sleep(sampleRate*1000);
                saveToFile();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }
}
