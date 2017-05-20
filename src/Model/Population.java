package Model;

import java.util.ArrayList;

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

    Population(Processor processor1, Processor processor2, int indice1, int indice2) {
        this.processor = (Processor) processor1.clone();
        this.processor.setQueens(new int[this.processor.getNbQueens()]);
        if(indice1>indice2){
            int tmp = indice1;
            indice1 = indice2;
            indice2 = tmp;
        }
        for (int i = 0; i < this.processor.getNbQueens(); i++) {
            if(i >= indice1 && i < indice2)
                this.processor.setQueenPosition(i, processor2.getQueens()[i]);
            this.processor.setQueenPosition(i, processor1.getQueens()[i]);
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
