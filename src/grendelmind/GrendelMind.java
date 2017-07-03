/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grendelmind;

import basicstuff.*;
import grendelBrainParts.*;

/**
 *
 * @author christopherrehm
 */
public class GrendelMind extends basicObject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GrendelDecider myDecider = new GrendelDecider();
        Thread theDecider = new Thread(myDecider);
        GrendelRouter myRouter = new GrendelRouter();
        Thread theRouter = new Thread(myRouter);
        theDecider.start();
        theRouter.start();   
    }   
}