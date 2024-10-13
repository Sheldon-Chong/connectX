import java.util.Scanner;

public class ConnectXMain {
	/* The start of the game */
	public static void main(String[] args) {
		ConnectX cx			= new ConnectX();;                          // Creates the ConnectFour object (DO NOT DELETE IT)
		Scanner input		= new Scanner(System.in);  // Scanner object to get user input
		Displayer display	= new Displayer();  

		display.GameHeader();
		
		System.out.print("Number of Disc to Win (2-6)? ");
		int numConnect;
		
		while (true) {
			numConnect = input.nextInt();
			if (numConnect >= 2 && numConnect <= 6)
				break;
			System.out.print("Invalid input. Please enter a number between 2 and 6: ");
		}

		
		display.ScoreBoard(cx.GetAllPlayers()[0], cx.GetAllPlayers()[1]);
		while(cx.GetCurrRound() < ConnectX.TOTAL_ROUNDS) {
			display.ShowRound(cx.GetCurrRound());
			cx.SetCurrPlayer(0);
			
			cx.ResetGrid();
			while(true) {
				display.ShowGrid(cx.GetGrid());  
				char disc_type;

				// prompt input
				disc_type = cx.GetCurrPlayerChar();
				int col;
				int status;
				while(true) {
					System.out.printf("Player %c, Select column to insert (%d-%d): ", disc_type , 1, ConnectX.DEFAULT_WIDTH);
					col = input.nextInt();
					status = cx.Insert(col -1);
					if (status == ConnectX.SUCCESS)
						break;
				   	else if (status == ConnectX.ERR_COLUMN_FULL)
					   System.out.println("Error: Column is full");
					else if (status == ConnectX.ERR_INVALID_COLUMN)
						System.out.println("Error: Invalid column");
				}
				
				if (cx.HasRoundWinner()) {
					display.ShowGrid(cx.GetGrid());

					System.out.printf("Round %d winner is player %c\n", cx.GetCurrRound(), disc_type);
					System.out.printf("%d will get %d points\n", cx.GetCurrRound(), numConnect);
					
					System.out.print("Click Enter to continue to next round.\n");

					// clear buffer
					input.nextLine();

					// wait for user to press enter
					input.nextLine();

					// proceed to the next round
					cx.NextRound();
					break;
				}
				cx.SwitchPlayer();
			}
			
		}
	}
}