/*
 * Copyright (C) 2017 christopherrehm.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package grendelBrainParts;

/**
 *
 * @author christopherrehm
 */

// File Name GreetingServer.java
import basicstuff.*;
import java.net.*;
import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreetingServer extends BasicObject {
    LinkedList<Message> aList = new LinkedList();
    

//    GreetingServer(LinkedList<Message> unProcessedMessages) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    protected void GreetingServer(LinkedList<Message> thelist){
       aList = thelist;
    }

    private ServerSocket serverSocket;
   
    /**
     *
     */
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            this.systemMessageStartUp("-----started the ServerSocket-----");
            while (true) {
                new EchoClientHandler(serverSocket.accept(),aList).start();
            }
        } catch (IOException e) {
            this.systemMessageError("failure at the echoClientServer startup");
        }
    }
    
    //this is supposed to be static
    public static class EchoClientHandler extends Thread {
        private final Socket clientSocket;
        public LinkedList<Message> daMessage = null;
    
        public EchoClientHandler(Socket socket,LinkedList<Message> mylist) {
            this.clientSocket = socket;
            daMessage = mylist;
        }
 
        @Override
        public void run() {
            Message myMessageHolder = null;
            System.out.println("-----*** in echoClientHandlerServer ***----- System Message Entering client handler");
            
            try {
                
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
             
                String inputLine;
                while(true) {
                    try {
                        myMessageHolder= (Message)inFromClient.readObject();
                        System.out.println("-----*** in echoClientHandlerServer ***----------SYSTEM MESSAGE-RECIEVED A MESSAGE OBJECT----- " + myMessageHolder);
                        this.daMessage.add(myMessageHolder);
                   
                        System.out.println("-----*** in echoClientHandlerServer ***----------System Message saved to local linked list");
                        //outToClient.writeObject();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(GreetingServer.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                }
 
            } catch (IOException ex){
                Logger.getLogger(GrendelRouter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
    private void log(String message) {
        System.out.println(message);
    }  
}