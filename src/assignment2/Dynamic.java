package assignment2;

import java.util.*;

public class Dynamic {

    /**
     * Returns the least cost that can be incurred by your company over the
     * k = hourlyVolume.length hours (i.e hour 0 to hour k-1) that you are
     * in charge of the pump. Given that a full service concluded the hour
     * before you were placed in charge of the system (i.e finished one hour
     * before hour 0), given parameters hourlyVolume, fullServiceCapacity,
     * regularServiceCapacity and minorServiceCapacity
     *
     * (See handout for details)
     *
     * This method must be implemented using an efficient bottom-up dynamic programming
     * solution to the problem (not memoised)
     *
     * @require the arrays hourlyVolume, fullServiceCapacity, regularServiceCapacity
     * and minorServiceCapacity are not null, and do not contain null values. Each
     * of the values in all arrays are non-negative (greater than or equal to 0).
     * fullServiceCapacity.length > 0, regularServiceCapacity.length > 0,
     * minorServiceCapacity.length > 0
     *
     * @ensure Returns the least cost that can be incurred by your company over the
     * k = hourlyVolume.length hours (i.e hour 0 to hour k-1) that you are
     * in charge of the pump. Given that a full service concluded the hour
     * before you were placed in charge of the system (i.e finished one hour
     * before hour 0), given parameters hourlyVolume, fullServiceCapacity,
     * regularServiceCapacity and minorServiceCapacity
     */
    public static int optimalLossDynamic(int[] hourlyVolume,
            int[] fullServiceCapacity, int [] regularServiceCapacity, int[] minorServiceCapacity) {
        int[][] services = new int[][]{fullServiceCapacity,
                regularServiceCapacity, minorServiceCapacity};
        int[][][] minCostPerHour = new int[hourlyVolume.length][hourlyVolume.length][3];

        if (hourlyVolume.length == 0) {
            return 0;
        }

        int lastHour = hourlyVolume.length - 1;
        for (int i = 0; i < minCostPerHour.length; i++) {
            for (int j = 0; j < services.length; j++) {
                int[] service = services[j];
                if (service.length <= i) {
                    minCostPerHour[lastHour][i][j] = hourlyVolume[lastHour];
                    continue;
                }

                minCostPerHour[lastHour][i][j] = Math.max(0, hourlyVolume[lastHour] - service[i]);
            }
        }

        printArray(minCostPerHour);

        // for each hour
        for (int i = minCostPerHour.length - 2; i >= 0; i--) {
            // for each hour of service
            for (int j = minCostPerHour.length - 1; j >= 0; j--) {
                // for each service
                for (int k = 0; k < services.length; k++) {
                    int[] currentService = services[k];

                    int noServiceMin = Integer.MAX_VALUE;
                    if (j + 1 < currentService.length && j + 1 < minCostPerHour.length) {
                        int serviceVolume = currentService[j];
                        noServiceMin = Integer.max(0, hourlyVolume[i] - serviceVolume);
                        noServiceMin += minCostPerHour[i + 1][j + 1][k];
                    }

                    // Calculate the min value if the full service was to be done now
                    int fullServiceMin = Integer.MAX_VALUE; // By default set it to the max value (so it won't be the min)
                    if (i + 4 < hourlyVolume.length) { // Check it could be valid to perform a full service in this hour
                        int loss = hourlyVolume[i] + hourlyVolume[i + 1]
                                + hourlyVolume[i + 2] + hourlyVolume[i + 3];

                        fullServiceMin = loss + minCostPerHour[i + 4][0][0];
                    }

                    // Calculate the min value if the regular service was to be done now
                    int regularServiceMin = Integer.MAX_VALUE;
                    if (i + 2 < hourlyVolume.length) {
                        int loss = hourlyVolume[i] + hourlyVolume[i + 1];
                        regularServiceMin = loss + minCostPerHour[i + 2][0][1];
                    }

                    // Calculate the min value if the minor service was to be done now
                    int minorServiceMin = Integer.MAX_VALUE;
                    if (i + 1 < hourlyVolume.length) {
                        int loss = hourlyVolume[i];
                        minorServiceMin = loss + minCostPerHour[i + 1][0][2];
                    }

                    minCostPerHour[i][j][k] = Arrays.stream(new int[]{
                            noServiceMin,
                            fullServiceMin,
                            regularServiceMin,
                            minorServiceMin
                    }).min().getAsInt();

                    printArray(minCostPerHour);
                }
            }
        }

        // return the result
        return minCostPerHour[0][0][0];
    }

    private static void printArray(int[][][] array) {
        for (int i = 0; i < array.length * 3; i++) {
            System.out.print(" - ");
        }
        System.out.println("");

        for (int i = 0; i < array.length; i++) { // service hour
            for (int j = 0; j < 3; j++) { // service
                for (int k = 0; k < array.length; k++) { // each hour
                    System.out.print(" " + array[k][i][j]);

                    if (k != array.length - 1) {
                        System.out.print(",");
                    }
                }

                System.out.print(" | ");
            }

            System.out.println("");
        }
    }


    /**
     * Returns a schedule of the services that should take place on each of the k
     * = hourlyVolume.length hours that you are in charge of the pump, that guarantees
     * that the least possible cost will be incurred by your company over these k
     * hours (given parameters hourlyVolume, fullServiceCapacity, regularServiceCapacity
     * and minorServiceCapacity)
     *
     * The schedule should be an array of services of length k, where for each array index
     * i, for 0 <= i < k, the value of the array at index i should be the service that is in
     * progress at that hour (Service.FULL_SERVICE, Service.REGULAR_SERVICE, Service.MINOR_SERVICE)
     * if there is a service or null if there is no service taking place at that time.
     *
     * For example, with a k value of 8, the return value
     * [null, null, REGULAR_SERVICE, REGULAR_SERVICE, null, null, MINOR_SERVICE, null]
     * represents a schedule where a regular service is conducted that takes place through the
     * third and fourth hours (hours 2 and 3) and a minor service is conducted in the seventh hour
     * (hour 6) and no services are conducted during the other hours.
     *
     * You should assume that a full service was completed the hour before you took control
     * of the pump (i.e 1 hour before hour 0)
     *
     * (See handout for details.)
     *
     * This method must be implemented using an efficient bottom-up dynamic programming solution
     * to the problem (not memoised)
     *
     * @require the arrays hourlyVolume, fullServiceCapacity, regularServiceCapacity
     * and minorServiceCapacity are not null, and do not contain null values. Each
     * of the values in all arrays are non-negative (greater than or equal to 0).
     * fullServiceCapacity.length > 0, regularServiceCapacity.length > 0,
     * minorServiceCapacity.length > 0
     *
     * @ensure Returns a schedule of the services that should take place on each of the k
     * = hourlyVolume.length hours that you are in charge of the pump, that guarantees
     * that the least possible cost will be incurred by your company over these k
     * hours (given parameters hourlyVolume, fullServiceCapacity, regularServiceCapacity
     * and minorServiceCapacity)
     */
    public static Service[] optimalServicesDynamic(int[] hourlyVolume,
            int[] fullServiceCapacity, int [] regularServiceCapacity, int[] minorServiceCapacity) {
        return null; // REMOVE THIS LINE AND WRITE THIS METHOD
    }

}
