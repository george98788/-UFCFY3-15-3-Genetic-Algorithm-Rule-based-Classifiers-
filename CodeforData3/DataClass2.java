package simplegaclass2;
import static simplegaclass2.SimpleGAdata3.lengthOfDataInstance;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author georg
 */
public class DataClass2 {
    
    double[] dataSet = new double[lengthOfDataInstance-1];
    double output;

    public DataClass2(double[] datasetTxT) {
        for (int i = 0; i < datasetTxT.length ; i++) {  
            if (i < lengthOfDataInstance-1){
                 dataSet[i] = datasetTxT[i];
            }
            else{
                output = datasetTxT[i];
            }
        } 
    } 
        
    
  
    public double[] getDataClass2Set() {
        return dataSet;
    }

    public void setDataClass2Set(double[] dataSet) {
        this.dataSet = dataSet;
    }

    public double getOutput2() {
        return output;
    }

    public void setOutput2(double output) {
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
