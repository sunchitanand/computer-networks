//SUNCHIT

import java.util.*;
import java.time.*;
import java.net.*;
import java.io.*;

public class Iperfer {

	//Function to send 1KB packets from client on passing server hostname, port number and connection time
	static void sendFromClient(String hostname, int port, long time) throws IOException 
	{
		double totalData = 0; 
		double rate = 0;
		byte[] bytes = new byte[1000];
		
		
		Socket clientSoc = new Socket(hostname, port); //Creating client socket object
		
		//PrintWriter out = new PrintWriter(clientSoc.getOutputStream(), true);
		//DataOutputStream out = new DataOutputStream(clientSoc.getOutputStream());
		//OutputStream out = clientSoc.getOutputStream();
		
		long timeout = System.currentTimeMillis() + (time * 1000); //Calculating timeout - time at which connection will end
		while(System.currentTimeMillis() < timeout) 
		{
			//out.write(bytes, 0, 1000);
			clientSoc.getOutputStream().write(bytes); //send 1KB to server
			totalData++;
		}
		rate = (totalData * 8) / (time*1000.0);
		clientSoc.close();
		System.out.println("sent = "+ totalData + " KB  rate = "+ String.format("%.5f",rate) +" Mbps");
	}
	
	//Function to initialize server on the passed port number
	static void receiveToServer(int port) throws IOException 
	{
		double totalData = 0,x=0;
		double rate = 0; 
		long time = 0; 
		byte[] bytes = new byte[1000];
		
		ServerSocket serverSoc = new ServerSocket(port); // Server socket object created
		//System.out.print("Waiting for client connection\n");
		
		Socket clientSoc = serverSoc.accept(); // client object that sends a request to the server
		
		//System.out.println("Connection Established");
		// BufferedReader in = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
		//InputStream in = clientSoc.getInputStream();
		
		long startTime = System.currentTimeMillis();
		while((x=clientSoc.getInputStream().read(bytes)) > 0) //receive bytes
        	{
            		totalData+=x; //keep track of bytes received
       		}

		long endTime = System.currentTimeMillis();
		time = endTime - startTime;
		//System.out.println(time);
		totalData=  totalData/1000;
		rate = (totalData * 8) / time;
		
		serverSoc.close();
		clientSoc.close();
		System.out.println("received = "+ totalData + " KB  rate = "+ String.format("%.5f",rate)+" Mbps");
		
	}
	public static void main(String args[])
	{
		int mode = 0;
		if(args[0].equals("-c") && args.length == 7)
			mode = 1;
		
		else if(args[0].equals("-s") && args.length == 3)
			mode = 2;
		
		else
			System.out.print("Error: missing or additional arguments");
		
		if(mode == 1) // CLIENT MODE
		{
			String hostname = args[2];
			int port = Integer.parseInt(args[4]);
			long time = Long.parseLong(args[6]);
			
			if(port < 1024 || port > 65535)
				System.out.println("Error: port number must be in the range 1024 to 65535");
				
			if(hostname.length() >= 254) 	//DNS Name limit is 255 octets ~ 253 characters
				System.out.println("Hostname size limit exceeded!");
			
			if(time > 3600) // 1 hour worth of data could result in a crash if rate is very high
				System.out.println("Connection limit exceeded!");
			try 
			{
				sendFromClient(hostname, port, time);
			}
			
			catch(IOException e)
			{
				System.out.print("I/O Interrupted or Server not responding");
			}
		}
		
		else if(mode == 2) // SERVER MODE
		{
			int port = Integer.parseInt(args[2]);
			
			if(port < 1024 || port > 65535)
				System.out.println("Error: port number must be in the range 1024 to 65535");
			
			try
			{
				receiveToServer(port);
			}
			
			catch(IOException e)
			{
				System.out.println("I/O Interrupted or Server not responding");
			}
		}
		
	}
}
