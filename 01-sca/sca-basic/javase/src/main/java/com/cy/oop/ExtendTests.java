package com.cy.oop;
class A{
    public void show(){}
}
class B extends A{
    public void display(){

    }
}

public class ExtendTests {
    public static void main(String[] args) {
        A a=new B();
        a.show();
        ((B) a).display();
    }
}
