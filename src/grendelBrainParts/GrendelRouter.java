/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grendelBrainParts;

import basicstuff.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author christopherrehm
 */
public class GrendelRouter extends BasicObject {
    
    int MyId = 101; 
    int port = 5000;

    /**
     *Set up a blank linked list for use by processor
     */
    public LinkedList<Message> unProcessedMessages;
    public LinkedList<Message> internetMessages;
    public LinkedList<Message> outputMessages;
    public LinkedList<Message> soundInMessages;
    public LinkedList<Message> VisionInMessages;
    public LinkedList<Message> greetingClientMessages;
    public LinkedList<Message> grendelDeciderMessages;
    public LinkedList<Message> grendelRouterMessages;
    public LinkedList<Message> grendelGreetingServerMessages;
    
    public GrendelRouter() {
        this.unProcessedMessages = new LinkedList();
        this.internetMessages = new LinkedList();   
        
        ArrayList<LinkedList<Message>> bigList;
        bigList = new ArrayList<>();
        bigList.add(this.unProcessedMessages);
        bigList.add(this.internetMessages);

    }
    
    @Override
    public void run() {
        //create a server mem space
        //GreetingServer myServer = null;
        this.systemMessageStartUp("starting the router cell");
        
        ObjectStatus myStats = new basicstuff.ObjectStatus();
        myStats.setMyName("grendelRouter");
        Thread routerThread = new Thread(myStats);
        routerThread.start();
        
        this.systemMessage("Just entered the Grendel Router routine");
        //create server and run
        
        GreetingServer myServer;
        myServer = new GreetingServer();
        Thread theServerThread = new Thread(myServer);
        theServerThread.start();
        this.systemMessageStartUp("-----Started the router cell server thread-----");
        
        while(true) {
            // add messages
            unProcessedMessages.addAll(myServer.aList);
            myServer.aList.clear();
            // check to see whats coming thru
            if (this.unProcessedMessages != null){
                System.out.println(this.unProcessedMessages);
            }
            // more process code goes here
        }
    }
    
    public class processor extends Thread {
        LinkedList<Message> theList;
        LinkedList<Message> myoutlist;
        
        //you have to pass all the linked lists for delivery to this function as it handles 
        // all of the sorting into the various out lists used by the various 
        public void processor(LinkedList<Message> unworkedList, LinkedList<Message> internetList) {
            theList = unworkedList;
            myoutlist = internetList;
        }
        
        @Override
        public void run() {
            
            int listSize = theList.size();
            Message messageInQueue = new Message();
            //messageNumberInQueue = 0;
            long destination;
            
            // for each message :
            while(theList.isEmpty()!= true){
                // read destination
                messageInQueue = theList.removeFirst();
                destination = getDestination(messageInQueue);
                int y =(int) destination;
                //if messege is going to device on internet:
                //place in queue for that destination
                // otherwise send directly to decider or analyzer
                switch (y){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8: // interent interface
                       // myoutlist.addLast(internetInterface, messageInQueue);
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
                    case 100:
                        break;
                    case 101: 
                        break;
                    default:
                        break;  
                }  
            }
        }
        
        private long getDestination(Message msgNr){
           long destination =0;
            destination = msgNr.showDestination(destination);
            // do the work here. 
            
            return destination;
        }
    }
}