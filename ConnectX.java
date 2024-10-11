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
  public final static int SUCCESS            = 1;

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
    this.numConnect = numConnect;
  }
  
  //====================== PRIVATE METHOD =======================//
  /* (Put your private methods here) */
  
  
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
    for (int y = 0; y < this.grid.length; y ++) {
      for (int x = 0; x < this.grid[y].length; x++) {
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
      this.currPlayer = this.allPlayers[1];
    else
      this.currPlayer = this.allPlayers[0];
  }  
  
  /* Check if the grid is full (Every block has a disc object)
   * @return - Returns true, if the grid is full.
   *           Returns false, if the grid is not full. */    
  public boolean IsGridFull() {
    boolean isFull = true;
    for (int y = 0; y < this.grid.length; y ++) {
      for (int x = 0; x < this.grid[y].length; x++) {
        if (this.grid[y][x] == null){
          isFull = false;
          break;
        }
      }
    }
    return isFull;
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

    if (col < 0 || col >= this.grid.length)
      return -2;

    if (this.grid[0][col] != null)
      return -1;
    for (int y = this.grid.length - 1; y >= 0; y--) {
      if (this.grid[y][col] == null) {
        this.grid[y][col] = new Disc(this.currPlayer.GetDiscType());
        return 1;
      }
    }
    return -1;
  }   
  
  /* Get the number of available (empty) blocks in the grid
   * @return - The number of empty blocks */
  public int NumAvailBlock() {
    int emptyBlocksCount = 0;
    for (int y = 0; y < this.grid.length; y++) {
      for (int x = 0; x < this.grid[y].length; x++) {
        if (this.grid[y][x] == null)
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

  
  private boolean checkLine( int posX, int posY, int incX, int incY ) {
    /*
    to make algo efficient:
    1. 
    */
    int cursorX = posX;
    int cursorY = posY;
    
    boolean found = true;
    for (int i = 0; i < 4; i++) {
      
      Disc currentDisc = this.grid[cursorY][cursorX];

      if ((currentDisc == null) ||
          (currentDisc.GetDiscType() != Disc.O_DISC))
        found = false;

      cursorX += incX;
      cursorY += incY;
    }
    return found;
  }

  /* Check if there is a winner for the current round 
   * @return - Returns true, if there is a winner.
   *           Returns false, if there is no winner */      
  public boolean HasRoundWinner() {
    return checkLine(0, this.grid.length - 1, 1,0);
  }
  
  /* Determine the final winner by checking the player's score. 
   * @return - Returns the array position value that represents the player who wins.
   *           Returns -3,if there's a tie (same score) */
  public int HasGameWinner() {
    return null;  // Dummy return value
  }
}