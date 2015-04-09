package zybo_server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sensors
{

    List<String> sensorNames = new ArrayList<>();
    List<Integer> sensorRates = new ArrayList<>();
    List<Integer> sensorValues = new ArrayList<>();

    public Sensors() throws FileNotFoundException, IOException
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
        System.out.println("Added" + sensorName + " with update every " + sensorRate + " seconds.");
    }

    public void increase(int sensorNumber)
    {
        // Check for <sensornumber> available
        // Increase <sensorsnumber>
    }

    public void decrease(int sensorNumber)
    {
        // Check for <sensornumber> available
        // Decrease <sensornumber>
    }

    public void stop(int sensorNumber)
    {
        // Check for <sensorNumber> available
        // Stop measurement on <sensorNumber>
    }

    public void start(int sensorNumber)
    {
        // Check for <sensorNumber> available
        // Start measurement on <sensorNumber>
    }

}
