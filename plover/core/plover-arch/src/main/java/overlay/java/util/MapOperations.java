package overlay.java.util;

import java.util.Map;

import overlay.Overlay;

@Overlay
public class MapOperations {

    /**
     * XXX - TypedMap.getKeyClass()?
     */
    public static void delete(Map<?, ?> map, String key) {
        map.remove(key);
    }

}
