/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        private JComboBox choice;
        private String[] valuesForChoise = {"СЗУ", "MЭ"};
        private JPanel logPanel;
        private JTextArea log;
        private JScrollPane scroll;
        
        private static final int rows = 5;
        private static final int cols = 5;
        private static double[][] arrayData = new double[rows][cols];
        private double[][] result;
        
        public static double function;
        private static int count = 1;
        private double[] matrU = new double[rows - 1];
        private double[] matrV = new double[cols - 1];
        private static double[][] arrayPlan2 = new double[rows - 1][cols - 1];
        
        
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
        solve.addActionListener(e -> {
            try {
                getValue();
            } catch (Exception ex) {
                System.out.println("Entered incorect values : " + ex);
            }
            double check = 0;
            double A = 0;
            double B = 0;
            for(int i = 0; i < arrayData.length - 1; i++ ) { 
                A += arrayData[i][arrayData.length - 1];
                B += arrayData[arrayData.length - 1][i];
            }
            check = A - B;
            if(check != 0) {
                JOptionPane.showMessageDialog(transportProblemFrame, 
                            "Задача не решается, т.к. потребностей больше чем запасов"
                                    + "или наоборот",
                            "Oops!",
                            JOptionPane.ERROR_MESSAGE);
            }
            else {
            int select = choice.getSelectedIndex();
            System.out.println(select);
            switch(select) {
                case 0: {
                    function = 0;
                    try {
                        getValue();
                    } catch (Exception ex) {
                        Logger.getLogger(TransportProblemWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    resetValue();
                    log.append(EOL);
                    log.append("Метод северо-западного угла");
                    try {
                    //NWC problemNWC = new NWC();
                    log.append(EOL);
                    showInfo();
                    log.append(EOL);
                    result = oporPlanNWC();
                    log.append("     Опорный план");
                    log.append(System.lineSeparator());
                    log.append(System.lineSeparator());
                    for(int k = 0; k < rows - 1; k++){
                        for(int j = 0; j < cols - 1; j++){
                             log.append(result[k][j] + "    ");
                        }
                        log.append(System.lineSeparator());
                        log.append(System.lineSeparator());
                    }
                    log.append("     Опорный план");
                    log.append(System.lineSeparator());
                    log.append(System.lineSeparator());
                    for(int k = 0; k < rows - 1; k++){
                        for(int j = 0; j < cols - 1; j++){
                             log.append(arrayPlan2[k][j] + "    ");
                        }
                        log.append(System.lineSeparator());
                        log.append(System.lineSeparator());
                    }
                    potenNWC(result);
                    log.append("     Оптимизированный план");
                    log.append(System.lineSeparator());
                    log.append(System.lineSeparator());
                    result = newPlanNWC(result);
                    for(int k = 0; k < rows - 1; k++){
                        for(int j = 0; j < cols - 1; j++){
                             log.append(result[k][j] + "    ");
                        }
                        log.append(System.lineSeparator());
                        log.append(System.lineSeparator());
                    }
                    log.append("     Оптимизированный план");
                    log.append(System.lineSeparator());
                    log.append(System.lineSeparator());
                    for(int k = 0; k < rows - 1; k++){
                        for(int j = 0; j < cols - 1; j++){
                             log.append(arrayPlan2[k][j] + "    ");
                        }
                        log.append(System.lineSeparator());
                        log.append(System.lineSeparator());
                    }
                        System.out.println("1 - " + function);
                        String finalValue = String.valueOf(function);
                        System.out.println("2 - " + finalValue);
                        log.append("Минимальный расход = " + function);
                        log.append(EOL);
                } catch (Exception ex) {
                        System.out.println("err nwc : " + ex);
                }
                    break;
                }
                case 1: {
                    function = 0;
                    try {
                        getValue();
                    } catch (Exception ex) {
                        Logger.getLogger(TransportProblemWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    log.append(EOL);
                    log.append("Метод минимальных элементов");
                    log.append(EOL);
                    resetValue();
                    showInfo();
                    try {
                        result = oporPlan();
                    } catch (Exception ex) {
                        Logger.getLogger(TransportProblemWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    log.append(EOL);
                    log.append("     Оптимизированный план");
                    log.append(EOL);

                    for(int k = 0; k < rows - 1; k++){
                        for(int j = 0; j < cols - 1; j++){
                            log.append(result[k][j] + "    ");
                        }
                        log.append(System.lineSeparator());
                        log.append(System.lineSeparator());
                    }
                    log.append("     Опорный план");
                    log.append(System.lineSeparator());
                    log.append(System.lineSeparator());
                    for(int k = 0; k < rows - 1; k++){
                        for(int j = 0; j < cols - 1; j++){
                            log.append(arrayPlan2[k][j] + "    ");
                        }
                        log.append(System.lineSeparator());
                        log.append(System.lineSeparator());
                    }
                    poten(result);
                    log.append("     Оптимизированный план");
                    log.append(System.lineSeparator());
                    log.append(System.lineSeparator());
                    try {
                        result = newPlan(result);
                    } catch (Exception ex) {
                        Logger.getLogger(TransportProblemWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for(int k = 0; k < rows - 1; k++){
                        for(int j = 0; j < cols - 1; j++){
                            log.append(result[k][j] + "    ");
                        }
                        log.append(System.lineSeparator());
                        log.append(System.lineSeparator());
                    }
                    log.append("     Оптимизированный план");
                    log.append(System.lineSeparator());
                    log.append(System.lineSeparator());
                    for(int k = 0; k < rows - 1; k++){
                        for(int j = 0; j < cols - 1; j++){
                            log.append(arrayPlan2[k][j] + "    ");
                        }
                        log.append(System.lineSeparator());
                        log.append(System.lineSeparator());
                    }
                    System.out.println(function);
                    log.append("Минимальный расход = " + function);
                    log.append(EOL);
                }
                break;
            }
        }
        });
        choice = new JComboBox(valuesForChoise);
        choice.setBounds(130, 325, 100, 30);
        topValuePanel.add(choice);
        return topValuePanel;
    }
    private void showInfo() { 
        log.append(" В резерве четырех железнодорожных станций А, В, С, D находятся"
                        + " соответственно " +  arrayData[0][4] + ", " +  arrayData[1][4]
                        + ", " +  arrayData[2][4] + ", " +  arrayData[3][4] + ", "
                        + "вагонов. Составить оптимальный план перегона этих вагонов к"
                        + " четырем пунктам погрузки хлеба,"
                        + " если пункту 1 необходимо " +  arrayData[4][0] + " вагонов,"
                        + " пункту 2 – " +  arrayData[4][1] + " вагонов,"
                        + " пункту 3 – " +  arrayData[4][2] + " вагонов"
                        + " и пункту 4 – " +  arrayData[4][3] + " вагонов. Стоимости"
                        + " перегонов одного вагона со станции А в указанные пункты"
                        + " соответственно равны " +  arrayData[0][0] + ", " +  arrayData[0][1]
                        + ", " +  arrayData[0][2] + ", " +  arrayData[0][3] + " д.е., со станции"
                        + " В – " +  arrayData[1][0] + ", " +  arrayData[1][1]
                        + ", " +  arrayData[1][2] + ", " +  arrayData[1][3] +  " д.е.,"
                        + " со станции С – " +  arrayData[2][0] + ", " +  arrayData[2][1]
                        + ", " +  arrayData[2][2] + ", " +  arrayData[2][3] + " д.е.,"
                        + " со станции D – " +  arrayData[3][0] + ", " +  arrayData[3][1]
                        + ", " +  arrayData[3][2] + ", " +  arrayData[3][3] + " ." 
                            );
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
        
        log.append("СЗУ -считать методом северо-западного угла" + EOL);
        log.append("МЭ - считать методом минимальных элементов"+ EOL);
        return logPanel;
    }
    
    public void resetValue() {
        for(int i = 0; i < rows - 1; i++ ) {
            matrU[i] = 0;
            matrV[i] = 0;
            for(int j = 0; j < cols - 1; j++) {
                arrayPlan2[i][j] = 0;
            }
        }
    }
    
    public void getValue() throws Exception {
        for (int k = 0; k < rows; k++) {
            
            for (int j = 0; j < cols; j++) {
                arrayData[k][j] = 0.0;
            }
        }
        try {
            for(int i = 0; i < linksOfFilledArrValue.length; i++) {
                for (int j = 0; j < linksOfFilledArrValue[i].length; j++) {
                    arrayData[i][j] = Double.valueOf(linksOfFilledArrValue[i][j].getText());
                }
            }
            for(int i = 0; i < rows - 1; i++) { 
                arrayData[i][rows - 1] = Double.valueOf(linksOfFilledAValue[i].getText());
                arrayData[rows - 1][i] = Double.valueOf(linksOfFilledBValue[i].getText());
            }
            arrayData[rows - 1][cols - 1] = 0.0d;
        } 
        catch(NumberFormatException nfe) {
            System.out.println("Err-1 in btn solve : " + nfe);
            JOptionPane.showMessageDialog(transportProblemFrame, 
                            "Заполните все поля!(Поля не могут быть пустыми"
                                    + " или иметь буквенное значение)",
                            "Ошибка заполнения полей!",
                            JOptionPane.ERROR_MESSAGE);
            //throw new Exception();
        }
    }

    public double[] findMinElementNWC() {
        double[] minElement = new double[3];
        minElement[0] = 1000;
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayData[k][j] < minElement[0] && arrayData[k][j] != 0) {
                    minElement[0] = arrayData[k][j];
                    minElement[1] = k;
                    minElement[2] = j;
                }
            }
        }
        arrayData[(int) minElement[1]][(int) minElement[2]] = 0;
        return minElement;
    }

    public double[][] oporPlanNWC() throws Exception {
        getValue();
        double[][] arrayPlan = new double[rows - 1][cols - 1];
        double sum = 1000;
        for (int k = 0; k < count; k++) {
            findMinElementNWC();
        }
        while (sum != 0) {
            double[] minElement = findMinElementNWC();
            double minMin = arrayData[(int) minElement[1]][cols - 1] < arrayData[rows - 1][(int) minElement[2]] ? arrayData[(int) minElement[1]][cols - 1] : arrayData[rows - 1][(int) minElement[2]];
            if (minMin == arrayData[(int) minElement[1]][cols - 1]) {
                for (int k = 0; k < cols - 1; k++) {
                    arrayData[(int) minElement[1]][k] = 0;
                }
                arrayPlan[(int) minElement[1]][(int) minElement[2]] = minElement[0];
                arrayPlan2[(int) minElement[1]][(int) minElement[2]] = arrayData[(int) minElement[1]][cols - 1];
                arrayData[rows - 1][(int) minElement[2]] = arrayData[rows - 1][(int) minElement[2]] - arrayData[(int) minElement[1]][cols - 1];
                arrayData[(int) minElement[1]][cols - 1] = 0;
            } else {
                arrayPlan[(int) minElement[1]][(int) minElement[2]] = minElement[0];
                arrayPlan2[(int) minElement[1]][(int) minElement[2]] = arrayData[rows - 1][(int) minElement[2]];
                for (int k = 0; k < rows - 1; k++) {
                    arrayData[k][(int) minElement[2]] = 0;
                }
                arrayData[(int) minElement[1]][cols - 1] = arrayData[(int) minElement[1]][cols - 1] - arrayData[rows - 1][(int) minElement[2]];
                arrayData[rows - 1][(int) minElement[2]] = 0;
            }
            sum = 0;
            for (int j = 0; j < cols - 1; j++) {
                sum += arrayData[rows - 1][j];
            }
            for (int k = 0; k < rows - 1; k++) {
                sum += arrayData[k][cols - 1];
            }
        }
        int localCount = 0;
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayPlan[k][j] != 0) {
                    localCount++;
                }
            }
        }
        if (localCount != rows + cols - 3) {
            count++;
            oporPlanNWC();
        }
        arrayPlan[0][0] = 1;
        return arrayPlan;
    }

    public double[][] oporPlan2NWC() throws Exception {
       getValue();
        double[][] arrayPlan = new double[rows - 1][cols - 1];
        double sum = 1000;
        int count2 = 0;
        double[] minElement = new double[3];
        while (sum != 0) {
            for (int k = 0; k < rows - 1; k++) {
                for (int j = count2; j < cols - 1; j++) {
                    if (arrayData[k][j] != 0) {
                        minElement[0] = arrayData[k][j];
                        minElement[1] = k;
                        minElement[2] = j;
                    }
                }
            }
            double minMin = arrayData[(int) minElement[1]][cols - 1] < arrayData[rows - 1][(int) minElement[2]] ? arrayData[(int) minElement[1]][cols - 1] : arrayData[rows - 1][(int) minElement[2]];
            if (minMin == arrayData[(int) minElement[1]][cols - 1]) {
                for (int k = 0; k < cols - 1; k++) {
                    arrayData[(int) minElement[1]][k] = 0;
                }
                arrayPlan[(int) minElement[1]][(int) minElement[2]] = minElement[0];
                arrayPlan2[(int) minElement[1]][(int) minElement[2]] = arrayData[(int) minElement[1]][cols - 1];
                arrayData[rows - 1][(int) minElement[2]] = arrayData[rows - 1][(int) minElement[2]] - arrayData[(int) minElement[1]][cols - 1];
                arrayData[(int) minElement[1]][cols - 1] = 0;
            } else {
                arrayPlan[(int) minElement[1]][(int) minElement[2]] = minElement[0];
                arrayPlan2[(int) minElement[1]][(int) minElement[2]] = arrayData[rows - 1][(int) minElement[2]];
                for (int k = 0; k < rows - 1; k++) {
                    arrayData[k][(int) minElement[2]] = 0;
                }
                arrayData[(int) minElement[1]][cols - 1] = arrayData[(int) minElement[1]][cols - 1] - arrayData[rows - 1][(int) minElement[2]];
                arrayData[rows - 1][(int) minElement[2]] = 0;
            }
            sum = 0;
            for (int j = 0; j < cols - 1; j++) {
                sum += arrayData[rows - 1][j];
            }
            for (int k = 0; k < rows - 1; k++) {
                sum += arrayData[k][cols - 1];
            }
        }
        int localCount = 0;
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayPlan[k][j] != 0) {
                    localCount++;
                }
            }
        }
        if (localCount != rows + cols - 3) {
            oporPlan2NWC();
        }
        return arrayPlan;
    }

    public void potenNWC(double[][] oporPlan) {
        for (int k = 0; k < rows - 1; k++) {
            matrU[k] = -1000;
        }
        for (int k = 0; k < cols - 1; k++) {
            matrV[k] = -1000;
        }
        matrU[0] = 0;
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (oporPlan[k][j] != 0) {
                    if (matrV[j] != -1000) {
                        matrU[k] = oporPlan[k][j] - matrV[j];
                    } else if (matrU[k] != -1000) {
                        matrV[j] = oporPlan[k][j] - matrU[k];
                    }
                }
            }
        }
    }

    public double[][] newPlanNWC(double[][] oporPlan) throws Exception {
        getValue();
        potenNWC(oporPlan);
        List<Double> fail = new ArrayList<>();
        List<Integer> failK = new ArrayList<>();
        List<Integer> failJ = new ArrayList<>();
        List<Double> failElement = new ArrayList<>();
        double temp = 0.0;
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayData[k][j] < matrU[k] + matrV[j]) {
                    temp = matrU[k] + matrV[j] - arrayData[k][j];
                    failElement.add(temp);
                    fail.add(arrayData[k][j]);
                    failK.add(k);
                    failJ.add(j);
                }
            }
        }
        if (fail.isEmpty()) {
            double function = 0.0;
            for (int k = 0; k < rows - 1; k++) {
                for (int j = 0; j < cols - 1; j++) {
                    if (arrayPlan2[k][j] != 0) {
                        function += arrayPlan2[k][j] * oporPlan[k][j];
                    }
                }
            }
        } else {
            double maxElement = 0;
            int index = -1;
            for (int k = 0; k < failElement.size(); k++) {
                if (failElement.get(k) > maxElement) {
                    maxElement = failElement.get(k);
                    index = k;
                }
            }
            int currentK = failK.get(index);
            int currentJ = failJ.get(index);
            System.out.println(currentK);
            System.out.println(currentJ);
            double firstElement = 0.0;
            int firstElementK = - 1;
            int firstElementJ = -1;
            List<Double> list1 = new ArrayList<>();
            double secondElement = 0.0;
            int secondElementK = -1;
            int secondElementJ = -1;
            List<Double> list2 = new ArrayList<>();
            double thirdElement = 0.0;
            int thirdElementK = -1;
            int thirdElementJ = -1;
            List<Double> list3 = new ArrayList<>();
            while (true) {
                for (int k = 0; k < cols - 1; k++) {
                    if (oporPlan[currentK][k] != 0 && k != currentJ) {
                        firstElement = arrayData[currentK][k];
                        firstElementK = currentK;
                        firstElementJ = k;
                        if (!list1.contains(arrayData[currentK][k])) {
                            list1.add(firstElement);
                            break;
                        }
                    }
                }
                for (int k = 0; k < rows - 1; k++) {
                    if (oporPlan[k][currentJ] != 0 && k != currentK) {
                        secondElement = arrayData[k][currentJ];
                        secondElementK = k;
                        secondElementJ = currentJ;
                        if (!list2.contains(arrayData[k][currentJ])) {
                            list2.add(secondElement);
                            break;
                        }
                    }
                }
                if (oporPlan[secondElementK][firstElementJ] != 0) {
                    thirdElement = arrayData[secondElementK][firstElementJ];
                    thirdElementK = secondElementK;
                    thirdElementJ = firstElementJ;
                    if (!list3.contains(arrayData[thirdElementJ][firstElementJ])) {
                        list3.add(thirdElement);
                        break;
                    }
                }
                if (firstElement != 0 && secondElement != 0 && thirdElement != 0) {
                    break;
                }
            }
            if (arrayPlan2[firstElementK][firstElementJ] < arrayPlan2[secondElementK][secondElementJ]) {
                arrayPlan2[thirdElementK][thirdElementJ] += arrayPlan2[firstElementK][firstElementJ];
                arrayPlan2[currentK][currentJ] += arrayPlan2[firstElementK][firstElementJ];
                oporPlan[currentK][currentJ] = arrayData[currentK][currentJ];
                arrayPlan2[secondElementK][secondElementJ] -= arrayPlan2[firstElementK][firstElementJ];
                arrayPlan2[firstElementK][firstElementJ] -= arrayPlan2[firstElementK][firstElementJ];
                oporPlan[firstElementK][firstElementJ] = 0.0;
            } else {
                arrayPlan2[thirdElementK][thirdElementJ] += arrayPlan2[secondElementK][secondElementJ];
                arrayPlan2[currentK][currentJ] = arrayPlan2[currentK][currentJ] + arrayPlan2[secondElementK][secondElementJ];
                oporPlan[currentK][currentJ] = arrayData[currentK][currentJ];
                arrayPlan2[firstElementK][firstElementJ] -= arrayPlan2[secondElementK][secondElementJ];
                arrayPlan2[secondElementK][secondElementJ] -= arrayPlan2[secondElementK][secondElementJ];
                oporPlan[secondElementK][secondElementJ] = 0.0;
            }
        }
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayPlan2[k][j] != 0) {
                    function += arrayPlan2[k][j] * oporPlan[k][j];
                }
            }
        }
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayPlan2[k][j] == 0) {
                    oporPlan[k][j] = 0;
                }
            }
        }
        return oporPlan;
    }
    
    public double[] findMinElement() {
        double[] minElement = new double[3];
        minElement[0] = 1000;
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayData[k][j] < minElement[0] && arrayData[k][j] != 0) {
                    minElement[0] = arrayData[k][j];
                    minElement[1] = k;
                    minElement[2] = j;
                }
            }
        }
        arrayData[(int) minElement[1]][(int) minElement[2]] = 0;
        return minElement;
    }

    public double[][] oporPlan() throws Exception {
        getValue();
        double[][] arrayPlan = new double[rows - 1][cols - 1];
        double sum = 1000;
        for (int k = 0; k < count; k++) {
            findMinElement();
        }
        while (sum != 0) {
            double[] minElement = findMinElement();
            double minMin = arrayData[(int) minElement[1]][cols - 1] < arrayData[rows - 1][(int) minElement[2]] ? arrayData[(int) minElement[1]][cols - 1] : arrayData[rows - 1][(int) minElement[2]];
            if (minMin == arrayData[(int) minElement[1]][cols - 1]) {
                for (int k = 0; k < cols - 1; k++) {
                    arrayData[(int) minElement[1]][k] = 0;
                }
                arrayPlan[(int) minElement[1]][(int) minElement[2]] = minElement[0];
                arrayPlan2[(int) minElement[1]][(int) minElement[2]] = arrayData[(int) minElement[1]][cols - 1];
                arrayData[rows - 1][(int) minElement[2]] = arrayData[rows - 1][(int) minElement[2]] - arrayData[(int) minElement[1]][cols - 1];
                arrayData[(int) minElement[1]][cols - 1] = 0;
            } else {
                arrayPlan[(int) minElement[1]][(int) minElement[2]] = minElement[0];
                arrayPlan2[(int) minElement[1]][(int) minElement[2]] = arrayData[rows - 1][(int) minElement[2]];
                for (int k = 0; k < rows - 1; k++) {
                    arrayData[k][(int) minElement[2]] = 0;
                }
                arrayData[(int) minElement[1]][cols - 1] = arrayData[(int) minElement[1]][cols - 1] - arrayData[rows - 1][(int) minElement[2]];
                arrayData[rows - 1][(int) minElement[2]] = 0;
            }
            sum = 0;
            for (int j = 0; j < cols - 1; j++) {
                sum += arrayData[rows - 1][j];
            }
            for (int k = 0; k < rows - 1; k++) {
                sum += arrayData[k][cols - 1];
            }
        }
        int localCount = 0;
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayPlan[k][j] != 0) {
                    localCount++;
                }
            }
        }
        if (localCount != rows + cols - 3) {
            count++;
            oporPlan();
        }
        arrayPlan[0][0] = 1;
        System.out.println(Arrays.deepToString(arrayPlan));
        System.out.println(Arrays.deepToString(arrayPlan2));
        return arrayPlan;
    }

    public double[][] oporPlan2() throws Exception {
        getValue();
        double[][] arrayPlan = new double[rows - 1][cols - 1];
        double sum = 1000;
        int count2 = 0;
        double[] minElement = new double[3];
        while (sum != 0) {
            for (int k = 0; k < rows - 1; k++) {
                for (int j = count2; j < cols - 1; j++) {
                    if (arrayData[k][j] != 0) {
                        minElement[0] = arrayData[k][j];
                        minElement[1] = k;
                        minElement[2] = j;
                    }
                }
            }
            double minMin = arrayData[(int) minElement[1]][cols - 1] < arrayData[rows - 1][(int) minElement[2]] ? arrayData[(int) minElement[1]][cols - 1] : arrayData[rows - 1][(int) minElement[2]];
            if (minMin == arrayData[(int) minElement[1]][cols - 1]) {
                for (int k = 0; k < cols - 1; k++) {
                    arrayData[(int) minElement[1]][k] = 0;
                }
                arrayPlan[(int) minElement[1]][(int) minElement[2]] = minElement[0];
                arrayPlan2[(int) minElement[1]][(int) minElement[2]] = arrayData[(int) minElement[1]][cols - 1];
                arrayData[rows - 1][(int) minElement[2]] = arrayData[rows - 1][(int) minElement[2]] - arrayData[(int) minElement[1]][cols - 1];
                arrayData[(int) minElement[1]][cols - 1] = 0;
            } else {
                arrayPlan[(int) minElement[1]][(int) minElement[2]] = minElement[0];
                arrayPlan2[(int) minElement[1]][(int) minElement[2]] = arrayData[rows - 1][(int) minElement[2]];
                for (int k = 0; k < rows - 1; k++) {
                    arrayData[k][(int) minElement[2]] = 0;
                }
                arrayData[(int) minElement[1]][cols - 1] = arrayData[(int) minElement[1]][cols - 1] - arrayData[rows - 1][(int) minElement[2]];
                arrayData[rows - 1][(int) minElement[2]] = 0;
            }
            sum = 0;
            for (int j = 0; j < cols - 1; j++) {
                sum += arrayData[rows - 1][j];
            }
            for (int k = 0; k < rows - 1; k++) {
                sum += arrayData[k][cols - 1];
            }
        }
        int localCount = 0;
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayPlan[k][j] != 0) {
                    localCount++;
                }
            }
        }
        if (localCount != rows + cols - 3) {
            oporPlan2();
        }
        System.out.println(Arrays.deepToString(arrayPlan2) + "А тут норм");
        System.out.println(Arrays.deepToString(arrayPlan));
        return arrayPlan;
    }

    public void poten(double[][] oporPlan) {
        for (int k = 0; k < rows - 1; k++) {
            matrU[k] = -1000;
        }
        for (int k = 0; k < cols - 1; k++) {
            matrV[k] = -1000;
        }
        matrU[0] = 0;
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (oporPlan[k][j] != 0) {
                    if (matrV[j] != -1000) {
                        matrU[k] = oporPlan[k][j] - matrV[j];
                    } else if (matrU[k] != -1000) {
                        matrV[j] = oporPlan[k][j] - matrU[k];
                    }
                }
            }
        }
    }

    public double[][] newPlan(double[][] oporPlan) throws Exception {
        getValue();
        poten(oporPlan);
        List<Double> fail = new ArrayList<>();
        List<Integer> failK = new ArrayList<>();
        List<Integer> failJ = new ArrayList<>();
        List<Double> failElement = new ArrayList<>();
        double temp = 0.0;
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayData[k][j] < matrU[k] + matrV[j]) {
                    temp = matrU[k] + matrV[j] - arrayData[k][j];
                    failElement.add(temp);
                    fail.add(arrayData[k][j]);
                    failK.add(k);
                    failJ.add(j);
                }
            }
        }
        if (fail.isEmpty()) {
            double function = 0.0;
            for (int k = 0; k < rows - 1; k++) {
                for (int j = 0; j < cols - 1; j++) {
                    if (arrayPlan2[k][j] != 0) {
                        function += arrayPlan2[k][j] * oporPlan[k][j];
                    }
                }
            }
        } else {
            double maxElement = 0;
            int index = -1;
            for (int k = 0; k < failElement.size(); k++) {
                if (failElement.get(k) > maxElement) {
                    maxElement = failElement.get(k);
                    index = k;
                }
            }
            int currentK = failK.get(index);
            int currentJ = failJ.get(index);
            System.out.println(currentK);
            System.out.println(currentJ);
            double firstElement = 0.0;
            int firstElementK = - 1;
            int firstElementJ = -1;
            List<Double> list1 = new ArrayList<>();
            double secondElement = 0.0;
            int secondElementK = -1;
            int secondElementJ = -1;
            List<Double> list2 = new ArrayList<>();
            double thirdElement = 0.0;
            int thirdElementK = -1;
            int thirdElementJ = -1;
            List<Double> list3 = new ArrayList<>();
            while (true) {
                for (int k = 0; k < cols - 1; k++) {
                    if (oporPlan[currentK][k] != 0 && k != currentJ) {
                        firstElement = arrayData[currentK][k];
                        firstElementK = currentK;
                        firstElementJ = k;
                        if (!list1.contains(arrayData[currentK][k])) {
                            list1.add(firstElement);
                            break;
                        }
                    }
                }
                for (int k = 0; k < rows - 1; k++) {
                    if (oporPlan[k][currentJ] != 0 && k != currentK) {
                        secondElement = arrayData[k][currentJ];
                        secondElementK = k;
                        secondElementJ = currentJ;
                        if (!list2.contains(arrayData[k][currentJ])) {
                            list2.add(secondElement);
                            break;
                        }
                    }
                }
                if (oporPlan[secondElementK][firstElementJ] != 0) {
                    thirdElement = arrayData[secondElementK][firstElementJ];
                    thirdElementK = secondElementK;
                    thirdElementJ = firstElementJ;
                    if (!list3.contains(arrayData[thirdElementJ][firstElementJ])) {
                        list3.add(thirdElement);
                        break;
                    }
                }
                if (firstElement != 0 && secondElement != 0 && thirdElement != 0) {
                    break;
                }
            }
            if (arrayPlan2[firstElementK][firstElementJ] < arrayPlan2[secondElementK][secondElementJ]) {
                arrayPlan2[thirdElementK][thirdElementJ] += arrayPlan2[firstElementK][firstElementJ];
                arrayPlan2[currentK][currentJ] += arrayPlan2[firstElementK][firstElementJ];
                oporPlan[currentK][currentJ] = arrayData[currentK][currentJ];
                arrayPlan2[secondElementK][secondElementJ] -= arrayPlan2[firstElementK][firstElementJ];
                arrayPlan2[firstElementK][firstElementJ] -= arrayPlan2[firstElementK][firstElementJ];
                oporPlan[firstElementK][firstElementJ] = 0.0;
            } else {
                arrayPlan2[thirdElementK][thirdElementJ] += arrayPlan2[secondElementK][secondElementJ];
                arrayPlan2[currentK][currentJ] = arrayPlan2[currentK][currentJ] + arrayPlan2[secondElementK][secondElementJ];
                oporPlan[currentK][currentJ] = arrayData[currentK][currentJ];
                arrayPlan2[firstElementK][firstElementJ] -= arrayPlan2[secondElementK][secondElementJ];
                arrayPlan2[secondElementK][secondElementJ] -= arrayPlan2[secondElementK][secondElementJ];
                oporPlan[secondElementK][secondElementJ] = 0.0;
            }
        }
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayPlan2[k][j] != 0) {
                    function += arrayPlan2[k][j] * oporPlan[k][j];
                }
            }
        }
        for (int k = 0; k < rows - 1; k++) {
            for (int j = 0; j < cols - 1; j++) {
                if (arrayPlan2[k][j] == 0) {
                    oporPlan[k][j] = 0;
                }
            }
        }
        System.out.println(Arrays.deepToString(oporPlan));
        System.out.println(Arrays.deepToString(arrayPlan2));
        System.out.println(function);
        return oporPlan;
    }
}
