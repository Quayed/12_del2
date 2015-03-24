package logic;

import java.io.IOException;
import java.net.UnknownHostException;

public interface ILogic {

	void connect(String host, int port) throws UnknownHostException, IOException;

	void tare();

	void read();

	void zero();

	void displayText(String msg);

	void displayWeight();

	void readMessage(String string);

	boolean isConnected();

}
