import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.io.File;
import java.awt.Dimension;

public class Solver extends JPanel {
    // Images
	private Image arrowUp;
	private Image arrowDown;
	private Image arrowLeft;
	private Image arrowRight;

	// Letter Constants
	private final static String UP = "UP";
	private final static String DOWN = "DOWN";
	private final static String LEFT = "LEFT";
	private final static String RIGHT = "RIGHT";
    
    // for path
    private int pathIndex = 1;
    private ArrayList<String> pathway = new ArrayList<String>();

    public Solver (ArrayList<String> pathway) {
        this.setLayout(null);
        this.setBounds(0,0,500,500);
        this.pathway = pathway;
        this.repaint();
        this.setOpaque(false);
    }

    // for path index
    public void nextIndex () {
        if (this.pathIndex < this.pathway.size()-1) {
            this.pathIndex++;
            // reload GUI
            this.repaint();
        }
    }

    public void prevIndex () {
        if (this.pathIndex > 1) {
            this.pathIndex--;
            // reload GUI
            this.repaint();
        }
    }

    // for repaint
    @Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			// loads all images needed
			arrowUp = ImageIO.read(new File("images/up_arrow.png"));
			arrowDown = ImageIO.read(new File("images/down_arrow.png"));
			arrowLeft = ImageIO.read(new File("images/left_arrow.png"));
			arrowRight = ImageIO.read(new File("images/right_arrow.png"));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		Graphics2D g2d = (Graphics2D)g;

		// put images per grid
        switch(this.pathway.get(this.pathIndex)){
            case UP:
                g.drawImage(arrowUp, 0, 0, 250, 125, null);
                break;
            case DOWN:
                g.drawImage(arrowDown, 0, 0, 250, 125, null);
                break;
            case LEFT:
                g.drawImage(arrowLeft, 0, 0, 250, 125, null);
                break;
            case RIGHT:
                g.drawImage(arrowRight, 0, 0, 250, 125, null);
                break;
            default:
                break;
        }
    }
}