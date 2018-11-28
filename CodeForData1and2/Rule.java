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
public class Rule {
    int conditionLength = lengthOfDataInstance-1;
    int conditionGene[] = new int[conditionLength];
    int output = 0;

    public  int getConditionLength() {
        return conditionLength;
    }

    public  void setConditionLength(int conditionLength) {
        conditionLength = conditionLength;
    }

    public  int[] getConditionGene() {
        return conditionGene;
    }

    public  void setConditionGene(int[] conditionGene) {
        conditionGene = conditionGene;
    }

    public int getOutput() {
        return output;
    }

    public static void setOutput(int output) {
        output = output;
    }

    
}
