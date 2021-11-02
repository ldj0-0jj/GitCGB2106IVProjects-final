package com.cy.oop;

public class IntegerCacheTests {
    public static void main(String[] args) {
        Integer t1=100;//自动封箱Integer.valueOf(100);
        Integer t2=100;
        Integer t3=200;
        Integer t4=200;
        System.out.println(t1==t2);//true (100在整数池)
        System.out.println(t3==t4);//false
        System.out.println(t3.equals(t4));//true

    }
}
