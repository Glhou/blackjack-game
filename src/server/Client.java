/**
 * 
 */
package server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import players.Player;



/**
 * @author mjschaub
 *
 */
public class Client 
{

	private DatagramSocket socket;
	private InetAddress ip;
	private Thread sendingThread;
	private int port;
	/**
	 * default constructor for the client
	 */
	public Client()
	{
		
	}
	/**
	 * opens a connection for the given client
	 * @return true if connection is successful or false if not
	 */
	public boolean openConnection(String ip_server, int port_server)
	{
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(ip_server);
			port = port_server;
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	/**
	 * receives the packet for each of the clients
	 * @return
	 */
	public String receivePacket()
	{
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		
		try {
			socket.receive(packet); //freezes the process until it receives an action
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String action = new String(packet.getData());
		return action;
		
	}
	/**
	 * sends the packet given the data in bytes of the string action
	 * @param data the bytes array of the string action
	 */
	public void sendPacket(final byte[] data)
	{
		sendingThread = new Thread("send")
		{
			public void run()
			{
				DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		sendingThread.start();
	}
	/**
	 * closes a client connection when they disconnect
	 */
	public void closeConnection()
	{
		new Thread()
		{
			public void run()
			{
				synchronized (socket)
				{
					socket.close();
				}
			}
		}.start();
	}
	
	
	
}
