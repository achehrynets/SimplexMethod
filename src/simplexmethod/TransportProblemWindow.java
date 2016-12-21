/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author jo1nsaint
 */
public class TransportProblemWindow {
        private JFrame transportProblemFrame;
        
    public TransportProblemWindow() {
        transportProblemFrame = new JFrame("Транспортная задача: ");
        transportProblemFrame.setBounds(450, 200, 500, 600);
        transportProblemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        transportProblemFrame.setResizable(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("Error in transportProblemWindow component : " + e);
        }
        
        transportProblemFrame.setVisible(true);
    }
    
}
