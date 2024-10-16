package arena;

import droid.Droid;
import design.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Arena {
    protected int size;
    protected List<String> startPoints;
    protected int arenaType;
    protected static final Scanner scanner = new Scanner(System.in);

    public Arena(int size, List<String> startPoints, int arenaType) {
        this.size = size;
        this.startPoints = startPoints;
        this.arenaType = arenaType;
    }

    public void displayArena(List<Droid> battleDroids) {
        char[][] grid = new char[size][size];
        int center = size / 2;
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '*';
            }
        }
        for (int i = 0; i < battleDroids.size(); i++) {
            String[] coordinates = startPoints.get(i).split(",");
            int x = Integer.parseInt(coordinates[0]) + center;
            int y = Integer.parseInt(coordinates[1]) + center;
            if (x >= 0 && x < size && y >= 0 && y < size) {
                if (grid[size - y - 1][x] == '*') {
                    if (i < battleDroids.size() / 2) {
                        grid[size - y - 1][x] = '1';
                    } else {
                        grid[size - y - 1][x] = '2';
                    }
                } else {
                    System.out.println("Координати вже зайняті. Спробуйте інші.");
                }
            } else {
                System.out.println("Дроїд " + i + " знаходиться поза межами арени.");
            }
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == '*') {
                    System.out.print("* ");
                } else if (grid[i][j] == '1') {
                    System.out.print(Color.GREEN_BOLD + grid[i][j] + Color.RESET + " ");
                } else if (grid[i][j] == '2') {
                    System.out.print(Color.RED_BOLD + grid[i][j] + Color.RESET + " ");
                }
            }
            System.out.println();
        }
    }

    public void setStartPoints(List<String> startPoints) {
        this.startPoints = startPoints;
    }

    public int calculateDamage(int damage) {
        if (arenaType == 1) {
            return damage / 2;
        } else if (arenaType == 2) {
            return damage * 2;
        }
        return damage;
    }

    public static List<String> getStartPoints(int size, List<Droid> battleDroids) {
        List<String> startPoints = new ArrayList<>();
        int center = size / 2;
        for (int i = 0; i < battleDroids.size(); i++) {
            boolean valid = false;
            do {
                System.out.print("Введіть координати дроїда " + (i + 1) + " (формат: x,y): ");
                String input = scanner.next();
                String[] coordinates = input.split(",");
                if (coordinates.length != 2) {
                    System.out.println("Некоректний ввід. Формат: x,y.");
                    continue;
                }
                try {
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    valid = Math.abs(x) <= center && Math.abs(y) <= center;
                    if (valid) {
                        startPoints.add(input);
                    } else {
                        System.out.println("Координати точки мають бути в межах від " + (-center) + " до " + center + ". Спробуйте ще раз.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Некоректний формат. Спробуйте ще раз.");
                    valid = false;
                }
            } while (!valid);
        }
        return startPoints;
    }
}
