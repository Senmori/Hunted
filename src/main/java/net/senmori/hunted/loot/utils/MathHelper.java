package net.senmori.hunted.loot.utils;

import java.util.Random;

/**
 * Created by Senmori on 3/29/2016.
 */
public class MathHelper {


    public static float sqrtFloat(float value) { return (float)Math.sqrt((double)value); }

    public static float sqrtDouble(double value) { return (float)Math.sqrt(value);}

    public static int floorFloat(float value) {
        int i = (int)value;
        return value < (float)i ? i-1: i;
    }

    public static int floorDouble(double value) {
        int i = (int)value;
        return value < (double)i ? i -1 : i;
    }

    public static int getRandomIntegerInRange(Random rand, int min, int max) {
        return min >= max ? min : rand.nextInt(max - min + 1) + min;
    }

    public static float getRandomFloatInRange(Random rand, float min, float max) {
        return min >= max ? min : rand.nextFloat() * (max - min) + min;
    }

    public static double getRandomDoubleInRange(Random rand, double min, double max){
        return min >= max ? min : rand.nextDouble() * (max - min) + min;
    }
}
