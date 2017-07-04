/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grendelBrainParts;

import basicstuff.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christopherrehm
 */
public class GrendelRouter extends BasicObject {
    
    int MyId = 101; 
    int port = 5000;
    allLinkedLists myLinkedLists;

    public GrendelRouter(){
        
        // constructor   
        myLinkedLists = new allLinkedLists();
    }
    
    @Override
    public void run() {
        
        this.systemMessageStartUp("starting the router cell");
        
        ObjectStatus myStats = new basicstuff.ObjectStatus();
        myStats.setMyName("grendelRouter");
        Thread routerThread = new Thread(myStats);
        routerThread.start();
        
        this.systemMessage("Just completed the Grendel Router start stats routine");
        
        //create server and run
        GreetingServer myServer;
        Processor myProcessor;
        myProcessor = new Processor(myLinkedLists);
        myServer = new GreetingServer(myLinkedLists);
        Thread theServerThread = new Thread(myServer);
        Thread theProcessorThread = new Thread(myProcessor);
        theServerThread.start();
        theProcessorThread.start();
        this.systemMessageStartUp("-----Started the router cell server thread-----");
        
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GrendelRouter.class.getName()).log(Level.SEVERE, null, ex);
            }
            // check to see whats coming thru
            if (myLinkedLists.unProcessedMessages != null){
                System.out.println(myLinkedLists.unProcessedMessages);
            }
            // more process code goes here
        }
    }
    
    public class Processor extends Thread {
        allLinkedLists theLinkedLists;
        Message messageInQueue;

        private Processor(allLinkedLists myLLObject) {
            this.theLinkedLists = new allLinkedLists();
            this.theLinkedLists = myLLObject;
        }
        //you have to pass all the linked lists for delivery to this function as it handles 
        // all of the sorting into the various out lists used by the various 
      
        @Override
        public void run() {
            
            while(theLinkedLists.unProcessedMessages.isEmpty()!= true){
                // read destination
                messageInQueue = this.theLinkedLists.unProcessedMessages.removeFirst();

                //if messege is going to device on internet:
                //place in queue for that destination
                // otherwise send directly to decider or analyzer
                switch (messageInQueue.showDestination(0)){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        this.theLinkedLists.visionInMessages.addLast(messageInQueue);
                        this.theLinkedLists.soundInMessages.addLast(messageInQueue);
                        this.theLinkedLists.internetMessages.addLast(messageInQueue);
                        this.theLinkedLists.outputMessages.addLast(messageInQueue);
                        this.theLinkedLists.grendelDeciderMessages.addLast(messageInQueue);
                        this.theLinkedLists.grendelRouterMessages.addLast(messageInQueue);
                        this.theLinkedLists.grendelGreetingServerMessages.addLast(messageInQueue);
                        this.theLinkedLists.greetingClientMessages.addLast(messageInQueue);
                        break;
                    case 3:
                        this.theLinkedLists.visionInMessages.addLast(messageInQueue);
                        break;
                    case 4:
                        this.theLinkedLists.soundInMessages.addLast(messageInQueue);
                        break;
                    case 5:
                        this.theLinkedLists.internetMessages.addLast(messageInQueue);
                        break;
                    case 6:
                        this.theLinkedLists.outputMessages.addLast(messageInQueue);
                        break;
                    case 7:
                        this.theLinkedLists.outputMessages.addLast(messageInQueue);
                        break;
                    case 8: // interent interface
                       this.theLinkedLists.internetMessages.addLast(messageInQueue);
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
                        this.theLinkedLists.greetingClientMessages.addLast(messageInQueue);
                        break;
                    case 100:
                        this.theLinkedLists.grendelDeciderMessages.addLast(messageInQueue);
                        break;
                    case 101: 
                        this.theLinkedLists.grendelRouterMessages.addLast(messageInQueue);
                        break;
                    case 102:
                        this.theLinkedLists.grendelGreetingServerMessages.addLast(messageInQueue);
                    default:
                        break;  
                }  
            }
        }
    }   
}

