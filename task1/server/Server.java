package task1.server;

import java.net.*;

import task1.Student;

import java.io.*; 

public class Server 
{ 
	Socket client = null;
	ObjectOutputStream out = null;
    ObjectOutputStream outFile = null;
    ObjectInputStream in = null;
    String fileName = "saved.dat";

	
	public Server(int port) 
	{ 
		try
		{ 
			ServerSocket server = new ServerSocket(8888);

			System.out.println("Waiting for a client ..."); 

			client = server.accept(); 
            out = new ObjectOutputStream(client.getOutputStream()); 
            in = new ObjectInputStream(client.getInputStream());  
            
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            outFile = new ObjectOutputStream(fileOutputStream);

            Student student = (Student) in.readObject();
            System.out.println(student.toString());
            outFile.writeObject(student);
            
            out.close();
            in.close();
            client.close();
            server.close();

		} 
		catch(IOException e) 
		{ 
			e.printStackTrace();
		} 
        catch(ClassNotFoundException e) { e.printStackTrace(); }
	} 

	public static void main(String args[]) 
	{ 
		Server server = new Server(6666); 
	} 
} 