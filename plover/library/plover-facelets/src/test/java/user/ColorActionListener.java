package user;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * This class is a JSF action-listener that listens to clicks on links in the color palette.
 *
 * @author "Neil Griffin"
 */
public class ColorActionListener
        implements ActionListener {

    @Override
    public void processAction(ActionEvent event)
            throws AbortProcessingException {
        String id = event.getComponent().getId();
        System.out.println("Selected palette link: " + id);
    }

}