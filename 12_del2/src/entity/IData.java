package entity;

public interface IData {

	String pullData();

	void putData(String data);

	boolean hasData();

	boolean isConnected();

	void setConnected(boolean state);
}