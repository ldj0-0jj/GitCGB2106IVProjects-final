package com.cy.oop;

interface Cache{
    int size();
}
class WeakCache implements  Cache{

    @Override
    public int size() {
        return 0;
    }
}

public class InterfaceTests {
}
