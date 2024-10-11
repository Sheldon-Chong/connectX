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

		cx.SetCurrPlayer(0);

		display.ScoreBoard(cx.GetAllPlayers()[0], cx.GetAllPlayers()[1]);
		while(cx.GetCurrRound() < ConnectX.TOTAL_ROUNDS) {
			
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
				
				System.out.println("\n test::::: " + cx.NumAvailBlock());

				if (cx.HasRoundWinner()) {
					System.out.println("TESTING");
					break;
				}
				cx.SwitchPlayer();
			}
			display.ShowRound(cx.GetCurrRound());
		}
		cx.NextRound();
	}
}