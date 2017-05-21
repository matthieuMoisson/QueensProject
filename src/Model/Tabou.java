package Model;

import logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p1509413 on 15/03/2017.
 */

public class Tabou extends AlgoRecherche{

    private int nbMaxIteration;

    private int nbTabou;

    private List<int[]> listeTabou;


    public Tabou(Processor processor, int nbMaxIteration, int nbTabou) {
        super(processor);
        this.nbMaxIteration = nbMaxIteration;
        this.nbTabou = nbTabou;
        this.initListeTabou();
    }

    /**
     * Fonction tabou qui prend le processor (donc le tableau de dame actuel et calcule tout ce que tu veut
     */
    @Override
    protected void launch() {
        int[] xMin = this.processor.getQueens();
        this.initListeTabou();
        int fMin = this.processor.calculateNbConflicts();
        int fCourant = fMin, fSuivant = fMin, i = 0, indice = this.getBestNeighbors();
        Logger.log("Nb de conflit de la solution initial : " + fMin, 3);
        do {
            if (listeVoisins.size() >= 0) {
                indice = this.getBestNeighbors();
                processor.permute(this.listeVoisins.get(indice)[0], this.listeVoisins.get(indice)[1]);
                fSuivant = processor.calculateNbConflicts();
                if (fCourant - fSuivant >= 0) {
                    // Ajouter à la liste tabou (regler les parametre de la liste tabou
                    this.listeTabou.add(this.listeVoisins.get(indice));
                    this.listeVoisins.remove(indice);
                    if (this.listeTabou.size() > nbTabou) {
                        this.listeVoisins.add(this.listeTabou.get(0));
                        this.listeTabou.remove(0);
                    }
                } else if (fSuivant < fMin) {
                    fMin = fSuivant;
                    xMin = this.processor.getQueens();
                }
            }
            i++;

        } while (i < nbMaxIteration && listeVoisins.size() > 0 && fSuivant != 0);
        Logger.log("Nb de conflit de la solution final : " + this.processor.calculateNbConflicts(), 3);
        Logger.log("Nombe d'itération : " + i, 3);
        Logger.log("Taille de la liste tabou : " + this.listeTabou.size(), 3);
    }

    /**
     * Renvoie l'indice du voisin (permuttation) qui a la meilleur fitness (le plus faible nb de conflits)
     * @return indice of permutation ID (listeVoisin)
     */
    public int getBestNeighbors(){
        int iMin = 0, min = -1;
        int nbConflict;
        // Parcourir tout les voisins et retourner celui avec le plus faible résultat
        for(int i = 0; i < listeVoisins.size(); i++){
            processor.permute(listeVoisins.get(i)[0], listeVoisins.get(i)[1]);
            nbConflict = processor.calculateNbConflicts();
            if(nbConflict < min || min == -1){
                iMin = i;
                min = nbConflict;
            }
            processor.permute(listeVoisins.get(i)[0], listeVoisins.get(i)[1]);
        }
        return iMin;
    }

    private void initListeTabou(){
        this.listeTabou = new ArrayList<>(nbTabou);
    }

    @Override
    public String toString() {
        String result = processor.toString() + "\n";
        result += "Taboo - [tailleListe: " + this.nbTabou +"] - [nbIterationsMax: " + this.nbMaxIteration + "]";
        return result;
    }

}
