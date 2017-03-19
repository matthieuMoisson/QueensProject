package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Matthieu on 18/03/2017.
 */
public class Recuit extends AlgoRecherche{

    private final int NB_MAX_N1;
    private final int NB_MAX_N2;
    private final float TEMPERATURE_INITIALE;


    // Constructeur générique à réalisé dans la classe mere
    public Recuit(Processor processor, int nbMaxN1, int nbMaxN2, float temperatureInitiale) {
        super(processor);
        this.NB_MAX_N1 = nbMaxN1;
        this.NB_MAX_N2 = nbMaxN2;
        this.TEMPERATURE_INITIALE = temperatureInitiale;
    }

    public void algoRecuit(){
        Random randomGenerator = new Random();
        int nbIteration = 0;
        // x0 c'est le processeur à l'état initial
        int[] xMin = this.processor.getQueens();
        float temperature = TEMPERATURE_INITIALE;
        int fMin = this.processor.calculateNbConflicts();
        int fCourant = fMin, fSuivant = fMin, indice;
        System.out.println("Nb de conflit de la solution initial : " + fMin);
        float p;
        for(int k = 0; k<NB_MAX_N1; k++){
            for(int l = 1; l < NB_MAX_N2; l++){
                indice = onePermutationAlea();
                processor.permute(this.listeVoisins.get(indice)[0], this.listeVoisins.get(indice)[1]);
                fSuivant = processor.calculateNbConflicts();
                if(fCourant - fSuivant <= 0){
                    if(fSuivant < fMin){
                        fMin = fSuivant;
                        xMin = this.processor.getQueens();
                    }
                }else {
                    p = (float) Math.random();
                    if (p <= Math.exp((fSuivant - fCourant) / temperature)) {
                        // On a deja permuté donc on ne fait rien
                    } else {
                        // On avait permuté donc on revient en arriere
                        processor.permute(this.listeVoisins.get(indice)[0], this.listeVoisins.get(indice)[1]);
                    }
                }
                nbIteration++;
            }
            // A modifier
            temperature = temperature*9/10;
        }
        System.out.println("Nb de conflit de la solution final : " + this.processor.calculateNbConflicts());
        System.out.println("Nombre d'itération : " + nbIteration);
    }

    // Retourne l'indice d'une permutation aléatoire dans la liste de voisin
    private  int onePermutationAlea() {
        Random randomGenerator = new Random();
        int i = randomGenerator.nextInt(processor.getNbQueens()); // Entre 1 et nbQueens
        return i;
    }

}
