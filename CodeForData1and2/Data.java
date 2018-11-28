/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplegaclass;


import static simplegaclass.SimpleGA.lengthOfDataInstance;
/**
 *
 * @author georg
 */
public class Data {
    
    int[] dataSet = new int[lengthOfDataInstance-1];
    int output;

    public Data(int[] datasetTxT) {
        for (int i = 0; i < datasetTxT.length ; i++) {  
            if (i < lengthOfDataInstance-1){
                 dataSet[i] = datasetTxT[i];
            }
            else{
                output = datasetTxT[i];
             
            }
           
        }
        
    } 
        
    
  
    public int[] getDataSet() {
        return dataSet;
    }

    public void setDataSet(int[] dataSet) {
        this.dataSet = dataSet;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
      @Override
    public String toString() {
        String dataRules = "";
        for (int i = 0; i < dataSet.length; i++) {
            dataRules += dataSet[i];
        }
        dataRules += " " + output;
        return dataRules;
    }
}
