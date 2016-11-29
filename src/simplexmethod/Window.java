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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jo1nsaint
 */
public class Window extends JFrame{
    
    private JPanel entryFunctions;
    private JPanel logPanel;
    
    private Integer m, n; // m - columns, n - rows
    
    private JTextField entryValue; // Количество переменных
    private JTextField entryRestrictions;// количество ограничений
    
    private JTextArea log;
    private JScrollPane scroll;
    
    
    private JLabel text1, text2, textFunction;
    
    private JButton btnOk1;
    
    public Window() throws HeadlessException {
    
        setTitle("Simplex Method");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setBounds(450, 200, 800, 500);
        
        add(createRestrictionsPane());
        add(createLogPanel());
        
    }
    
    private JPanel createLogPanel(){
        
        logPanel = new JPanel();
        logPanel.setLayout(null);
        logPanel.setBorder(new TitledBorder("Лог работы программы"));
        logPanel.setBounds(450, 10, 320, 400);
        
        log = new JTextArea();
        log.setLineWrap(true);  // Перенос в логе
        log.setWrapStyleWord(true);
        
        scroll = new JScrollPane(log);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBounds(10, 25, 300, 365);
        
        logPanel.add(scroll);
        
        return logPanel;
    }
    
    private JPanel createRestrictionsPane(){
        
        JPanel entryRestrictionsPanel = new JPanel(null);
        entryRestrictionsPanel.setBorder(new TitledBorder("Ввод значений: "));
        entryRestrictionsPanel.setBounds(20, 10, 400, 150);
        
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
        
        
        
        btnOk1.addMouseListener(new MouseAdapter() {
            @Override
                public void mouseClicked(MouseEvent e) {
                    try {

                        m = Integer.valueOf(entryValue.getText().toString());
                        n = Integer.valueOf(entryRestrictions.getText().toString());

                        addFunctionsField();

                    } catch (Exception ex) {
                        System.out.println("Err-1 in btn1 :" + ex);
                    }
                } 
            }        
        );
        
        entryRestrictionsPanel.add(text1);
        entryRestrictionsPanel.add(text2);
        entryRestrictionsPanel.add(entryValue);
        entryRestrictionsPanel.add(entryRestrictions);
        entryRestrictionsPanel.add(btnOk1);
        
        return entryRestrictionsPanel;
    }
    
    private void addFunctionsField(){
        
       JPanel panel = new JPanel(null);
       panel.setBorder(new TitledBorder("Ввод значений"));
       panel.setBounds(20, 170, 200, 300);
       textFunction = new  JLabel("F(x) = ");
       
       panel.add(text1);
       
       add(panel);

    }
    
    
}
