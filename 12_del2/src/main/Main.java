package main;
// OK fra Thomas
import logic.Logic;
import controller.Controller;
import entity.Data;
import entity.IData;

public class Main {
	public static void main(String[] args) {
		IData data = new Data();
		new Controller(new Logic(data ), data);
	}
}