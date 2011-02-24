package com.bee32.plover.arch.ui.annotated;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

public class RefdocsUtil {

    public static Refdoc[] getRefdocs(AnnotatedElement annotatedElement) {
        Refdocs _refdocs = annotatedElement.getAnnotation(Refdocs.class);
        if (_refdocs != null) {
            Refdoc[] refdocs = _refdocs.value();
            return refdocs;
        }

        Refdoc _refdoc = annotatedElement.getAnnotation(Refdoc.class);
        if (_refdoc != null) {
            return new Refdoc[] { _refdoc };
        }

        return null;
    }

    public static <AnnotatedMember extends AnnotatedElement & Member> //
    Refdoc[] getMemberRefdocs(AnnotatedMember member) {
        Refdoc[] maps = getRefdocs(member);
        return maps;
    }

}
