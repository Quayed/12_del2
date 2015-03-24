package boundary;

public interface IUI {

	String[] getHostAndPort(String defaultHost, int defaultPort);

	void message(String string);

	String userInteraction();

	String getDisplayText();

	void disconnected();

}