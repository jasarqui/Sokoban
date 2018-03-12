import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Main {
	static String filepath = "puzzle2.in";
	static Sokoban gamePanel;
	static JFrame frame;
	public static void main(String[] args) {
		// initialize game frame
		frame = new JFrame("Sokoban-UI");
		frame.setPreferredSize(new Dimension(600,635));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setLayout(null);

		// set Sokoban as gamePanel
 		gamePanel = new Sokoban(filepath);
 		frame.setContentPane(gamePanel);		

		// Game constants
		final boolean ONGOING = false;

		// show buttons
		JButton buttonFile = new JButton();
		JButton buttonAI = new JButton();

		// for load file
		buttonFile.setSize(100,25);
		buttonFile.setLocation(250,500);
		buttonFile.setVisible(true);
		buttonFile.setText("Load");
		buttonFile.setFocusable(false);
		frame.add(buttonFile);
		// for solve 
		buttonAI.setSize(100,25);
		buttonAI.setLocation(250,500);
		buttonAI.setVisible(true);
		buttonAI.setText("Solve!");
		buttonAI.setFocusable(false);
		frame.add(buttonAI);

		// set keylisteners
 		frame.addKeyListener(new KeyListener(){
             public void keyPressed(KeyEvent ke){
				if(ke.getKeyCode()==KeyEvent.VK_UP){
					if (gamePanel.state == ONGOING){
						gamePanel.moveUp();
					}
				} else if(ke.getKeyCode()==KeyEvent.VK_LEFT){
					if (gamePanel.state == ONGOING){
						gamePanel.moveLeft();
					}
				} else if(ke.getKeyCode()==KeyEvent.VK_DOWN){
					if (gamePanel.state == ONGOING){
						gamePanel.moveDown();
					}
				} else if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
					if (gamePanel.state == ONGOING) {
						gamePanel.moveRight();
					}
				}
			}

			public void keyTyped(KeyEvent ke){}
			public void keyReleased(KeyEvent ke){}
        });

		// button file chooser
		buttonFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "in");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(chooser);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					filepath = chooser.getSelectedFile().getName();
				}
				gamePanel.setGamePuzzle(filepath);
			}
		});

		// button AI
		buttonAI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				SolverPanel solver = new SolverPanel(filepath);
            }
        });

		// set game frame default attributes
		frame.setResizable(false);
        frame.setFocusable(true);
		frame.pack();
		frame.setVisible(true);
	}
}
