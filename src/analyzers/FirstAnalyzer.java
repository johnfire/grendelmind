/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzers;

import basicstuff.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
