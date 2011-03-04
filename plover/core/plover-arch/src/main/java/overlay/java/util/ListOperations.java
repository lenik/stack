package overlay.java.util;

import java.util.List;

import overlay.Overlay;

@Overlay
public class ListOperations {

    public static void delete(List<?> list, int index) {
        list.remove(index);
    }

}
