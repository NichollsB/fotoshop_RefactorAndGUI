
package fotoshop.Events;

/**
 * Interface adding the methods required to implement the EventController
 * @see #addController(fotoshop.Events.EventController) 
 * @see #fireEvent(java.lang.String, fotoshop.Events.PropertyMessage) 
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public interface ControllerEventSender {
    public void addController(EventController c);
    public void fireEvent(String property, PropertyMessage message);
}
