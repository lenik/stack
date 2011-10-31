package user.spring;

import com.bee32.plover.arch.DataService;

public class ManyFaces
        extends DataService
        implements IManyFace1, IManyFace2 {

    @Override
    public void face1() {
    }

    @Override
    public void face2() {
    }

}
