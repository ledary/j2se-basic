package com.netty.in.action;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/7
 **/
public class Data {
    private int n;
    private int m;

    public Data(int n,int m){
        this.n = n;
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    @Override
    public String toString(){
        int r = Math.floorDiv(n,m);
        return n + "/" + m + "=" + r;
    }
}
