package assignment2;

import java.util.Arrays;

public class Recursive {

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
     * This method must be implemented using a recursive programming solution to
     * this problem. It is expected to have a worst-case running time that is
     * exponential in k
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
    public static int optimalLossRecursive(int[] hourlyVolume,
            int[] fullServiceCapacity, int [] regularServiceCapacity, int[] minorServiceCapacity) {
        // IMPLEMENT THIS METHOD BY IMPLEMENTING THE PRIVATE METHOD IN THIS
        // CLASS THAT HAS THE SAME NAME
        return optimalLossRecursive(hourlyVolume, fullServiceCapacity, regularServiceCapacity, minorServiceCapacity,
                0, Service.FULL_SERVICE, 0);
    }

    /**
     * Given parameters hourlyVolume, fullServiceCapacity, regularServiceCapacity and
     * minorServiceCapacity, return the least cost that can be incurred from hour
     * "currentHour" to hour "k-1" (inclusive) of the hours you are in charge of the pump
     * (where k = hourlyVolume.length), given that the last maintenance activity before hour
     * "currentHour" is given by parameter "lastService", and that it occurred
     * "hoursSinceService" hours before hour "currentHour".
     *
     * (See handout for details)
     *
     * This method must be implemented using a recursive programming solution to
     * this problem. It is expected to have a worst-case running time that is
     * exponential in k
     *
     * @require the arrays hourlyVolume, fullServiceCapacity, regularServiceCapacity
     * and minorServiceCapacity are not null, and do not contain null values. Each
     * of the values in all arrays are non-negative (greater than or equal to 0).
     * fullServiceCapacity.length > 0, regularServiceCapacity > 0, minorServiceCapacity > 0
     *
     * @ensure Returns the least cost that can be incurred by your company over the
     * k = hourlyVolume.length hours (i.e hour 0 to hour k-1) that you are
     * in charge of the pump. Given that a full service concluded the hour
     * before you were placed in charge of the system (i.e finished one hour
     * before hour 0), given parameters hourlyVolume, fullServiceCapacity,
     * regularServiceCapacity and minorServiceCapacity
     */
    private static int optimalLossRecursive(int[] hourlyVolume,
            int[] fullServiceCapacity, int [] regularServiceCapacity,
            int[] minorServiceCapacity, int currentHour, Service lastService, int hoursSinceService) {

        // Array of references to the original array
        // It is indexed by the lastService enum to get the current service
        int[] currentService = new int[][]{fullServiceCapacity,
                regularServiceCapacity, minorServiceCapacity}[lastService.ordinal()];

        // Base case: return 0
        if (currentHour >= hourlyVolume.length) {
            return 0;
        }

        // Calculate the minimum loss if no service is done in the current hour
        int currentServiceVolume = hoursSinceService >= currentService.length ? 0 : currentService[hoursSinceService];
        int noServiceMin = Integer.max(0, hourlyVolume[currentHour] - currentServiceVolume);
        noServiceMin += optimalLossRecursive(hourlyVolume,
                fullServiceCapacity, regularServiceCapacity, minorServiceCapacity,
                currentHour + 1, lastService, hoursSinceService + 1);

        // Calculate the min value if the full service was to be done now
        int fullServiceMin = Integer.MAX_VALUE; // By default set it to the max value (so it won't be the min)
        if (currentHour + 4 < hourlyVolume.length) { // Check it could be valid to perform a full service in this hour
            int loss = hourlyVolume[currentHour] + hourlyVolume[currentHour + 1]
                    + hourlyVolume[currentHour + 2] + hourlyVolume[currentHour + 3];

            fullServiceMin = loss + optimalLossRecursive(hourlyVolume,
                    fullServiceCapacity, regularServiceCapacity, minorServiceCapacity,
                    currentHour + 4, Service.FULL_SERVICE, 0);
        }

        // Calculate the min value if the regular service was to be done now
        int regularServiceMin = Integer.MAX_VALUE;
        if (currentHour + 2 < hourlyVolume.length) {
            int loss = hourlyVolume[currentHour] + hourlyVolume[currentHour + 1];
            regularServiceMin = loss + optimalLossRecursive(hourlyVolume,
                    fullServiceCapacity, regularServiceCapacity, minorServiceCapacity,
                    currentHour + 2, Service.REGULAR_SERVICE, 0);
        }

        // Calculate the min value if the minor service was to be done now
        int minorServiceMin = Integer.MAX_VALUE;
        if (currentHour + 1 < hourlyVolume.length) {
            int loss = hourlyVolume[currentHour];
            minorServiceMin = loss + optimalLossRecursive(hourlyVolume,
                    fullServiceCapacity, regularServiceCapacity, minorServiceCapacity,
                    currentHour + 1, Service.MINOR_SERVICE, 0);
        }

        // Return the min from the four values above
        return Arrays.stream(new int[]{
                noServiceMin,
                fullServiceMin,
                regularServiceMin,
                minorServiceMin
        }).min().getAsInt();
    }
}
