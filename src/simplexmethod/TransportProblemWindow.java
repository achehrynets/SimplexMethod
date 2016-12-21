/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import static simplexmethod.SimplexMethodWindow.EOL;

/**
 *
 * @author jo1nsaint
 */
public class TransportProblemWindow {
    
        private static final int n = 4;
        private JFrame transportProblemFrame;
        
        private JPanel topValuePanel;
        private JLabel arrTextOne;
        private JLabel arrTextTwo;
        private JTextField arrValue;
        private JLabel stockText;
        private JTextField a;
        private JLabel needskText;
        private JTextField b;
        private JTextField[][] linksOfFilledArrValue = new JTextField[n][n];
        private JTextField[] linksOfFilledAValue = new JTextField[n];
        private JTextField[] linksOfFilledBValue = new JTextField[n];
        private JButton solve;
        private JPanel logPanel;
        private JTextArea log;
        private JScrollPane scroll;
        
    public TransportProblemWindow() {
        transportProblemFrame = new JFrame("Транспортная задача: ");
        transportProblemFrame.setBounds(450, 200, 600, 400);
        transportProblemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        transportProblemFrame.setResizable(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("Error in transportProblemWindow component : " + e);
        }
        
        transportProblemFrame.add(fillValueForTransportProblem());
        transportProblemFrame.add(createLogPanel());
        transportProblemFrame.setVisible(true);
    }
    private JPanel fillValueForTransportProblem() {
        topValuePanel = new JPanel(null);
        topValuePanel.setBounds(10, 10, 280, 370);
        topValuePanel.setBorder(new TitledBorder("Ввод значений"));
        arrTextOne = new JLabel("Стоимость перегона одного вагона");
        arrTextOne.setBounds(10, 8, 280, 30);
        topValuePanel.add(arrTextOne); //
        arrTextTwo = new JLabel("из станции в указанный пункт");
        arrTextTwo.setBounds(10, 25, 250, 30);
        topValuePanel.add(arrTextTwo); //       
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                arrValue = new JTextField();
                arrValue.setBounds(50 * j + 10, 50 + i * 40, 40, 30);
                linksOfFilledArrValue[i][j] = arrValue;
                topValuePanel.add(arrValue);
            }
        }
        stockText = new JLabel("Кол-во вагонов на станции:");
        stockText.setBounds(10, 215, 220, 30);
        topValuePanel.add(stockText);       
        for (int i = 0; i < n; i++) {            
             a = new JTextField();
             a.setBounds(10 + i * 50, 240, 40, 30);            
             linksOfFilledAValue[i] = a;           
             topValuePanel.add(a);
        }
        needskText = new JLabel("Пунктам погрузки необходимо:");
        needskText.setBounds(10, 265, 250, 30);
        topValuePanel.add(needskText);       
        for (int i = 0; i < n; i++) {            
             b = new JTextField();
             b.setBounds(10 + i * 50, 290, 40, 30);            
             linksOfFilledBValue[i] = b;           
             topValuePanel.add(b);
        }
        solve = new JButton("Расчитать");
        solve.setBounds(10, 325, 100, 30);  
        topValuePanel.add(solve);
        return topValuePanel;
    }
    
    private JPanel createLogPanel(){
        
        logPanel = new JPanel();
        logPanel.setLayout(null);
        logPanel.setBounds(400, 10, 320, 460);
        
        log = new JTextArea();
        
        // Не позволянет изменять или вводить значения
        log.setEditable(false);
        log.setLineWrap(true);  // Перенос в логе
        log.setWrapStyleWord(true);
        
        scroll = new JScrollPane(log);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBounds(300, 25, 300, 350);
        
        logPanel.add(scroll);
       
        log.append(" В резерве трех железнодорожных станций А, В, С находятся"
                + " соответственно 60, 80, 100 вагонов. Составить оптимальный"
                + " план перегона этих вагонов к четырем пунктам погрузки хлеба,"
                + " если пункту 1 необходимо 40 вагонов, пункту 2 – 60 вагонов,"
                + " пункту 3 – 80 вагонов и пункту 4 – 60 вагонов. Стоимости"
                + " перегонов одного вагона со станции А в указанные пункты"
                + " соответственно равны 1, 2, 3, 4 д.е., со станции"
                + " В – 4, 3, 2 и 1 д.е., со станции С – 1, 2, 2, 1"
            );
        
        return logPanel;
    }
}
