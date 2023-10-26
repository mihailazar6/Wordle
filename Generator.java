import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Generator {
    Random generator= new Random();
    String file_name = "src/sol.txt";
    int x = generator.nextInt(2315);
    // get a random word
    public String word = get_word(file_name, x);

    public static String get_word(String numeFisier, int random) {
        try {
            Optional<String> Opt = Files.lines(Paths.get(numeFisier))
                    .skip(random)
                    .findFirst();

            return Opt.orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean is_valid(String file_name, String text) {
        String[] vector = new String[2315];
        File fisier = new File(file_name);

        try {
            Scanner scanner = new Scanner(fisier);

            int i = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                vector[i++] = line;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int result = Arrays.binarySearch(vector, text);
        if (result >= 0) {
            return true;
        }
        return false;

    }
    public static int[] color (String word, String guess) {
        int[] v = new int[5];
        for (int i = 0; i < 5; i++) {
            int x = 0;
            for (int j = 0; j < 5; j++)
                if (guess.substring(i, i + 1).equals(word.substring(j, j + 1)))
                    x = 1;
            if (guess.substring(i, i + 1).equals(word.substring(i, i + 1)))
                x = 2;
            v[i] = x;
        }
        return v;
    }

}
