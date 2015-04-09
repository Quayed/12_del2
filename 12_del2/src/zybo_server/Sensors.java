package zybo_server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Sensors
{

    public Sensors() throws FileNotFoundException, IOException
    {
        FileReader fil = new FileReader("Sensors.txt");
        BufferedReader ind = new BufferedReader(fil);
        String linje = ind.readLine();
        while (linje != null)
        {
            String[] bidder = linje.split("-");
            int sensorRate = Integer.parseInt(bidder[1]);
            String sensorName = bidder[0];
            addSensor(sensorName,sensorRate);
            linje = ind.readLine();
        }
    }
    
    private void addSensor(String SensorName, int sensorRate){
    
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
