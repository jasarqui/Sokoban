import java.util.ArrayList;

// state class to easily store position of player and path
public class State {
    private int xPos;
    private int yPos;
    private String[][] map = new String[10][10];
    private ArrayList<String> path;

    public State(int xPos, int yPos, String[][] map, String action) {
        this.xPos = xPos;
        this.yPos = yPos;
        
        // map
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                this.map[y][x] = map[y][x];
            }
        }

        this.path = new ArrayList<String>();
        this.path.add(action);
    }

    public State(State newState) {
        this.xPos = newState.getxPos();
        this.yPos = newState.getyPos();
        
        // get the map
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                this.map[y][x] = newState.getMap()[y][x];
            }
        }

        this.path = new ArrayList<String>(newState.getPath());
    }

    // Setters
    // sets the direction that lead to here
    public void setAction (String action) {
        this.path.add(action);
    }

    // sets the x position of the player
    public void setxPos (int xPos) {
        this.xPos = this.xPos + (xPos);
    }

    // sets the y position of the player
    public void setyPos (int yPos) {
        this.yPos = this.yPos + (yPos);
    }

    // sets the current state of the map
    public void setMap (int xPos, int yPos, String update) {
        this.map[yPos][xPos] = update;
    }

    // initializes the map
    public void initMap (String[][] map) {
        this.map = map;
    }

    // Getters
    // get the xPos
    public int getxPos () {
        return this.xPos;
    }

    // get the yPos
    public int getyPos () {
        return this.yPos;
    }

    // get the current map
    public String[][] getMap () {
        return this.map;
    }

    // get the previous path
    public String getPrevAction () {
        return this.path.get(this.path.size()-1);
    }

    // get the path
    public ArrayList<String> getPath () {
        return this.path;
    }
}