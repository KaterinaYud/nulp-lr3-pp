package main;

import droid.Droid;
import droid.Medic;
import droid.Warrior;
import arena.Arena;
import design.Color;
import arena.WaterArena;
import arena.FireArena;
import fileManager.FileManager;
import battle.DuelBattle;
import battle.TeamBattle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<Droid> droids = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean battleWas = false;
    static TeamBattle teamBattle = new TeamBattle();
    public static void main(String[] args) {
        firstDroids();
        Color.menu();
        while (true) {
            System.out.println(Color.BLUE_BOLD + "***ЗРОБІТЬ ВИБІР***" + Color.RESET);
            System.out.println("1. Створити дроїда.");
            System.out.println("2. Показати список дроїдів.");
            System.out.println("3. Почати дуель (1 на 1).");
            System.out.println("4. Почати бій команда на команду.");
            System.out.println("5. Записати бій у файл.");
            System.out.println("6. Зчитати інформацію з файлу.");
            System.out.println("7. Видалити дроїда.");
            System.out.println("8. Вихід програми.");
            System.out.print("Тисни --> ");
            int choice = getValidIntInput();
            switch (choice) {
                case 1:
                    createDroid();
                    break;
                case 2:
                    showDroids();
                    break;
                case 3:
                    startDuel();
                    break;
                case 4:
                    startTeamBattle();
                    break;
                case 5:
                    if (battleWas) {
                        List<String> log = teamBattle.getLog();
                        FileManager.logBattle(battleWas, log);
                    } else {
                        FileManager.logBattle(false, new ArrayList<>());
                    }
                    break;
                case 6:
                    FileManager.readBattle();
                    break;
                case 7:
                    removeDroid();
                    break;
                case 8:
                    System.exit(0);
                default:
                    System.out.println("Неправильний вибір, спробуйте ще раз.");
            }
            System.out.println("==================================\n\n");
        }
    }

    private static void firstDroids() {
        droids.add(new Warrior("Хром", 100, 30));
        droids.add(new Medic("Ультра", 100, 15));
        droids.add(new Warrior("Ангел", 100, 30));
        droids.add(new Medic("Sky", 100, 15));
        droids.add(new Medic("Онта", 100, 15));
        droids.add(new Warrior("Umma", 100, 30));
        droids.add(new Medic("Інніта", 100, 15));
        droids.add(new Warrior("Рукт", 100, 30));
        droids.add(new Medic("Ельф", 100, 15));
    }

    private static void createDroid() {
        int type;
        do {
            System.out.println("Виберіть тип дроїда:");
            System.out.println("1. Warrior Droid");
            System.out.println("2. Medic Droid");
            type = getValidIntInput();
            if (type != 1 && type != 2) {
                System.out.println("Неправильний тип дроїда. Спробуйте ще раз.");
            }
        } while (type != 1 && type != 2);
        System.out.println("Введіть ім'я для дроїда:");
        String name = scanner.next();
        int health = 100;
        int damage = type == 1 ? 30 : 15;
        if (type == 1) {
            droids.add(new Warrior(name, health, damage));
        } else {
            droids.add(new Medic(name, health, damage));
        }
    }

    private static void showDroids() {
        if (droids.isEmpty()) {
            System.out.println(Color.RED_BOLD + "Список порожній." + Color.RESET);
        } else {
            System.out.println(Color.GREEN_BOLD + "Список дроїдів:" + Color.RESET);
            int index = 1;
            for (Droid droid : droids) {
                String color = droid.isMedic() ? Color.CYAN : Color.RED;
                System.out.println(color + index + ". " + droid.getName() + " - Здоров'я: " + droid.getHealth() + ", пошкодження: " + droid.getDamage() + Color.RESET);
                index++;
            }
        }
    }

    private static Arena chooseArena(List<Droid> battleDroids) {
        int arenaChoice;
        do {
            System.out.println("Оберіть арену:");
            System.out.println("1. Вода");
            System.out.println("2. Вогонь");
            arenaChoice = getValidIntInput();
            if (arenaChoice != 1 && arenaChoice != 2) {
                System.out.println("Некоректний вибір арени. Спробуйте ще раз.");
            }
        } while (arenaChoice != 1 && arenaChoice != 2);
        int arenaSize;
        do {
            System.out.println("Введіть розмір арени:");
            arenaSize = getValidIntInput();
            if (arenaSize <= 0) {
                System.out.println("Некоректний розмір арени. Спробуйте ще раз.");
            }
        } while (arenaSize <= 0);
        Arena arena = (arenaChoice == 1) ? new WaterArena(arenaSize, new ArrayList<>(), 1) : new FireArena(arenaSize, new ArrayList<>(), 2);
        List<String> startPoints = arena.getStartPoints(arenaSize, battleDroids);
        arena.setStartPoints(startPoints);
        arena.displayArena(battleDroids);
        return arena;
    }

    private static void startDuel() {
        showDroids();
        System.out.println("Оберіть двох дроїдів для дуелі:");
        int droid1Index = getValidDroidIndex();
        int droid2Index;
        do {
            droid2Index = getValidDroidIndex();
            if (droid2Index == droid1Index) {
                System.out.println("Цей дроїд вже вибрано. Спробуйте ще раз.");
            }
        } while (droid2Index == droid1Index);
        List<Droid> duelDroids = List.of(droids.get(droid1Index), droids.get(droid2Index));
        Arena arena = chooseArena(duelDroids);
        DuelBattle duelBattle = new DuelBattle();
        duelBattle.startDuel(duelDroids.get(0), duelDroids.get(1), arena);
        battleWas = true;
    }

    private static void startTeamBattle() {
        showDroids();
        System.out.println("Скільки дроїдів буде в кожній команді?");
        int teamSize = getValidIntInput();
        while (teamSize <= 0 || teamSize > droids.size() / 2) {
            if (teamSize <= 0) {
                System.out.println("Некоректна кількість дроїдів. Спробуйте ще раз.");
            } else {
                System.out.println("Кількість дроїдів перевищує допустимі значення. Спробуйте ще раз");
            }
            teamSize = getValidIntInput();
        }

        System.out.println("Оберіть дроїдів для команди 1:");
        List<Droid> team1 = selectTeam(teamSize, new ArrayList<>());
        System.out.println("Оберіть дроїдів для команди 2:");
        List<Droid> team2 = selectTeam(teamSize, team1);
        List<Droid> BattleDroids = new ArrayList<>(team1);
        BattleDroids.addAll(team2);
        Arena arena = chooseArena(BattleDroids);
        TeamBattle teamBattle = new TeamBattle();
        teamBattle.startBattle(team1, team2, arena);
        battleWas = true;
    }

    private static List<Droid> selectTeam(int teamSize, List<Droid> alreadySelectedDroids) {
        List<Droid> team = new ArrayList<>();
        while (team.size() < teamSize) {
            System.out.println("Доступні дроїди:");
            for (int j = 0; j < droids.size(); j++) {
                Droid currentDroid = droids.get(j);
                if (!alreadySelectedDroids.contains(currentDroid) && !team.contains(currentDroid)) {
                    System.out.println((j + 1) + ": " + currentDroid.getName());
                }
            }
            int droidIndex = getValidDroidIndex(new ArrayList<>(team), droids);
            Droid selectedDroid = droids.get(droidIndex);
            if (!alreadySelectedDroids.contains(selectedDroid)) {
                team.add(selectedDroid);
            } else {
                System.out.println("Цей дроїд вже обраний в іншій команді! Оберіть іншого.");
            }
        }
        return team;
    }


    private static int getValidDroidIndex() {
        return getValidDroidIndex(new ArrayList<>(), droids);
    }

    private static int getValidDroidIndex(List<Droid> selectedDroids, List<Droid> availableDroids) {
        int index;
        while (true) {
            index = getValidIntInput();
            if (index < 1 || index > availableDroids.size()) {
                System.out.println("Некоректний індекс. Спробуйте ще раз.");
            } else if (selectedDroids.contains(availableDroids.get(index - 1))) {
                System.out.println("Цей дроїд вже обрано. Спробуйте ще раз.");
            } else {
                return index - 1;
            }
        }
    }

    private static void removeDroid() {
        showDroids();
        System.out.println("Введіть індекс для видалення:");
        int indexToRemove = getValidDroidIndex(new ArrayList<>(), droids) + 1;
        droids.remove(indexToRemove - 1);
        System.out.println("Дроїд видалений.");
    }

    private static int getValidIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Некоректне введення. Спробуйте ще раз.");
            }
        }
    }
}
