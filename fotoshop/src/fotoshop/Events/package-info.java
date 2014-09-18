/**
 * The Events package contains classes related to the applications controller.
 * GUI objects trigger property change events, sending a PropertyMessage object
 * to the controller, which in turn sends commands to the Editor.
 * <div>
 * <b>See Also:</b><br/>
 * {@link fotoshop.Events.EventController}: Recieves property change events from
 * GUI components and sends them on to the {@link fotoshop.Editor} as a
 * {@link fotoshop.Command}. Also updates the GUI when it requires refreshing. <br/><br/>
 * {@link fotoshop.Events.PropertyMessage} The message object sent from the GUI
 * component. Contains any numerical, string, boolean, or Files than may need
 * to be passed to the Editor, and methods to retrieve them.<br/><br/>
 * {@link fotoshop.Events.ControllerEventSender}: Interface for event sending
 * GUI components. <br/>
 * </div>
 */
package fotoshop.Events;
