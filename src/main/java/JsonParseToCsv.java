import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * @author Robin Duda
 *
 * Aggregates dates into second-buckets and returning the count.
 */
class JsonParseToCsv {
    private static final String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private TreeMap<Integer, Integer> count = new TreeMap<>();
    private int end = 0;
    private int begin = Integer.MAX_VALUE;

    JsonParseToCsv input(ArrayList<JsonObject> lines) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(ISO_8601);

        for (JsonObject line : lines) {
            int time = (int) Math.floor(format.parse(line.getString("@timestamp")).getTime() / 1000);

            count.put(time, (count.get(time) == null) ? 1 : count.get(time) + 1);

            if (time > end) end = time;
            if (time < begin) begin = time;
        }
        return this;
    }

    void output(String path) throws IOException {
        Path file = Paths.get(path);
        String data = "";

        for (int count : parse()) {
            data += count + "\n";
        }

        Files.write(file, data.getBytes());
    }

    int[] parse() {
        int[] counts = new int[end - begin + 1];

        for (Integer time : count.keySet()) {
            counts[time - begin] = count.get(time);
        }

        return counts;
    }
}
