/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzers;

import basicstuff.*;

/**
 *
 * @author christopherrehm
 */
public class FirstAnalyzer extends BasicObject implements Runnable{
    int pid;
    
    @Override
    public void run() {   
    
        pid=this.getPID();
    }
}
