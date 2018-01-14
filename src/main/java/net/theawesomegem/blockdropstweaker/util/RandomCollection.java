package net.theawesomegem.blockdropstweaker.util;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by TheAwesomeGem on 1/5/2018.
 */
public class RandomCollection<E>
{
    private final NavigableMap<Double, E> map = new TreeMap<>();
    private double total = 0;

    private Random random;

    public RandomCollection(Random random)
    {
        this.random = random;
    }

    public RandomCollection<E> add(double weight, E result)
    {
        if (weight <= 0)
            return this;

        total += weight;
        map.put(total, result);

        return this;
    }

    public E next()
    {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
