package Model;

import logger.Logger;

import java.util.Arrays;

/**
 * N-Queen project main class
 */
public class QueenGame {


    // ---------------------- Parameters
    private static final int NB_TABOU = 10;
    private static final int NB_QUEEN = 100;
    private static final Algorithms ALGO = Algorithms.GENETIC;
    // ---------------------- End Parameters


    private final Processor processor;
    public Processor getProcessor() {return this.processor;}

    // ---------------------- Constructors
    public QueenGame(int nbQueen, Algorithms algo) {
        processor = new Processor(nbQueen, true);
        //launchAlgo(processor, algo);
    }

    public QueenGame() {
        processor = new Processor(NB_QUEEN);
        //launchAlgo(processor, ALGO);
    }
    // ---------------------- End Constructors

    /**
     * Launcher
     */
    private void launchAlgo(Processor processor, Algorithms algo) {
        AlgoRecherche algoRecherche;
        switch (algo) {
            case TABOO:
                algoRecherche = new Tabou(processor, 200, NB_TABOU);
                break;
            case RECUIT:
                // Pour 100 dame les parametre suivant sont pas mal nb1 1000, nb2 100 mu 0.01 ttemp 10000
                // Pour 500 reine nb1 10000, nb2 10, mu 0.01, temp 100000
                algoRecherche = new Recuit(processor, 10000, 100, 0.05);
                break;
            case GENETIC:
                algoRecherche = new Genetic(processor, 1000, 100, 800);
                break;
            default:
                algoRecherche = new Recuit(processor,1000,1000, 0.9999, 0.00001);
                break;
        }

        long elapsedTime = algoRecherche.run();
        long min, sec, ms;
        ms = elapsedTime%1000;
        sec = (elapsedTime/1000)%60;
        min = elapsedTime/60000;
        Logger.log("La durée de l'algo " + algo + " est de : " + min + " min, " + sec + " s, " + ms + " ms", 1);
    }

    public static void main(String[] args) {
        QueenGame qg = new QueenGame();
        //qg.launchAlgo(new Processor(NB_QUEEN), ALGO);


        testAuto();
    }


    private static void testAuto() {

        Logger.logLevelToDisplay = 3;

        //tabouAuto();
        //recuitAuto();
        geneticAuto();
    }

    private static void tabouAuto() {
        AlgoRecherche tabou;
        Processor processor;

        int nbMaxIteration = 1000;
        int[] nbQueenArray = {10, 20 , 50, 100, 200};
        int[] tabouSizeArray = {0, 5 , 10, 20, 100};

        long nbMillis = 0;
        long[] rowValue = new long[tabouSizeArray.length];

        Logger.log(("------------------------------------------------------------") ,3);
        Logger.log(("TABOU"),3);
        for (int i = 0; i < nbQueenArray.length; i++) {
            Logger.log(("------------------------------"),3);
            Logger.log(("Nb Reines : " + nbQueenArray[i]),3);
            for (int j = 0; j < tabouSizeArray.length; j++) {
                Logger.log(("--------------"),3);
                Logger.log(("Nb Tabou : " + tabouSizeArray[j]),3);
                processor = new Processor(nbQueenArray[i], true);
                tabou = new Tabou(processor, nbMaxIteration, tabouSizeArray[j]);
                nbMillis = tabou.run();
                rowValue[j] = nbMillis;
            }
            Logger.log(("------------------------------"),3);
            Logger.log("temps en milis : " + Arrays.toString(rowValue),3);
        }
        Logger.log(("------------------------------------------------------------"), 3);
    }

    private static void recuitAuto() {
        AlgoRecherche recuit;
        Processor processor;

        int[] nbQueenArray = {1000};
        int[] n1Array = {1000, 10000};
        int[] n2Array = {1, 10, 100};

        long nbMillis = 0;
        long[] rowValue = new long[(n1Array.length+1)* (n2Array.length+1)];

        Logger.log(("------------------------------------------------------------") ,3);
        Logger.log(("RECUI"),3);
        for (int i = 0; i < nbQueenArray.length; i++) {
            Logger.log(("------------------------------"),3);
            Logger.log(("Nb Reines : " + nbQueenArray[i]),3);
            for (int j = 0; j < n1Array.length; j++) {
                Logger.log(("--------------"),3);
                Logger.log(("Nb 1 : " + n1Array[j]),3);
                for (int k = 0; k < n1Array.length; k++) {
                    Logger.log(("--------------"), 3);
                    Logger.log(("Nb 2 : " + n2Array[k]), 3);
                    processor = new Processor(nbQueenArray[i], true);
                    recuit = new Recuit(processor, n1Array[j], n1Array[j], 0.005);
                    nbMillis = recuit.run();
                    rowValue[(j+1)*(k+1)] = nbMillis;
                }
            }
            Logger.log(("------------------------------"),3);
            Logger.log("temps en milis : " + Arrays.toString(rowValue),3);
        }
        Logger.log(("------------------------------------------------------------"), 3);
    }

    private static void geneticAuto() {
        AlgoRecherche genetic;
        Processor processor;

        int nbMaxIteration = 1000;
        int[] nbQueenArray = {10, 20 , 50, 100};
        int[] initialesArray = {50, 100, 500};
        int[] elitisteArray = {0, 10, 50};

        long nbMillis = 0;
        long[] rowValue = new long[(elitisteArray.length+1)*(1+initialesArray.length)];

        Logger.log(("------------------------------------------------------------") ,3);
        Logger.log(("GENETIC"),3);
        for (int i = 0; i < nbQueenArray.length; i++) {
            Logger.log(("------------------------------"),3);
            Logger.log(("Nb dames : " + nbQueenArray[i]),3);
            for (int j = 0; j < initialesArray.length; j++) {
                Logger.log(("--------------"),3);
                Logger.log(("Nb population initiales : " + initialesArray[j]),3);
                for (int k = 0; k < elitisteArray.length; k++) {
                    Logger.log(("--------------"), 3);
                    Logger.log(("Nb elitiste : " + elitisteArray[k]), 3);
                    processor = new Processor(nbQueenArray[i], true);
                    genetic = new Genetic(processor, initialesArray[j], elitisteArray[k], 800);
                    nbMillis = genetic.run();
                    rowValue[(j+1)*(k+1)] = nbMillis;
                }
            }
            Logger.log(("------------------------------"),3);
            Logger.log("temps en milis : " + Arrays.toString(rowValue),3);
        }
        Logger.log(("------------------------------------------------------------"), 3);
    }
}
