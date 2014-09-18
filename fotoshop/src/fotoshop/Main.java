package fotoshop;

import fotoshop.Events.EventController;
import fotoshop.GUI.GUI;

/**
 * This is the main class for the Fotoshop application
 * Creates an {@link Editor editor} and {@link EventController controller}. Creates a
 * {@link GUI gui} object within a separate thread. Also calls the 
 * {@link fotoshop.Events.EventController#setEditor(fotoshop.Editor) setEditor()}
 * method with the editor object, and the 
 * {@link fotoshop.Events.EventController#setGUI(fotoshop.GUI.GUI) setGUI()} method
 * with the gui object.
 * 
 * @author Benjamin Nicholls, bn65
 * @version 2013.11.27
 */
public class Main {
    /**
     * Start the editor. Create a new instance of Editor
     * @param args 
     */
    
   public static void main(String[] args) {

       final Editor editor = new Editor();
       final EventController controller = new EventController();
       javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                GUI gui = new GUI(controller);
                controller.setGUI(gui);
            }
        });
       controller.setEditor(editor);
    }
}
