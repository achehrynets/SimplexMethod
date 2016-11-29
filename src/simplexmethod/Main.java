/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;

/**
 *
 * @author jo1nsaint
 */
public class Main {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                Window w = new Window();
            }
        };
        
        runnable.run();
       
    }
}
