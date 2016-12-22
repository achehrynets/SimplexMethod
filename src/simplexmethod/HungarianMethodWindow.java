/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;

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
import static simplexmethod.SimplexMethodWindow.EOL;

/**
 *
 * @author jo1nsaint
 */
public class HungarianMethodWindow {

    private JFrame hungarianFrame;
    private JPanel logPanel;
    private JTextArea log;
    private JScrollPane scroll;
    private JPanel valuesPanel;
    private JTextField values;
    private JTextField[][] valuesLinks;
    private String[] companyWayString = {"Вице-призеденты","По финансам", "По маркетингу",
                                            "По производству", "По персоналу"
                                        };
    private String[] vicePresidentsString = {"A", "Б", "В", "Г"};
    private JLabel vicePresidentsLabel;
    private JLabel companyWayLabel;
    private JLabel targetLabel;
    private JComboBox select;
    private String[] selectString = {"min", "max"};
    private static final int rows = 4;
    private static final int cols = 4;
    private static int count = 0;
    private double[] matrU = new double[rows - 1];
    private double[] matrV = new double[cols - 1];
    private static double[][] arrayPlan2 = new double[rows - 1][cols - 1];
    private double arrayData[][] = new double[rows][cols];
    private double[] resultRows = new double[rows - 1];
    private double[] resultColumns = new double[cols - 1];
    private double minResult = 0;
    private double[][] arrayData2 = new double[rows][cols];
    private int selectedMinOrMax;
    private double maxResult;
    private JButton solve;
        
    public HungarianMethodWindow() {
        hungarianFrame = new JFrame("Венгерский метод");
        hungarianFrame.setBounds(450, 200, 600, 300);
        hungarianFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hungarianFrame.setResizable(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("Error in transportProblemWindow component : " + e);
        }
        hungarianFrame.add(fillValuesPanel());
        hungarianFrame.add(createLogPanel());
        hungarianFrame.setVisible(true);
    }
 
    private JPanel fillValuesPanel() {
        valuesPanel = new JPanel();
        valuesPanel.setLayout(null);
        valuesPanel.setBorder(new TitledBorder("Ввод значений"));
        valuesPanel.setBounds(10,10, 350, 290);
        
        companyWayLabel = new JLabel(companyWayString[0]);
        companyWayLabel.setBounds(10, 20, 150, 30);
        valuesPanel.add(companyWayLabel);
        for(int i = 1; i < 5; i++) {
            companyWayLabel = new JLabel(companyWayString[i]);
            companyWayLabel.setBounds(10, 10 + i * 40, 150, 30);
            valuesPanel.add(companyWayLabel);
        }
        
        for(int i = 0; i < rows; i++ ) {
            vicePresidentsLabel = new JLabel(vicePresidentsString[i]);
            vicePresidentsLabel.setBounds(160 + i * 50, 20, 30, 30);
            valuesPanel.add(vicePresidentsLabel);
        }
        valuesLinks = new JTextField[rows][rows];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < rows; j++) {
                values = new JTextField();
                values.setBounds(50 * j + 150, 50 + i * 40, 40, 30);
                valuesLinks[i][j] = values;
                valuesPanel.add(values);
            }
        }
        targetLabel = new JLabel("Выберите цель");
        targetLabel.setBounds(10, 200, 150, 30);
        valuesPanel.add(targetLabel);
        
        select = new JComboBox(selectString);
        select.setBounds(150, 200, 70, 30);
        valuesPanel.add(select);
        
        solve = new JButton("Расчитать");
        solve.setBounds(80, 240, 150, 30);
        valuesPanel.add(solve);
        
        solve.addActionListener( e -> {
            setData();
            selectedMinOrMax = select.getSelectedIndex();
            switch(selectedMinOrMax) {
                case 0: {
                    resetResulValues();
                    solveMin();
                    break;
                }
                case 1: {
                    resetResulValues();
                    solveMax();
                    break;
                }
            }
        });
        
        return valuesPanel;
    }
    
    private JPanel createLogPanel(){
        
        logPanel = new JPanel();
        logPanel.setLayout(null);
        //logPanel.setBorder(new TitledBorder("Лог работы программы"));
        logPanel.setBounds(350, 10, 320, 460);
        
        log = new JTextArea();
        
        // Не позволянет изменять или вводить значения
        log.setEditable(false);
        log.setLineWrap(true);  // Перенос в логе
        log.setWrapStyleWord(true);
        
        scroll = new JScrollPane(log);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBounds(370, 17, 225, 280);
     
        logPanel.add(scroll);
       
        return logPanel;
    }
    
    private void fillValue() { 
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < rows; j++) {
                arrayData[i][j] = 0;
            }
        }
    }
    
    private void setData() { 
        try{
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < rows; j++) {
                    arrayData[i][j] = Double.valueOf(valuesLinks[i][j].getText());
                }
            }
        }
        catch(NumberFormatException nfe) {
            System.out.println("Err entered values : " + nfe);
            JOptionPane.showMessageDialog(hungarianFrame, 
                                            "Не корректно введенны данныe!"
                                            + EOL + "Проверьте, нет ли пустых ячек"
                                            , "Ошибка ввода!",
                                            JOptionPane.ERROR_MESSAGE
                                        );
            fillValue();
        }
    }
    
    
    public double[] findMinElementRowsMin() {
        double[] minElement = new double[rows];
        for (int k = 0; k < rows; k++) {
            minElement[k] = 1000;
            for (int j = 0; j < cols; j++) {
                if (arrayData[k][j] < minElement[k]) {
                    minElement[k] = arrayData[k][j];
                }
            }
        }
        return minElement;
    }

    public void reductMatrMin(double[] minElement) {
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < cols; j++) {
                arrayData[k][j] = arrayData[k][j] - minElement[k];
            }
        }
    }

    public double[] findMinElementColsMin() {
        double[] minElement = new double[rows];
        for (int k = 0; k < cols; k++) {
            minElement[k] = 1000;
            for (int j = 0; j < rows; j++) {
                if (arrayData[j][k] < minElement[k]) {
                    minElement[k] = arrayData[j][k];
                }
            }
        }
        return minElement;
    }

    public void reductMatr2Min(double[] minElement) {
        for (int k = 0; k < cols; k++) {
            for (int j = 0; j < rows; j++) {
                arrayData[j][k] = arrayData[j][k] - minElement[k];
            }
        }
    }

    public void findResultMin() {
        int count2 = 0;
        double[][] arr = new double[rows][cols];
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < cols; j++) {
                arr[k][j] = arrayData[k][j];
            }
        }
        while (true) {
            for (int k = 0; k < rows; k++) {
                for (int j = 0; j < cols; j++) {
                    arrayData[k][j] = arr[k][j];
                }
            }
            for (int k = 0; k < rows; k++) {
                int tempCount = 0;
                int currentCols = 0;
                for (int j = 0; j < cols; j++) {
                    if (arrayData[k][j] == 0) {
                        tempCount++;
                        currentCols = j;
                    }
                }
                if (tempCount == 1) {
                    for (int z = 0; z < rows; z++) {
                        arrayData[z][currentCols] = -1;
                    }
                    for (int z = 0; z < cols; z++) {
                        arrayData[k][z] = -1;
                    }
                    arrayData[k][currentCols] = 666;
                } else {
                    for (int h = 0; h < cols; h++) {
                        if (arrayData[k][h] == 0) {
                            if (count != 0) {
                                count--;
                            } else {
                                for (int z = 0; z < rows; z++) {
                                    arrayData[z][h] = -1;
                                }
                                for (int z = 0; z < cols; z++) {
                                    arrayData[k][z] = -1;
                                }
                                arrayData[k][h] = 666;
                            }
                        }
                    }
                }
            }
            for (int k = 0; k < rows; k++) {
                for (int j = 0; j < cols; j++) {
                    if (arrayData[k][j] == 666) {
                        count++;
                    }
                }
            }
            if (count == rows) {
                break;
            }
            count2++;
            count = count2;
            System.out.println(count);
        }
    }
    
    private void resetResulValues() {
        for(int i = 0; i < resultColumns.length; i++) { 
            resultColumns[i] = 0;
            resultRows[i] = 0;
        }
        minResult = 0;
        maxResult = 0;
    }
    
    private void solveMin() {
        saveData();
        log.append(EOL);
        resultRows = findMinElementRowsMin();
        reductMatrMin(resultRows);
        resultColumns = findMinElementColsMin();
        reductMatr2Min(resultColumns);
        findResultMin();
        changeValues();
        log.append("     Матрица назначения");
        log.append(System.lineSeparator());
        for(int k = 0; k < rows; k++){
            for(int j = 0; j < cols; j++){
                log.append(arrayData[k][j] + "    ");
            }
            log.append(System.lineSeparator());
            log.append(System.lineSeparator());
        }
        countResultForMin();
        log.append("Минимизированный командировочный расход = " + minResult + " тыс $");
        log.append(EOL);
        log.append(EOL);
//        for(int i = 0; i < arrayData.length; i++) {
//            for(int j = 0; j < arrayData.length; j++) {
//                for(int l = 0; l < arrayData.length; l++) {
//                    
//                }
//            }
//        }
        
    }
    
    public void saveData() {
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < cols; j++) {
                arrayData2[k][j] = arrayData[k][j];
            }
        }
    }

    public void modifMatr() {
        double maxElement = 0;
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < cols; j++) {
                if (arrayData[k][j] > maxElement) {
                    maxElement = arrayData[k][j];
                }
            }
        }
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < cols; j++) {
                arrayData[k][j] = arrayData[k][j] * (-1) + maxElement;
            }
        }
    }

    public double[] findMinElementRows() {
        double[] minElement = new double[rows];
        for (int k = 0; k < rows; k++) {
            minElement[k] = 1000;
            for (int j = 0; j < cols; j++) {
                if (arrayData[k][j] < minElement[k]) {
                    minElement[k] = arrayData[k][j];
                }
            }
        }
        return minElement;
    }

    public void reductMatr(double[] minElement) {
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < cols; j++) {
                arrayData[k][j] = arrayData[k][j] - minElement[k];
            }
        }
    }

    public double[] findMinElementCols() {
        double[] minElement = new double[rows];
        for (int k = 0; k < cols; k++) {
            minElement[k] = 1000;
            for (int j = 0; j < rows; j++) {
                if (arrayData[j][k] < minElement[k]) {
                    minElement[k] = arrayData[j][k];
                }
            }
        }
        return minElement;
    }

    public void reductMatr2(double[] minElement) {
        for (int k = 0; k < cols; k++) {
            for (int j = 0; j < rows; j++) {
                arrayData[j][k] = arrayData[j][k] - minElement[k];
            }
        }
    }

    public void findResult() {
        int count2 = 0;
        double[][] arr = new double[rows][cols];
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < cols; j++) {
                arr[k][j] = arrayData[k][j];
            }
        }
        while (true) {
            for (int k = 0; k < rows; k++) {
                for (int j = 0; j < cols; j++) {
                    arrayData[k][j] = arr[k][j];
                }
            }
            for (int k = 0; k < rows; k++) {
                int tempCount = 0;
                int currentCols = 0;
                for (int j = 0; j < cols; j++) {
                    if (arrayData[k][j] == 0) {
                        tempCount++;
                        currentCols = j;
                    }
                }
                if (tempCount == 1) {
                    for (int z = 0; z < rows; z++) {
                        arrayData[z][currentCols] = -1;
                    }
                    for (int z = 0; z < cols; z++) {
                        arrayData[k][z] = -1;
                    }
                    arrayData[k][currentCols] = 666;
                } else {
                    for (int h = 0; h < cols; h++) {
                        if (arrayData[k][h] == 0) {
                            if (count != 0) {
                                count--;
                            } else {
                                for (int z = 0; z < rows; z++) {
                                    arrayData[z][h] = -1;
                                }
                                for (int z = 0; z < cols; z++) {
                                    arrayData[k][z] = -1;
                                }
                                arrayData[k][h] = 666;
                            }
                        }
                    }
                }
            }
            for (int k = 0; k < rows; k++) {
                for (int j = 0; j < cols; j++) {
                    if (arrayData[k][j] == 666) {
                        count++;
                    }
                }
            }
            if (count == rows) {
                break;
            }
            count2++;
            count = count2;
            System.out.println(count);
        }
    }
    
    private void solveMax() {
        saveData();
        modifMatr();
        resultRows = findMinElementRows();
        reductMatr(resultRows);
        resultColumns = findMinElementCols();
        reductMatr2(resultColumns);
        findResult();
        changeValues();
        log.append("     Матрица назначения");
        log.append(System.lineSeparator());
        for(int k = 0; k < rows; k++){
            for(int j = 0; j < cols; j++){
                log.append(arrayData[k][j] + "    ");
            }
            log.append(System.lineSeparator());
            log.append(System.lineSeparator());
        }
        countResultForMax();
        log.append("Максимизированный командировочный доход = " + maxResult + " тыс $");
        log.append(EOL);
        log.append(EOL);    
    }

    private void changeValues() {
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < cols; j++) {
                if (arrayData[k][j] == -1.0) {
                    arrayData[k][j] = 0;
                } else {
                    arrayData[k][j] = 1;
                }
            }
        }
    
    }
    
    private void countResultForMin() {
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < cols; j++) {
                if (arrayData[k][j] != 0) {
                    minResult += arrayData2[k][j];
                }
            }
        }
    }
    
    private void countResultForMax() {
        for (int k = 0; k < rows; k++) {
            for (int j = 0; j < cols; j++) {
                if (arrayData[k][j] != 0) {
                    maxResult += arrayData2[k][j];
                }
            }
        }
    }
    
}
