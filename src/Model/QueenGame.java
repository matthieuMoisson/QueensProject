package Model;

/**
 * Created by p1509413 on 15/03/2017.
 */
public class QueenGame {

    private final Processor processor;

    public Processor getProcessor() {return this.processor;}

    public QueenGame(int nbQueen) {

        processor = new Processor(nbQueen);
        processor.fillRandom();

        int nbIt = 0;

        while (processor.calculateNbConflicts() > 0) {
            processor.fillRandom();
            nbIt ++;
        }
        System.out.println(processor);
        System.out.println(nbIt);
        int nbConflicts = processor.calculateNbConflicts();
        System.out.println(nbConflicts);

    }
}
