package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IO {
    public static Board readInput(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            int N = Integer.parseInt(firstLine[0]);
            int M = Integer.parseInt(firstLine[1]);
            int pieceCount = Integer.parseInt(br.readLine().trim());

            Board board = new Board(N, M);

            List<String> allLines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                allLines.add(line);
            }

            int startingLine = 0;

            if (allLines.size() > N) {
                for (int i = 0; i < allLines.size(); i++) {
                    if (allLines.get(i).contains("K")) {
                        if (i == 0) {
                            startingLine = 1;
                        } else if (i >= allLines.size() - 1) {
                            startingLine = allLines.size() - N;
                        }
                    }
                }
            }

            for (int i = 0; i < allLines.size(); i++) {
                String currentLine = allLines.get(i);

                for (int j = 0; j < currentLine.length(); j++) {
                    char c = currentLine.charAt(j);
                    if (c == '.') continue;

                    int relativeI = i - startingLine;
                    boolean insideMatrix = (relativeI >= 0 && relativeI < N && j < M);

                    if (c == 'K') {
                        Coords coord = new Coords(relativeI, j);
                        board.setGoal(coord);
                    } else if (insideMatrix) {
                        Coords coord = new Coords(relativeI, j);
                        if (!board.getPieces().containsKey(c)) {
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
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}