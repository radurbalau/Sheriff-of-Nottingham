package com.tema1.main;

import com.tema1.goods.Goods;

public class GoodsHelper {

    private boolean isDeclared;
    private Goods good;

    public GoodsHelper(final boolean isDeclared, final Goods good) {
        this.isDeclared = isDeclared;
        this.good = good;
    }

    public GoodsHelper(final Goods good) {
        this.good = good;
    }

    /****/
    public void setGood(final Goods good) {
        this.good = good;
    }

    /****/
    public Goods getGood() {
        return good;
    }

    /****/
    public boolean getisDeclared() {
        return isDeclared;
    }

    /****/
    public void setDeclared(final boolean declared) {
        isDeclared = declared;
    }

    /****/
    @Override
    public String toString() {
        return "GoodsHelper{"
                + "isDeclared=" + isDeclared
                + ", good=" + good
                + '}';
    }
}
