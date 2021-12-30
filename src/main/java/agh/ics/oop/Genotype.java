package agh.ics.oop;


import java.util.Arrays;
import java.util.Random;

public class Genotype {
    private final int[] genes;
    private final Random random = new Random();

    public Genotype(){
        int[] genotype = new int[32];
        for (int i=0; i<32;i++){
            int randNumber = random.nextInt(8);
            genotype[i] = randNumber;
        }
        Arrays.sort(genotype);
        genes = genotype;
    }


    public Genotype(Genotype motherGenotype, Genotype fatherGenotype, int genotypeBorder){
        int[] fatherGenes = fatherGenotype.getGenes();
        int[] motherGenes = motherGenotype.getGenes();
        int[] childGenotype = new int[32];
        int motherSide = random.nextInt(2);
        if (motherSide == 0){
            System.arraycopy(motherGenes, 0, childGenotype, 0, genotypeBorder);
            System.arraycopy(fatherGenes, genotypeBorder, childGenotype, genotypeBorder, 32 - genotypeBorder);
        }
        else{
            System.arraycopy(fatherGenes, 0, childGenotype, 0, genotypeBorder);
            System.arraycopy(motherGenes, genotypeBorder, childGenotype, genotypeBorder, 32 - genotypeBorder);
        }
        Arrays.sort(childGenotype);
        genes = childGenotype;
    }

    public int[] getGenes() {
        return genes;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int gene:genes){
            stringBuilder.append(gene);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return Arrays.equals(genes, genotype.genes);
    }

    @Override
    public int hashCode() {
        return 31 + Arrays.hashCode(genes);
    }
}
