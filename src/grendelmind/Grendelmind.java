/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grendelmind;

//import basicstuff.*;
import grendelBrainParts.*;

    

/**
 *
 * @author christopherrehm
 */
public class Grendelmind /*extends basicObject*/ {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        grendelDecider myDecider = new grendelDecider();
        grendelrouter myRouter = new grendelrouter();
        
        myDecider.start();
        myRouter.start();
        
        
        
    }
    
}
