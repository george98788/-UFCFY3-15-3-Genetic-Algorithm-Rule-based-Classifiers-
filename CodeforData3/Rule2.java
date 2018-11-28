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
public class Rule2 {

    int conditionLength = lengthOfDataInstance-1;
    double conditionGenelowerBound[] = new double[lengthOfDataInstance -1];
    double conditionGeneUpperbound[] = new double[lengthOfDataInstance -1];
    double output = 0;

    public int getConditionLength() {
        return conditionLength;
    }

    public void setConditionLength(int conditionLength) {
        conditionLength = conditionLength;
    }

    public double[] getConditionGenelowerBound() {
        return conditionGenelowerBound;
    }

    public double[] conditionGeneUpperbound() {
        return conditionGenelowerBound;
    }

    public void conditionGeneUpperbound(double[] conditionGeneUpperbound) {
        conditionGeneUpperbound = conditionGeneUpperbound;
    }
 public void conditionGenelowerBound(double[] conditionGenelowerBound) {
        conditionGenelowerBound = conditionGenelowerBound;
    }
    public double getOutput() {
        return output;
    }

    public static void setOutput(double output) {
        output = output;
    }

}
