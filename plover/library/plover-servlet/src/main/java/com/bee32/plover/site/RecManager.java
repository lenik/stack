package com.bee32.plover.site;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class RecManager {

    List<IRequestEntryCompletion> completions = new ArrayList<>();

    public RecManager() {
        for (IRequestEntryCompletion targeter : ServiceLoader.load(IRequestEntryCompletion.class))
            completions.add(targeter);
    }

    public void completeEntry(RequestEntry entry) {
        for (IRequestEntryCompletion completion : completions)
            completion.completeEntry(entry);
    }

    static RecManager instance;

    public static RecManager getInstance() {
        if (instance == null) {
            synchronized (RecManager.class) {
                if (instance == null) {
                    instance = new RecManager();
                }
            }
        }
        return instance;
    }

}
