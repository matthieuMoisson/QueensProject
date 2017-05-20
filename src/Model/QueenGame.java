package Model;

import logger.Logger;

import java.util.Arrays;

/**
 * N-Queen project main class
 */
public class QueenGame {


    // ---------------------- Parameters
    private static final int NB_TABOU = 10;
    private static final int NB_QUEEN = 500;
    private static final Algorithms ALGO = Algorithms.TABOO;
    // ---------------------- End Parameters


    private final Processor processor;
    public Processor getProcessor() {return this.processor;}

    // ---------------------- Constructors
    public QueenGame(int nbQueen, Algorithms algo) {
        processor = new Processor(nbQueen, true);
        launchAlgo(processor, algo);
    }

    public QueenGame() {
        processor = new Processor(NB_QUEEN);
        launchAlgo(processor, ALGO);
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
                algoRecherche = new Genetic(processor, 10, 0);
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
//        QueenGame qg = new QueenGame();
        //qg.launchAlgo(new Tabou(new Processor(NB_QUEEN), 200, NB_TABOU));


        testAuto();
    }


    private static void testAuto() {

        Logger.logLevelToDisplay = 3;

        tabouAuto();
        recuitAuto();
        geneticAuto();
    }

    private static void tabouAuto() {

        AlgoRecherche tabou;
        Processor processor;

        int nbMaxIteration = 10000;
        int[] nbQueenArray = {10, 20 , 50, 100, 200, 500, 1000};
        int[] tabouSizeArray = {0, 1, 10 , 15, 20, 30, 50};

        long nbMillis = 0;
        long[] rowValue = new long[tabouSizeArray.length];

        Logger.log(("------------------------------") ,3);
        Logger.log(("TABOU"),3);
        Logger.log(("Nb Reines : " + Arrays.toString(nbQueenArray)),3);
        Logger.log(("Nb Tabou : " + Arrays.toString(tabouSizeArray)),3);
        for (int i = 0; i < nbQueenArray.length; i++) {
            processor = new Processor(nbQueenArray[i]);
            for (int j = 0; j < tabouSizeArray.length; j++) {
                tabou = new Tabou(processor, nbMaxIteration, tabouSizeArray[j]);
                nbMillis = tabou.run();
                rowValue[j] = nbMillis;
            }
            Logger.log(Arrays.toString(rowValue),3);
        }
        Logger.log(("------------------------------"), 3);


    }

    private static void recuitAuto() {

    }

    private static void geneticAuto() {

    }
}
