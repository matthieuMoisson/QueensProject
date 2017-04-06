package Model;

/**
 * Created by Matthieu on 06/04/2017.
 * J'ai modifié mon entête
 */
public class Population {
    private int nbConflit;
    private Processor processor;
    private int nbConflictCumule = 0;


    public Population() {
        this.processor = new Processor(this.processor.getNbQueens(), true);
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

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }
}
