package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p1509413 on 15/03/2017.
 */

public class Tabou{

    private int nbMaxIteration;

    private int nbTabou;

    private final Processor processor;

    int [][] neighbors;

    private List<int[]> listeVoisins;

    private List<int[]> listeTabou;


    public Tabou(Processor processor, int nbMaxIteration, int nbTabou) {
        this.processor = processor;
        this.nbMaxIteration = nbMaxIteration;
        this.nbTabou = nbTabou;
        this.initListeTabou();
        this.initListeVoisins();
    }

    // Fonction tabou qui prend le processor (donc le tableau de dame actuel et calcule tout ce que tu veut
    public void algoTabou(){
        int[] xMin = this.processor.getQueens();
        this.initListeTabou();
        int fMin = this.processor.calculateNbConflicts();
        int fCourant = fMin, fSuivant = fMin, i=0, indice = this.getBestNeighbors();
        do{
            if(listeVoisins.size()>=0){
                indice = this.getBestNeighbors();
                processor.permute(this.listeVoisins.get(indice)[0], this.listeVoisins.get(indice)[1]);
                fSuivant = processor.calculateNbConflicts();
                if(fCourant - fSuivant >= 0){
                    // Ajouter à la liste tabou (regler les parametre de la liste tabou
                    this.listeTabou.add(this.listeVoisins.get(indice));
                    this.listeVoisins.remove(indice);
                    if(this.listeTabou.size() > nbTabou){
                        this.listeVoisins.add(this.listeTabou.get(0));
                        this.listeTabou.remove(0);
                    }
                }else if(fSuivant < fMin){
                    fMin = fSuivant;
                    xMin = this.processor.getQueens();
                }
            }
            i++;
        }while(i <= nbMaxIteration && listeVoisins.size() > 0 && fSuivant != 0);
        System.out.println("Nb de conflit de la solution final : " + this.processor.calculateNbConflicts());
        System.out.println("i : " + i);
        System.out.println("Taille tabou : " + this.listeTabou.size());
    }


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

    private void initListeTabou(){
        this.listeTabou = new ArrayList<>();
    }

}
