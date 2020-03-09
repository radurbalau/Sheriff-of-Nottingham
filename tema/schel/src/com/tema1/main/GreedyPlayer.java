package com.tema1.main;

import com.tema1.goods.GoodsType;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class GreedyPlayer extends BasicPlayer {


    GreedyPlayer(final boolean isSherif, final int coins, final int index) {
        super(isSherif, coins, index);
    }

    /****/
    @Override //astsa cu runda para o pun in consola
    public void basicsBehaviour() {
        super.basicsBehaviour();
        if (NrOfRounds.getNrOfRounds() % 2 == 0) {

            List<GoodsHelper> illegalGoods = new ArrayList<>();
            if (getBag().size() <= com.tema1.common.Constants.getMaxHandForGreedy()) {
                for (int i = 0; i < getHand().size(); i++) {
                    GoodsHelper index = getHand().get(i);
                    if (index.getGood().getType() == GoodsType.Illegal) {
                        illegalGoods.add(index);
                    }
                }

                if (!illegalGoods.isEmpty()) {  //daca da 1 functia,le inverseaza
                    GoodsHelper maximumIllegalGood = Collections.
                            max(illegalGoods, (goodsHelper, t1) -> {
                                if (t1.getGood().getType() == GoodsType.Legal) {  //din getHandsefac
                                    return 0;
                                }
                                return -Integer.compare(goodsHelper.
                                        getGood().getProfit(), t1.getGood().getProfit());
                                //daca primul e mai mic functia
                                //va da -1 dar pentru -1vrem sa le schimbe deci --1;
                            }
                    );
                    getBag().add(maximumIllegalGood);
                    getHand().remove(maximumIllegalGood);
                }

            }
        }
    }


}

