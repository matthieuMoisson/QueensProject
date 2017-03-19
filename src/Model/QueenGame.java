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
        Tabou tabou = new Tabou(processor, 100, nbTabou);
        tabou.algoTabou();
    }
}
