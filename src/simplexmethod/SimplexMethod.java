/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmethod;

import java.util.*;

/**
 *
 * @author jo1nsaint
 */
public class SimplexMethod {
    

    private double matr[][];
    private double function[];
    private boolean flag = false;
    
    public SimplexMethod() {
    }

    public SimplexMethod(double[][] matr, double[] function) {

        this.matr = matr;
        this.function = function;
        
    }

    public double[] findPlan() {
        int count = 0;
        double[] arrayIndexPlan;
        double sumt;
        double[] sum = new double[8];
        for (int z = 0; z < 4; z++) {
            for (int j = 0; j < 7; j++) {
                if (matr[z][j] == 1) {
                    sumt = 0.0;
                    for (int k = 0; k < 4; k++) {
                        if (k != z) {
                            sumt += Math.abs(matr[k][j]);
                        }
                    }
                    if (sumt == 0) {
                        count++;
                    }
                }
            }
        }
        arrayIndexPlan = new double[count];
        int i = 0;
        for (int z = 0; z < 4; z++) {
            for (int j = 0; j < 7; j++) {
                if (matr[z][j] == 1) {
                    sumt = 0.0;
                    for (int k = 0; k < 4; k++) {
                        if (k != z) {
                            sumt += Math.abs(matr[k][j]);
                        }
                    }
                    if (sumt == 0) {
                        arrayIndexPlan[i] = j;
                        i++;
                    }
                }
            }
        }
        return arrayIndexPlan;
    }

    public double[] findDelta(double[] currentPlan) {
        int length = currentPlan.length;
        double[] C = new double[length];
        double[] delta = new double[8];
        for (int z = 0; z < length; z++) {
            C[z] = function[(int) currentPlan[z]];
        }
        for (int k = 0; k < 8; k++) {
            double temp = 0.0;
            for (int j = 0; j < length; j++) {
                temp += C[j] * matr[j][k];
            }
            delta[k] = temp - function[k];
        }
        return delta;
    }

    public void checkPlan(double[] currentDelta) {
        double minElement = currentDelta[0];
        double[] nextDelta = new double[8];
        int indexElem = 0;
        
        for (int k = 1; k < 8; k++) {
            if (minElement > currentDelta[k]) {
                minElement = currentDelta[k];
                indexElem = k;
            }
        }
        double[] minOper = new double[4];
        int indexNewPlan = 0;
        if (minElement < 0) {
            for (int k = 0; k < 4; k++) {
                if (matr[k][indexElem] > 0) {
                    minOper[k] = matr[k][7] / matr[k][indexElem];
                } else {
                    minOper[k] = 1000;
                }
            }
            double minOperation = minOper[0];
            for (int k = 1; k < 4; k++) {
                if (minOper[k] < minOperation) {
                    minOperation = minOper[k];
                    indexNewPlan = k;
                }
            }
            double[][] newMatr = new double[4][8];
            for (int k = 0; k < 4; k++) {
                for (int j = 0; j < 8; j++) {
                    if (k == indexNewPlan) {
                        newMatr[indexNewPlan][j] = matr[indexNewPlan][j] / matr[indexNewPlan][indexElem];
                    } else {
                        newMatr[k][j] = matr[k][j] - (matr[indexNewPlan][j] / matr[indexNewPlan][indexElem]) * matr[k][indexElem];
                    }
                }
            }
            matr = newMatr;
            
        } else {
            flag = true;
        }
    }

    public boolean getFlag() {
        return flag;
    }

    public double mainResult(double[] result) {
        double mainFunct = 0.0;
        for (int k = 0; k < 7; k++) {
            mainFunct += function[k] * result[k];
        }
        return mainFunct;
    }

    public double[] justDoIt() {
        double[] currentDelta;
        double[] currentPlan = null;
        int counter = 0;
        while (!getFlag()) {
            currentPlan = findPlan();
            currentDelta = findDelta(currentPlan);
            checkPlan(currentDelta);
            counter++;
            if(counter > 1000) {
                throw new NullPointerException();
            }
        }
        double[] result = new double[7];
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 7; k++) {
                result[k] = 0;
            }
        }
        for (int k = 0; k < currentPlan.length; k++) {
            result[(int) currentPlan[k]] = matr[k][7];
        }
        return result;
    }
    
}
