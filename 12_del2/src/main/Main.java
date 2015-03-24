package main;

import logic.Logic;
import controller.Controller;
import entity.Data;
import entity.IData;
import boundary.UI;

public class Main {
	public static void main(String[] args) {
		IData data = new Data();
		new Controller(new UI(), new Logic(data ), data);
	}
}