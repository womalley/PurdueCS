import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;



/**
 * Created by bomal_000 on 4/16/2016.
 */
public class Stats {

    static HashMap<String, Integer> playerWins(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader inFile = new FileReader(file);
        BufferedReader read = new BufferedReader(inFile);
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        try {
            while (true) {
                String line = read.readLine();
                String[] playerList = line.split(" ");

                if (playerList[0].equals("0")) {
                    int i;

                    for (i = 1; i < 6; i++) {
                        Integer k = map.get(playerList[i]);

                        if (k == null) {
                            map.put(playerList[i], 1);
                        }
                        else {
                            map.put(playerList[i], ++k);
                        }
                    }
                }
                else if (playerList[0].equals("1")) {
                    int i;

                    for (i = 7; i < 12; i++) {
                        Integer k = map.get(playerList[i]);

                        if (k == null) {
                            map.put(playerList[i], 1);
                        }
                        else {
                            map.put(playerList[i], ++k);
                        }
                    }
                }
                else {
                    read.close();
                    return map;
                }
            }
        } catch (Exception e) {
            System.out.println("End of the file.");
            read.close();
            return map;
        }
    }

    static String winner(HashMap<String, Integer>map) {

        Collection<Integer> collect = map.values();
        int numWon = 0;

        for (Integer i : collect) {
            if (numWon < i) {
                numWon = i;
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == numWon) {
                return (entry.getKey());
            }
        }
        return ("ERROR!");
    }
}
