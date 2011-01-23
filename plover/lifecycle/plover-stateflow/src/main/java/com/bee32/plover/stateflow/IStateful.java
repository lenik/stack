package com.bee32.plover.stateflow;

public interface IStateful {

    int getStateCount();

    State getState(int stateIndex);

}
