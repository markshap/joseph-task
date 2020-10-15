package com.markshapiro.josephtask;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class Main {

    private static boolean survives(final int pos, final int N, final int M, final int shift) {
        if (M == 1 || N == 1) {
            // special case. The last one survives
            return pos == N;
        } else if ((pos + shift) % M == 0) {
            // sorry, man
            return false;
        }  else {
            // the next N is reduced by the number of killed
            int nextN = N - (N + shift)  / M;
            // the next Pos is reduced by the number of killed in front of the current pos
            int nextPos = pos - (pos + shift)  / M;
            // the next shift is the old one plus the reminder
            int nextShift = (shift + N % M) % M;
            return survives(nextPos, nextN, M, nextShift);
        }
    }

    private static int josephTask(final int N, final int M) {
        return IntStream.range(1, N+1).parallel().filter(i-> survives(i, N, M, 0)).findAny().orElse(-1);
    }

     public static void main(String[] args) {
         assertEquals(1, josephTask(1, 1));
         assertEquals(2, josephTask(2, 1));
         assertEquals(50000000, josephTask(50000000, 1));
         assertEquals(32891137, josephTask(50000000, 2));
         assertEquals(3, josephTask(8, 5));
         assertEquals(28, josephTask(40, 3));
    }
}
