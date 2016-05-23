import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * @author Robin Duda
 */
public class Main {
    private static final String OUTPUT = "output.csv";

    public static void main(String[] args) {
        String path = getPath();
        try {
            new JsonParseToCsv()
                    .input(JsonFileReader.readJsonLines(path))
                    .output(OUTPUT);

        } catch (IOException | ParseException e) {
            System.out.println("Failed to parse file.");
        }
        System.out.println("Parsed file saved to " + OUTPUT);
    }

    private static String getPath() {
        System.out.print("Enter file path: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
