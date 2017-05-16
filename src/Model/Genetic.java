package Model;

import java.util.*;

/**
 * Created by Matthieu on 06/04/2017.
 * J'ai modifié mon entête
 */
public class Genetic extends AlgoRecherche {

    private final int NB_POPULATION_INITALE;
    private final int NB_ELITISTE;

    List<Population> populations;

    List<Population> populationsSelected;
    private int nbTotalConflict;

    public Genetic(Processor processor, int nbPopulationInitial, int nbElististe) {
        super(processor);
        this.NB_POPULATION_INITALE = nbPopulationInitial;
        this.initializePopulation();
        this.NB_ELITISTE = nbElististe;
        this.populationsSelected = new ArrayList<>(this.NB_POPULATION_INITALE);
    }

    @Override
    protected void launch(){
        System.out.println("je passe par la");
        boolean ended = false;
        this.initializePopulation();
        /*
        while(this.evaluatePopulation()){
            this.setNewPopulation();
            //System.out.printf("je passe par la");
        }
        */
    }

    // Optimisatio utilisé treemap pour éviter d'avoir a réaliser le trie après
    private void initializePopulation() {
        this.populations = new ArrayList<>(this.NB_POPULATION_INITALE);
        // Initliaser la populations initial
        for(int i = 0; i < this.NB_POPULATION_INITALE; i++){
            this.populations.add(new Population(processor.getNbQueens()));
        }
    }

    private boolean evaluatePopulation(){
        boolean oneIsBest = false;
        this.nbTotalConflict = 0;
        for(int i = 0; i < this.NB_POPULATION_INITALE; i++){
            int nbCOnflict = this.populations.get(i).calculateNbConflit();
            if(nbCOnflict == 0){
                oneIsBest = true;
            }
            this.nbTotalConflict += nbCOnflict;
        }
        this.sortPopulation();
        return oneIsBest;
    }

    private void setNewPopulation(){
        // Peut ramer, a reprendre si c'est un peut long, à ameliorer
        // Elitiste

        this.populationsSelected = new ArrayList<>(this.NB_POPULATION_INITALE);
        for(int i = 0; i < this.NB_ELITISTE; i++){
            this.populationsSelected.add(this.populations.get(i));
        }
        // this.populationsSelected = this.populations.subList(0, this.NB_ELITISTE);

        // Roulette
        Random randomGenerator = new Random();
        int x;
        for(int i = 0; i < this.NB_POPULATION_INITALE - this.NB_ELITISTE; i++){
            x = randomGenerator.nextInt(this.nbTotalConflict*(this.NB_POPULATION_INITALE-1));
            this.populationsSelected.add(this.getRouletteElement(x));
        }
    }

    private void setNewPopulationCroisement(){

    }

    private Population getRouletteElement(int x){
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
        return new Population(processor.getNbQueens());
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
