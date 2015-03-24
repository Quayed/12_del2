package controller;

import entity.IData;
import boundary.IUI;

public class ReadController extends Thread {

	private IUI ui;
	private IData data;

	public ReadController(IUI ui, IData data) {
		this.ui = ui;
		this.data = data;
	}

	// this controller continuously tries to pull any data received from the server (the connection socket stores server inputs in a data stack) then updates the UI
	@Override
	public void run() {
		String input;

		while (true) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			while (data.hasData()) {
				input = data.pullData();
				if (input != null)
					ui.message("Recieved message: " + input);
			}
		}
	}

}