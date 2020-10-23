package assignment2.test;

import assignment2.Dynamic;
import assignment2.Recursive;
import assignment2.Service;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class MyTests {

    @Test
    public void myDynamicTest2() {
        Random random = new Random();
        int counter = 0;
        while (counter++ < 10) {
            int[] hourlyVolume = new int[random.nextInt(20)+1];
            for (int i = 0; i < hourlyVolume.length; i++) {
                hourlyVolume[i] = random.nextInt(50);
            }

            int[] fullServiceCapacity = new int[random.nextInt(30)+1];
            for (int i = 0; i < fullServiceCapacity.length; i++) {
                fullServiceCapacity[i] = random.nextInt(50);
            }

            int[] regularServiceCapacity = new int[random.nextInt(30)+1];
            for (int i = 0; i < regularServiceCapacity.length; i++) {
                regularServiceCapacity[i] = random.nextInt(50);
            }

            int[] minorServiceCapacity = new int[random.nextInt(30)+1];
            for (int i = 0; i < minorServiceCapacity.length; i++) {
                minorServiceCapacity[i] = random.nextInt(50);
            }

            Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity),
                    Dynamic.optimalLossDynamic(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity));
        }

    }

    @Test
    public void myDynamicTest3() {
        int[] hourlyVolume =       {0,0,0,0,100,90,80,0,0,0,0,100,90,80,0,0,0,0,100,90,80,0,50,40};
        int[] fullServiceCapacity = {100,90,80,70,60,50,40,30,20,10};
        int[] regularServiceCapacity = {70,50,40,30,20,10};
        int[] minorServiceCapacity = {50,40,20,10};
        int expectedResult = 0;

        long startTime = System.nanoTime();
        Assert.assertEquals(expectedResult, Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity));
        long duration1 = (System.nanoTime() - startTime) / 1000000;
        Assert.assertEquals(expectedResult, Dynamic.optimalLossDynamic(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity));
        long duration2 = ((System.nanoTime() - startTime) / 1000000) - duration1;
        System.out.println(String.format("R=[%d]s D=[%d]s", duration1, duration2));
    }

    @Test
    public void myDynamicTest4() {
        int[] hourlyVolume =       {0, 0, 0, 0, 100,90,80};
        int[] fullServiceCapacity = {100,90,80,70,60,50,40,30,20,10};
        int[] regularServiceCapacity = {70,50,40,30,20,10};
        int[] minorServiceCapacity = {50,40,20,10};
        int expectedResult = 0;

        System.out.println(Arrays.toString(Dynamic.optimalServicesDynamic(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity)));
        Assert.assertEquals(expectedResult, Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity));
        Assert.assertEquals(expectedResult, Dynamic.optimalLossDynamic(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity));
    }

    @Test
    public void myDynamicTest5() {
        int[] hourlyVolume =       {};
        int[] fullServiceCapacity = {100,90,80,70,60,50,40,30,20,10};
        int[] regularServiceCapacity = {70,50,40,30,20,10};
        int[] minorServiceCapacity = {50,40,20,10};
        int expectedResult = 0;


        Assert.assertEquals(expectedResult, Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity));
        Assert.assertEquals(expectedResult, Dynamic.optimalLossDynamic(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity));
    }

}
