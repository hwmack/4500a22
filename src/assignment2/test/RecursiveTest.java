package assignment2.test;

import assignment2.Recursive;
import org.junit.Assert;
import org.junit.Test;

public class RecursiveTest {

    @Test
    public void exampleRecursiveTest() {
        int[] hourlyVolume =       {50,40,90,10,5,100,40,20,50};
        int[] fullServiceCapacity = {100,90,80,70,60,50,40,30,20,10};
        int[] regularServiceCapacity = {70,50,40,30,20,10};
        int[] minorServiceCapacity = {50,40,20,10};
        int expectedResult = 75;

        Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity), expectedResult);
    }

    @Test
    public void exampleRecursiveTest1() {
        int[] hourlyVolume =       {0};
        int[] fullServiceCapacity = {100,90,80,70,60,50,40,30,20,10};
        int[] regularServiceCapacity = {70,50,40,30,20,10};
        int[] minorServiceCapacity = {50,40,20,10};
        int expectedResult = 0;

        Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity), expectedResult);
    }

    @Test
    public void exampleRecursiveTest2() {
        int[] hourlyVolume =       {10, 20, 50, 70, 100, 11, 12, 30, 80, 90, 70, 10, 20, 1000};
        int[] fullServiceCapacity = {100,90,80,70,60,50,40,30,20,10};
        int[] regularServiceCapacity = {70,50,40,30,20,10};
        int[] minorServiceCapacity = {50,40,20,10};
        int expectedResult = 1103;

        Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity), expectedResult);
    }

    @Test
    public void exampleRecursiveTest3() {
        int[] hourlyVolume =       {10};
        int[] fullServiceCapacity = {10};
        int[] regularServiceCapacity = {7};
        int[] minorServiceCapacity = {5};
        int expectedResult = 0;

        Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity), expectedResult);
    }

    @Test
    public void exampleRecursiveTest4() {
        int[] hourlyVolume =       {10, 10, 10, 10, 100};
        int[] fullServiceCapacity = {10, 10, 10, 10};
        int[] regularServiceCapacity = {0};
        int[] minorServiceCapacity = {100};
        int expectedResult = 10;

        Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity), expectedResult);
    }

    @Test
    public void noValidPath() {
        int[] hourlyVolume =       {10, 10, 10, 10, 100};
        int[] fullServiceCapacity = {};
        int[] regularServiceCapacity = {};
        int[] minorServiceCapacity = {};
        int expectedResult = 140;

        Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity), expectedResult);
    }

    @Test
    public void emptyHourlyVolume() {
        int[] hourlyVolume =       {};
        int[] fullServiceCapacity = {};
        int[] regularServiceCapacity = {};
        int[] minorServiceCapacity = {};
        int expectedResult = 0;

        Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity), expectedResult);
    }

    @Test
    public void emptyArrays() {
        int[] hourlyVolume =       {1, 3, 5, 8, 11};
        int[] fullServiceCapacity = {};
        int[] regularServiceCapacity = {};
        int[] minorServiceCapacity = {10};
        int expectedResult = 13;

        Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity), expectedResult);
    }

    @Test
    public void allZeros() {
        int[] hourlyVolume =       {1, 3, 5, 8, 11};
        int[] fullServiceCapacity = {0, 0, 0, 0};
        int[] regularServiceCapacity = {0, 0, 0};
        int[] minorServiceCapacity = {0, 0, 0, 0};
        int expectedResult = 28;

        Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity), expectedResult);
    }

    @Test
    public void checkingOldThingThatShouldBeFixed() {
        int[] hourlyVolume =       {10, 10, 10, 10, 100};
        int[] fullServiceCapacity = {};
        int[] regularServiceCapacity = {};
        int[] minorServiceCapacity = {10};
        int expectedResult = 120;

        Assert.assertEquals(Recursive.optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity), expectedResult);
    }
}
