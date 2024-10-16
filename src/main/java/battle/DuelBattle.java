package battle;

import droid.Droid;
import arena.Arena;
import java.util.ArrayList;
import java.util.List;

public class DuelBattle extends TeamBattle {
    public void startDuel(Droid droid1, Droid droid2, Arena arena) {
        List<Droid> team1 = new ArrayList<>();
        team1.add(droid1);
        List<Droid> team2 = new ArrayList<>();
        team2.add(droid2);
        startBattle(team1, team2, arena);
    }
}
