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

    // Constructeur générique à réalisé dans la classe mere
    public Recuit(Processor processor, int nbMaxN1, int nbMaxN2) {
        super(processor);
        this.NB_MAX_N1 = nbMaxN1;
        this.NB_MAX_N2 = nbMaxN2;
    }



    // Retourne l'indice d'une permutation aléatoire dans la liste de voisin
    private  int onePermutationAlea() {
        Random randomGenerator = new Random();
        int i = randomGenerator.nextInt(processor.getNbQueens()); // Entre 1 et nbQueens
        return i;
    }

}
