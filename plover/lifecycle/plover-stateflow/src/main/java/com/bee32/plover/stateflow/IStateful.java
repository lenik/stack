package com.bee32.plover.stateflow;

public interface IStateful {

    IStateSchema getSchema();

    State getState();

}
