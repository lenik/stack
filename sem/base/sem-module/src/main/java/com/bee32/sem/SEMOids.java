package com.bee32.sem;

public interface SEMOids {

    int[] ROOT = { 3, 15 };

    int Base = 1;
    int Process = 2;
    int Resource = 3;
    // social
    int Support = 5;
    int Biz1 = 6;
    int Biz2 = 7;

    interface base {
        int Frame = 1;
        int Module = 2;
        int Desktop = 3;
        int Company = 4;
        int People = 5;
        int File = 6;
        int Mail = 7;
        int Event = 8;
        int Bank = 9;
        int Calendar = 10;
    }

    interface process {
        int Process = 1;
        int Techproc = 2;
        int Workflow = 3;
    }

    interface resource {
        int World = 1;
        int Inventory = 2;
        int Asset = 3;
        int Bom = 4;
    }

    interface biz1 {
        int Chance = 1;
        int Purchase = 2;
        int Make = 3;
    }

    interface support {
        int Track = 2;
        int Kb = 3;
    }

}
