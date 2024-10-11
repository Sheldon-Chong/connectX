public class Player {
   private int discType;
   private int score;
  
  //======================= CONSTRUCTOR =======================//
  /* @discTpe - The type of disc that is being created (Either O or X) */
  public Player(int discType) {
    this.discType = discType;
  }
  
  //====================== PUBLIC METHOD =======================//
  /* Gets the player's disc type (0 or 1)
   * @return - The value of the disc type */
  public int GetDiscType() {
    return this.discType;
  }
  
  /* Gets the players score 
   * @return - The player's score */
  public int GetScore() {
    return this.score;
  }
  
  /* Add scores to the player's exiting score
   * @param score - The score that needs to be added
   *                to the player's score */
  public void AddScore(int score) {
    this.score += score;
  }
}