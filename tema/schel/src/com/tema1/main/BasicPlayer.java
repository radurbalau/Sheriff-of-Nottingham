package com.tema1.main;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;

import java.util.*;

public class BasicPlayer {

    private int coins;
    private boolean isSherif;
    private List<GoodsHelper> bag = new LinkedList<>();
    private GoodsHelper objectDeclaredOnRound;
    private List<GoodsHelper> hand = new LinkedList<>();
    private int index;
    private HashMap<Integer, Integer> whatHeSell = new HashMap<>();
    private List<GoodsHelper> listOfSellItems = new LinkedList<>();

    BasicPlayer() {
        coins = 0;
        isSherif = false;
    }

    public BasicPlayer(final boolean isSherif, final int coins, final int index) {
        this.isSherif = isSherif;
//        this.bag.addAll(bag);
        this.coins = coins;
        this.index = index;
    }

    /**
     *
     */
    public void makeListOfSellItems() {
        for (GoodsHelper indice : bag) {
            listOfSellItems.add(indice);
            if (indice.getGood().getType() == GoodsType.Illegal) {
                if (indice.getGood().getId() == com.tema1.common.Constants.getSilkeId()) {
                    GoodsHelper cheese = new GoodsHelper(true, GoodsFactory.
                            getInstance().getGoodsById(com.tema1.common.Constants.getCheeseId()));
                    listOfSellItems.add(cheese);   //cheese care are id-ul 1
                    listOfSellItems.add(cheese);
                    listOfSellItems.add(cheese);
                }
                if (indice.getGood().getId() == com.tema1.common.Constants.getPepperId()) {
                    GoodsHelper chicken = new GoodsHelper(true, GoodsFactory.
                            getInstance().getGoodsById(com.tema1.common.Constants.getChickenId()));
                    listOfSellItems.add(chicken);   //cheese care are id-ul 3
                    listOfSellItems.add(chicken);
                }
                if (indice.getGood().getId() == com.tema1.common.Constants.getBarrelId()) {
                    GoodsHelper bread = new GoodsHelper(true, GoodsFactory.
                            getInstance().getGoodsById(com.tema1.common.Constants.getBreadId()));
                    listOfSellItems.add(bread);   //bread care are id-ul 2
                    listOfSellItems.add(bread);
                }
                if (indice.getGood().getId() == com.tema1.common.Constants.getBeerId()) {
                    GoodsHelper wine = new GoodsHelper(true, GoodsFactory.
                            getInstance().getGoodsById(com.tema1.common.Constants.getWineId()));
                    listOfSellItems.add(wine);   //cheese care are id-ul 7
                    listOfSellItems.add(wine);
                    listOfSellItems.add(wine);
                    listOfSellItems.add(wine);
                }
                if (indice.getGood().getId() == com.tema1.common.Constants.getSeafoodId()) {
                    GoodsHelper tomato = new GoodsHelper(true, GoodsFactory.
                            getInstance().getGoodsById(com.tema1.common.Constants.getTomatoId()));
                    listOfSellItems.add(tomato);   //cheese care are id-ul 4
                    listOfSellItems.add(tomato); //x3 potato cu id 6
                    GoodsHelper potato = new GoodsHelper(true, GoodsFactory.
                            getInstance().getGoodsById(com.tema1.common.Constants.getPotatoId()));
                    listOfSellItems.add(potato);
                    listOfSellItems.add(potato);
                    listOfSellItems.add(potato);
                    GoodsHelper chicken = new GoodsHelper(true, GoodsFactory.
                            getInstance().getGoodsById(com.tema1.common.Constants.getChickenId()));
                    listOfSellItems.add(chicken);
                }
            }
        }
        bag.clear();
    }

    /**
     *
     */

    public void apparitionsOfObjets() {
        //entry.key bunul
        //entry.get value frecventa bunului
        for (int i = 0; i < bag.size(); i++) {   //keyul e idul din GOODSFACTORY
            GoodsHelper iterator = bag.get(i);
            whatHeSell.put(bag.get(i).getGood().getId(), whatHeSell.getOrDefault(bag.get(i).
                                                        getGood().getId(), 0) + 1);
        }
    }

    private boolean playerIsCaught() {  //aici sunt merchant  //true daca e prins
                                    // false daca nu a fost prins
        if (!isSherif) {
            for (GoodsHelper iterator : bag) { //iterare prin o colectie
                if (iterator.getGood() != objectDeclaredOnRound.getGood()) {
                    return true;
                }
            }
        }
        return false;
    }

    private int returnNumberOfObjectsDeclared(final BasicPlayer basicPlayer) {
        int nr = 0;
        for (GoodsHelper iterator : basicPlayer.bag) {
            if (iterator.getGood() == basicPlayer.objectDeclaredOnRound.getGood()) {
                nr++;
            }
        }
        return nr;
    }

    /**** @param basicPlayer
     * @param gameInput
     */

    public void isPlayerSincere(final BasicPlayer basicPlayer, final GameInput gameInput) {
        List<GoodsHelper> pozitii = new LinkedList<>();

        if (!basicPlayer.playerIsCaught()) {
            coins = coins - returnNumberOfObjectsDeclared(basicPlayer)
                    * basicPlayer.objectDeclaredOnRound.getGood().getPenalty();
            basicPlayer.coins = basicPlayer.coins + returnNumberOfObjectsDeclared(basicPlayer)
                    * basicPlayer.objectDeclaredOnRound.getGood().getPenalty();
        } else {
            List<GoodsHelper> goodsHelpers = basicPlayer.getBag();
            for (int i = 0; i < goodsHelpers.size(); i++) {
                GoodsHelper iterator = goodsHelpers.get(i);
                if (iterator.getGood() != basicPlayer.objectDeclaredOnRound.getGood()) {
                    coins = coins + iterator.getGood().getPenalty();
                    basicPlayer.coins = basicPlayer.coins - iterator.getGood().getPenalty();
                    gameInput.getAssetIds().add(iterator.getGood().getId());
                    pozitii.add(iterator);
                }


            }


            basicPlayer.getBag().removeAll(pozitii);
        }
    }

    /**
     *
     * @param gameInput
     */

    public void fillHandOfGoods(final GameInput gameInput) {  //AICI SUNT MERCHANT
        hand.clear();
        while (hand.size() < com.tema1.common.Constants.getHandSize()) {
            GoodsHelper goodsHelper = new GoodsHelper(true, GoodsFactory.getInstance().
                                        getGoodsById(gameInput.getAssetIds().get(0)));
            hand.add(goodsHelper);
            gameInput.getAssetIds().remove(0); //sterge din deck
        }
    }

    /**
     *
     */
    public void basicsBehaviour() {
        int max = 0;
        GoodsHelper maxGood;
        HashMap<Integer, Integer> maxLegalGoods = new HashMap<>();
        for (GoodsHelper indice : getHand()) {
            if (indice.getGood().getType() == GoodsType.Illegal) {
                continue;
            }
            maxLegalGoods.put(indice.getGood().getId(), maxLegalGoods.
                    getOrDefault(indice.getGood().getId(), 0) + 1);
        }           //value e nr de aparitii;
        Map.Entry<Integer, Integer> maxObject = null;
        for (Map.Entry<Integer, Integer> e : maxLegalGoods.entrySet()) {
            if (maxObject == null) {
                maxObject = e;
                continue;
            }

            if (e.getValue() > maxObject.getValue()) {
                maxObject = e;
            } else if (e.getValue().equals(maxObject.getValue())) {
                GoodsFactory eObject = GoodsFactory.getInstance();
                if (eObject.getGoodsById(e.getKey()).getProfit() > eObject.
                                getGoodsById(maxObject.getKey()).getProfit()) {
                    maxObject = e;
                } else if (eObject.getGoodsById(e.getKey()).getProfit() == eObject.
                                    getGoodsById(maxObject.getKey()).getProfit()) {
                    if (eObject.getGoodsById(e.getKey()).getId() > eObject.
                                    getGoodsById(maxObject.getKey()).getId()) {
                        maxObject = e;
                    }
                }
            }
        }


        List<GoodsHelper> listGoods = new ArrayList<>();
        if (maxObject != null) { //s-a gasit un bun legal
            for (GoodsHelper indice : getHand()) {
                if (maxObject.getKey() == indice.getGood().getId()) {
                    bag().add(indice);
                    listGoods.add(indice);
                }
            }
            GoodsHelper aux = new GoodsHelper(true, GoodsFactory.
                    getInstance().getGoodsById(maxObject.getKey()));
            objectDeclaredOnRound = aux;
            getHand().removeAll(listGoods);
        } else { //daca da 1 functia,le inverseaza
            GoodsHelper maxIllegalGood = Collections.max(getHand(), new Comparator<GoodsHelper>() {
                        @Override
                        public int compare(GoodsHelper goodsHelper, GoodsHelper t1) {
                            if (t1.getGood().getType() == GoodsType.Legal) {  //din get Hand se face
                                return 0;
                            }
                            return Integer.compare(goodsHelper.getGood().
                                    getProfit(), t1.getGood().getProfit());
                            //daca primul e mai mic functia va da -1 dar pentru -1vrem sa le schimbe deci --1;
                        }
                    }
            );
            bag().add(maxIllegalGood);
            getHand().remove(maxIllegalGood);

            GoodsHelper aux = new GoodsHelper(true, GoodsFactory.
                                            getInstance().getGoodsById(0)); //mere
            objectDeclaredOnRound = aux;
        }

    }

    /****/
    public void sellItemFromSellItemsList() {
        for (GoodsHelper iterator : listOfSellItems) {
            setCoins(getCoins() + iterator.getGood().getProfit());
        }
    }

    /****/
    public boolean isSherif() {
        return isSherif;
    }

    /****/
    public void setSherif(final boolean sherif) {
        isSherif = sherif;
    }

    /****/
    public List<GoodsHelper> getBag() {
        return bag;
    }

    /****/
    public void setBag(final List<GoodsHelper> bag) {
        this.bag = bag;
    }

    /****/
    public HashMap<Integer, Integer> getWhatHeSell() {
        return whatHeSell;
    }

    /****/
    public void setWhatHeSell(final HashMap<Integer, Integer> whatHeSell) {
        this.whatHeSell = whatHeSell;
    }

    /****/
    public int getCoins() {
        return coins;
    }

    /****/
    public void setCoins(final int coins) {
        this.coins = coins;
    }

    /****/
    public int getIndex() {
        return index;
    }

    /****/
    public void setIndex(final int index) {
        this.index = index;
    }

    /****/
    public List<GoodsHelper> getListOfSellItems() {
        return listOfSellItems;
    }

    /****/
    public void setListOfSellItems(final List<GoodsHelper> listOfSellItems) {
        this.listOfSellItems = listOfSellItems;
    }

    /****/
    public GoodsHelper getObjectDeclaredOnRound() {
        return objectDeclaredOnRound;
    }

    /****/
    public void setObjectDeclaredOnRound(final GoodsHelper objectDeclaredOnRound) {
        this.objectDeclaredOnRound = objectDeclaredOnRound;
    }

    /****/
    public boolean getisSherif() {
        return isSherif;
    }

    /****/
    public List<GoodsHelper> bag() {
        return bag;
    }

    /****/
    public List<GoodsHelper> getHand() {
        return hand;
    }

    /****/
    public void setHand(final List<GoodsHelper> hand) {
        this.hand = hand;
    }


}

