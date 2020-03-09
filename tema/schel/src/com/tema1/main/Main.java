package com.tema1.main;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public final class Main {

    private Main() { }
    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        List<BasicPlayer> players = new ArrayList<>();


        Game gameMaster = new Game(gameInput.getPlayerNames());
        players.addAll(gameMaster.getPlayerList());

        for (int rounds = 0; rounds < gameInput.getRounds(); rounds++) {
            gameMaster.oneRound(gameInput);
        }

        for (BasicPlayer index : players) {
            index.sellItemFromSellItemsList();
        }
        gameMaster.bonusKingQueen();

        players.sort(new Comparator<BasicPlayer>() {
            @Override
            public int compare(final BasicPlayer basicPlayer, final BasicPlayer t1) {
                return -Integer.compare(basicPlayer.getCoins(), t1.getCoins());
            }
        });


        for (BasicPlayer s : players) {
            if (s.getClass() == BasicPlayer.class) {
                System.out.println(s.getIndex() + " " + "BASIC" + " " + s.getCoins());
            } else if (s.getClass() == GreedyPlayer.class) {
                System.out.println(s.getIndex() + " " + "GREEDY" + " " + s.getCoins());
            } else if (s.getClass() == BribedPlayer.class) {
                System.out.println(s.getIndex() + " " + "BRIBED" + " " + s.getCoins());
            }
        }

    }

}
