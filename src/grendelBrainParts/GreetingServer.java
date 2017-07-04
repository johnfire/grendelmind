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
    GrendelRouter theRouter = new GrendelRouter();
    
//    GreetingServer(LinkedList<Message> unProcessedMessages) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    protected void GreetingServer(GrendelRouter myRouter,LinkedList thelist){
       aList = thelist;  
       this.theRouter =myRouter;
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
        int myconnection = 0;
        boolean LockConnection = false;
        
        private final Socket clientSocket;
        
        public LinkedList<Message> daMessage;
        public LinkedList<Message> myOutputList;
        
        //myOutputList = new LinkedList<>();
    
        public EchoClientHandler(Socket socket,LinkedList<Message> mylist) {
            this.clientSocket = socket;
            daMessage = mylist;
            this.myOutputList = new LinkedList();  
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
                        // read objects from input srtream as available
                        while(inFromClient.available()>= 50){
                           myMessageHolder = (Message)inFromClient.readObject();
                        }
                        // test to see if lock connection neeeds to be set
                        if(LockConnection == false) {// lock this handler to the correct sender
                           this.myconnection = (int) myMessageHolder.showOrigin();
                           LockConnection = true;
                        }
                        
                        System.out.println("-----*** in echoClientHandlerServer ***----------SYSTEM MESSAGE-RECIEVED A MESSAGE OBJECT----- " + myMessageHolder);
                        
                        // need code here to loop and load up unprocessed message list 
                        this.daMessage.add(myMessageHolder);
                        
                        //done getting messages now  send messages
                        switch(this.myconnection){
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                //this.myOUtputList = visionList;
                                break;
                            case 4:
                                //this.myOutputLst = soundList;
                                break;
                            case 5:
                                //this.myOutputList = soundInList;
                                break;
                            case 6:
                                //this.myOutputList = soundOutList;
                                break;
                            case 7:
                                //this.myOutputlist = movementList;
                                break;
                            case 8:
                                //this.myOutputList = internetList; 
                                break;
                            case 9:
                                break;
                            case 10:
                                break;
                            case 11:
                                break;
                            case 12:
                                break;
                            case 13:
                                break;
                            case 14:
                                break;
                            case 15:
                                //thismyOutputList = outputList;
                                break;
                            case 100:
                                break;
                            case 102:
                                break;
                            default:
                                break;      
                        }
                        
                        System.out.println("-----*** in echoClientHandlerServer ***----------System Message saved to" + " local linked list");
                        //this code sends out all messages to where they need to go.
                        while(myOutputList.isEmpty() != true) {
                            outToClient.writeObject(myOutputList.removeFirst());
                        }  
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