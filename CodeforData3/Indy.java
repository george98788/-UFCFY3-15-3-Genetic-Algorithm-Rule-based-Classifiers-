package simplegaclass2;
import static simplegaclass2.SimpleGAdata3.lengthOfDataInstance;
import static simplegaclass2.SimpleGAdata3.NumR;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;


/**
 *
 * @author g2-ellicott
 */
public class Indy {

    static int numberOfGenes = 150;
    static int sizeOfPop = 2000;
  
    Random rand = new Random();

    private double[] gene = new double[numberOfGenes];
    int fitness = 0;
    int output = 0;

    public int getN() {
        return numberOfGenes;
    }

    public void setN(int n) {
        this.numberOfGenes = n;
    }

    public int getP() {
        return sizeOfPop;
    }

    public void setP(int p) {
        this.sizeOfPop = p;
    }

    public Random getRand() {
        return rand;
    }

    public double getGene(int index) {
        return gene[index];
    }

    public double[] getGenes() {
        return this.gene;
    }

    public void setGene(int index, double gene) {
        this.gene[index] = gene;
    }

    public void setGenes(double[] gene) {
        this.gene = gene;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public Indy() {
        for (int i = 1; i < numberOfGenes; i++) {
            double randomNumber = rand.nextDouble();
            double randomNumber2 = rand.nextInt(2);
            ///put outpput back on 1/0
            if (i == 1) {
                gene[i - 1] = randomNumber;
            } else {
                if (i % 15 != 0 && i - 1 != gene.length - 1) {

                    gene[i - 1] = randomNumber;
                } else {
                    gene[i - 1] = randomNumber2;
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
    }

    public void fitnessfunction(Indy individual, DataClass2[] dataArray) {
        int k = 0;
        Rule2[] rulebase = new Rule2[NumR];
        
        fitness = 0;
        for (int i = 0; i < rulebase.length; i++) {
            rulebase[i] = new Rule2();
        }
        for (int i = 0; i < NumR; i++) {
            for (int j = 0; j < lengthOfDataInstance-1; j++) {
                rulebase[i].conditionGenelowerBound[j] = individual.getGene(k++);
                rulebase[i].conditionGeneUpperbound[j] = individual.getGene(k++);
            }
            rulebase[i].output = individual.getGene(k++);
        }
        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < NumR; j++) {
                if (matches_cond(dataArray[i].dataSet, rulebase[j].conditionGenelowerBound, rulebase[j].conditionGeneUpperbound) == 1) {
                    if (matches_output(dataArray[i].output, rulebase[j].output) == 1) {
                       
                        fitness++;
                    }
                    break; // note it is important to get the next data item after a match
                }
            }
        }
    }

    public int matches_cond(double[] dataSet, double[] conditionGenelowerBound, double[] conditionGeneUpperbound) {
        int amountCorrect = 0;
        for (int i = 0; i < dataSet.length; i++) {
            if (conditionGenelowerBound[i] >= dataSet[i] && dataSet[i] <= conditionGeneUpperbound[i]) {
                amountCorrect++;
            }
        }
        if (amountCorrect == dataSet.length) {
            return 1;
        } else {
            return 0;
        }

    }

    public int matches_output(double dataSet, double output) {
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
            stringOutput += gene[i] + " ";
        }
        stringOutput += " " + fitness;
        return stringOutput;
    }
}
