import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class GameResultWriter {
    private final String FILE_NAME = "results.txt";

    public void writeGameResult(String result, int[] secretCode) {
        int gameNumber = getGameNumber();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String timestamp = dateFormat.format(new Date());

            writer.write("Game #" + gameNumber + " " + timestamp);
            writer.newLine();
            writer.write("Загаданное число: " + Arrays.toString(secretCode));
            writer.newLine();
            writer.write(result);
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка при записи.");
        }
    }

    private int getGameNumber() {
        int gameNumber = 1;

        try (Scanner scanner = new Scanner(new FileReader(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Game #")) {
                    int number = extractGameNumber(line);
                    if (number >= gameNumber) {
                        gameNumber = number + 1;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return gameNumber;
        }

        return gameNumber;
    }

    private int extractGameNumber(String line) {
        int startIndex = line.indexOf("#") + 1;
        int endIndex = line.indexOf(" ", startIndex);
        String numberString = line.substring(startIndex, endIndex);
        return Integer.parseInt(numberString);
    }
}