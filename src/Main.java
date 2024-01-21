import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the file path: ");
        String filePath = scanner.nextLine();

        if (fileExists(filePath)) {
            Tokenizer tokenizer = new Tokenizer(filePath);
            Parser myParser = new Parser(tokenizer.Tokenize());
            myParser.parse();
        } else {
            System.out.println("File does not exist.");
        }

        scanner.close();
    }

    private static boolean fileExists(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path) && Files.isRegularFile(path);
    }
}
