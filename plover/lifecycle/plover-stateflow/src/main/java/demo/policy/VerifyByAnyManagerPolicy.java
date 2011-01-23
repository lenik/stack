package demo.policy;

import java.util.Set;
import java.util.TreeSet;

import com.seccaproject.sems.security.verify.AbstractVerifyPolicy;
import com.seccaproject.sems.security.verify.BadVerifyDataException;
import com.seccaproject.sems.security.verify.IVerifiableObject;
import com.seccaproject.sems.security.verify.VerifyException;

import demo.apps.business.Data;

/**
 * 由任一管理员审核策略。
 */
public class VerifyByAnyManagerPolicy extends AbstractVerifyPolicy {

    private final Set<String> powerfulManagers;

    public VerifyByAnyManagerPolicy(String... powerfulManagers) {
        this.powerfulManagers = new TreeSet<String>();
        for (String pm : powerfulManagers)
            this.powerfulManagers.add(pm);
    }

    public VerifyByAnyManagerPolicy(Set<String> powerfulManagers) {
        this.powerfulManagers = powerfulManagers;
    }

    public void verify(IVerifiableObject verifiableObject)
            throws VerifyException, BadVerifyDataException {
        if (verifiableObject == null)
            throw new NullPointerException("verifiableObject");

        Object _data = verifiableObject.getVerifyData();
        if (_data == null)
            throw new VerifyException("尚未审核。");

        if (!(_data instanceof Data))
            throw new BadVerifyDataException(Data.class, _data);

        Data data = (Data) _data;

        if (!powerfulManagers.contains(data.getVerifier()))
            throw new VerifyException("无效的审核人：" + data.getVerifier());
    }

}
