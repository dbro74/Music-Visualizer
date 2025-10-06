import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MusicVisualizer {
    private static final int canvasWidth = 1000;
    private static final int canvasHeight = 100;

    public static void main(String[] args) {
        // Required args: (1) file name (2) number of groups
        if (args.length != 2) {
            System.out.println("Please provide the name of an audio file and the number of groups.");
            System.exit(1);
        }

        String filename = args[0];
        int numOfGroups = 0;

        // Check that a number of groups is provided
        try {
            numOfGroups = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("The number of groups must be a number.");
            System.exit(1);
        }
        if (numOfGroups < 1) {
            System.out.println("The number of groups must be a positive number.");
            System.exit(1);
        }

        // Check that the file exists
        Path path = Paths.get("resources/" + filename);
        if (Files.notExists(path)) {
            System.out.println("That audio file does not exist.");
            System.exit(1);
        }

        double[] samples = StdAudio.read("resources/" + filename);
        int groupLength = Math.floorDiv(samples.length, numOfGroups);

        StdDraw.setCanvasSize(canvasWidth, canvasHeight);

        double x = 0;
        double y = 0.5;
        
        for (int i = 0; i < numOfGroups; i++) {
            int start = i * groupLength;
            int stop = start + groupLength;
            double max = 0;
            for (int j = start; j < stop; j++) {
                double sample = Math.abs(samples[j]);
                if (sample > max) {
                    max = sample;
                }
                StdAudio.play(samples[j]);
            }
            StdDraw.line(x, y - max * 0.5, x, y + max * 0.5);
            x += 1.0 / numOfGroups;
        }
    }
}
