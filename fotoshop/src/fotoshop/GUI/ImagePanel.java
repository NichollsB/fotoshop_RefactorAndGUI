
package fotoshop.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * JPanel for displaying the image in the main GUI
 * @see #ImagePanel() 
 * @see #ImagePanel(int, int) 
 * @see #showImage() 
 * @see #showImage(java.awt.image.BufferedImage) 
 * 
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class ImagePanel extends JPanel {
    /**
     * The Image that this panel should display
     */
    private BufferedImage image;
    /**
     * The size the image should be displayed at. This is a dynamic value and
     * can change depending of GUI JFrame size.
     */
    private Dimension size = new Dimension(800, 600);
    /**
     * x position the image should be displayed at. This is a dynamic value and
     * can change depending of GUI JFrame size.
     */
    private int x=0;
    /**
     * y position the image should be displayed at. This is a dynamic value and
     * can change depending of GUI JFrame size.
     */
    private int y=0;
    /**
     * Initialise the ImagePanel with default {@link #size size} value.
     */
    public ImagePanel(){
        setPreferredSize(size);
        setMinimumSize(size);
        setSize(size);
    }
    /**
     * Initialise the panel with an input size
     * @param x int width to give to the panel
     * @param y int height to give to the panel
     */
    public ImagePanel(int x, int y){
        size = new Dimension(x, y);
        setPreferredSize(size);
        setMinimumSize(size);
        setSize(size);
    }
    
    /**
     * Show the passed in BufferedImage in the panel. Resizes the image depending
     * on the current size of the panel and displays it in the center of the panel.
     * @param img BufferedImage to display in the panel
     */
    public void showImage(BufferedImage img){
        if(img != null){
            this.image = img;
            size = getSize();
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            float i = img.getHeight(); float j = img.getWidth();
            float src = i/j;
            i = getSize().height; j = getSize().width;
            if(i/j >= src){
                size.width = getSize().width;
                size.height = Math.round(size.width * src);
            }
            else{
                size.height = getSize().height;
                size.width = Math.round(size.height / src);
            }
            x = (getSize().width/2)-(size.width/2);
            y = (getSize().height/2)-(size.height/2);
            repaint();
        }
    }
    /**
     * Call the {@link #showImage(java.awt.image.BufferedImage) } method with the
     * last image displayed. Refreshes the size, and re-paints.
     */
    public void showImage(){
        showImage(image);
    }

    /**
     * Paint the image to the panel
     * @param g {@link java.awt.Graphics }
     */
    public void paintComponent(Graphics g){

        g.drawImage(image, x, y, size.width, size.height, null);
        
    }
    

}
