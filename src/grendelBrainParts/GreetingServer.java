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

/** greeting server contains all the server functionality
 * 
 * @author christopherrehm
 */

public class GreetingServer extends BasicObject {

    allLinkedLists thelinkedListObject = new allLinkedLists();
    private ServerSocket serverSocket;

    GreetingServer(allLinkedLists aThis) {
       this.thelinkedListObject = aThis;
    }
   
    /**
     *
     */
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            this.systemMessageStartUp("-----Greeting Server----- started the ServerSocket-----");
            while (true) {
                new EchoClientHandler(serverSocket.accept(), this.thelinkedListObject).start();
                this.systemMessageStartUp("-----Greeting Server----- started new echo server handler");
            }
        } catch (IOException e) {
            this.systemMessageError("failure at the echoClientServer startup");
        }
    }
    
    //this is supposed to be static
    public static class EchoClientHandler extends Thread {
        
        private int myconnection = 0;
        private boolean LockConnection = false;
        allLinkedLists theLinkedListObject;
        private final Socket clientSocket;
        LinkedList<Message> myOutputList;
        Message firstMessage = new Message();
        Message testMessage = new Message();
        
        public EchoClientHandler(Socket socket,allLinkedLists aLLObj) {
            
            this.clientSocket = socket; 
            this.theLinkedListObject =aLLObj;
            this.myOutputList = new LinkedList<>();
            
        }
 
        @Override
        public void run() {
            LinkedList<Message> myMessageHolder = new LinkedList();
            System.out.println("-----*** in echoIndyServer ***----- System Message Entering client handler");
            
            try {
                
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                
                try {
                    firstMessage = (Message)inFromClient.readObject();
                    System.out.println("-----*** in echoIndyServer ***----- address of first message is"+ firstMessage);
                    this.theLinkedListObject.unProcessedMessages.add(firstMessage);
                    // test to see if lock connection neeeds to be set
                    if(LockConnection == false) {// lock this handler to the correct sender
                        this.myconnection = firstMessage.showOrigin();
                        LockConnection = true;
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GreetingServer.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("-----*** in echoIndyServer ***----- ERROR DID NOT GET FIRST MESSAGE!!!!!");
                }
               
                // my name is set, now enter loop and send and receive messages
                
                while(true) {
                    try {
                        // read objects from input srtream as available
                        while(inFromClient.available()>= 50){
                           myMessageHolder.add((Message)inFromClient.readObject());
                           this.theLinkedListObject.unProcessedMessages.removeAll(myMessageHolder);
                           System.out.println("-----*** in echoClientHandlerServer ***----------SYSTEM MESSAGES-RECIEVED some MESSAGE OBJECT----- " + myMessageHolder);
                        }
                        
                        //done getting messages now  send messages
                        switch(this.myconnection){
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                this.myOutputList = this.theLinkedListObject.visionInMessages;
                                break;
                            case 4:
                                this.myOutputList = this.theLinkedListObject.soundInMessages;
                                break;
                            case 5:
                                this.myOutputList = this.theLinkedListObject.internetMessages;
                                break;
                            case 6:
                                this.myOutputList = this.theLinkedListObject.outputMessages;
                                break;
                            case 7:
                                this.myOutputList = this.theLinkedListObject.outputMessages;
                                break;
                            case 8:
                                this.myOutputList = this.theLinkedListObject.internetMessages;
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
                            case 20:
                                break;
                            case 100:
                                break;
                            case 101:
                                break;
                            case 102:
                                break;
                            default:
                                break;      
                        }
                        
                        //this code sends out all messages to where they need to go.
                        while(myOutputList.isEmpty() != true) {
                            outToClient.writeObject(myOutputList.removeFirst());
                            System.out.println("-----*** echo Server Sender***---  just sent message");
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