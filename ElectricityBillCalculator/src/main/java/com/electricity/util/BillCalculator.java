package com.electricity.util;

import com.electricity.model.Bill;

/**
 * Calculates the electricity bill using slab (telescopic) rates.
 *
 * Slab 1  : first 50 units          -> Rs. 3.50 / unit
 * Slab 2  : next 100 units (51-150) -> Rs. 4.00 / unit
 * Slab 3  : next 100 units (151-250)-> Rs. 5.20 / unit
 * Slab 4  : units above 250         -> Rs. 6.50 / unit
 */
public class BillCalculator {

    public static final int SLAB1_LIMIT = 50;
    public static final int SLAB2_LIMIT = 100;
    public static final int SLAB3_LIMIT = 100;

    public static final double SLAB1_RATE = 3.50;
    public static final double SLAB2_RATE = 4.00;
    public static final double SLAB3_RATE = 5.20;
    public static final double SLAB4_RATE = 6.50;

    /**
     * Builds a fully populated {@link Bill} (slab-wise amounts + total)
     * for the given number of consumed units.
     *
     * @param units number of units consumed (must be >= 0)
     */
    public static Bill calculate(int units) {

        if (units < 0) {
            throw new IllegalArgumentException(
                    "Units cannot be negative");
        }

        Bill bill = new Bill();
        bill.setUnits(units);

        int remaining = units;

        // Slab 1 : first 50 units
        int slab1Units = Math.min(remaining, SLAB1_LIMIT);
        double slab1Amount = slab1Units * SLAB1_RATE;
        remaining -= slab1Units;

        // Slab 2 : next 100 units
        int slab2Units = Math.min(remaining, SLAB2_LIMIT);
        double slab2Amount = slab2Units * SLAB2_RATE;
        remaining -= slab2Units;

        // Slab 3 : next 100 units
        int slab3Units = Math.min(remaining, SLAB3_LIMIT);
        double slab3Amount = slab3Units * SLAB3_RATE;
        remaining -= slab3Units;

        // Slab 4 : everything above 250 units
        int slab4Units = remaining;
        double slab4Amount = slab4Units * SLAB4_RATE;

        bill.setSlab1Amount(slab1Amount);
        bill.setSlab2Amount(slab2Amount);
        bill.setSlab3Amount(slab3Amount);
        bill.setSlab4Amount(slab4Amount);

        double total =
                slab1Amount + slab2Amount + slab3Amount + slab4Amount;

        bill.setTotalAmount(total);

        return bill;
    }
}
