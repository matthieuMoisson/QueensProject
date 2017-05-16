package Model;

import java.util.Random;

/**
 * Created by Gaetan on 16/05/2017.
 *
 */
public class RandomCustom {

    static Random random;

    static {
        random = new Random();
    }

    public static int getInt(int bound) {
        return random.nextInt(bound);
    }

}
