/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grendelBrainParts;

import basicstuff.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christopherrehm
 */
public class GrendelRouter extends BasicObject {
    
    int MyId = 101; 
    int pid;
    int port = 5000;
    

    /**
     *Set up a blank linked list for use by processor
     */
    public LinkedList<Message> unProcessedMessages;

    public GrendelRouter() {
        this.unProcessedMessages = new LinkedList();
       
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
        
        //System.out.println("we are in the grendelRouter routine");
        // get this process number
        try {
             pid = Integer.parseInt(new File("/proc/self").getCanonicalFile().getName());
        } catch (IOException ex) {
            Logger.getLogger(GrendelRouter.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        //create server and run
        
        GreetingServer myServer;
        myServer = new GreetingServer();
        Thread theServerThread = new Thread(myServer);
        theServerThread.start();
        //System.out.println("-----System Message- started server thread-----");
        this.systemMessageStartUp("-----Started the router cell server thread-----");
        
        while(true) {
            //unProcessedMessages = myServer.
        }
    }

    public void addMessage(Message aMessage){
        this.unProcessedMessages.addLast(aMessage);    
    }
}