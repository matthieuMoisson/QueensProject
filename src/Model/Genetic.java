package Model;

import com.sun.org.apache.bcel.internal.generic.POP;
import logger.Logger;

import java.util.*;

/**
 * Created by Matthieu on 06/04/2017.
 * J'ai modifié mon entête
 */
public class Genetic extends AlgoRecherche {

    public static final int RATIO_MUTATION = 2;
    private final int nbPopulationInitale;
    private final int NB_ELITISTE;

    List<Population> populations;

    List<Population> populationsSelected;
    private int nbTotalConflict;

    public Genetic(Processor processor, int nbPopulationInitial, int nbElististe) {
        super(processor);
        this.nbPopulationInitale = nbPopulationInitial;
        this.initializePopulation();
        this.NB_ELITISTE = nbElististe;
        this.populationsSelected = new ArrayList<>(this.nbPopulationInitale);
    }

    @Override
    protected void launch(){
        this.initializePopulation();
        while(!this.evaluatePopulation()){
            this.setNewPopulation();
            this.setNewPopulationCroisement();
            this.mutate(this.populationsSelected.size() / RATIO_MUTATION);
//            this.populations = this.populationsSelected;
            Logger.log(nbTotalConflict + "", 1);
        }
    }


    // Optimisatio utilisé treemap pour éviter d'avoir a réaliser le trie après
    private void initializePopulation() {
        this.populations = new ArrayList<>(this.nbPopulationInitale);
        // Initliaser la populations initial
        for(int i = 0; i < this.nbPopulationInitale; i++){
            this.populations.add(new Population(processor.getNbQueens()));
        }
    }

    /**
     * Evalue et trie la population
     * @return true si une solution a été trouvée, faux sinon
     */
    private boolean evaluatePopulation(){
        this.nbTotalConflict = 0;
        for(int i = 0; i < this.nbPopulationInitale; i++){
            int nbCOnflict = this.populations.get(i).calculateNbConflit();
            if(nbCOnflict == 0){
                return true;
            }
            this.nbTotalConflict += nbCOnflict;
        }
        this.sortPopulation();
        return false;
    }

    /**
     * Sélection
     * Roulette
     */
    private void setNewPopulation(){
        // Peut ramer, a reprendre si c'est un peut long, à ameliorer
        // Elitiste

        this.populationsSelected = new ArrayList<>(this.nbPopulationInitale);
        for(int i = 0; i < this.NB_ELITISTE; i++){
            this.populationsSelected.add(this.populations.get(i));
        }
        // this.populationsSelected = this.populations.subList(0, this.NB_ELITISTE);

        // Roulette
        Random randomGenerator = new Random();
        int x;
        for(int i = 0; i < this.nbPopulationInitale - this.NB_ELITISTE; i++){
            x = randomGenerator.nextInt(this.nbTotalConflict*(this.nbPopulationInitale -1));
            this.addElementsWithRoulette(x);
        }
    }

    /**
     * Croisement
     */
    private void setNewPopulationCroisement(){
        int n = this.populationsSelected.size();
        int m = n / RATIO_MUTATION;

        Random randomGenerator = new Random();

        for (int i = 0; i < m; i++) {
            int index1 = randomGenerator.nextInt(n);
            int index2 = randomGenerator.nextInt(n);
            int indicePermutation = randomGenerator.nextInt(this.processor.getNbQueens());
            List<Population> tempPopulation = merge(populationsSelected.get(index1), populationsSelected.get(index2), indicePermutation);
            this.populationsSelected.set(index1, tempPopulation.get(0));
            this.populationsSelected.set(index2, tempPopulation.get(1));
        }
    }

    private List<Population> merge(Population population1, Population population2, int indiceCoupe) {
        List<Population> result = new ArrayList<>(4);
        result.add(population1);
        result.add(population2);
        result.add(new Population(population1.getProcessor(), population2.getProcessor(), indiceCoupe));
        result.add(new Population(population2.getProcessor(), population1.getProcessor(), indiceCoupe));
        Collections.sort(result, new PopulationComparator());
        return result;
    }

    /**
     * nbPermutation claque sur selectedPopulation
     * @param nbPermutation
     */
    private void mutate(int nbPermutation) {
        int n = this.populationsSelected.size();

        for (int i = 0; i < nbPermutation; i++) {
            int indicePermutation = RandomCustom.getInt(n);
            this.populationsSelected.get(indicePermutation).getProcessor().permuteRandom();
            this.populationsSelected.get(indicePermutation).calculateNbConflit();
        }

    }


    private void addElementsWithRoulette(int x){
        int conflicCumule = 0;
        // On parcours dans le sens croissant car plus de chance de prendre les plus grand en premier
        int i = 0;
        while(conflicCumule<x){
            conflicCumule += (this.nbTotalConflict - this.populations.get(i).getNbConflit());
            if(conflicCumule>x){
                // On garde l'element i
                this.populationsSelected.add(this.populations.get(i));
            }
            i++;
        }
    }

    /*
    trie de la population par ordre de fitness croissante
     */
    public void sortPopulation(){
        Collections.sort(this.populations, new PopulationComparator());
    }

    private class PopulationComparator implements Comparator<Population>{
        @Override
        public int compare(Population p1, Population p2) {
            if(p1.getNbConflit() > p2.getNbConflit()){
                return 1;
            }else if(p1.getNbConflit() == p2.getNbConflit()){
                return 0;
            }else{
                return -1;
            }
        }
    }
}
