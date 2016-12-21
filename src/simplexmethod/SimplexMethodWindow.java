/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jo1nsaint
 */
public class SimplexMethodWindow {
    
    public static final String EOL = System.lineSeparator(); 
    double[] functionResult = new double[8];
    double result;
    String[] animals = {"Куры = ", "Овцы = ", "Лягушки = ", "Конина = "};
    private JFrame mainWindow;
    
    private JPanel logPanel;
    private JPanel panel; 
    
    private Integer m, n; // n - количество переменных, m - количество ограничений
    
    private JTextField entryValue; // Количество переменных
    private JTextField entryRestrictions;// количество ограничений
    
    private JTextField xIJ = null; // значения для матрицы свободных членов и переменных
    
    private JTextArea log;
    private JScrollPane scroll; // scroll for log
    
    private JLabel text1, text2, textFunction;
    private JLabel example1;
    private JLabel example2;
    
    private JButton btnOk1;
    
    private JComboBox minOrMax;
    private final String[] minOrMaxValue = {"choise", "min", "max"};
    
    private int number;
    private Integer minOrMaxChoise = 2;// save minOrMax value
    
    private static final JTextField[] linksFunctionValue = new JTextField[8];
    private static final JTextField[][] linksArrValue = new JTextField[4][8];
    
    private double matr[][];
    private double functionVector[];
    
    private int[] setXForAnimals = {80, 135, 185, 255};
    private String[] setLabelNameForAnimals = {"Куры", "Овцы", "Лягушки", "Конь"};
        
    public SimplexMethodWindow(){
        
        mainWindow = new JFrame();
        mainWindow.setTitle("Simplex Method");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(null);
        mainWindow.setBounds(450, 200, 800, 500);
        mainWindow.setResizable(false);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("Error in window component : " + e);
        }
        
        mainWindow.add(createRestrictionsPane());
        mainWindow.add(createLogPanel());
        
        mainWindow.setVisible(true);
        
        JOptionPane.showMessageDialog(mainWindow, 
                        "Оптимально работает для матриц размеров 1x2,"
                                + " 2x2, 2x3, 3x2 3x3, 3x4."
                                + EOL + "На матрицы 4х4 работает не корректно!!!"
                            );
          
    }
    
        private JPanel createRestrictionsPane(){
        
        JPanel entryRestrictionsPanel = new JPanel(null);
        entryRestrictionsPanel.setBorder(new TitledBorder("Ввод значений: "));
        entryRestrictionsPanel.setBounds(20, 10, 400, 150);
        
        text1 = new JLabel("Введите количество видов = ");
        text1.setBounds(30, 30, 300, 40);
        
        text2 = new JLabel("Введите количество предприятий = ");
        text2.setBounds(30, 70, 300, 40);
        
        entryValue = new JTextField();
        entryValue.setBounds(290, 35, 50, 30);
        
        entryRestrictions = new JTextField();
        entryRestrictions.setBounds(290, 75, 50, 30);
        
        btnOk1 = new JButton("Расчитать");
        btnOk1.setBounds(30, 110, 150, 30);
        
        btnOk1.addMouseListener(new MouseAdapter() {
                    @Override
                        public void mouseClicked(MouseEvent e) {
                            
                            try {
                                n = Integer.valueOf(entryValue.getText());
                                m = Integer.valueOf(entryRestrictions.getText());
                                 } catch (IllegalArgumentException ex) {
                                System.out.println("Err-1 in btn1 :" + ex);
                                n = m = 0;
                            }
                                if(((n > 4) || (m > 4)) ||  (n == 0) || (m == 0)) {
                                        JOptionPane.showMessageDialog(mainWindow, 
                                                "Количество видов и предприятий"
                                                + "не может быть больше 4-х или"
                                                + " пустым!", "Ошибка ввода!",
                                                JOptionPane.ERROR_MESSAGE);
                                }    
                                else{
                                    mainWindow.add(addFunctionsField());
                                    mainWindow.repaint();
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
        
        log.append("B - количество дней на заказ");
        log.append(EOL);
        log.append("Колонки - время роста живности");
        log.append(EOL);
        log.append("Cтроки - предприятия и их заказ");
        log.append(EOL);
        
        
        logPanel.add(scroll);
       
        return logPanel;
    }
     
    private JPanel addFunctionsField(){
        
       panel = new JPanel(null);
       panel.setBorder(new TitledBorder("Ввод значений"));
       panel.setBounds(20, 170, 400, 300);       
       number = 1;     
       JTextField x;       
       for (int i = 0; i < n; i++) {            
            x = new JTextField();
            x.setBounds(78 + i * 60, 40, 40, 30);            
            linksFunctionValue[i] = x;           
            panel.add(x);     
            number++;
       }
        minOrMax = new JComboBox(minOrMaxValue);
        minOrMax.setBounds(320, 25, 75, 25);
        minOrMax.addActionListener((e) -> {
            int choise = minOrMax.getSelectedIndex();
            minOrMaxChoise = choise;
        });
        
        panel.add(minOrMax);
                
        JLabel price = new JLabel("Прибыль: ");
        price.setBounds(5, 15, 75, 30);
        panel.add(price);
           
        JLabel animal = new JLabel();
        for(int i = 0; i < n; i++) {
            animal = new JLabel(setLabelNameForAnimals[i]);
            animal.setBounds(setXForAnimals[i], 15, 70, 30);
            panel.add(animal);
        }
        for(int i = 0; i < m; i++ ){
            for(int j = 0; j < n + 1; j++){
                
                xIJ = new JTextField();
                xIJ.setBounds(60 + j * 60, 100 + i * 40, 40, 30);
                
                linksArrValue[i][j] = xIJ;
           
                panel.add(xIJ);
            }
        }
       
        JButton btn2 = new JButton();
        btn2.setText("Решить");
        btn2.setBounds(200, 260, 140, 30);
        
        btn2.addActionListener((ActionEvent e) -> {
            
            matr = new double [4][8];
            functionVector = new double[8];
            initArr(matr, functionVector);
            getValue(matr, functionVector);
            printArr(matr);
            System.out.println(Arrays.toString(functionVector));
            SimplexMethod simplex = new SimplexMethod(matr, functionVector);
            
            functionResult = simplex.justDoIt();
            result = simplex.mainResult(functionResult);
            log.append(EOL);
            log.append("Время роста:");
            
            log.append(EOL);
            for(int i = 0; i < n; i++) {
                log.append(animals[i] + functionResult[i]);
                log.append(EOL);
                
            }
            log.append("Вы получите прибыль = " + result);
            System.out.println(Arrays.toString(functionResult));
            System.out.println(result);
            
       });
        
        panel.add(btn2);
        
        return panel;
    }
    
    public void printArr(double[][] arr) {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 8; j++) {
                System.out.print("[ " + arr[i][j] + " ]");
            }
            System.out.println();
        }
    }
    
    public void getValue(double [][] arr, double [] function) {
        try{
            for(int i = 0; i < m; i++) {
                arr[i][7] = Double.valueOf(linksArrValue[i][n].getText());
                for(int j = 0; j < n; j++) {
                    arr[i][j] = Double.valueOf(linksArrValue[i][j].getText());
                    function[j] = Double.valueOf(linksFunctionValue[j].getText());

                }

            }
        } catch(NumberFormatException nfe) {
            System.out.println("Exception in parsing arr value : " + nfe.toString());
            JOptionPane.showMessageDialog(mainWindow, 
                            "Заполните все поля!(Поля не могут быть пустыми"
                                    + " или имень буквенное значение)",
                            "Ошибка заполнения полей!",
                            JOptionPane.ERROR_MESSAGE);
        }
        if(minOrMaxChoise == 1) {
            for(int i = 0; i < n; i++){
                
                function[i] = function[i] * (-1);
            }
        }
        int k = n;
            for(int i = 0; i < m; i++){
                    matr[i][k] = 1;
                    k++;
                }
            }
    
    public void initArr(double [][] matr, double[] function) {
        for (int k = 0; k < m; k++) {
            function[k] = 0;
            for (int j = 0; j < 8; j++) {
                matr[k][j] = 0;
            }
        }
    }
    
}
