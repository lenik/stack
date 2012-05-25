package com.bee32.sem.frame.menu;

public interface IMenuAssembler {

    <T extends MenuComposite> T require(Class<T> mcClass);

}
