package Model;

import sun.tracing.ProbeSkeleton;

import java.util.Random;

/**
 * Created by Matthieu on 18/03/2017.
 * Rcuit simulé
 */
class Recuit extends AlgoRecherche{

    private final int NB_MAX_N1;
    private final int NB_MAX_N2;
    private final int NB_VOISINS_INIT_TEMPERATURE;
    private final double PROBABILITE_INITIALE;
    private final double PROBABILITE_FINALE;
    private double MU;
    private double TEMPERATURE_INITIAL;


    // Constructeur générique à réalisé dans la classe mere
    public Recuit(Processor processor, int nbMaxN1, int nbMaxN2, double probabiliteInitiale, double probabiliteFinale) {
        super(processor);
        this.NB_MAX_N1 = nbMaxN1;
        this.NB_MAX_N2 = nbMaxN2;
        this.PROBABILITE_INITIALE = probabiliteInitiale;
        this.NB_VOISINS_INIT_TEMPERATURE = 10;
        this.PROBABILITE_FINALE = probabiliteFinale;

        this.MU = Math.exp(Math.log(Math.log(PROBABILITE_INITIALE) / Math.log(PROBABILITE_FINALE)) / NB_MAX_N1);
        this.TEMPERATURE_INITIAL = calculerT0();
        System.out.println("MU = " + MU);
    }

    public Recuit(Processor processor, int nbMaxN1, int nbMaxN2, double mu, int temperature){
        this(processor, nbMaxN1, nbMaxN2, 0.0, 0.0);
        this.TEMPERATURE_INITIAL = temperature;
        this.MU = mu;
    }

    void algoRecuit(){
        Random randomGenerator = new Random();
        int nbIteration = 0;
        // x0 c'est le processeur à l'état initial
        int[] xMin = this.processor.getQueens();
        double temperature = this.TEMPERATURE_INITIAL;
        System.out.println("T0 = " + temperature);
        int fMin = this.processor.calculateNbConflicts();
        int fCourant = fMin, fSuivant = fMin, indice;
        System.out.println("Nb de conflit de la solution initial : " + fMin);
        float p;
        for(int k = 0; k<NB_MAX_N1; k++){
            for(int l = 1; l < NB_MAX_N2; l++){
                indice = onePermutationAlea();
                processor.permute(this.listeVoisins.get(indice)[0], this.listeVoisins.get(indice)[1]);
                fSuivant = processor.calculateNbConflicts();
                int deltaF = fSuivant - fCourant;
                int sauvegarde = fCourant;
                fCourant = fSuivant;
                if(deltaF <= 0){
                    if(fSuivant < fMin){
                        fMin = fSuivant;
                        //xMin = this.processor.getQueens();

                    }

                }else {
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
                nbIteration++;
            }
            // A modifier
            temperature *= MU;
        }
        // System.out.println("Nb de conflit de la solution final : " + this.processor.calculateNbConflicts());
        System.out.println("Nb de conflit de la meilleure solution : " + fMin);
        System.out.println("Nombre d'itération : " + nbIteration);
    }

    // Retourne l'indice d'une permutation aléatoire dans la liste de voisin
    private  int onePermutationAlea() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(processor.getNbQueens()); // Entre 1 et nbQueens
    }

    /**
     *
     * @return temperature
     */
    private double calculerT0() {
        return -calculateDeltaFMax(NB_VOISINS_INIT_TEMPERATURE) / Math.log(PROBABILITE_INITIALE);
    }

    /**
     * Calcule FMax pour permettre de calculer la température initiale
     * @param nbItérations
     * @return
     */
    private int calculateDeltaFMax(int nbItérations) {
        int deltaFMax, fCourant, fSuivant;
        int indice;
        int deltaF;
        deltaFMax = 0;
        fCourant = processor.calculateNbConflicts();
        for (int i=0; i < nbItérations; i ++) {
            indice = onePermutationAlea();
            processor.permute(listeVoisins.get(indice)[0], listeVoisins.get(indice)[1]);
            fSuivant = processor.calculateNbConflicts();
            deltaF = Math.abs(fSuivant - fCourant);
            if (deltaF > deltaFMax) {
                deltaFMax = deltaF;
            }
            processor.permute(listeVoisins.get(indice)[0], listeVoisins.get(indice)[1]);
        }
        System.out.println("FMAX = " + deltaFMax);
        return deltaFMax;
    }

}
