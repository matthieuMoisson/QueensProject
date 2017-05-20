package Model;

/**
 * Created by Matthieu on 06/04/2017.
 * J'ai modifié mon entête
 */
public class Population implements Cloneable {
    private int nbConflit;
    private Processor processor;


    Population(int nbQueens) {
        this.processor = new Processor(nbQueens, true);
    }

    public Population(Processor processor) {
        this.processor = (Processor) processor.clone();
    }

    Population(Processor processor1, Processor processor2, int indice) {
        this.processor = (Processor) processor1.clone();
        for (int i = indice; i < processor2.getNbQueens(); i++) {
            this.processor.setQueenPosition(i, processor2.getQueens()[i]);
        }
        this.calculateNbConflit();
    }

    int getNbConflit() {
        return nbConflit;
    }

    int calculateNbConflit() {
        return this.nbConflit = this.processor.calculateNbConflicts();
    }

    Processor getProcessor() {
        return processor;
    }

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
