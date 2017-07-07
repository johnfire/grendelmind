/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grendelBrainParts;

import basicstuff.Message;
import java.util.LinkedList;

/** linked lists all in one object. this is the heart of the router system. these lists are used to rout messages around the system
 * 
 *
 * @author christopherrehm
 */
public class allLinkedLists {
    
    /** this is a temp set up to prove concept of keeping all these lists in one object. there may be a bottle neck here
     * it may be neeeded to have 9 subobjects one for each echoserver and each has its own unprocessedmessages list
     * then those 9 lists are added at some point to the main list.
     */
    
    public LinkedList<Message> unProcessedMessages;
    public LinkedList<Message> internetMessages;
    public LinkedList<Message> outputMessages;
    public LinkedList<Message> soundInMessages;
    public LinkedList<Message> visionInMessages;
    public LinkedList<Message> greetingClientMessages;
    public LinkedList<Message> grendelDeciderMessages;
    public LinkedList<Message> grendelRouterMessages;
    public LinkedList<Message> grendelGreetingServerMessages;
    public boolean iAmLocked = false;
    
    public allLinkedLists() {
        this.unProcessedMessages = new LinkedList();
        this.internetMessages = new LinkedList();   
        this.outputMessages = new LinkedList();
        this.soundInMessages = new LinkedList ();
        this.visionInMessages = new LinkedList ();
        this.greetingClientMessages = new LinkedList();
        this.grendelDeciderMessages = new LinkedList();
        this.grendelRouterMessages = new LinkedList();
        this.grendelGreetingServerMessages = new LinkedList();
    }
}
    
