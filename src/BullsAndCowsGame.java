import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BullsAndCowsGame {
    private final int CODE_LENGTH = 4;
    private final int MAX_GUESSES = 10;

    private final int[] secretCode;
    private final GameResultWriter gameResultWriter;

    public BullsAndCowsGame(GameResultWriter gameResultWriter) {
        secretCode = generateSecretCode();
        this.gameResultWriter = gameResultWriter;
    }

    public void play() {
        boolean isGameWon = false;
        int numGuesses = 0;
        StringBuilder gameResult = new StringBuilder();

        System.out.println("Это игра \"Быки-Коровы\"!");
        System.out.println("Задумано четырехзначное число. Отгадаешь?");
        System.out.println(Arrays.toString(secretCode));

        for (int i = 1; i <= MAX_GUESSES; i++) {
            System.out.println("Попытка #" + i);
            int[] guess = readGuessFromConsole();
            numGuesses++;

            int bulls = countBulls(secretCode, guess);
            int cows = countCows(secretCode, guess);

            System.out.println("Быки: " + bulls);
            System.out.println("Коровы: " + cows);

            gameResult.append("Запрос: ").append(Arrays.toString(guess)).append(" Ответ: Коровы: ").append(cows).append(", быки: ").append(bulls).append("\n");

            if (bulls == CODE_LENGTH) {
                isGameWon = true;
                break;
            }
        }

        if (isGameWon) {
            System.out.println("Поздравляю!");
            gameResult.append("Число угадано за ").append(numGuesses).append(" попыток.");
        } else {
            System.out.println("Сочувствую!( Задуманное число: " + Arrays.toString(secretCode));
            gameResult.append("Число не угадано.");
        }

        gameResultWriter.writeGameResult(gameResult.toString(), secretCode);
    }

    private int[] generateSecretCode() {
        int[] code = new int[CODE_LENGTH];
        Random random = new Random();
        boolean[] usedDigits = new boolean[10];

        for (int i = 0; i < CODE_LENGTH; i++) {
            int digit;
            do {
                digit = random.nextInt(10);
            } while (usedDigits[digit]);
            usedDigits[digit] = true;
            code[i] = digit;
        }

        return code;
    }

    private int[] readGuessFromConsole() {
        Scanner scanner = new Scanner(System.in);
        int[] guess = new int[CODE_LENGTH];

        System.out.print("Введите четырехзначное число: ");
        String input = scanner.nextLine();

        while (input.length() != CODE_LENGTH) {
            System.out.print("Неверный формат числа. Попробуйте снова: ");
            input = scanner.nextLine();
        }

        for (int i = 0; i < CODE_LENGTH; i++) {
            guess[i] = Character.getNumericValue(input.charAt(i));
        }

        return guess;
    }

    private int countBulls(int[] secretCode, int[] guess) {
        int bulls = 0;

        for (int i = 0; i < CODE_LENGTH; i++) {
            if (secretCode[i] == guess[i]) {
                bulls++;
            }
        }

        return bulls;
    }

    private int countCows(int[] secretCode, int[] guess) {
        int cows = 0;

        for (int i = 0; i < CODE_LENGTH; i++) {
            for (int j = 0; j < CODE_LENGTH; j++) {
                if (secretCode[i] == guess[j] && i != j) {
                    cows++;
                }
            }
        }

        return cows;
    }
}