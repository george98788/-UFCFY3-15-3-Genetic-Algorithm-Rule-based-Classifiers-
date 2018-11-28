/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplegaclass;

import java.util.Random;
import static simplegaclass.SimpleGA.numberOfGenes;
import static simplegaclass.SimpleGA.sizeOfPop;
import static simplegaclass.SimpleGA.lengthOfDataInstance;
import static simplegaclass.SimpleGA.NumR;
/**
 *
 * @author g2-ellicott
 */
public class Individual {

    Random rand = new Random();

    int fitness = 0;
    int output = 0;

    private int[] gene = new int[numberOfGenes];

    public int getN() {
        return numberOfGenes;
    }

   

    public int getP() {
        return sizeOfPop;
    }

   

    public Random getRand() {
        return rand;
    }

    public int getGene(int index) {
        return gene[index];
    }

    public int[] getGenes() {
        return this.gene;
    }

    public void setGene(int index, int gene) {
        this.gene[index] = gene;
    }

    public void setGenes(int[] gene) {
        this.gene = gene;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public Individual() {
        for (int i = 0; i < numberOfGenes; i++) {
//            int randomNumber = rand.nextInt(2);
//            gene[i] = randomNumber;
            if (i % (lengthOfDataInstance-1) != 0 && i != gene.length - 1) {
                int randomNumber = rand.nextInt(3);
                gene[i] = randomNumber;
            } else {
                int randomNumber = rand.nextInt(2);
                gene[i] = randomNumber;
            }
        }

    }

//    public void evaluation() {
//        fitness = 0;
//        //Below counts the 1's in the string
//        for (int j = 0; j < numberOfGenes; j++) {
//
//            if (gene[j] == 1) {
//                fitness++;
//            }
//        }
//    }
    public void fitnessfunction(Individual individual, Data[] dataArray) {
        int k = 0;
        Rule[] rulebase = new Rule[NumR];
        
        fitness = 0;
        for (int i = 0; i < rulebase.length; i++) {
            rulebase[i] = new Rule();
        }
        for (int i = 0; i < NumR; i++) {
            for (int j = 0; j < lengthOfDataInstance-1; j++) {
                rulebase[i].conditionGene[j] = individual.getGene(k++);
            }
            rulebase[i].output = individual.getGene(k++);
        }
        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < NumR; j++) {
                if (matches_cond(dataArray[i].dataSet, rulebase[j].conditionGene) == 1) {
                    if (matches_output(dataArray[i].output, rulebase[j].output) == 1) {
                        fitness++;
                    }
                    break; // note it is important to get the next data item after a match
                }
            }
        }
    }

    public int matches_cond(int[] dataSet, int[] conditionGene) {
        int amountCorrect = 0;
        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i] == conditionGene[i] || conditionGene[i] == 2) {
                amountCorrect++;
            }
        }
        if (amountCorrect == dataSet.length) {
            return 1;
        } else {
            return 0;
        }

    }

    public int matches_output(int dataSet, int output) {
        if (dataSet == output) {
            return 1;
        } else {
            return 0;
        }

    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public int decoderValueSquared(int[] genes) {
        int value;
        String genesToString = "";
        for (int i = 0; i < genes.length; i++) {
            genesToString += genes[i];
        }
        value = Integer.parseInt(genesToString, 2);
        return value * value;
    }

    //Below is  f(x,y) = 0.26.( x2 + y2 – 0.48.x.y
    public double decoderValueComplex(int[] genes) {
        int valueX;
        int valueY;
        double value;
        String genesToStringX = "";
        String genesToStringY = "";
        for (int i = 1; i < genes.length; i++) {
            if (i < genes.length / 2) {
                genesToStringX += genes[i];
            } else {
                genesToStringY += genes[i];
            }

        }
        valueX = Integer.parseInt(genesToStringX, 2);
        valueY = Integer.parseInt(genesToStringY, 2);
        value = (0.26 * (Math.pow(valueX, 2)) + (Math.pow(valueY, 2)) - 0.48 * valueX * valueY);
        return value;
    }

    @Override
    public String toString() {
        String stringOutput = "";
        for (int i = 0; i < gene.length; i++) {
            stringOutput += gene[i];
        }
        stringOutput += " " + fitness;
        return stringOutput;
    }
}
