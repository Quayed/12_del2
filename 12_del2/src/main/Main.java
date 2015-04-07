package main;
// OK fra Thomas
import java.io.IOException;

import logic.Logic;
import controller.Connector;
import controller.Controller;
import entity.Data;
import entity.IData;

public class Main {
	public static void main(String[] args) {
		IData data = new Data();
		new Controller(new Logic(data ), data);
/*		
		Connector connector = new Connector(data);
		try {
			connector.connect("localhost", 8000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hej hej");
		connector.sendMessage("S");
		System.out.println(connector.getData());
		connector.sendMessage("S");
		System.out.println(connector.getData());
		connector.sendMessage("RM20 8 \"Indtast\" \"\" \"&3\"");
		System.out.println(connector.getData());
		System.out.println(connector.getData());
*/
	}
}