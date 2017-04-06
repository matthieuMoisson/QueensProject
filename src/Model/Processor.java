package Model;

import java.util.Random;

/**
 * Created by p1509413 on 15/03/2017.
 */
public class Processor {

    public int[] getQueens() {
        return queens;
    }

    public int[] queens;

    public int getNbQueens() {
        return nbQueens;
    }

    private int nbQueens;

    public Processor(int nbQueens) {
        this.nbQueens = nbQueens;
        this.queens = new int[this.nbQueens];
        init();
    }

    public Processor(int nbQueens, boolean fillRandom) {
        this(nbQueens);
        if(fillRandom){
            this.fillRandom();
        }
    }

    /**
     * Fill the array queens with random disposition
     */
    public void fillRandom() {
        Random randomGenerator = new Random();
        int x,y;
        for (int i = 0; i < nbQueens; i ++) {
            x = randomGenerator.nextInt(nbQueens); // Entre 1 et nbQueens
            y = randomGenerator.nextInt(nbQueens); // Entre 1 et nbQueens
            permute(x, y);
        }
    }

    private void init() {
        for (int i = 0; i < nbQueens; i ++) {
            queens[i] = i+1;
        }
    }

    public void permute(int x, int y) {
        int tmp = queens[x];
        queens[x] = queens[y];
        queens[y] = tmp;
    }

    public int calculateNbConflicts() {
        int nbConflict = 0;

        for (int colIndex = 0; colIndex < nbQueens; colIndex ++) {
            for (int ecart = 1; ecart + colIndex < nbQueens ; ecart ++) {
                // Si on sort du tableau, pas besoin de compter les conflits
                if (ecart + queens[colIndex] > nbQueens && queens[colIndex] - ecart < 1) {
                    break;
                }
                if (isConflict(colIndex, ecart)){
                    nbConflict ++;
                }
            }
            // System.out.println(nbConflict);
        }

        return nbConflict;
    }

    /**
     * Renvoie vrai si il y'a un conflit dans les diagonales entre la prochaine valeur parcourue et la valeur courrante
     *
     * @param colIndex
     * @param ecart
     * @return
     */
    private boolean isConflict(int colIndex, int ecart) {

        int currentValue = queens[colIndex];
        int nextValue = queens[colIndex + ecart];

        if (currentValue + ecart == nextValue || currentValue - ecart == nextValue) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < nbQueens; i ++) {
            stb.append(queens[i]);
            stb.append("-");
        }
        stb.deleteCharAt(stb.length()-1);
        return stb.toString();
    }

}
