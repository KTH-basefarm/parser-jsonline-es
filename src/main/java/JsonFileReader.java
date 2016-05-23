import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Robin Duda
 *
 * Reads an ElasticSearch json-lines file.
 */
abstract class JsonFileReader {

    static ArrayList<JsonObject> readJsonLines(String path) throws IOException {
        String[] lines = readFile(path).split("\n");
        ArrayList<JsonObject> list = new ArrayList<>();

        for (String line : lines) {
            list.add(new JsonObject(line));
        }

        return list;
    }

    private static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(FileSystems.getDefault().getPath(currentPath() + "/" + path)));
    }

    private static String currentPath() {
        return Paths.get("").toAbsolutePath().normalize().toString();
    }
}
