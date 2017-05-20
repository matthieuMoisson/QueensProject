package Model;

import java.util.Random;

/**
 * Created by MOISSON Matthieu
 *
 * on 15/03/2017.
 */
public class Processor implements Cloneable{

    int[] getQueens() {
        return queens;
    }
    void setQueens(int[] q) {
        queens = q;
    }

    public int[] queens;

    int getNbQueens() {
        return nbQueens;
    }

    private int nbQueens;

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    Processor(int nbQueens) {
        this.nbQueens = nbQueens;
        this.queens = new int[this.nbQueens];
        init();
    }

    Processor(int nbQueens, boolean fillRandom) {
        this(nbQueens);
        if(fillRandom){
            this.fillRandom();
        }
    }

    void setQueenPosition(int index, int value) {
        queens[index] = value;
    }

    /**
     * Fill the array queens with random disposition
     */
    private void fillRandom() {
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

    void permute(int x, int y) {
        int tmp = queens[x];
        queens[x] = queens[y];
        queens[y] = tmp;
    }

    int calculateNbConflicts() {
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
     * @return
     */
    private boolean isConflict(int colIndex, int ecart) {

        int currentValue = queens[colIndex];
        int nextValue = queens[colIndex + ecart];

        if (currentValue + ecart == nextValue || currentValue - ecart == nextValue || currentValue == nextValue) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Nb Queens: " + this.nbQueens;
    }

    public String display() {
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < nbQueens; i ++) {
            stb.append(queens[i]);
            stb.append("-");
        }
        stb.deleteCharAt(stb.length()-1);
        return stb.toString();
    }

    void permuteRandom() {
        this.permute(RandomCustom.getInt(nbQueens), RandomCustom.getInt(nbQueens));
    }
}
