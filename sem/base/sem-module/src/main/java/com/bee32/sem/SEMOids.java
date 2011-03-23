package com.bee32.sem;

public interface SEMOids {

    int[] ROOT = { 3, 15 };

    int Base = 1;
    int Process = 2;
    int Resource = 3;
    int Social = 4;
    int Support = 5;

    interface base {
        int Frame = 1;
        int Module = 2;
        int Desktop = 3;
        int User = 4;
        int People = 5;
        int File = 6;
        int Mail = 7;
        int Event = 8;
        int Bank = 9;
    }

    interface process {
        int Process = 1;
        int Techproc = 2;
        int Workflow = 3;
    }

    interface resource {
        int Store = 1;
        int Thing = 2;
        int Asset = 3;
        int Contract = 4;
    }

    interface social {
        int Staff = 1;
        int Customer = 2;
        int Supplier = 3;
        int Partner = 4;
    }

    interface support {
        int Calendar = 1;
        int Track = 2;
        int Kb = 3;
    }

}
