package test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class IPhonePushNotification {

	private static final String url = "gateway.sandbox.push.apple.com";
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket(url, 2195);
		OutputStream dos = socket.getOutputStream();
		char command = 0;
		char tokenLen1 = 0;
		char tokenLen2 = 32;
		char[] token = "sdsadsadsa".toCharArray();
		
		char[] msg = "my message".toCharArray();
		char[] msgLen1 = "".toCharArray();
		
		dos.write(null);
		dos.close();
	}
}
