/* The game that keeps track of all the information, such as
 * the grid and which player's turn */
public class ConnectX {
    public final static int DEFAULT_HEIGHT =  6;    // The default height of the grid (DO NOT CHANGE)
    public final static int DEFAULT_WIDTH  =  7;    // The default width of the grid (DO NOT CHANGE)
    public final static int TOTAL_ROUNDS   =  4;    // The total round of games (DO NOT CHANGE)
    
    private Disc[][] grid;                          // The grid of the game. A 2D array of Disc objects
    private Player[] allPlayers;                    // An array that stores all the player's object
    private Player currPlayer;                      // Keeps track of the current player
    private int currRound;                          // Keep track of the current round
    private int numConnect;                         // The number of connected disc to win.
    
    public final static int ERR_COLUMN_FULL    = -1;
    public final static int ERR_INVALID_COLUMN = -2;
    public final static int ERR_TIE            = 1;
    public final static int SUCCESS            = 1;

    private final static int NORTH = 1;
    private final static int SOUTH = -1;
    private final static int EAST = 1;
    private final static int WEST = -1;

    private final static int PLAYER_O = 0;
    private final static int PLAYER_X = 1;

    //======================= CONSTRUCTOR =======================//
    /* Initialize the game's instance variables. */
    public ConnectX() {
        // Instantiate the grid when ConnectX object is constructed. 
        // Default number connected disc to win is 4.
        grid = new Disc[DEFAULT_HEIGHT][DEFAULT_WIDTH];
        this.numConnect = 4;
        this.allPlayers = new Player[2];
        this.allPlayers[0] = new Player(Disc.O_DISC);
        this.allPlayers[1] = new Player(Disc.X_DISC);
        this.currRound = 1;
    }
    
    /* Initialize the game's instance variables. 
     * @param numConnect   - Number of connected disc to win. */
    public ConnectX(int numConnect) {
        this();
        this.numConnect = numConnect;
    }
    
    //====================== PRIVATE METHOD =======================//
    /* (Put your private methods here) */
        /* Check if a specified position is out of bounds in the array
     * @param x - The x position in the grid
     * @param y - The y position in the grid
     * @return  - Returns true, if the position is out of bounds.
     *            Returns false, if the position is within bounds.
     */
    private boolean isExceedBounds(int x, int y) {
        if ((x > DEFAULT_WIDTH - 1) || (x < 0)
        || (y > DEFAULT_HEIGHT - 1) || (y < 0))
            return true;
        return false;
    }

    /* Get the disc type in the specified position
     * @param x - The x position in the grid
     * @param y - The y position in the grid
     * @return  - Returns the disc type in the specified position.
     *            Returns -1, if the position is out of bounds.
     *            Returns -2, if the position is empty.
     */
    private int getDisc(int x, int y) {
        if (this.isExceedBounds(x, y))
            return -1;
        if (this.grid[y][x] == null)
            return -2;
        return this.grid[y][x].GetDiscType();
    }

    /* Get the player object that corresponds to the disc type
     * @param discType - The type of disc that is being created (Either O or X)
     * @return         - The player object that corresponds to the disc type
     */
    private Player getPlayer(int discType) {
        if (this.allPlayers[0].GetDiscType() == discType)
            // if the disc type is 0, then get the player at position 0
            return this.allPlayers[0];
        else
            // if the disc type is 1, then get the player at position 1
            return this.allPlayers[1];
    }
    
    /* Check if there is a line of connected disc in the specified direction
     * @param posX      - The x position in the grid to start the line check
     * @param posY      - The y position in the grid to start the line check
     * @param incX      - To what degree the x position should be incrimented
     * @param incY      - To what degree the y position should be incrimented
     * @param checkDist - The amount of cells to check within the table for connected disc
     * @return          - Returns true, if there is a line of connected disc.
     *                    Returns false, if there is no line of connected disc.
     */
    private boolean checkLine( int posX, int posY, // starting position to perform the line check
                               int incX, int incY, // to what degree the x and yshould be incrimented. This determines the direction of the line
                               int checkDist ) {
        
        // initialize the cursors to the starting position
        // cursors will be incrimented by the amount specified by incX and incY every iteration of the for loop, for as many times specified by checkDist
        int cursorX = posX;
        int cursorY = posY;

        // get the disc type of the starting position
        // the for loop will then search if there is a line of connected disc of the same type
        int discSearchType = getDisc(posX, posY);

        if (discSearchType < 0)
            // if the starting position is out of bounds or empty, return false
            return false;

        // check if there is a line of connected disc
        // the for loop will check for as many times as specified by checkDist
        for ( int i = 0; i < checkDist; i++ ) {
            // get the disc type of the current position
            int currentDisc = getDisc(cursorX, cursorY);

            if ((currentDisc < 0) || (currentDisc != discSearchType)) 
                // if the current position is out of bounds, empty or the disc type is different
                return false;

            // move the cursor to the next position
            cursorX += incX;
            cursorY += incY;
        }
        return true;
    }

    /* Check in 8 directions if there is a line of connected disc
     * @param x         - The x position in the grid
     * @param y         - The y position in the grid
     * @param checkDist - The amount of cells to check within the table for connected disc
     * @return          - Returns true, if there is a line of connected disc.
     */
    private boolean checkAllDirections(int x, int y, int checkDist) {
        // performs checkline in all 8 directions
        return (
            checkLine(x, y, 0,    NORTH, checkDist) || // North
            checkLine(x, y, 0,    SOUTH, checkDist) || // South
            checkLine(x, y, EAST, 0,     checkDist) || // East
            checkLine(x, y, WEST, 0,     checkDist) || // West
            
            checkLine(x, y, EAST, NORTH, checkDist) || // North east
            checkLine(x, y, WEST, NORTH, checkDist) || // North west
            checkLine(x, y, EAST, SOUTH, checkDist) || // South east
            checkLine(x, y, WEST, SOUTH, checkDist)    // South west
        );
    }

    //====================== PUBLIC METHOD =======================//
    /* Get the current round number. 
     * First round will be 1, second round will be 2, etc...
     * @return - The current round number. */
    public int GetCurrRound() {
        return this.currRound;
    }
    
    /* Increment the round number by 1. */
    public void NextRound() {
        this.currRound++;
    }
    
    /* Get the 2D array Disc array
     * @return - The 2D grid containing Disc objects */
    public Disc[][] GetGrid() {
        return this.grid;
    }
    
    /* Resets the grid so it contains no objects */
    public void ResetGrid() {
        // iterate for every row
        for (int y = 0; y < this.grid.length; y ++) {

            // iterate for every column
            for (int x = 0; x < this.grid[y].length; x++) {
                // set the current cell to empty
                this.grid[y][x] = null;
            }
        }
    }
    
    /* Get the player object array  
     * @return - The array that contains all the players' object */
    public Player[] GetAllPlayers() {
        return this.allPlayers;
    }
    
    /* Returns the current player object
     * @return - The current player's object */
    public Player GetCurrPlayer() {
        return this.currPlayer;
    }
    
    /* Set who will be the current player
     * @param index - The value that represents the player. This
     *                is based on the array position of where the 
     *                player is stored. */
    public void SetCurrPlayer(int index) {
        this.currPlayer = this.allPlayers[index];
    }  
    
    /* Switch player's turn to the next player (If current player is X, then it 
     * switches to O. If current player is O, then it switches to X) */   
    public void SwitchPlayer() {
        if (this.currPlayer.GetDiscType() == Disc.O_DISC)
            this.currPlayer = this.allPlayers[PLAYER_X];
        else
            this.currPlayer = this.allPlayers[PLAYER_O];
    }  
    
    /* Check if the grid is full (Every block has a disc object)
     * @return - Returns true, if the grid is full.
     *           Returns false, if the grid is not full. */    
    public boolean IsGridFull() {
        // Iterate for every row
        for (int y = 0; y < this.grid.length; y++) {

            // Iterate for every column
            for (int x = 0; x < this.grid[y].length; x++) {
                // if we encounter a cell that is empty, we can already assume that the grid is not full
                if (this.grid[y][x] == null)
                    return false;
            }
        }
        return true;
    }
    
    /* Inserts a disc object in the specified column.
     * @param col - The column that the player wants to insert the disc
     *              Note: col = 0 is the 1st column 
     *                    col = 1 is the 2nd column
     *                    etc...
     * @return    - Returns 1, if the disc is inserted successfully into the column.
     *              Returns -1, if the column is full. 
     *              Returns -2, if the column is invalid. */    
    public int Insert(int col) {

        if (col < 0 || col > this.grid.length)
            // if the specified column index is out of bounds
            return ERR_INVALID_COLUMN;

        if (this.grid[0][col] != null)
            // if the top cell is not empty, the column is full
            return ERR_COLUMN_FULL;
        
        for (int y = this.grid.length - 1; y >= 0; y--) {
            if (this.grid[y][col] == null) {
                // if the cell is empty, insert the disc
                this.grid[y][col] = new Disc(this.currPlayer.GetDiscType());
                return SUCCESS;
            }
        }

        return ERR_COLUMN_FULL;
    }   
    
    /* Get the number of available (empty) blocks in the grid
     * @return - The number of empty blocks */
    public int NumAvailBlock() {
        int emptyBlocksCount = 0;

        // iterate for every row
        for (int y = 0; y < this.grid.length; y++) {
            
            // iterate for every column
            for (int x = 0; x < this.grid[y].length; x++) {
                if (this.grid[y][x] == null)
                    // add to block count if the cell is empty
                    emptyBlocksCount++;
            }
        }

        return emptyBlocksCount;
    }   
    
    /* Get the current player's name (either X or O) as a char
     * @return - The player's name, either 'X' or 'O' */   
    public char GetCurrPlayerChar() {
        if (this.currPlayer.GetDiscType() == Disc.O_DISC)
            return 'O';
        else
            return 'X';
    }

    /* Check if there is a winner for the current round 
     * @return - Returns true, if there is a winner.
     *           Returns false, if there is no winner */      
    public boolean HasRoundWinner() {
        int checkDist = this.numConnect;
    
        // Iterate for every row
        for (int y = 0; y < this.grid.length; y++) {
            
            // Iterate for every column
            for (int x = 0; x < this.grid[y].length; x++) {
                if (checkAllDirections(x, y, checkDist))
                    // if there is a line of connected disc
                    return true;
            }
        }
    
        return false;
    }

    /* Determine the final winner by checking the player's score. 
     * @return - Returns the array position value that represents the player who wins.
     *           Returns -3,if there's a tie (same score) */
    public int HasGameWinner() {
        if (getPlayer(PLAYER_O).GetScore() > this.allPlayers[PLAYER_X].GetScore())
            // if player O has more points
            return PLAYER_O;
        else if (getPlayer(PLAYER_O).GetScore() < this.allPlayers[PLAYER_X].GetScore())
            // if player X has more points
            return PLAYER_X;
        else
            return ERR_TIE;
    }  
}