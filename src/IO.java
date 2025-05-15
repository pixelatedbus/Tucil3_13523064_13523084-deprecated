package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IO {
    public static void readInput(String filename){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String[] firstLine = br.readLine().split(" ");
            int N = Integer.parseInt(firstLine[0]);
            int M = Integer.parseInt(firstLine[1]);
            String[] secondLine = br.readLine().split(" ");
            int pieceCount = Integer.parseInt(secondLine[0]);

        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
