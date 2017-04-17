package Model;

/**
 * N-Queen project main class
 */
public class QueenGame {


    // ---------------------- Parameters
    private static final int NB_TABOU = 10;
    private static final int NB_QUEEN = 1000;
    private static final Algorithms ALGO = Algorithms.RECUIT;
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
//        launchAlgo(processor, ALGO);
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
                algoRecherche = new Recuit(processor, 1000, 100, 0.01, 100000);
                break;
            case GENETIC:
                algoRecherche = new Genetic(processor, 100, 10);
                break;
            default:
                algoRecherche = new Recuit(processor,1000,1000, 0.9999, 0.00001);
                break;
        }

        long elapsedTime = algoRecherche.run();
        System.out.println(algo + " took " + elapsedTime + " ms");
    }

    private void launchAlgo(AlgoRecherche algoRecherche) {
        long elapsedTime = algoRecherche.run();
        System.out.println(algoRecherche + " took " + elapsedTime + " ms");
    }

    public static void main(String[] args) {
        QueenGame qg = new QueenGame();
        qg.launchAlgo(new Tabou(new Processor(100), 200, NB_TABOU));
    }
}
