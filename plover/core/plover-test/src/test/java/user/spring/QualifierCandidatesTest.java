package user.spring;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.stereotype.Service;

import com.bee32.plover.inject.qualifier.Variant;
import com.bee32.plover.test.WiredTestCase;

class Gear {

    public String name;

    public Gear() {
        name = getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return name;
    }

}

@Service
class DefaultGear
        extends Gear {
}

@Service
@Variant("Special")
class SpecialGear
        extends Gear {
}

public class QualifierCandidatesTest
        extends WiredTestCase {

    @Inject
    Gear defaultGear;

    @Inject
    @Variant("Special")
    Gear specialGear;

    @Test
    public void theDefaultOne() {
        assertEquals(DefaultGear.class, defaultGear.getClass());
    }

    @Test
    public void theSpecialOne() {
        assertEquals(SpecialGear.class, specialGear.getClass());
    }

}
