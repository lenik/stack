package com.bee32.sem.frame.search;

import java.util.Collection;

public interface ISearchFragmentsHolder {

    Collection<? extends SearchFragment> getSearchFragments(String groupId);

    void clearSearchFragments();

    void addSearchFragment(String groupId, SearchFragment fragment);

    void setSearchFragment(String groupId, SearchFragment fragment);

    void removeSearchFragmentGroup(String groupId);

    void removeSearchFragment(SearchFragment fragment);

}
