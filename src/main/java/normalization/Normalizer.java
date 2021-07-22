/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package normalization;

import java.util.ArrayList;

/**
 *
 * @author manos
 */
public class Normalizer {

    private double max;
    private double min;

    private double normalizedMax;
    private double normalizedMin;

    public Normalizer(double min, double max, double normalizedMin, double normalizedMax) {
        this.min = min;
        this.max = max;
        this.normalizedMax = normalizedMax;
        this.normalizedMin = normalizedMin;
    }

    public double normalizeValue(double value) {
        return ((value - min) / (max - min)) * (normalizedMax - normalizedMin) + normalizedMin;
    }

    public static double sigmoid(double x) {
        return 1 / (1 + (1 / Math.exp(x)));
    }

    public void normalizeList(ArrayList<Double> in) {
        for (int i = 0; i < in.size(); i++) {
            Double d = normalizeValue(in.get(i));
            in.set(i,d);
        }
    }
    
    
    

}
