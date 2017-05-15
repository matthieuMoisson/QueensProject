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
