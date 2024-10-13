import java.util.Scanner;

public class ConnectXMain {
    /* The start of the game */
    public static void main(String[] args) {
        Scanner input		= new Scanner(System.in);  // Scanner object to get user input
        Displayer display	= new Displayer();  

        display.GameHeader();
        
        // GET NUMBER OF DISC TO CONNECT
        System.out.print("Number of Disc to Win (2-6)? ");
        int numConnect;
        
        // loop until user enters a valid number
        while (true) {
            numConnect = input.nextInt();
            
            if (numConnect >= 2 && numConnect <= 6)
                // if number is valid
                break;
            else
                // if number outside the valid range
                System.out.print("Invalid input. Please enter a number between 2 and 6: ");
        }

        ConnectX cx = new ConnectX(numConnect); // Creates the ConnectFour object (DO NOT DELETE IT)
        
        // loop until all rounds are played
        while(cx.GetCurrRound() < ConnectX.TOTAL_ROUNDS) {
            // display score board
            display.ScoreBoard(cx.GetAllPlayers()[0], cx.GetAllPlayers()[1]);
            
            // display round number
            display.ShowRound(cx.GetCurrRound());

            // set current player to player O
            cx.SetCurrPlayer(0);
            
            // clear the board
            cx.ResetGrid();
            
            // loop until round winner is found
            while(true) {
                // display grid
                display.ShowGrid(cx.GetGrid());  

                // get current player's disc type
                char disc_type = cx.GetCurrPlayerChar();

                // loop until user inserts disc successfully
                while(true) {
                    // prompt user to select column to insert disc
                    System.out.printf("Player %c, Select column to insert (%d-%d): ", disc_type , 1, ConnectX.DEFAULT_WIDTH);
                    int col = input.nextInt();

                    // insert disc
                    int status = cx.Insert(col -1);

                    // error handling
                    if (status == ConnectX.SUCCESS)
                        // if disc is inserted successfully
                        break;
                    else if (status == ConnectX.ERR_COLUMN_FULL)
                        // if column is full
                       System.out.println("Error: Column is full");
                    else if (status == ConnectX.ERR_INVALID_COLUMN)
                        // if column is invalid (out of bounds)
                        System.out.println("Error: Invalid column");
                }
                
                if (cx.HasRoundWinner()) {
                    // display grid
                    display.ShowGrid(cx.GetGrid());

                    // display round winner
                    System.out.printf("Round %d winner is player %c\n", cx.GetCurrRound(), disc_type);
                    System.out.printf("%c will get %d points\n", cx.GetCurrPlayerChar(), cx.NumAvailBlock());
                    System.out.print("Click Enter to continue to next round.\n");
                    
                    // add score to current player
                    cx.GetCurrPlayer().AddScore(cx.NumAvailBlock());

                    // clear buffer
                    // wait for user to press enter
                    input.nextLine();
                    input.nextLine();

                    break;
                }
                else if (cx.IsGridFull()) {
                    // announce tie
                    System.out.printf("Round %d is a tie. Grid is full\n", cx.GetCurrRound());
                    
                    // clear buffer
                    // wait for user to press enter
                    input.nextLine();
                    input.nextLine();

                    break;
                }

                cx.SwitchPlayer();
            }
            
            // proceed to the next round
            cx.NextRound();
        }

        // display final score
        display.ScoreBoard(cx.GetAllPlayers()[0], cx.GetAllPlayers()[1]);
        
        // display final winner
        if (cx.HasGameWinner() == -3)
            System.out.println("Game is a tie");
        else {
            int discType = cx.GetAllPlayers()[cx.HasGameWinner()].GetDiscType();
            char disc;
            if (discType == Disc.O_DISC)
                disc = 'O';
            else
                disc = 'X';
            System.out.printf("The final winner is Player %c\n", disc);
        }
    }
}