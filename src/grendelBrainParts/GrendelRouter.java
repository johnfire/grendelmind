/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grendelBrainParts;

import basicstuff.*;
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
        
        this.systemMessageStartUp("Starting the grendel Router cell");
        
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
        this.systemMessageStartUp("----- in grendel router ::   Started the Grendel Router cell server and threads-----");
        
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(GrendelRouter.class.getName()).log(Level.SEVERE, null, ex);
            }
            // check to see whats coming thru
            if(myLinkedLists.unProcessedMessages.size() > 0){
                System.out.println(this.myLinkedLists.unProcessedMessages + " These are addresses of the messages in unprocessed list.");
                System.out.println(this.myLinkedLists.grendelRouterMessages + " These are addreses of messages in router list");
                System.out.println(this.myLinkedLists.outputMessages + " these are the addresses of output list messages");
                System.out.println("#######-############-#########- end of list");
            }
            // more router code goes here
            
        }
    }
    
    public class Processor extends Thread {
        allLinkedLists theLinkedLists;
        Message messageInQueue;
        int[] intAry = {};

        private Processor(allLinkedLists myLLObject) {
            this.theLinkedLists = new allLinkedLists();
            this.theLinkedLists = myLLObject;
            messageInQueue = new Message(0,0,0,0,intAry,"", false);
        }
        //you have to pass all the linked lists for delivery to this function as it handles 
        // all of the sorting into the various out lists used by the various 
      
        @Override
        public void run() {
            System.out.println(java.time.LocalTime.now() +" ----- Test Message----- Entered Processor run routine");
            while(true){
                try {
                    Thread.sleep(150);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GrendelRouter.class.getName()).log(Level.SEVERE, null, ex);
                }
                //System.out.println(java.time.LocalTime.now() + "the size of the unprocessed file is " + this.theLinkedLists.unProcessedMessages.size());
                
                if (this.theLinkedLists.unProcessedMessages.size() > 0 ){
                    System.out.println(java.time.LocalTime.now() + " -----Test Message ----- Entering sort loop");
                    // read destination
                    messageInQueue = this.theLinkedLists.unProcessedMessages.removeFirst();

                    //if messege is going to device on internet:
                    //place in queue for that destination
                    // otherwise send directly to decider or analyzer
                    switch (messageInQueue.showDestination()){
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
                        System.out.println("Added something to router message list");
                        break;
                    case 101: 
                        this.theLinkedLists.grendelRouterMessages.addLast(messageInQueue);
                        break;
                    case 102:
                        this.theLinkedLists.grendelGreetingServerMessages.addLast(messageInQueue);
                    default:
                        break;  
                    
                    }  
                    System.out.println(java.time.LocalTime.now() + " End of processing loop");
                }  
            }
        }
    }   
}

