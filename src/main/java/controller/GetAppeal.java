package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import model.Appeal;
import model.PrivateData;
import model.Report;
import java.io.*;



public class GetAppeal {

    public void getAppealList(String id) throws IOException {

        HttpResponse<String> response = Unirest.get(PrivateData.getURL()+"{taskId}.json")
                .routeParam("taskId", String.valueOf(id))
                .queryString("key",PrivateData.getKeyValue())
                .queryString("include", "children")
                .asString();

        ObjectMapper respMap = new ObjectMapper();
        JsonNode answer = respMap.readTree(response.getBody());
        Appeal appeal = new Appeal(answer);


        if (appeal.getStatus().equals("Новая")) {

            InputStream inputStream = System.in;
            Reader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            System.out.print("Ввести имя пользователя : ");
            String assignTaskUser = bufferedReader.readLine();


            Report.setListTask(getNewAppeal(assignTaskUser,appeal));

        } else {

            Report.setListTask(getClosedAppeal(appeal));

        }

    }

    private String getNewAppeal(String assignTaskUser, Appeal appeal) {
        return String.format("%s %s %s\n(%s, %s, %s)\n(%s, %s, %s, %s)\n\n",
                appeal.getTrackerName(), appeal.getId(), appeal.getSubject(),
                appeal.getIncomeCanal(), appeal.getTaskFrom(), appeal.getPriority(),
                appeal.getLinkedTasks(), appeal.getLinkedTasksChildren(),
                assignTaskUser(assignTaskUser), appeal.getDueDate()
        );

    }

    private String getClosedAppeal(Appeal appeal) {

        return String.format("%s %s %s\n(%s, %s,%s)\n(%s)\n\n",
                appeal.getTrackerName(), appeal.getId(), appeal.getSubject(),
                appeal.getIncomeCanal(), appeal.getTaskFrom(), appeal.getIncomeDate(),
                appeal.getReasonFoTask()
        );
    }

    private String assignTaskUser(String assignTaskUser) {
        if (assignTaskUser.equals("Юра") || assignTaskUser.equals("Преградов Юрий")) {
            return "@pregradov";
        } else if
        (assignTaskUser.equals("Саша")) {
            return "@astashkinav";
        } else {
            return "Пользователь не найден";
        }

    }


}
