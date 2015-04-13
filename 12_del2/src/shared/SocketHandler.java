package shared;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketHandler {

	private final DataOutputStream out;
	private final BufferedReader in;
	private Socket socket;

	public SocketHandler(String host, int port) throws UnknownHostException, IOException {
		this(new Socket(host, port));
	}

	public SocketHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.out = new DataOutputStream(socket.getOutputStream());
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public SocketHandler(SocketHandler socketHandler, int port) throws IOException {
		this(new Socket(socketHandler.socket.getInetAddress(), port));
	}

	public void print(String msg) throws IOException {
		out.writeBytes(msg);
	}

	public void println(String msg) throws IOException {
		print(msg + "\r\n");
	}

	public String readLine() throws IOException {
		return in.readLine();
	}

	public final void disconnect() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InetAddress getInetAddress() {
		return socket.getInetAddress();
	}

	public BufferedReader getIn() {
		return in;
	}

	// public void disconnect() {
	// try {
	// socket.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

}