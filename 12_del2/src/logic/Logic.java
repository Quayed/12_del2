package logic;

import java.io.IOException;
import java.net.UnknownHostException;

import controller.Connector;

public class Logic implements ILogic {
	private Connector connector;

	public Logic() {
		connector = new Connector();
	}

	@Override
	public void connect(String host, int port) throws UnknownHostException, IOException {
		connector.connect(host, port);
	}

	@Override
	public void read() {
		connector.sendMessage("S");
	}

	@Override
	public void tare() {
		connector.sendMessage("T");
	}

	@Override
	public void zero() {
		connector.sendMessage("Z");
	}

	@Override
	public void displayText(String msg) {
		connector.sendMessage("D " + msg);
	}

	@Override
	public void displayWeight() {
		connector.sendMessage("DW");
	}

	@Override
	public void readMessage(String type) {
		if (type.equals("s")) {
			connector.sendMessage("RM20 8 \"get\" \"type\" \"random\"");
		} else if (type.equals("i")) {
			connector.sendMessage("RM20 4 \"get\" \"type\" \"random\"");
		}
	}

	@Override
	public boolean isConnected() {
		return connector.isConnected();
	}

}
