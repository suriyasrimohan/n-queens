import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rows, columns;
        if (args.length == 2) {
            rows = Integer.parseInt(args[0]);
            columns = Integer.parseInt(args[1]);
        } else {
            Scanner userInput = new Scanner(System.in);
            System.out.println("\nPlease enter the dimensions of the board.");
            System.out.print("Rows: ");
            rows = userInput.nextInt();
            System.out.print("Columns: ");
            columns = userInput.nextInt();
            userInput.close();
        }

        Chessboard chessBoard = new Chessboard(rows, columns);

        System.out.println("\n===========\nSTART SOLVE\n===========\n\n[ Please Wait -- May Take A While! ]");

        chessBoard.solve(0);

        int numOfSolutions = chessBoard.getNumOfSolutions();
        System.out.printf(
            "\nNumber of Solutions for queen placements on a chessboard of size %d by %d is: %d\n",
            rows, columns, numOfSolutions);

        if (numOfSolutions > 0) {
            System.out.println("\n🎉 Victory! Solutions found! 🎉");
        } else {
            System.out.println("\nNo solutions found.");
        }
    }
}
