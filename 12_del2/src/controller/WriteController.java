package controller;

import logic.ILogic;
import boundary.IUI;

// NOTE:
// This class doesn't necessarily need a new 'Thread', it could instead use the 'main-Thread' that starts the program.
public class WriteController extends Thread {

	private IUI ui;
	private ILogic logic;

	public WriteController(IUI ui, ILogic logic) {
		this.ui = ui;
		this.logic = logic;
	}

	@Override
	public void run() {
		// Recursive call
		while (true)
			userMessage();
	}

	// this method asks the user to reconnect if disconnected, else it asks the user for an input, which is handled and passed to the logic
	private void userMessage() {
		String input = ui.userInteraction().toLowerCase();

		// user input option
		switch (input) {
		case "s":
			logic.read();
			break;

		case "t":
			logic.tare();
			break;

		case "z":
			logic.zero();
			break;

		case "d":
			// Display text on Scale
			logic.displayText(ui.getDisplayText());
			break;

		case "dw":
			// Display Weight on Scale
			logic.displayWeight();
			break;

		case "rm20 s":
			// Read message String
			logic.readMessage("s");
			break;

		case "rm20 i":
			// Read message Integer
			logic.readMessage("i");
			break;
		}

		// reconnect part
		if (!logic.isConnected()) {
			ui.disconnected();
			Controller.connect(ui, logic);
		}

	}

}
