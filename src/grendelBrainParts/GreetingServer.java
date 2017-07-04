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
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreetingServer extends BasicObject {

    allLinkedLists theRouter = new allLinkedLists();

    GreetingServer(allLinkedLists aThis) {
       this.theRouter = aThis;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
//    GreetingServer(LinkedList<Message> unProcessedMessages) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    

    private ServerSocket serverSocket;

    GreetingServer(allLinkedLists myLinkedLists) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    /**
     *
     */
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            this.systemMessageStartUp("-----started the ServerSocket-----");
            while (true) {
                new EchoClientHandler(serverSocket.accept(), this.theRouter).start();
            }
        } catch (IOException e) {
            this.systemMessageError("failure at the echoClientServer startup");
        }
    }
    
    //this is supposed to be static
    public static class EchoClientHandler extends Thread {
        private int myconnection = 0;
        private boolean LockConnection = false;
        allLinkedLists myRouter;
        private final Socket clientSocket;
        LinkedList<Message> myOutputList;
        Message firstMessage = new Message();
         
        
        
        public EchoClientHandler(Socket socket,allLinkedLists aRouter) {
            
            this.clientSocket = socket; 
            this.myRouter =aRouter;
            this.myOutputList = new LinkedList<>();
            

        }
 
        @Override
        public void run() {
            LinkedList<Message> myMessageHolder = new LinkedList();
            System.out.println("-----*** in echoClientHandlerServer ***----- System Message Entering client handler");
            
            try {
                
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
             
                String inputLine;
                
                try {
                    firstMessage = (Message)inFromClient.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GreetingServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                // test to see if lock connection neeeds to be set
                        if(LockConnection == false) {// lock this handler to the correct sender
                           this.myconnection = firstMessage.showOrigin();
                           LockConnection = true;
                        }
                // my name is set, now enter loop and send and receive messages
                
                while(true) {
                    try {
                        // read objects from input srtream as available
                        while(inFromClient.available()>= 50){
                           myMessageHolder.add((Message)inFromClient.readObject());
                           this.myRouter.unProcessedMessages.removeAll(myMessageHolder);
                        }
                        System.out.println("-----*** in echoClientHandlerServer ***----------SYSTEM MESSAGES-RECIEVED A MESSAGE OBJECT----- " + myMessageHolder);
                        
                        //done getting messages now  send messages
                        switch(this.myconnection){
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                this.myOutputList = this.myRouter.visionInMessages;
                                break;
                            case 4:
                                this.myOutputList = this.myRouter.soundInMessages;
                                break;
                            case 5:
                                this.myOutputList = this.myRouter.internetMessages;
                                break;
                            case 6:
                                this.myOutputList = this.myRouter.outputMessages;
                                break;
                            case 7:
                                this.myOutputList = this.myRouter.outputMessages;
                                break;
                            case 8:
                                this.myOutputList = this.myRouter.internetMessages;
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