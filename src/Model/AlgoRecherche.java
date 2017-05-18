package Model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        // Listes de tout les voisins, à gauche l'indice du voisins, à droite la valeur de la colonne
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

    /**
     * Run the algorithm and return the time consumed
     * @return long : the number of milliseconds elapsed
     */
    public long run() {
        long startTime = System.currentTimeMillis();

        launch();

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        return elapsedTime;
    }

    /**
     * Launch the algorithm
     */
    protected void launch(){throw new NotImplementedException();};


}
