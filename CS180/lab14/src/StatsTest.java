import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by bomal_000 on 4/16/2016.
 */
public class StatsTest {

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        try {
            map = Stats.playerWins("lab14inputfile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collection<Integer> collect = map.values();

        for (Integer i : collect) {
            System.out.print(i + " ");
        }

        System.out.println("\n" + Stats.winner(map));
    }
}
