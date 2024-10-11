public class Displayer {
  //======================= CONSTRUCTOR =======================//
  public Displayer() {
  }
  
  //====================== PRIVATE METHOD ======================//
  // (Put your private methods here) */
  
  
  //====================== PUBLIC METHOD =======================//
  /* Displays the game title screen */
  public void GameHeader() {
    String div = "==============================\n";
    System.out.println(div + "|        CONNECT FOUR        |\n" + div);
  }
  
  /* Display the player's score on the screen 
   * @param x - Object that represents player x  
   * @param o - Object that represents player o */
  public void ScoreBoard(Player o, Player x) {
    System.out.println("CURRENT SCORE------------------");
    System.out.printf("PLAYER O: %d points\n", o.GetScore());
    System.out.printf("PLAYER X: %d points\n", x.GetScore());
    System.out.println("--------------------------------");
  }
  
  /* Display the current round number
   * @param roundNum - The current round's number */
  public void ShowRound(int roundNum ) {
    System.out.printf("*************** ROUND %d ***************\n", roundNum);
  }
  
  /* Takes in a 2D array disc array and displays it on 
   * the screen, in a nice grid form.
   * @param grid - The 2D array that contains Disc objects */
  public void ShowGrid(Disc[][] grid) {
    String indent = "    ";
    ShowRound(1);
    String rowDiv = "-----------------------------";
    for(int y =0; y < grid.length; y ++) {
      System.out.println(indent+ rowDiv);
      System.out.print(indent);
      
      for(int i = 0; i < grid[y].length; i ++) {
        char tile;
        if (grid[y][i] == null)
          tile = ' ';
        else if (grid[y][i].GetDiscType() == Disc.O_DISC)
          tile = 'O';
        else
          tile = 'X';
        System.out.printf("| %c ", tile);
      }
      System.out.println("|");
    }
    System.out.println(indent + rowDiv);
    System.out.println("COL:  1   2   3   4   5   6   7");
  }
}