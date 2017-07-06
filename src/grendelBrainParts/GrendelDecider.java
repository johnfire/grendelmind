/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grendelBrainParts;

import basicstuff.*;

/**
 *
 * @author christopherrehm
 *//**/
public class GrendelDecider extends BasicObject {

   int MyId = 100;
    
    @Override
    public void run() {   

    this.systemMessageStartUp("Starting the Decider cell");

        ObjectStatus myStats = new basicstuff.ObjectStatus();
        myStats.setMyName("grendelDecider");
        Thread deciderThread = new Thread(myStats);
        deciderThread.start();
        this.systemMessageStartUp("Grendel Decider cell is up and running");
    }
}


