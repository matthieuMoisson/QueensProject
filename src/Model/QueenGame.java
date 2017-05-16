package Model;

/**
 * N-Queen project main class
 */
public class QueenGame {


    // ---------------------- Parameters
    private static final int NB_TABOU = 10;
    private static final int NB_QUEEN = 10;
    private static final Algorithms ALGO = Algorithms.GENETIC;
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
     * @param processor
     * @param algo
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
                algoRecherche = new Genetic(processor, 100, 10);
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
        System.out.println("La durée de l'algo " + algo + " est de : " + min + " min, " + sec + " s, " + ms + " ms");
    }

    public static void main(String[] args) {
        QueenGame qg = new QueenGame();
        //qg.launchAlgo(new Tabou(new Processor(NB_QUEEN), 200, NB_TABOU));
    }
}
