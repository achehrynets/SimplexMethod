/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jo1nsaint
 */
public class Window extends JFrame{
    
    private JPanel entryFunctions;
    private JPanel logPanel;
    
    private JTextField entryValue; // Количество переменных
    private JTextField entryRestrictions;// количество ограничений
    
    private JLabel text1, text2;
    
    private JButton btnOk1;
    
    public Window() throws HeadlessException {
    
        setTitle("Simplex Method");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setBounds(450, 200, 800, 500);
        
        
        
        add(createRestrictionsPane());
        
    }
    
    
    private JPanel createRestrictionsPane(){
        
        JPanel entryRestrictionsPanel = new JPanel(null);
        entryRestrictionsPanel.setBorder(new TitledBorder("Ввод значений: "));
        entryRestrictionsPanel.setBounds(20, 10, 400, 250);
        
        text1 = new JLabel("Введите количество переменных = ");
        text1.setBounds(30, 30, 300, 30);
        
        text2 = new JLabel("Введите количество ограничений = ");
        text2.setBounds(30, 50, 300, 30);
        
        entryValue = new JTextField();
        entryValue.setBounds(290, 35, 50, 20);
        
        entryRestrictions = new JTextField();
        entryRestrictions.setBounds(290, 58, 50, 20);
        
        btnOk1 = new JButton("Расчитать");
        btnOk1.setBounds(30, 80, 150, 30);
        
        entryRestrictionsPanel.add(text1);
        entryRestrictionsPanel.add(text2);
        entryRestrictionsPanel.add(entryValue);
        entryRestrictionsPanel.add(entryRestrictions);
        entryRestrictionsPanel.add(btnOk1);
        
        return entryRestrictionsPanel;
    }
    
    
    
}
