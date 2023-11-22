package task1.client;

import java.net.*;

import task1.Student;

import java.io.*; 

public class Client 
{ 
	public static void main(String args[]) 
	{ 
        Socket client = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        ObjectInputStream fromFile = null;
        String fileName = "download.dat";
        try{
            client = new Socket("127.0.0.1", 8888);

            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());

            FileInputStream fileInputStream = new FileInputStream(fileName);
            fromFile = new ObjectInputStream(fileInputStream);
            Student student = (Student) fromFile.readObject();
            out.writeObject(student);
            out.flush();

            out.close();
            in.close();
            client.close();
        } catch(Exception e) {e.printStackTrace();}
	} 
} 