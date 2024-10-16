package battle;

import droid.Droid;
import droid.Medic;
import droid.Warrior;
import arena.Arena;
import design.Color;
import fileManager.FileManager;
import java.util.ArrayList;
import java.util.List;

public class TeamBattle {
    protected List<String> log = new ArrayList<>();
    public void startBattle(List<Droid> team1, List<Droid> team2, Arena arena) {
        log.clear();
        log.add("==================================");
        log.add(Color.YELLOW_BOLD + "Бій починається!" + Color.RESET);
        log.add("==================================");
        System.out.println("==================================");
        System.out.println(Color.YELLOW_BOLD + "Бій починається!" + Color.RESET);
        System.out.println("==================================");
        int roundCount = 0;
        while (!team1.isEmpty() && !team2.isEmpty() && roundCount < 100) {
            processTeamTurn(team1, team2, arena);
            displayTeamStatus(team1, team2);
            if (team2.isEmpty()) {
                log.add(Color.PURPLE_BOLD + "Команда 1 перемогла!" + Color.RESET);
                System.out.println(Color.PURPLE_BOLD + "Команда 1 перемогла!" + Color.RESET);
                break;
            }
            processTeamTurn(team2, team1, arena);
            displayTeamStatus(team1, team2);
            if (team1.isEmpty()) {
                log.add(Color.PURPLE_BOLD + "Команда 2 перемогла!" + Color.RESET);
                System.out.println(Color.PURPLE_BOLD + "Команда 2 перемогла!" + Color.RESET);
                break;
            }
            roundCount++;
        }
        if (roundCount >= 100) {
            log.add(Color.YELLOW_BOLD + "Бій закінчується нічиєю!" + Color.RESET);
            System.out.println(Color.YELLOW_BOLD + "Бій закінчується нічиєю!" + Color.RESET);
        }
        FileManager.logBattle(true, log);
    }

    protected void processTeamTurn(List<Droid> attackerTeam, List<Droid> defenderTeam, Arena arena) {
        for (Droid attacker : attackerTeam) {
            if (attacker.getHealth() > 0) {
                Droid defender = getWarriorFromTeam(defenderTeam);
                if (defender == null) {
                    defender = defenderTeam.get(0);
                }
                if (attacker.isWarrior() && !((Warrior) attacker).hasUsedShield() && attackerTeam.size() > 1) {
                    ((Warrior) attacker).activateShield();
                }
                if (attacker.isMedic() && attackerTeam.size() > 1) {
                    Medic medic = (Medic) attacker;
                    if (!medic.hasHealed()) {
                        for (Droid teammate : attackerTeam) {
                            if (teammate.getHealth() < 90) {
                                heal(medic, teammate);
                                medic.setHasHealed(true);
                                break;
                            }
                        }
                    }
                } else if (attacker.getHealth() > 0) {
                    attack(attacker, defender, arena);
                    if (defender.getHealth() <= 0) {
                        log.add(Color.RED + defender.getName() + " був знищений!" + Color.RESET);
                        System.out.println(Color.RED + defender.getName() + " був знищений!" + Color.RESET);
                        defenderTeam.remove(defender);
                    }
                }
            }
        }
    }


    protected void attack(Droid attacker, Droid defender, Arena arena) {
        int baseDamage = attacker.getDamage();
        int damage = arena.calculateDamage(baseDamage);
        if (defender.isWarrior() && ((Warrior) defender).isShieldActive() && !((Warrior) defender).hasUsedShield()) {
            log.add(Color.PURPLE + defender.getName() + " відбиває атаку!" + Color.RESET);
            System.out.println(Color.PURPLE + defender.getName() + " відбиває атаку!" + Color.RESET);
            ((Warrior) defender).useShield();
            log.add(Color.BLUE + defender.getName() + " активує щит!" + Color.RESET);
            System.out.println(Color.BLUE + defender.getName() + " активує щит!" + Color.RESET);
        } else {
            log.add(attacker.getName() + " атакує " + defender.getName() + " і завдає " + damage + " шкоди.");
            System.out.println(attacker.getName() + " атакує " + defender.getName() + " і завдає " + damage + " шкоди.");
            defender.takeDamage(damage);
            if (defender.getHealth() < 0) {
                defender.setHealth(0);
            }
            log.add(defender.getName() + ": " + defender.getHealth() + " HP залишилось.");
            System.out.println(defender.getName() + ": " + defender.getHealth() + " HP залишилось.");
        }
    }

    protected void heal(Medic medic, Droid target) {
        target.setHealth(target.getHealth() + 20);
        log.add(Color.CYAN + medic.getName() + " лікує " + target.getName() + " на 20 HP." + Color.RESET);
        System.out.println(Color.CYAN + medic.getName() + " лікує " + target.getName() + " на 20 HP." + Color.RESET);
    }

    protected void displayTeamStatus(List<Droid> team1, List<Droid> team2) {
        log.add(Color.YELLOW_BOLD + "Статус команд після раунду:" + Color.RESET);
        System.out.println(Color.YELLOW_BOLD + "Статус команд після раунду:" + Color.RESET);
        displayTeam(team1, Color.GREEN_BOLD);
        displayTeam(team2, Color.RED_BOLD);
        log.add("==================================");
        System.out.println("==================================");
    }

    private void displayTeam(List<Droid> team, String color) {
        for (Droid droid : team) {
            log.add(color + droid.getName() + ": " + droid.getHealth() + " HP" + Color.RESET);
            System.out.println(color + droid.getName() + ": " + droid.getHealth() + " HP" + Color.RESET);
        }
    }

    private Droid getWarriorFromTeam(List<Droid> team) {
        for (Droid droid : team) {
            if (droid.isWarrior()) {
                return droid;
            }
        }
        return null;
    }

    public List<String> getLog() {
        return log;
    }
}
