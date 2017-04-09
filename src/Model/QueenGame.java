package Model;

/**
 * Created by p1509413 on 15/03/2017.
 */
public class QueenGame {

    private final Processor processor;

    private static final int NB_TABOU = 10;
    private static final int NB_QUEEN = 1000;
    private static final Algorithms ALGO = Algorithms.RECUIT;

    public Processor getProcessor() {return this.processor;}

    public QueenGame(int nbQueen, Algorithms algo) {
        processor = new Processor(nbQueen, true);
        launchAlgo(processor, algo);
    }

    public QueenGame() {
        processor = new Processor(NB_QUEEN);
        launchAlgo(processor, ALGO);
    }

    private void launchAlgo(Processor processor, Algorithms algo) {
        System.out.println("Launching " + algo);
        long startTime = System.currentTimeMillis();
        switch (algo) {
            case TABOO:
                Tabou tabou = new Tabou(processor, 200, NB_TABOU);
                tabou.algoTabou();
                break;
            case RECUIT:
                Recuit recuit = new Recuit(processor, 1000, 100, 0.01, 100000);
                recuit.algoRecuit();
                break;
            case GENETIC:
                Genetic genetic = new Genetic(processor, 100, 10);
                genetic.algoGenetic();
                break;
            default:
                Recuit recuitProba = new Recuit(processor,1000,1000, 0.9999, 0.00001);
                recuitProba.algoRecuit();
                break;
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime + " ms");
    }

    public static void main(String[] args) {
        new QueenGame();
    }
}
