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
			display.ShowGrid(cx.GetGrid());  
			
			while(true) {
				char disc_type;

				if (cx.GetCurrPlayer().GetDiscType() == Disc.O_DISC)
					disc_type = 'O';
				else
					disc_type = 'X';
				
				// prompt input
				int col;
				while (true) {
					System.out.printf("Player %c, Select column to insert (%d-%d): ", disc_type , 1, ConnectX.DEFAULT_WIDTH);
					col = input.nextInt();
					if (col >= 1 && col <= ConnectX.DEFAULT_WIDTH)
						break;
					System.out.println("Error: Invalid input");
				}
			
				cx.Insert(col -1);

				if (cx.HasRoundWinner())
					break;
			}
			display.ShowRound(cx.GetCurrRound());
			cx.SwitchPlayer();
		}
		cx.NextRound();
	}
}