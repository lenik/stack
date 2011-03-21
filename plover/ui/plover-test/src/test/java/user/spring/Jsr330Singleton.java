package user.spring;

import javax.inject.Singleton;

@Singleton
public class Jsr330Singleton {

    public Jsr330Singleton() {
        System.err.println("Jsr330Singleton::ctor()");
    }

}
