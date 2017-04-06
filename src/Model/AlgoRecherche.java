package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthieu on 19/03/2017.
 */
public class AlgoRecherche {

    public final Processor processor;
    public List<int[]> listeVoisins;

    public AlgoRecherche(Processor processor) {
        this.processor = processor;
        this.initListeVoisins();
    }

    private void initListeVoisins(){
        this.listeVoisins = new ArrayList<int[]>();
        int nbQueens = this.processor.getNbQueens();
        int nbPermutation = ((nbQueens-1)*nbQueens) /2;
        // Listes de tout les voisins, à gauche l'indice du voisins, à droite la valeur de la colonne
        // System.out.println("Initiale : " + toString());
        int[] voisin = new int[2];
        for(int firstCol = 0; firstCol < nbQueens-1; firstCol ++) {
            for (int secondCol = firstCol + 1; secondCol < nbQueens; secondCol++) {
                voisin = new int[2];
                voisin[0] = firstCol;
                voisin[1] = secondCol;
                this.listeVoisins.add(voisin);
            }
        }
    }


}
