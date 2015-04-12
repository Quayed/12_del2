package zybo_server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SensorHandler
{

    private final List<String> sensorNames = new ArrayList<>();
    private final List<Integer> sensorRates = new ArrayList<>();
    private final List<Integer> sensorValues = new ArrayList<>();

    public SensorHandler() throws FileNotFoundException, IOException
    {
        FileReader fil = new FileReader("Sensors.txt");
        BufferedReader ind = new BufferedReader(fil);

        String linje = ind.readLine();
        while (linje != null)
        {
            String[] bidder = linje.split("_");
            String sensorName = bidder[0];
            int sensorRate = Integer.parseInt(bidder[1]);
            int sensorValue = Integer.parseInt(bidder[2]);
            addSensor(sensorName, sensorRate, sensorValue);
            linje = ind.readLine();
        }
    }

    private void addSensor(String sensorName, int sensorRate, int sensorValue)
    {
        sensorNames.add(sensorName);
        sensorRates.add(sensorRate);
        sensorValues.add(sensorValue);
        System.out.println("Added " + sensorName + " with update every " + sensorRate + " seconds.");
    }

    public String increase(int sensorNumber)
    {
        if (sensorNames.size() >= sensorNumber - 1)
        {
            if (sensorRates.get(sensorNumber - 1) <= 64)
            {
                sensorRates.set(sensorNumber - 1, (2 * sensorRates.get(sensorNumber - 1)));
                String answer = "Successful, Sensor " + sensorNumber + " now has an update rate of " + sensorRates.get(sensorNumber - 1) + " Seconds";
                System.out.println(answer);
                return answer;
            }
        }
        else
        {
            String answer = "Unsuccessful, no sensor with that value. Try to print list of sensors.";
            System.out.println(answer);
            return answer;
        }
        return null;
    }

    public String decrease(int sensorNumber)
    {
        if (sensorNames.size() >= sensorNumber - 1)
        {
            if (sensorRates.get(sensorNumber - 1) > 1)
            {
                sensorRates.set(sensorNumber - 1, (sensorRates.get(sensorNumber - 1) / 2));
                String answer = "Successful, Sensor " + sensorNumber + " now has an update rate of " + sensorRates.get(sensorNumber - 1) + " Seconds";
                System.out.println(answer);
                return answer;
            }
        }
        else
        {
            String answer = "Unsuccessful, no sensor with that value. Try to print list of sensors.";
            System.out.println(answer);
            return answer;
        }
        return null;
    }

    public String stop(int sensorNumber) throws InterruptedException
    {
        if (sensorNames.size() >= sensorNumber - 1)
        {
            for (Thread t : Thread.getAllStackTraces().keySet())
            {
                if (t.getName().equals(sensorNumber + ""))
                {
                    t.interrupt();
                    String answer = "Successful, Sensor " + sensorNumber + " has been stopped";
                    System.out.println(answer);
                    return answer;
                }
            }
        }
        String answer = "Unsuccessful, no active sensor with that value.";
        System.out.println(answer);
        return answer;
    }

    public String start(int sensorNumber) throws IOException
    {
        if (sensorNames.size() >= sensorNumber - 1)
        {
            SampleHandler sample = new SampleHandler(sensorNames.get(sensorNumber - 1), sensorRates.get(sensorNumber - 1), sensorValues.get(sensorNumber - 1));
            Thread sh = new Thread(sample, sensorNumber + "");
            sh.start();
            String answer = "Successful, Sensor " + sensorNumber + " has started logging with an update rate of " + sensorRates.get(sensorNumber - 1) + " Seconds";
            System.out.println(answer);
            return answer;
        }
        else
        {
            String answer = "Unsuccessful, no sensor with that value. Try to print list of sensors.";
            System.out.println(answer);
            return answer;
        }
    }

    public String list()
    {
        String answer = sensorNames.toString();
        System.out.println(answer);
        return answer;
    }

    public String deleteLog() throws IOException
    {
        FileWriter file = new FileWriter("/home/xilinx/SensorData.log");
        PrintWriter out = new PrintWriter(file);
        out.close();
        String answer = "Log has been wiped.";
        System.out.println(answer);
        return answer;
    }
}
