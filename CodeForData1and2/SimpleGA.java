/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplegaclass;

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
public class SimpleGA {

    /**
     * @param args the command line arguments
     */
    //Parameters
    static boolean tournamentSelection = true; //turn false for selection wheel
    static int numberOfGenerations = 300;
    static int parent1;
    static int parent2;
    static int crossoverChance = 20;
    static double mutationRate = 97;
    static int sizeOfPop = 1000;
    static int numberOfGenes = 40;
    static int NumR = 5;
    static int dataSetOutput;
    static int lengthOfDataInstance = 8;

    public static void main(String[] args) throws FileNotFoundException {

        int[] dataSet = new int[lengthOfDataInstance];
        Individual[] pop = new Individual[sizeOfPop];
        Individual[] newpop = new Individual[sizeOfPop];
        Individual[] fittestPopIndiviual = new Individual[1];
        Data[] dataArray = new Data[64];

        int generationTotalFitness;

        for (int i = 0; i < sizeOfPop; i++) {
            Individual newnidy = new Individual();
            pop[i] = newnidy;
        }

        File file = new File("C:\\Users\\George\\Desktop\\bio\\SimpleGA-master data2\\SimpleGA-master\\SimpleGAClass\\src\\simplegaclass\\data2.txt");
        Scanner scan = new Scanner(file);
        int dataArrayIndex = 0;
        int index = 0;
        int index1 = 0;
        while (scan.hasNext()) {

            if (index < lengthOfDataInstance) {
                dataSet[index] = scan.nextInt();
            } else {
                index = -1;
                Data data = new Data(dataSet);
                dataArray[dataArrayIndex] = data;
                dataArrayIndex++;
                index1++;
                System.out.println(index1);
            }
            index++;
        }

        for (int generation = 0; generation < numberOfGenerations; generation++) {
            for (int i = 0; i < sizeOfPop; i++) {
                pop[i].fitnessfunction(pop[i], dataArray);
            }
            //Grabs Fittest individual and stores it
            Individual fittest = new Individual();
            int[] fittestGenes = new int[numberOfGenes];
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

            System.out.println("--------Pop Before selection, crossover and mutation---------");

            System.out.println("");

            //---------------------Selection Type------------------------------
            if (tournamentSelection) {
                newpop = tournamnetSelection(pop);
            } else {
                newpop = selectionWheel(pop);
            }
            //---------------------Crossover Below------------------------------     
            newpop = crossover(newpop);

            System.out.println("");
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
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\georg\\OneDrive\\Documents\\NetBeansProjects\\SimpleGAClass\\SimpleGAClass\\src\\simplegaclass\\dataSimpleGA.txt", true));
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
    static int totalFitness(Individual[] aPop) {
        int total = 0;
        for (Individual individual : aPop) {
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
    static Individual[] tournamnetSelection(Individual[] pop) {
        Individual[] newpop = new Individual[sizeOfPop];
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
    static Individual[] selectionWheel(Individual[] pop) {
        Individual[] newpop = new Individual[sizeOfPop];
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

    static Individual[] crossover(Individual[] newpop) {
        Random rand3 = new Random();
        Random rand5 = new Random();
        double randomCrossoverChance = rand3.nextInt(100);
        for (int j = 0; j < sizeOfPop; j += 2) {
            int crossoverpoint = rand5.nextInt(numberOfGenes);
            if (randomCrossoverChance > crossoverChance) {
                int[] childOne = new int[numberOfGenes];
                int[] childTwo = new int[numberOfGenes];

                for (int b = 0; b < crossoverpoint; b++) {
                    childOne[b] = newpop[j].getGene(b);
                    childTwo[b] = newpop[j + 1].getGene(b);
                }
                for (int c = crossoverpoint; c < numberOfGenes; c++) {
                    childOne[c] = newpop[j + 1].getGene(c);
                    childTwo[c] = newpop[j].getGene(c);
                }
                Individual newnidOne = new Individual();
                newnidOne.setGenes(childOne);
                Individual newnidTwo = new Individual();
                newnidTwo.setGenes(childTwo);
                newpop[j] = newnidOne;
                newpop[j + 1] = newnidTwo;
            }
        }
        return newpop;
    }

    static Individual[] mutation(Individual[] newpop) {
        Random rand2 = new Random();
        Random rand6 = new Random();

        for (int i = 0; i < sizeOfPop; i++) {
            for (int j = 0; j < numberOfGenes; j++) {
                double randomMutation = rand2.nextInt(100);
                int randomNumber = rand6.nextInt(2);
                if (randomMutation > mutationRate) {
                    if ((j + 1) % (lengthOfDataInstance - 1) == 0) {
                        if (newpop[i].getGene(j) == 0) {
                            newpop[i].setGene(j, 1);
                        } else if (newpop[i].getGene(j) == 1) {
                            if (randomNumber == 0) {
                                newpop[i].setGene(j, 0);
                            }
                        } else {
                            randomNumber = rand6.nextInt(2);
                            if (randomNumber == 0) {
                                newpop[i].setGene(j, 0);
                            } else {
                                newpop[i].setGene(j, 1);
                            }
                        }
                    } else {
                        if (newpop[i].getGene(j) == 0) {
                            if (randomNumber == 0) {
                                newpop[i].setGene(j, 1);
                            } else {
                                newpop[i].setGene(j, 2);
                            }
                        } else if (newpop[i].getGene(j) == 1) {
                            randomNumber = rand6.nextInt(2);
                            if (randomNumber == 0) {
                                newpop[i].setGene(j, 0);
                            } else {
                                newpop[i].setGene(j, 2);
                            }
                        } else {
                            randomNumber = rand6.nextInt(2);
                            if (randomNumber == 0) {
                                newpop[i].setGene(j, 0);
                            } else {
                                newpop[i].setGene(j, 1);
                            }
                        }
                    }
                }
            }
        }
        return newpop;
    }
}
