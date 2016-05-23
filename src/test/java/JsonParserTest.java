import io.vertx.core.json.JsonObject;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * @author Robin Duda
 */
public class JsonParserTest {
    private static final String TEST_FILE = "test.txt";
    private static final String TEST_OUTPUT = "test.csv";

    @Test
    public void shouldConvertJsonToCSV() throws ParseException {
        ArrayList<JsonObject> items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            JsonObject item = new JsonObject().put("@timestamp", "2000-01-01T00:00:0" + (i + 1) + ".000Z");

            if (i == 5)
                items.add(item);

            items.add(item);
        }

        int[] results = new JsonParseToCsv().input(items).parse();

        for (int i = 0; i < 10; i++) {
            if (i == 5)
                assert results[i] == 2;
            else
                assert results[i] == 1;
        }
    }


    @Test
    public void shouldReadJsonLinesFromFile() throws IOException {
        JsonFileReader.readJsonLines("test.txt");
    }

    @Test
    public void shouldOutputCSVFile() throws IOException, ParseException {
        new JsonParseToCsv().input(JsonFileReader.readJsonLines(TEST_FILE)).output(TEST_OUTPUT);
        assert Paths.get(TEST_OUTPUT).toFile().delete();
    }
}
