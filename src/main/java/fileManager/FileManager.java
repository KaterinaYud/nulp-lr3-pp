package fileManager;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileManager {
    private static final String FILE_PATH = "battle.txt";
    public static void logBattle(boolean battleWas, List<String> log) {
        if (!battleWas) {
            System.out.println("Неможливо записати бій, оскільки його ще не було.");
            return;
        }
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            for (String entry : log) {
                writer.write(entry + "\n");
            }
            writer.write("------------------------\n");
            System.out.println("Записано у файл.");
        } catch (IOException e) {
            System.out.println("Помилка під час запису до файлу: " + e.getMessage());
        }
    }

    public static void readBattle() {
        Path filePath = Paths.get(FILE_PATH);
        if (!Files.exists(filePath)) {
            System.out.println("Файл не існує. Жодного бою ще не записано.");
            return;
        }
        try {
            if (Files.size(filePath) == 0) {
                System.out.println("Файл порожній. Жодного бою ще не записано.");
                return;
            }
            List<String> log = Files.readAllLines(filePath);
            for (String line : log) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу: " + e.getMessage());
        }
    }
}
