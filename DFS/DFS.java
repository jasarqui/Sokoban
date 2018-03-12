// Data Structures
import java.util.Scanner;
import java.util.Deque;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

// I/O
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// UI
import javax.swing.JPanel;

public class DFS {
	// Keeper Graphic Details
	private int xPos;
	private int yPos;

	// Game
	private String[][] gamePuzzle;
	private final static boolean WIN = true;
	private final static boolean ONGOING = false;
	public boolean state;

	// AI
	private Deque<State> frontier = new LinkedList<State>();
	private State initialState;

	// Letter Constants
	private final static String BOX_OFF_STORAGE = "b";
	private final static String BOX_ON_STORAGE = "B";
	private final static String KEEPER_OFF_STORAGE = "k";
	private final static String KEEPER_ON_STORAGE = "K";
	private final static String FLOOR_NO_STORAGE = "e";
	private final static String FLOOR_WITH_STORAGE = "s";
	private final static String WALL = "w";
	private final static String NULL_FLOOR = "x";

	// Class Specification
	public DFS(String[][] map) {
		// set defaults
		this.xPos = xPos;
		this.yPos = yPos;

		// load puzzle
		this.gamePuzzle = new String[10][10];
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                this.gamePuzzle[y][x] = map[y][x];
            }
        }

		// initialize game
		boolean foundPos = false;

		// position of warehouse keeper
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if (this.gamePuzzle[i][j].equals(KEEPER_OFF_STORAGE)){
					this.xPos = j;
					this.yPos = i;
					foundPos = true;
					break;
				} else if (this.gamePuzzle[i][j].equals(KEEPER_ON_STORAGE)){
					this.xPos = j;
					this.yPos = i;
					foundPos = true;
					break;
				}
			}
			if (foundPos) break;
		}

		this.initialState = new State(this.xPos, this.yPos, this.gamePuzzle, "INITIAL");
	}

	// Methods
	// Actions(s) function
	public ArrayList<String> findActions(State gameState) {
		ArrayList<String> actions = new ArrayList<String>();

		// checks the next and afternext positions
		// adds to actions list if available
		// cannot go back to previous position
		if (this.checkLeft(gameState)) {
			actions.add("LEFT");
		}
		
		if (this.checkRight(gameState)) {
			actions.add("RIGHT");
		}
		
		if (this.checkUp(gameState)) {
			actions.add("UP");
		}
		
		if (this.checkDown(gameState)) {
			actions.add("DOWN");
		}

		return actions;
	}

	// checks the left side of the player if available
	public boolean checkLeft(State gameState) {
		// set current puzzle states
		String currentPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()];
		String nextPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()-1];
		String afterNextPosition;
		
		// having an out of bound position after the next tile
		// means it has reached an outer wall
		// this prevents errors
		if (gameState.getxPos()-2 < 0){
			return false;
		} else {
			afterNextPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()-2];
		}
		
		// if next position is a floor
		if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
			// always return true
			return true;
		// if next position is a box
		} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
			// if after the next position is a floor
			if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
				// if floor afternext, return true
				return true;	
			}
			// if wall or null afternext, return false
			return false;
		}
		// else if wall or null tile, return false by default
		return false;
	}

	// checks the right side of the player if available
	public boolean checkRight(State gameState) {
		// set current puzzle states
		String currentPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()];
		String nextPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()+1];
		String afterNextPosition;
		
		// having an out of bound position after the next tile
		// means it has reached an outer wall
		// this prevents errors
		if (gameState.getxPos()+2 > 9){
			return false;
		} else {
			afterNextPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()+2];
		}
		
		// if next position is a floor
		if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
			// always return true
			return true;
		// if next position is a box
		} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
			// if after the next position is a floor
			if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
				// if floor afternext, return true
				return true;	
			}
			// if wall or null afternext, return false
			return false;
		}
		// else if wall or null tile, return false by default
		return false;
	}

	// checks the upper side of the player if available
	public boolean checkUp(State gameState) {
		// set current puzzle states
		String currentPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()];
		String nextPosition = gameState.getMap()[gameState.getyPos()-1][gameState.getxPos()];
		String afterNextPosition;
		
		// having an out of bound position after the next tile
		// means it has reached an outer wall
		// this prevents errors
		if (gameState.getyPos()-2 < 0){
			return false;
		} else {
			afterNextPosition = gameState.getMap()[gameState.getyPos()-2][gameState.getxPos()];
		}
		
		// if next position is a floor
		if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
			// always return true
			return true;
		// if next position is a box
		} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
			// if after the next position is a floor
			if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
				// if floor afternext, return true
				return true;	
			}
			// if wall or null afternext, return false
			return false;
		}
		// else if wall or null tile, return false by default
		return false;
	}

	// checks the lower side of the player if available
	public boolean checkDown(State gameState) {
		// set current puzzle states
		String currentPosition = gameState.getMap()[gameState.getyPos()][gameState.getxPos()];
		String nextPosition = gameState.getMap()[gameState.getyPos()+1][gameState.getxPos()];
		String afterNextPosition;
		
		// having an out of bound position after the next tile
		// means it has reached an outer wall
		// this prevents errors
		if (gameState.getyPos()+2 > 9){
			return false;
		} else {
			afterNextPosition = gameState.getMap()[gameState.getyPos()+2][gameState.getxPos()];
		}
		
		// if next position is a floor
		if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
			// always return true
			return true;
		// if next position is a box
		} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
			// if after the next position is a floor
			if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
				// if floor afternext, return true
				return true;	
			}
			// if wall or null afternext, return false
			return false;
		}
		// else if wall or null tile, return false by default
		return false;
	}

	public State nextState(State currentState, String action) {
		State state = new State(currentState);
		// Note that out of bound state already checked in findActions
		String currentPosition = state.getMap()[state.getyPos()][state.getxPos()];
		String nextPosition;
		String afterNextPosition;
		// constants for readability
		final int MOVE_LEFT = -1;
		final int MOVE_RIGHT = 1;
		final int MOVE_UP = -1;
		final int MOVE_DOWN = 1;

		switch (action) {
			case "LEFT":
				nextPosition = state.getMap()[state.getyPos()][state.getxPos()-1];
				afterNextPosition = state.getMap()[state.getyPos()][state.getxPos()-2];

				// if next position is a floor
				if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
					// change current position to a floor tile
					if (currentPosition.equals(KEEPER_OFF_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos(), FLOOR_NO_STORAGE);
					} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos(), FLOOR_WITH_STORAGE);
					}

					// change next position to a keeper tile
					if (nextPosition.equals(FLOOR_NO_STORAGE)){
						state.setMap(state.getxPos()-1, state.getyPos(), KEEPER_OFF_STORAGE);
					} else if (nextPosition.equals(FLOOR_WITH_STORAGE)){
						state.setMap(state.getxPos()-1, state.getyPos(), KEEPER_ON_STORAGE);
					}
			
					// update x-position of keeper		
					state.setxPos(MOVE_LEFT);
					state.setAction("LEFT");
				
				// if next position is a box
				} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
					// if after the next position is a floor
					if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
						// change current position to a floor tile
						if (currentPosition.equals(KEEPER_OFF_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos(), FLOOR_NO_STORAGE);
						} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos(), FLOOR_WITH_STORAGE);
						}

						// change next position to a keeper tile
						if (nextPosition.equals(BOX_OFF_STORAGE)){
							state.setMap(state.getxPos()-1, state.getyPos(), KEEPER_OFF_STORAGE);
						} else if (nextPosition.equals(BOX_ON_STORAGE)){
							state.setMap(state.getxPos()-1, state.getyPos(), KEEPER_ON_STORAGE);
						}

						// change after the next position to a box tile
						if (afterNextPosition.equals(FLOOR_NO_STORAGE)){
							state.setMap(state.getxPos()-2, state.getyPos(), BOX_OFF_STORAGE);
						} else if (afterNextPosition.equals(FLOOR_WITH_STORAGE)){
							state.setMap(state.getxPos()-2, state.getyPos(), BOX_ON_STORAGE);
						}

						// update x-position of keeper
						state.setxPos(MOVE_LEFT);
						state.setAction("LEFT");	
					}

					// else if after next position is a wall or a box, do nothing
				}

				break;
			case "RIGHT":
				nextPosition = state.getMap()[state.getyPos()][state.getxPos()+1];
				afterNextPosition = state.getMap()[state.getyPos()][state.getxPos()+2];

				// if next position is a floor
				if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
					// change current position to a floor tile
					if (currentPosition.equals(KEEPER_OFF_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos(), FLOOR_NO_STORAGE);
					} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos(), FLOOR_WITH_STORAGE);
					}

					// change next position to a keeper tile
					if (nextPosition.equals(FLOOR_NO_STORAGE)){
						state.setMap(state.getxPos()+1, state.getyPos(), KEEPER_OFF_STORAGE);
					} else if (nextPosition.equals(FLOOR_WITH_STORAGE)){
						state.setMap(state.getxPos()+1, state.getyPos(), KEEPER_ON_STORAGE);
					}
			
					// update x-position of keeper		
					state.setxPos(MOVE_RIGHT);
					state.setAction("RIGHT");
				
				// if next position is a box
				} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
					// if after the next position is a floor
					if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
						// change current position to a floor tile
						if (currentPosition.equals(KEEPER_OFF_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos(), FLOOR_NO_STORAGE);
						} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos(), FLOOR_WITH_STORAGE);
						}

						// change next position to a keeper tile
						if (nextPosition.equals(BOX_OFF_STORAGE)){
							state.setMap(state.getxPos()+1, state.getyPos(), KEEPER_OFF_STORAGE);
						} else if (nextPosition.equals(BOX_ON_STORAGE)){
							state.setMap(state.getxPos()+1, state.getyPos(), KEEPER_ON_STORAGE);
						}

						// change after the next position to a box tile
						if (afterNextPosition.equals(FLOOR_NO_STORAGE)){
							state.setMap(state.getxPos()+2, state.getyPos(), BOX_OFF_STORAGE);
						} else if (afterNextPosition.equals(FLOOR_WITH_STORAGE)){
							state.setMap(state.getxPos()+2, state.getyPos(), BOX_ON_STORAGE);
						}

						// update x-position of keeper
						state.setxPos(MOVE_RIGHT);
						state.setAction("RIGHT");	
					}

					// else if after next position is a wall or a box, do nothing
				}

				break;
			case "UP":
				nextPosition = state.getMap()[state.getyPos()-1][state.getxPos()];
				afterNextPosition = state.getMap()[state.getyPos()-2][state.getxPos()];

				// if next position is a floor
				if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
					// change current position to a floor tile
					if (currentPosition.equals(KEEPER_OFF_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos(), FLOOR_NO_STORAGE);
					} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos(), FLOOR_WITH_STORAGE);
					}

					// change next position to a keeper tile
					if (nextPosition.equals(FLOOR_NO_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos()-1, KEEPER_OFF_STORAGE);
					} else if (nextPosition.equals(FLOOR_WITH_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos()-1, KEEPER_ON_STORAGE);
					}
			
					// update y-position of keeper		
					state.setyPos(MOVE_UP);
					state.setAction("UP");
				
				// if next position is a box
				} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
					// if after the next position is a floor
					if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
						// change current position to a floor tile
						if (currentPosition.equals(KEEPER_OFF_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos(), FLOOR_NO_STORAGE);
						} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos(), FLOOR_WITH_STORAGE);
						}

						// change next position to a keeper tile
						if (nextPosition.equals(BOX_OFF_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos()-1, KEEPER_OFF_STORAGE);
						} else if (nextPosition.equals(BOX_ON_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos()-1, KEEPER_ON_STORAGE);
						}

						// change after the next position to a box tile
						if (afterNextPosition.equals(FLOOR_NO_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos()-2, BOX_OFF_STORAGE);
						} else if (afterNextPosition.equals(FLOOR_WITH_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos()-2, BOX_ON_STORAGE);
						}

						// update y-position of keeper
						state.setyPos(MOVE_UP);
						state.setAction("UP");	
					}

					// else if after next position is a wall or a box, do nothing
				}

				break;
			case "DOWN":
				nextPosition = state.getMap()[state.getyPos()+1][state.getxPos()];
				afterNextPosition = state.getMap()[state.getyPos()+2][state.getxPos()];

				// if next position is a floor
				if(nextPosition.equals(FLOOR_NO_STORAGE) || nextPosition.equals(FLOOR_WITH_STORAGE)){
					// change current position to a floor tile
					if (currentPosition.equals(KEEPER_OFF_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos(), FLOOR_NO_STORAGE);
					} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos(), FLOOR_WITH_STORAGE);
					}

					// change next position to a keeper tile
					if (nextPosition.equals(FLOOR_NO_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos()+1, KEEPER_OFF_STORAGE);
					} else if (nextPosition.equals(FLOOR_WITH_STORAGE)){
						state.setMap(state.getxPos(), state.getyPos()+1, KEEPER_ON_STORAGE);
					}
			
					// update y-position of keeper		
					state.setyPos(MOVE_DOWN);
					state.setAction("DOWN");
				
				// if next position is a box
				} else if (nextPosition.equals(BOX_OFF_STORAGE) || nextPosition.equals(BOX_ON_STORAGE)){
					// if after the next position is a floor
					if (afterNextPosition.equals(FLOOR_NO_STORAGE) || afterNextPosition.equals(FLOOR_WITH_STORAGE)){
						// change current position to a floor tile
						if (currentPosition.equals(KEEPER_OFF_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos(), FLOOR_NO_STORAGE);
						} else if (currentPosition.equals(KEEPER_ON_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos(), FLOOR_WITH_STORAGE);
						}

						// change next position to a keeper tile
						if (nextPosition.equals(BOX_OFF_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos()+1, KEEPER_OFF_STORAGE);
						} else if (nextPosition.equals(BOX_ON_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos()+1, KEEPER_ON_STORAGE);
						}

						// change after the next position to a box tile
						if (afterNextPosition.equals(FLOOR_NO_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos()+2, BOX_OFF_STORAGE);
						} else if (afterNextPosition.equals(FLOOR_WITH_STORAGE)){
							state.setMap(state.getxPos(), state.getyPos()+2, BOX_ON_STORAGE);
						}

						// update y-position of keeper
						state.setyPos(MOVE_DOWN);
						state.setAction("DOWN");	
					}

					// else if after next position is a wall or a box, do nothing
				}

				break;
		}

		return state;
	}

	public boolean checkWinCondition(State currentState) {
		int storageCount = 0;
		
		for(int y = 0; y < 9; y++){
			for(int x = 0; x < 9; x++){
				if (currentState.getMap()[y][x].equals(FLOOR_WITH_STORAGE) ||
					currentState.getMap()[y][x].equals(KEEPER_ON_STORAGE)
				) {
					// counts the number of storages without a box on it
					storageCount++;
				}
			}
		}

		// if no more storage space exists without a box, end game
		if (storageCount == 0){
			return true;
		} else {
			return false;
		}
	}

	public State solve() {
		// create the queue and add the initial state
		int iterations = 0;
		State currentState;
		this.frontier.offerLast(this.initialState);
		// initialize explored state
		HashSet<String> exploredStates = new HashSet<String>();
		
		while (this.frontier.peek() != null) {
			// get the head of the queue
			currentState = new State(this.frontier.poll());

			// check if current state is a win condition
			if (this.checkWinCondition(currentState)) {
				System.out.println("Solution found");

				// write to a file
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter("puzzle.out"));
					writer.write(currentState.getPath().get(1) + "\n");
					for (int nextPath = 2; nextPath < currentState.getPath().size(); nextPath++) {
						writer.append(currentState.getPath().get(nextPath) + "\n");
					}
					writer.close();
				} catch (IOException e) {
					System.err.println("Problem writing to puzzle.out");
				}

				return currentState;
			}
			else {
				// check all possible moves that can be made
				for (String action: findActions(currentState)) {
					// check if state is already explored
					if (!exploredStates.contains(Arrays.deepToString(
						this.nextState(currentState, action).getMap()
					))) {
						// System.out.println(action);
						// add to explored if not explored yet
						exploredStates.add(Arrays.deepToString(currentState.getMap()));
						// also to frontier
						this.frontier.offerLast(this.nextState(currentState,
						action));
					}
				}
			}
			iterations++;
			System.out.println("Number of Iterations: " + iterations);
		}

		return null;
	}
}