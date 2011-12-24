package com.bee32.sem.people.web;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.sem.frame.web.IndexRichCompatController;
import com.bee32.sem.people.SEMPeopleModule;

@RequestMapping(PersonCompatController.PREFIX + "/*")
public class PersonCompatController
        extends IndexRichCompatController {

    public static final String PREFIX = SEMPeopleModule.PREFIX + "/person";

}
