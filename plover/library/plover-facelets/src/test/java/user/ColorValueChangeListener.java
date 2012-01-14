package user;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

/**
 * This class is a JSF value-change-listener that listens to user edits in the color text fields.
 *
 * @author "Neil Griffin"
 */
public class ColorValueChangeListener
        implements ValueChangeListener {

    @Override
    public void processValueChange(ValueChangeEvent event)
            throws AbortProcessingException {
        String color = event.getComponent().getId();
        System.out.println(color + " value changed: " + event.getNewValue());
    }

}