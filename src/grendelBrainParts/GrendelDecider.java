/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grendelBrainParts;

import basicstuff.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christopherrehm
 *//**/
public class GrendelDecider extends BasicObject {
    int pid;
    int runVar = 1;
    
    @Override
    public void run() {   
    System.out.println("we are in the grendelDecider routine");
 
        try {
             pid = Integer.parseInt(new File("/proc/self").getCanonicalFile().getName());
        } catch (IOException ex) {
            Logger.getLogger(GrendelDecider.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        ObjectStatus myStats = new basicstuff.ObjectStatus();
        myStats.setMyName("grendelDecider");
        Thread intInfThread = new Thread(myStats);
        intInfThread.start();
       
    }
}


