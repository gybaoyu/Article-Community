package cn.abalone.util;

/**
 * Create by Abalone
 * CreateTime: 2021/2/3 15:55
 */

public class ThreeTuple<A,B,C>extends TwoTuple<A,B> {
    public final C pages;

    public ThreeTuple(A first, B second, C third) {
        super(first, second);
        pages = third;
    }
}
