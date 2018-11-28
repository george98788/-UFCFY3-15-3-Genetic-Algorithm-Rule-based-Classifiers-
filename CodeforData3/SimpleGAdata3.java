package simplegaclass2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


/**
 *
 * @author g2-ellicott
 */
public class SimpleGAdata3 {

    /**
     * @param args the command line arguments
     */
    static int numberOfGenerations = 2000;
    static int parent1;
    static int parent2;
    static int crossoverChance = 17;
    static double mutationRate = 96;
    static int lengthOfDataInstance = 8;
    static int numberOfGenes = 150;
    static int sizeOfPop = 2500;
    static int NumR = 10;
    static int dataSetOutput;

    public static void main(String[] args) throws FileNotFoundException {

        double[] dataSet = new double[lengthOfDataInstance];
        Indy[] pop = new Indy[sizeOfPop];
        Indy[] newpop = new Indy[sizeOfPop];
        Indy[] fittestPopIndiviual = new Indy[1];
        DataClass2[] dataArray = new DataClass2[2000];
        int generationTotalFitness;
        for (int i = 0; i < sizeOfPop; i++) {
            Indy newnidy = new Indy();
            pop[i] = newnidy;
        }

        File file = new File("C:\\Users\\George\\Desktop\\bio\\SimpleGA-masterdata3\\SimpleGA-master\\simplegaclass2\\data3.txt");
        Scanner scan = new Scanner(file);
        int dataArrayIndex = 0;
        int index = 0;
        //stores the attributes into array of arrays
        while (scan.hasNext()) {
            if (index < lengthOfDataInstance) {
                dataSet[index] = scan.nextDouble();
            } else {
                index = -1;
                DataClass2 data = new DataClass2(dataSet);
                dataArray[dataArrayIndex] = data;
                dataArrayIndex++;

            }
            index++;
        }
        for (int generation = 0; generation < numberOfGenerations; generation++) {
            for (int i = 0; i < sizeOfPop; i++) {
                pop[i].fitnessfunction(pop[i], dataArray);
            }
            //Grabs Fittest individual and stores it
            Indy fittest = new Indy();
            double[] fittestGenes = new double[numberOfGenes];
            int maxFittness = pop[0].fitness;
            for (int i = 0; i < sizeOfPop; i++) {
                if (pop[i].fitness >= maxFittness) {
                    maxFittness = pop[i].fitness;
                    for (int j = 0; j < numberOfGenes; j++) {
                        fittestGenes[j] = pop[i].getGene(j);
                    }
                }
            }
            fittest.setGenes(fittestGenes);
            fittestPopIndiviual[0] = fittest;
            fittest.fitnessfunction(fittestPopIndiviual[0], dataArray);

            //Prints first population
            System.out.println("--------Pop Before selection, crossover and mutation---------");
//            for (int i = 0; i < sizeOfPop; i++) {
//                System.out.println(pop[i].toString());
//            }
            System.out.println("");

            //---------------------Selection Type------------------------------
            newpop = tournamnetSelection(pop);
            // newpop  = selectionWheel(pop);
            //---------------------Crossover Below------------------------------     
            newpop = crossover(newpop);
            //---------------------Mutation Below------------------------------     
            newpop = mutation(newpop);
            for (int i = 0; i < sizeOfPop; i++) {
                newpop[i].fitnessfunction(newpop[i], dataArray);
            }
            System.out.println("--------Pop after selection, crossover and mutation---------");
//            for (Individual individual : newpop) {
//                System.out.println(individual);
//            }

            //calculating lowest fittness 
            int lowestFitness = newpop[0].fitness;
            int lowestFittnessIndex = 0;
            for (int i = 0; i < sizeOfPop; i++) {
                if (newpop[i].fitness <= lowestFitness) {
                    lowestFitness = newpop[i].fitness;
                    lowestFittnessIndex = i;
                }
            }
            newpop[lowestFittnessIndex] = fittestPopIndiviual[0];
            generationTotalFitness = totalFitness(newpop);

            System.out.println("Generation size of: " + (generation + 1));
            System.out.println("Number of Genes: " + numberOfGenes);
            System.out.println("Size of Population: " + sizeOfPop);
            System.out.println("Number of rules:" + 5);
            System.out.println("Crossover chance: " + (100 - crossoverChance) + "%");
            System.out.println("mutation chance: " + ((100 - mutationRate)) + "%");
            System.out.println("Fitness Indivual: " + fittestPopIndiviual[0]);
            System.out.println("Total Fitness: " + generationTotalFitness);
            System.out.println("Average Fitness: " + average(generationTotalFitness, sizeOfPop));

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\George\\Desktop\\bio\\SimpleGA-masterdata3\\SimpleGA-master\\simplegaclass2\\data.txt", true));
                String output = generation + "," + generationTotalFitness + "," + average(generationTotalFitness, sizeOfPop) + "," + fittestPopIndiviual[0].fitness;
                writer.append(output);
                writer.newLine();
                writer.close();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            System.out.println("------------------------------END OF GENERATION --------------------------------");
            System.arraycopy(newpop, 0, pop, 0, sizeOfPop);
        }
    }

    //Calculates the total fitness for population
    static int totalFitness(Indy[] aPop) {
        int total = 0;
        for (Indy individual : aPop) {
            total += individual.fitness;
        }
        return total;
    }

    //Calculates the average fitness for population
    static int average(int total, int sizeOfPop) {
        int average = total / sizeOfPop;
        return average;
    }

    //Selects fittest of two parents at random from pop (Tournament Selection)
    static Indy[] tournamnetSelection(Indy[] pop) {
        Indy[] newpop = new Indy[sizeOfPop];
        for (int i = 0; i < sizeOfPop; i++) {
            Random rand = new Random();
            parent1 = rand.nextInt(sizeOfPop);
            parent2 = rand.nextInt(sizeOfPop);

            if (pop[parent1].fitness > pop[parent2].fitness) {
                newpop[i] = pop[parent1];
            } else {
                newpop[i] = pop[parent2];
            }
        }
        return newpop;
    }

    //Selection Wheel, uses the random num to select from generation 
    static Indy[] selectionWheel(Indy[] pop) {
        Indy[] newpop = new Indy[sizeOfPop];
        int totalFitnessForSelectionWheel = totalFitness(pop);
        Random rand4 = new Random();
        for (int i = 0; i < sizeOfPop; i++) {
            int randomSelectionWheel = rand4.nextInt(totalFitnessForSelectionWheel);
            int runningTotalPointer = 0;
            int k = 0;
            while (runningTotalPointer <= randomSelectionWheel) {
                runningTotalPointer += pop[k].fitness;
                k++;
            }
            newpop[i] = pop[k - 1];
        }
        return newpop;
    }
//performs recombination on two solutions, the cahnce of crossover is set by crossoverChance
    //the point of crossover is random 

    static Indy[] crossover(Indy[] newpop) {
        Random rand3 = new Random();
        Random rand5 = new Random();
        double randomCrossoverChance = rand3.nextInt(100);
        for (int j = 0; j < sizeOfPop; j += 2) {
            int crossoverpoint = rand5.nextInt(numberOfGenes);
            if (randomCrossoverChance > crossoverChance) {
                double[] childOne = new double[numberOfGenes];
                double[] childTwo = new double[numberOfGenes];

                for (int b = 0; b < crossoverpoint; b++) {
                    childOne[b] = newpop[j].getGene(b);
                    childTwo[b] = newpop[j + 1].getGene(b);
                }
                for (int c = crossoverpoint; c < numberOfGenes; c++) {
                    childOne[c] = newpop[j + 1].getGene(c);
                    childTwo[c] = newpop[j].getGene(c);
                }
                Indy newnidOne = new Indy();
                newnidOne.setGenes(childOne);
                Indy newnidTwo = new Indy();
                newnidTwo.setGenes(childTwo);
                newpop[j] = newnidOne;
                newpop[j + 1] = newnidTwo;
            }
        }
        return newpop;
    }
//performs mutation

    static Indy[] mutation(Indy[] newpop) {
        Random rand2 = new Random();
        Random rand6 = new Random();
        Random rand3 = new Random();

        for (int i = 0; i < sizeOfPop; i++) {
            for (int j = 0; j < numberOfGenes; j++) {
                double test = rand3.nextInt(110);
                test = test / 1000;
                // System.out.println(test);
                double randomMutation = rand2.nextInt(100);
                Random rand7 = new Random();
                double mutateOutput = rand7.nextInt(2);
                double mutatedgene = newpop[i].getGene(j);
                int randomNumber = rand6.nextInt(2);
                if (randomMutation > mutationRate) {
                    //below it only mutaions the 1 and every 15th gene as a 1 or 0
                    if ((j + 1) % 15 == 0) {
                        if (newpop[i].getGene(j) == 0) {
                            newpop[i].setGene(j, 1);
                        } else {
                            newpop[i].setGene(j, 0);
                        }
                    } else {
                        //below performs the range, makes sure the gene does not
                        //go below 0 or above 1
                        if (randomNumber == 0) {
                            if (newpop[i].getGene(j) + test > 1) {
                                mutatedgene = mutatedgene - test;
                            } else {
                                mutatedgene = mutatedgene + test;
                            }
                        } else {
                            if (newpop[i].getGene(j) - test < 0) {
                                mutatedgene = mutatedgene + test;
                            } else {
                                mutatedgene = mutatedgene - test;
                            }
                        }
                        newpop[i].setGene(j, mutatedgene);
                    }
                }
            }
        }
        return newpop;
    }
}
