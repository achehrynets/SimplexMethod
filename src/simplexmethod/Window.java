/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;


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
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jo1nsaint
 */
public class Window {
    
    private JFrame mainWindow;
    
    private JPanel entryFunctions;
    private JPanel logPanel;
    
    
    private Integer m, n; // m - columns, n - rows (entryValues)
    
    private JTextField entryValue; // Количество переменных
    private JTextField entryRestrictions;// количество ограничений
    private JTextField x;
    private JTextField xIJ; // значения для матрицы свободных членов и переменных
       
    private JTextArea log;
    private JScrollPane scroll; // scroll for log
    
    private JLabel text1, text2, textFunction;
    private JLabel printX;
    private JLabel example1;
    private JLabel example2;
    
    private JButton btnOk1;
    
    private JComboBox minOrMax;
    private final String[] minOrMaxValue = {"min", "max"};
    
    private int number;
    private Integer minOrMaxChoise;// save minOrMax value
    
    public Window(){
        mainWindow = new JFrame();
        mainWindow.setTitle("Simplex Method");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(null);
        mainWindow.setBounds(450, 200, 800, 500);
        mainWindow.setResizable(false);
        
        mainWindow.add(createRestrictionsPane());
        mainWindow.add(createLogPanel());
        
        mainWindow.setVisible(true);
          
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
                                //TODO: Доделать проверку
                                if((entryValue.getText().trim().length() <= 0)
                                        && (Integer.valueOf(entryValue.getText().toString()) > 4)){
                                            //&& (Integer.valueOf(entryRestrictions.getText().toString()) > 4)){
                                
                                    throw new IllegalArgumentException("Выполз за пределы");
                                    
                                }    
                                else{
                                    
                                    n = Integer.valueOf(entryValue.getText().toString());
                                    m = Integer.valueOf(entryRestrictions.getText().toString());
                                
                                    System.out.println("Check values : " + m + " " + n);
                                
                                    mainWindow.add(addFunctionsField());
                                    mainWindow.repaint();
                                    
                                }
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
    
    private JPanel createLogPanel(){
        
        logPanel = new JPanel();
        logPanel.setLayout(null);
        logPanel.setBorder(new TitledBorder("Лог работы программы"));
        logPanel.setBounds(450, 10, 320, 460);
        
        log = new JTextArea();
        
        // Не позволянет изменять или вводить значения
        log.setEditable(false);
        log.setLineWrap(true);  // Перенос в логе
        log.setWrapStyleWord(true);
        
        scroll = new JScrollPane(log);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBounds(10, 25, 300, 425);
        
        logPanel.add(scroll);
       
        return logPanel;
    }
     
    private JPanel addFunctionsField(){
        
       JPanel panel = new JPanel(null);
       panel.setBorder(new TitledBorder("Ввод значений"));
       panel.setBounds(20, 200, 400, 270);
       
       textFunction = new  JLabel("F(x) = ");
       textFunction.setBounds(30, 15, 150, 30);
       
       panel.add(textFunction);
       
       number = 1;
       
        for (int i = 0; i < n; i++) {
            
            x = new JTextField();
            x.setBounds(75 + i * 60, 20, 40, 20);
            
            printX = new JLabel();
            printX.setBounds(118 + i * 60, 20, 20, 20);
            printX.setText("x" + number);
            
            panel.add(x);
            panel.add(printX);
            number++;
        }
        

        minOrMax = new JComboBox(minOrMaxValue);
        minOrMax.setBounds(330, 20, 35, 20);
        minOrMax.addActionListener((e) -> {
            
            int choise = minOrMax.getSelectedIndex();
            
            System.out.println("Selected choise :  " + choise + " //if 0 - min, 1 - max");
            
            minOrMaxChoise = choise;
        });
        
        panel.add(minOrMax);
                
        example1 = new JLabel();
        example1.setText("Введите значения согласно примеру: ");
        example1.setBounds(20, 50, 300, 20);
        
        example2 = new JLabel();
        example2.setText("b = x1 + x2 + ... + xn");
        example2.setBounds(60, 70, 200, 20);
        
        panel.add(example1);
        panel.add(example2);
        
        

        for(int i = 0; i < m; i++ ){

            for(int j = 0; j < n + 1; j++){
                
                xIJ = new JTextField();
                //xIJ.setText("x"+i+j);
                xIJ.setBounds(60 + j * 60, 100 + i * 40, 40, 20);
                
                panel.add(xIJ);
            }
        }
       
        JButton btn2 = new JButton();
        btn2.setText("Решить");
        btn2.setBounds(60, 220, 140, 30);
        
        panel.add(btn2);
        
        return panel;
    }
    
    
}
