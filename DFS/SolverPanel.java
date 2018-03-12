import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.io.File;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.Dimension;

public class SolverPanel {
    Solver solver;
    public SolverPanel(String filepath) {
		// load puzzle
		File puzzle = new File(filepath);
		String[][] gamePuzzle;
		String[] loadedPuzzle = new String[10];
		int line = 0;

		try {
			Scanner sc = new Scanner(puzzle);

            while (sc.hasNextLine()) {
                loadedPuzzle[line] = sc.nextLine();
				line++;
            }
			
            sc.close();   
        } catch (Exception ex) {
        	System.out.println("File not found");
        }

        // ------------- put puzzle to array -------------
    	gamePuzzle = new String[10][10];
        // split per space
        for(int i = 0; i < 10; i++){
        	gamePuzzle[i] = loadedPuzzle[i].split(" ");
        }

        JFrame aiFrame = new JFrame("Sokoban-AI");
        // set ai frame default attributes
        aiFrame.setPreferredSize(new Dimension(250,125));
        aiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
        aiFrame.setLayout(null);
        aiFrame.setResizable(false);
        aiFrame.setFocusable(true);

        // create the next and prev buttons
        JButton prev = new JButton();
        prev.setBounds(5,5,80,25);
        prev.setVisible(true);
        prev.setText("Prev");
        prev.setFocusable(false);
        aiFrame.add(prev);

        JButton next = new JButton();
        next.setBounds(165,5,80,25);
        next.setVisible(true);
        next.setText("Next");
        next.setFocusable(false);
        aiFrame.add(next);
        
        // solve the map
        DFS dfsSolver = new DFS(gamePuzzle);

        // create the solverPanel
        long startTime = System.currentTimeMillis(); 
        this.solver = new Solver(dfsSolver.solve().getPath());
        long endTime = System.currentTimeMillis();
        System.out.println("Sokoban solved in " + (endTime - startTime));
        
        aiFrame.add(solver);

        prev.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solver.prevIndex();
            }
        });

        next.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solver.nextIndex();
            }
        });

        // remove focus
        aiFrame.pack();
        aiFrame.setVisible(true);
        aiFrame.setFocusable(false);
    }
}