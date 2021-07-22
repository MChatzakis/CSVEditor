/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sort;

import csv.CSVLine;
import java.util.Comparator;
import utils.CommonUtils;

/**
 *
 * @author chatzakis
 */
public class NumberColumnComparator implements Comparator<CSVLine>{
    
    private int column;
    private int reverse = 1;
    
    public NumberColumnComparator(int column, boolean reversed){
        this.column = column;
        if(reversed){
            reverse = -1;
        }
    }
    
    public int compare(CSVLine lineA, CSVLine lineB){
        
        Double d1 = 0.0;
        Double d2 = 0.0;
     
        String strLineA = lineA.getLine().get(column);
        String strLineB = lineB.getLine().get(column);
   
        if(!CommonUtils.isNumeric(strLineA) && !CommonUtils.isNumeric(strLineB)){
            return 0;
        }
        
        if(!CommonUtils.isNumeric(strLineA)){
            return 1;
        }
        
        if(!CommonUtils.isNumeric(strLineB)){
            return -1;
        }
        
        d1 = Double.parseDouble(strLineA);
        d2 = Double.parseDouble(strLineB);
        
        return reverse * Double.compare(d1, d2);
    }
}
