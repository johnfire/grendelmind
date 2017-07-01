/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzers;

import basicstuff.basicObject;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christopherrehm
 */
public class firstAnalyzer extends basicObject implements Runnable{
    int pid;
    int runVar = 1;
    
    @Override
    public void run() {   
    System.out.println("we are in the firstAnalyzer routine");
 
        try {
             pid = Integer.parseInt(new File("/proc/self").getCanonicalFile().getName());
        } catch (IOException ex) {
            Logger.getLogger(firstAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("we are in the firstAnalyzer routine first time"+ "my process ID is "+ pid);
        while (runVar== 1){
            try {
                Thread.sleep(5000);
                System.out.println("we are in the firstAnalyzer routine, process number "+pid);  
            } catch (InterruptedException ex) {
                Logger.getLogger(firstAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
}
