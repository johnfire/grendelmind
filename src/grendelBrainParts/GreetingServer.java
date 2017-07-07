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
   
    /**Creates and starts the server socket
     *
     */
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            this.systemMessageStartUp(java.time.LocalTime.now() + "-----Greeting Server----- Started the ServerSocket-----");
            while (true) {
                new EchoClientHandler(serverSocket.accept(), this.thelinkedListObject).start();
                this.systemMessageStartUp(java.time.LocalTime.now() + "-----Greeting Server----- Started new echo server handler");
            }
        } catch (IOException e) {
            this.systemMessageError(java.time.LocalTime.now() + "Failure at the echoClientServer startup");
        }
    }
    
    /** this is the object that actually handles comm between server and a client.
     * 
     */
    
    //this is supposed to be static
    public static class EchoClientHandler extends Thread {
        int[] intAry = {1,2,3};
        private int myconnection = 0;
        private boolean LockConnection = false;
        private final Socket clientSocket;
        allLinkedLists theLinkedListObject;
        Message firstMessage = new Message(0,0,0,0,intAry,"", false);
        //Message arrivingMessage = new Message(0,0,0,0,intAry,"", false);
        Message arrivingMessage;
        LinkedList<Message> myOutputList;
        
        public EchoClientHandler(Socket socket,allLinkedLists aLLObj) {
            
            this.clientSocket = socket; 
            this.theLinkedListObject =aLLObj;
            this.myOutputList = new LinkedList<>();
        }
 
        @Override
        public void run() {
            LinkedList<Message> myMessageHolder = new LinkedList();
            System.out.println(java.time.LocalTime.now() + "-----*** in echoIndyServer ***----- System Message Entering client handler");
            
            try {
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                while(true) {
                    System.out.println(java.time.LocalTime.now() + "-----*** in echoIndyServer ***----- Starting echo server loop");
                    try {
                        //streams came from here
                       
                        try {
                           
                            if(10 <=inFromClient.available()){
                                Message arrivingMessage = (Message) inFromClient.readObject();
                                this.firstMessage = arrivingMessage;
                            }
                            if(LockConnection == false) {// lock this handler to the correct sender
                                this.myconnection = this.firstMessage.showOrigin();
                                System.out.println("the object is "+ this.firstMessage.showID() +":id     "+ this.firstMessage.showOrigin()+ ":origin    " + firstMessage.showDestination());
                                System.out.println(java.time.LocalTime.now() + " -----*** in echoIndyServer ***----- Just set my connection to " + this.myconnection);
                                LockConnection = true;
                            }
                            // lock linked list object
                            while(theLinkedListObject.iAmLocked == true){
                                //System.out.println("server sort thread is going to sleep");
                                Thread.sleep(5);
                            }
                            if(theLinkedListObject.iAmLocked == false ){
                                theLinkedListObject.iAmLocked = true;
                            }
                            // use object
                            this.theLinkedListObject.unProcessedMessages.addLast(this.firstMessage);
                            System.out.print(this.theLinkedListObject.unProcessedMessages.size() + ":  in grendelServerecho client server :: Size of unprocessed list right after message add \n");
                            System.out.println(java.time.LocalTime.now() + " -----*** in echoClientHandlerServer (" + this.myconnection + ")***-*-*-*-*-SYSTEM MESSAGES-RECIEVED some MESSAGE OBJECT-----" + this.arrivingMessage.showMessageNr());
                        }catch (IOException e){
                           // inFromClient.close();
                           // outToClient.close();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GreetingServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //this.theLinkedListObject.unProcessedMessages.removeAll(myMessageHolder);
                        
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
                                break;
                            case 20:
                                this.myOutputList = this.theLinkedListObject.greetingClientMessages;
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
                        while (myOutputList.isEmpty() != true) try{
                            Message outgoingMessage = new Message(0,0,0,0,intAry,"in GreetingServer", false);
                            outgoingMessage = myOutputList.removeFirst();
                            outToClient.writeObject(outgoingMessage);
                            System.out.println(java.time.LocalTime.now() + "-----*** echo Server Sender (" + this.myconnection + ")***-#-#-#-#-#- SENDING MESSAGE FROM ROUTER SOMEWHERE Just sent message");
                        }  catch (IOException ex){
                           //outToClient.close();
                        }
                        this.theLinkedListObject.iAmLocked =false;
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