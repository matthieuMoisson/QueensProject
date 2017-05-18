package Model;

import logger.Logger;

import java.util.Random;

/**
 * Created by Matthieu on 18/03/2017.
 * Rcuit simulé
 */
class Recuit extends AlgoRecherche {

    private final int nbMaxN1;
    private final int nbMaxN2;
    private final int nbVoisinsInitTemperature;
    private final double probabiliteInitiale;
    private final double probabiliteFinale;
    private double MU;
    private double temperatureInitial;


    // Constructeur générique à réalisé dans la classe mere
    public Recuit(Processor processor, int nbMaxN1, int nbMaxN2, double probabiliteInitiale, double probabiliteFinale) {
        super(processor);
        this.nbMaxN1 = nbMaxN1;
        this.nbMaxN2 = nbMaxN2;
        this.probabiliteInitiale = probabiliteInitiale;
        this.nbVoisinsInitTemperature = 10;
        this.probabiliteFinale = probabiliteFinale;

        this.MU = Math.exp(Math.log(Math.log(this.probabiliteInitiale) / Math.log(this.probabiliteFinale)) / this.nbMaxN1);
        this.temperatureInitial = calculerT0();
    }

    public Recuit(Processor processor, int nbMaxN1, int nbMaxN2, double mu) {
        this(processor, nbMaxN1, nbMaxN2, 0.0, 0.0);
        //this.temperatureInitial = temperature;
        this.temperatureInitial = this.processor.calculateNbConflicts() * 2;
        this.MU = mu;
        Logger.log("MU = " + MU, 1);
    }

    @Override
    protected void launch() {
        boolean fini = false;
        Random randomGenerator = new Random();
        int nbIteration = 0;
        // x0 c'est le processeur à l'état initial
        int[] xMin = this.processor.getQueens();
        double temperature = this.temperatureInitial;
        Logger.log(("T0 = " + temperature), 1);
        int fMin = this.processor.calculateNbConflicts();
        int fCourant = fMin, fSuivant = fMin, indice;
        Logger.log("Nb de conflit de la solution initial : " + fMin, 1);
        float p;

        outerloop:
        for (int k = 0; k < nbMaxN1; k++) {
            for (int l = 1; l < nbMaxN2; l++) {

                nbIteration++;
                indice = onePermutationAlea();
                processor.permute(this.listeVoisins.get(indice)[0], this.listeVoisins.get(indice)[1]);
                fSuivant = processor.calculateNbConflicts();
                int deltaF = fSuivant - fCourant;
                int sauvegarde = fCourant;
                fCourant = fSuivant;
                if (deltaF <= 0) {
                    if (fSuivant < fMin) {
                        fMin = fSuivant;
                        //xMin = this.processor.getQueens();
                        if (fSuivant == 0) {
                            break outerloop;
                        }
                    }

                } else {
                    p = (float) Math.random();
                    //System.out.println("exp(-deltaF/temperature) = " + Math.exp(-deltaF / temperature) + "/ temperature = " + temperature);
                    if (p <= Math.exp(-deltaF / temperature)) {
                        // On a deja permuté donc on ne fait rien
                    } else {
                        // On avait permuté donc on revient en arriere
                        fCourant = sauvegarde;
                        processor.permute(this.listeVoisins.get(indice)[0], this.listeVoisins.get(indice)[1]);
                    }
                }

            }
            // A modifier
            temperature *= MU;
        }
        // System.out.println("Nb de conflit de la solution final : " + this.processor.calculateNbConflicts());
        Logger.log("Nb de conflit de la meilleure solution : " + fMin, 1);
        Logger.log("Nombre d'itération : " + nbIteration);
        Logger.log("Temperature aprés itération : " + temperature);
    }

    // Retourne l'indice d'une permutation aléatoire dans la liste de voisin
    private int onePermutationAlea() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(processor.getNbQueens()); // Entre 1 et nbQueens
    }

    /**
     * @return temperature
     */
    private double calculerT0() {
        return -calculateDeltaFMax(nbVoisinsInitTemperature) / Math.log(probabiliteInitiale);
    }

    /**
     * Calcule FMax pour permettre de calculer la température initiale
     *
     * @param nbItérations
     * @return
     */
    private int calculateDeltaFMax(int nbItérations) {
        int deltaFMax, fCourant, fSuivant;
        int indice;
        int deltaF;
        deltaFMax = 0;
        fCourant = processor.calculateNbConflicts();
        for (int i = 0; i < nbItérations; i++) {
            indice = onePermutationAlea();
            processor.permute(listeVoisins.get(indice)[0], listeVoisins.get(indice)[1]);
            fSuivant = processor.calculateNbConflicts();
            deltaF = Math.abs(fSuivant - fCourant);
            if (deltaF > deltaFMax) {
                deltaFMax = deltaF;
            }
            processor.permute(listeVoisins.get(indice)[0], listeVoisins.get(indice)[1]);
        }
        Logger.log("FMAX = " + deltaFMax, 1);
        return deltaFMax;
    }

    @Override
    public String toString() {
        String result = processor.toString() + "\n";
        result += "Recuit - [N1: " + this.nbMaxN1 + "] - [N2: " + this.nbMaxN2 + "]" + "[MU: " + MU + "] - " +
                "[TEMP_INIT: " + temperatureInitial + "]";
        return result;
    }

}
