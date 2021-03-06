package Model;

import logger.Logger;

import java.util.*;

/**
 * Created by Matthieu on 06/04/2017.
 * J'ai modifié mon entête
 */
public class Genetic extends AlgoRecherche {

    private final int nbPopulationInitale;
    private final int NB_ELITISTE;
    private final int NB_CHANCE;

    private List<Population> population;

    private List<Population> newPopulation;
    private int nbTotalConflict;

    private static final int NB_MAX_ITERATIONS = 100000;

    Genetic(Processor processor, int nbPopulationInitial, int nbElististe, int nbChance) {
        super(processor);
        Logger.log("Nb de conflit de la solution initial : " + this.processor.calculateNbConflicts(), 3);
        this.nbPopulationInitale = nbPopulationInitial;
        this.initializePopulation();
        this.NB_ELITISTE = nbElististe;
        this.NB_CHANCE = nbChance;
    }

    @Override
    protected void launch(){

        evaluatePopulation(this.population, 0);
        this.population.sort(new PopulationComparator());
        int nbIteration = 0;
        while(this.population.get(0).getNbConflit() != 0 && nbIteration < NB_MAX_ITERATIONS){
            nbIteration++;
            this.setNewPopulation();
            this.setNewPopulationCroisement();
            this.population.clear();
            this.population = this.newPopulation;
            evaluatePopulation(this.population, 0);
            this.population.sort(new PopulationComparator());
            //if(nbIteration % 10000 == 0)
                //System.out.println(this.population.get(0).getNbConflit());
        }
        Logger.log("Nb de conflit de la solution final : " + this.population.get(0).getNbConflit(), 3);
        Logger.log("Nombe d'itération : " + nbIteration, 3);
    }


    // Optimisatio utilisé treemap pour éviter d'avoir a réaliser le trie après
    private void initializePopulation() {
        this.population = new ArrayList<>(this.nbPopulationInitale);
        // Initliaser la population initial
        for(int i = 0; i < this.nbPopulationInitale; i++){
            this.population.add(new Population(processor.getNbQueens()));
        }
    }

    /**
     * Evalue la population
     * @return true si une solution a été trouvée, faux sinon
     */
    private void evaluatePopulation(List<Population> population, int indice){
        this.nbTotalConflict = 0;
        for(int i = indice; i < population.size(); i++){
            int nbConflict = population.get(i).calculateNbConflit();
            if(nbConflict == 0){
                return;
            }
            this.nbTotalConflict += nbConflict;
        }
    }

    private List<Population> getElitiste(){
        ArrayList<Population> populationElitiste = new ArrayList<>(this.NB_ELITISTE);
        for(int i = 0; i < this.NB_ELITISTE; i++){
            populationElitiste.add(this.population.get(i));
        }
        return populationElitiste;
    }

    /**
     * Sélection
     * Roulette
     */
    private void setNewPopulation(){
        this.newPopulation = new ArrayList<>(this.nbPopulationInitale);
        this.newPopulation.addAll(getElitiste());


        // Roulette
        Random randomGenerator = new Random();
        int x;
        for(int i = 0; i < (this.nbPopulationInitale/2) - this.NB_ELITISTE; i++){
            x = randomGenerator.nextInt(this.nbTotalConflict*(this.nbPopulationInitale/2 -1));
            this.addElementsWithRoulette(x);
        }
    }

    private void addElementsWithRoulette(int x){
        // A modifier pour prendre en fonction du rang et non du nombre de conflit
        int conflicCumule = 0;
        // On parcours dans le sens croissant car plus de chance de prendre les plus grand en premier
        int i = 0;
        do{
            i++;
            conflicCumule += (this.nbTotalConflict - this.population.get(i-1).getNbConflit());
        }while (conflicCumule<x);
        Population p = (Population) this.population.get(i-1).clone();
        //mutate(p);
        this.newPopulation.add(p);
    }

    /**
     * Croisement
     */
    private void setNewPopulationCroisement(){
        int n = this.newPopulation.size();
        int m = n / 2;

        Random randomGenerator = new Random();

        for (int i = 0; i < m; i++) {
            int index1 = randomGenerator.nextInt(n);
            int index2 = randomGenerator.nextInt(n);
            int indice1 = randomGenerator.nextInt(this.processor.getNbQueens());
            int indice2 = randomGenerator.nextInt(this.processor.getNbQueens());
            merge(newPopulation.get(index1), newPopulation.get(index2), indice1, indice2);
        }
    }

    private void merge(Population population1, Population population2, int indice1, int indice2) {
        Population p1 = new Population(population1.getProcessor(), population2.getProcessor(), indice1, indice2);
        Population p2 = new Population(population2.getProcessor(), population1.getProcessor(), indice1, indice2);
        mutate(p1);
        mutate(p2);
        this.newPopulation.add(p1);
        this.newPopulation.add(p2);
    }

    /**
     * nbPermutation claque sur selectedPopulation
     */
    private void mutate(Population population) {
        Random randomGenerator = new Random();
        int indice = randomGenerator.nextInt(1000);
        if(indice>NB_CHANCE)
            population.getProcessor().permuteRandom();
        population.calculateNbConflit();
    }

    /*
    trie de la population par ordre de fitness croissante
     */
    public void sortPopulation(){
        this.population.sort(new PopulationComparator());
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
