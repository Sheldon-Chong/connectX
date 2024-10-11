public class Disc {
  public final static int O_DISC = 0;   // Value that represents O Disc
  public final static int X_DISC = 1;   // Value that represents X Disc
  
  private int discType;                 // Keeps track of the current disc's type (X or O)
  
  //======================= CONSTRUCTOR =======================//
  /* @discTpe - The type of disc that is being created (Either X or O) */
  public Disc (int discType) {
    this.discType = discType;
  }
  
  //====================== PUBLIC METHOD =======================//
  /* Get the disc type from the disc object
   * @return - Return the type of disc. Values are based on 
   *           Global variables within Disc class */
  public int GetDiscType() {
    return this.discType;
  }
}