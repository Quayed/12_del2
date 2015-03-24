package entity;

import java.util.LinkedList;
import java.util.Queue;

public class Data implements IData {
	private boolean connected;
	Queue<String> dataBuffer = new LinkedList<String>();

	@Override
	public String pullData() {
		return dataBuffer.poll();
	}

	@Override
	public void putData(String data) {
		dataBuffer.add(data);
	}

	@Override
	public boolean hasData() {
		return !dataBuffer.isEmpty();
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void setConnected(boolean state) {
		this.connected = state;
	}

}