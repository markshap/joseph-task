package com.markshapiro.josephtask;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class Main {

    private static boolean survives(final int pos, final int N, final int M) {
        int p = pos;
        int n = N;
        int shift = 0;
        while (n > 1 && M > 1) {
            if ((p + shift) % M == 0) {
                // sorry, man
                return false;
            }
            ;
            // the next N is reduced by the number of killed
            int nextN = n - (n + shift) / M;
            // the next Pos is reduced by the number of killed in front of the current pos
            p = p - (p + shift) / M;
            // the next shift is the old one plus the reminder
            shift = (shift + n % M) % M;
            n = nextN;
        }
        return p == n;
    }

    private static int josephTask(final int N, final int M) {
        return IntStream.range(1, N + 1).parallel().filter(i -> survives(i, N, M)).findAny().orElse(-1);
    }

     public static void main(String[] args) {
         assertEquals(1, josephTask(1, 1));
         assertEquals(2, josephTask(2, 1));
         assertEquals(50000000, josephTask(50000000, 1));
         assertEquals(32891137, josephTask(50_000_000, 2));
         // check with the analytic solution for M=2
         assertEquals((int)(1 + 2 * (50_000_000 - Math.pow(2,(int)(Math.log10(50_000_000) / Math.log10(2))))), josephTask(50_000_000, 2));
         assertEquals(3, josephTask(8, 5));
         assertEquals(28, josephTask(40, 3));
    }
}
