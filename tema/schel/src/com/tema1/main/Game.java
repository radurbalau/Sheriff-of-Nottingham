package com.tema1.main;

import com.tema1.goods.GoodsFactory;
import com.tema1.goods.LegalGoods;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Comparator;


public class Game {

    //constructor for the game
    private List<BasicPlayer> playerList = new LinkedList<>();

    /****/
    public List<BasicPlayer> getPlayerList() {
        return playerList;
    }

    /****/
    public void setPlayerList(final List<BasicPlayer> playerList) {
        this.playerList = playerList;
    }

    public Game(final List<String> listOfPlayers) {
        makePlayers(listOfPlayers);
    }

    /****/
    public void makePlayers(final List<String> listOfPlayers) {
        for (int i = 0; i < listOfPlayers.size(); i++) {
            String ss = listOfPlayers.get(i);
            if (ss.equals("basic")) {
                BasicPlayer playerBA = new BasicPlayer(
                        false, com.tema1.common.Constants.getNrOfCoins(), i);
                playerList.add(playerBA);
            } else if (ss.equals("greedy")) {
                BasicPlayer playerG = new GreedyPlayer(
                        false, com.tema1.common.Constants.getNrOfCoins(), i);
                playerList.add(playerG);
            } else if (ss.equals("bribed")) {
                BasicPlayer playerBB = new BribedPlayer(
                        false, com.tema1.common.Constants.getNrOfCoins(), i);
                playerList.add(playerBB);
            }
        }
    }



    /****/
    public void bonusKingQueen() {
        int maximQueen = 0;
        int maximKing = 0;
        int position = 0;
        int kingposition = 0;

            List<BasicPlayer> copy = new ArrayList<>();
            copy.addAll(playerList);

        for (int j = 0; j <= com.tema1.common.Constants.getNrOfLegalGoods(); j++) {
            int finalJ = j;
            copy.sort(new Comparator<BasicPlayer>() {
               @Override
               public int compare(final BasicPlayer basicPlayer, final BasicPlayer t1) {
                   return -Integer.compare(basicPlayer.
                           getWhatHeSell().getOrDefault(finalJ, 0), t1.
                           getWhatHeSell().getOrDefault(finalJ, 0));
               }
           });

            if (copy.get(0).getWhatHeSell().getOrDefault(j, 0) != 0) {
                copy.get(0).setCoins(copy.get(0).getCoins()
                        + ((LegalGoods) GoodsFactory.getInstance().getGoodsById(j)).getKingBonus());
            }
            if (copy.get(1).getWhatHeSell().getOrDefault(j, 0) != 0) {
                copy.get(1).setCoins(copy.get(1).getCoins()
                        + ((LegalGoods) GoodsFactory.getInstance().
                        getGoodsById(j)).getQueenBonus());
            }
            }
            //in max e nr de aparitii

        }

    /****/
    public void oneRound(final GameInput gameInput) {
            //pentru o runda de 1 tuea mai intai e primul serif si dupae al 2 lea serif
            for (int j = 0; j < playerList.size(); j++) {
                BasicPlayer players = playerList.get(j);
                players.setSherif(true);
                for (BasicPlayer nonSheriff : playerList) {
                    if (!nonSheriff.getisSherif()) {
                        nonSheriff.fillHandOfGoods(gameInput);

                        if (players.getCoins() < com.tema1.common.Constants.
                                    getMinCoinsForInspecting()) {
                            break;
                        }
                        nonSheriff.basicsBehaviour();

                        nonSheriff.apparitionsOfObjets();
                        players.isPlayerSincere(nonSheriff, gameInput);

                        nonSheriff.makeListOfSellItems();
                    }
                }
                players.setSherif(false);

            }
           NrOfRounds.setNrOfRounds(NrOfRounds.getNrOfRounds() + 1);

        }




}
