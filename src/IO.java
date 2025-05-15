package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IO {
    public static Board readInput(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            int N = Integer.parseInt(firstLine[0]);
            int M = Integer.parseInt(firstLine[1]);
            int pieceCount = Integer.parseInt(br.readLine().trim());

            Board board = new Board(N, M);

            for(int i = 0; i < N; i++){
                String line = br.readLine();
                for(int j = 0; j < M; j++){
                   char c = line.charAt(j);
                   if(c == '.') continue;

                   Coords coord = new Coords(i, j);

                   if(c == 'K'){
                       board.setGoal(coord);
                   } else {
                       if(!board.getPieces().containsKey(c)){
                           Piece piece = new Piece(c);
                           piece.addCoord(coord);
                           board.addPiece(piece);
                       } else {
                           board.getPieces().get(c).addCoord(coord);
                       }
                   }
                }
            }

            return board;

        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
