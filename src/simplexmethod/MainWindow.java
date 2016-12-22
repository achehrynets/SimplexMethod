/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author jo1nsaint
 */
public class MainWindow {
    private final JFrame window;
    private JComboBox choiseMethod;
    private JButton accept;
    private JLabel methodName;
    
    private final String[] methodsName = {
        "Симплекс метод",
            "Т-Задача",
                "Венгерский метод"
    };
    
    public MainWindow() {
        window = new JFrame("ММДО");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(650, 280, 300, 230);
        window.setLayout(null);
        window.setResizable(false);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("Error in window component : " + e);
        }
        
        window.add(createChoiseComboBox());
        window.add(createMethodsFieldName());
        window.add(acceptBtn());
        
        JLabel signature = new JLabel("Create by Andrey Chegrinets KH-14-2");
        signature.setBounds(10, 200, 300, 30);
        
        window.add(signature);
        window.setVisible(true);
    }
    
    public JComboBox createChoiseComboBox() {
        choiseMethod = new JComboBox(methodsName);
        choiseMethod.setBounds(50, 50, 180, 30);
        
        return choiseMethod;
    }
    
    public JLabel createMethodsFieldName() {
        methodName = new JLabel("Выберите метод:");
        methodName.setBounds(50, 25, 170, 30);
        
        return methodName;
    }
    
    
    public JButton acceptBtn() {
        accept = new JButton("Подтвердить");
        accept.setBounds(50, 90, 120, 30);
        
        accept.addActionListener((ActionEvent e) -> {
            switch(choiseMethod.getSelectedIndex()) {
                case 0: {
                    window.setVisible(false);
                    SimplexMethodWindow simplexMethod = new SimplexMethodWindow();
                    break;
                }
                case 1: {
                    window.setVisible(false);
                    TransportProblemWindow transportProblem = new TransportProblemWindow();
                    break;
                }
                case 2: {
                    window.setVisible(false);
                    HungarianMethodWindow hungarianMethodWindow = new HungarianMethodWindow();
                    break;
                }
            }
        });
        
        return accept;
    }
    
}
