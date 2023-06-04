package core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JsonReader {

    public static JsonObject testData;

    public JsonObject readConfigData() {
        try {
            File file = new File("data.json");
            String json = new String(Files.readAllBytes(file.toPath()));

            Gson gson = new Gson();

            testData = gson.fromJson(json, JsonObject.class);

        } catch (IOException ex) {
            System.out.println("Could not find file: data.json");
        }
        return testData;
    }
}