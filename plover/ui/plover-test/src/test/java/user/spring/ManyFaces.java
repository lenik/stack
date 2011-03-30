package user.spring;

import org.springframework.stereotype.Component;

import com.bee32.plover.arch.EnterpriseService;

@Component
public class ManyFaces
        extends EnterpriseService
        implements IManyFace1, IManyFace2 {

    @Override
    public void face1() {
    }

    @Override
    public void face2() {
    }

}
