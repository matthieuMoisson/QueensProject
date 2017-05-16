package Model;

/**
 * Created by Matthieu on 06/04/2017.
 * J'ai modifié mon entête
 */
public class Population {
    private int nbConflit;
    private Processor processor;


    public Population(int nbQueens) {
        this.processor = new Processor(nbQueens, true);
    }

    public Population(Processor processor) {
        this.processor = processor;
    }

    public Population(Processor processor1, Processor processor2, int indice) {
        this.processor = processor1;
        for (int i = indice; i < processor2.getNbQueens(); i++) {
            this.processor.setQueenPosition(i, processor2.getQueens()[i]);
        }
        this.calculateNbConflit();
    }

    public int getNbConflit() {
        return nbConflit;
    }

    public int calculateNbConflit() {
        return this.nbConflit = this.processor.calculateNbConflicts();
    }

    public Processor getProcessor() {
        return processor;
    }
}
