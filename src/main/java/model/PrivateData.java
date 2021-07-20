package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class PrivateData {

    private static String URL;
    private static String KEY_VALUE;

    public PrivateData() {

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(new File("config.ini")));
        } catch (FileNotFoundException e) {
            System.out.println("файл конфигурации не найден");
        } catch (IOException e) {
            e.printStackTrace();
        }

        URL = String.valueOf(properties.get("URL"));
        KEY_VALUE = String.valueOf(properties.get("KEY_VALUE"));
    }

    public static String getURL() {
        return URL;
    }

    public static String getKeyValue() {
        return KEY_VALUE;
    }
}
