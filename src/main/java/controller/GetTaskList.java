package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import model.PrivateData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class GetTaskList {


    public String getTaskList(String status, String dataType) throws IOException {

        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder idTaskList = new StringBuilder();

        HttpResponse<String> response = Unirest.get(PrivateData.getURL() + ".json")
                .queryString("key", PrivateData.getKeyValue())
                .queryString("project_id", "3")
                .queryString("author_id", "51")
                .queryString("status_id", status)
                .queryString(dataType, formatDate.format(date))
                .asString();

        ObjectMapper respMap = new ObjectMapper();
        JsonNode jn = respMap.readTree(response.getBody());

        for (int i = 0; i < jn.path("issues").size(); i++) {

            if (status.equals("open")
                    && !jn.path("issues").path(i).get("tracker").get("name").asText().equals("Сопровождение")) {

                idTaskList.append(jn.path("issues").path(i).get("id").asText()).append(" ");

            } else if (jn.path("issues").path(i).get("tracker").get("name").asText().equals("Обращение")
                    || jn.path("issues").path(i).get("tracker").get("name").asText().equals("Инцидент")) {
                idTaskList.append(jn.path("issues").path(i).get("id").asText()).append(" ");
            }

        }
        return idTaskList.toString();

    }

}
