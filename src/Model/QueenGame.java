package Model;

/**
 * Created by p1509413 on 15/03/2017.
 */
public class QueenGame {

    private final Processor processor;

    public Processor getProcessor() {return this.processor;}

    public QueenGame(int nbQueen, int nbTabou) {
        processor = new Processor(nbQueen);
        processor.fillRandom();
        // Tabou tabou = new Tabou(processor, 200, nbTabou);
        // tabou.algoTabou();
        Recuit recuit = new Recuit(processor,100,1000, 0.99, 0.01);
        recuit.algoRecuit();
    }
}
