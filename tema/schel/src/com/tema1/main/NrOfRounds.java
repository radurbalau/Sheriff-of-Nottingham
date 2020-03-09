package com.tema1.main;

final class NrOfRounds {
    private static int nrOfRounds = 0;

    private NrOfRounds() {
        nrOfRounds = 0;
    }


    public static int getNrOfRounds() {
        return nrOfRounds;
    }

    public static void setNrOfRounds(final int nrOfRounds) {
        NrOfRounds.nrOfRounds = nrOfRounds;
    }
}
