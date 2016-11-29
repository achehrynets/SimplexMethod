/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;

import java.awt.HeadlessException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jo1nsaint
 */
public class Window extends JFrame{

    private JTextField x11, x12, x13, x21, x22, x23, x31, x32, x33;
    private JPanel x1, x2, x3;
    private JComboBox choiseRestrictionX1, choiseRestrictionX2, choiseRestrictionX3;
    private String[] choise = {">=", "<=", "="}; 
    private JTextField b1, b2, b3;
    private JTextField valueX1, valueX2, valueX3; //answer the simplex
    private JTextField Fx1, Fx2, Fx3;
    private JComboBox minOrMax;
    private String[] choiseMinOrMax = {"максимальное (max)", "минимальное (min)"};
    
    public Window() throws HeadlessException {
    
        setTitle("Simplex Method");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
    }
    
    
    
    
    
}
