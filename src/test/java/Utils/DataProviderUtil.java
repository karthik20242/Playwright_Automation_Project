
package Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class DataProviderUtil {

    public static Object[][] getJsonDataToMap(String filePath) throws IOException {

        Path path = Paths.get(filePath);

        // Fix the file path relative to the project directory
        if (!path.isAbsolute()) {
            path = Paths.get(System.getProperty("user.dir")).resolve(path);
        }

        // Read JSON file
        String jsonData = Files.readString(path);

        // Convert JSON into a list of HashMaps
        Gson gson = new Gson();

        Type listType = new TypeToken<List<HashMap<String, String>>>() {}.getType();

        List<HashMap<String, String>> dataList =
                gson.fromJson(jsonData, listType);

        // Convert the list into Object[][] for TestNG
        Object[][] data = new Object[dataList.size()][1];

        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i);
        }

        return data;
    }
}
