/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fjcomp;

import sequential.*;



/**
 *
 * @author boya
 */
public class StopWatch {
    long startTime;
    long stopTime;
    
    public StopWatch(){
        startTime = System.currentTimeMillis();

    }
    public void stop(){
        stopTime = System.currentTimeMillis();

    }

    public long getElapsedTime(){
        return stopTime - startTime;

    }    

}

