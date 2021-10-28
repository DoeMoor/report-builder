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

    Appeal appeal;

    public JsonNode getAppealFromRedmine(String id) throws IOException {

        HttpResponse<String> response = Unirest.get(PrivateData.getURL() + "/{taskId}.json")
                .routeParam("taskId", String.valueOf(id))
                .queryString("key", PrivateData.getKeyValue())
                .queryString("include", "children")
                .asString();


        ObjectMapper respMap = new ObjectMapper();
        return respMap.readTree(response.getBody());
    }


    public void setAppealInList(String id) throws IOException {
        appeal = new Appeal(getAppealFromRedmine(id));

        if (appeal.getStatus().equals("Новая") || appeal.getStatus().equals("Новый") || appeal.getStatus().equals("В работе")){
            Report.setListTask(getNewAppeal(appeal));
        } else {
            Report.setListTask(getClosedAppeal(appeal));
        }
    }

    private String getNewAppeal(Appeal appeal) {
        if (appeal.getTrackerName().equals("Обращение")) {

            return String.format("%s %s: %s\n(%s, %s, %s)\n(%s, %s, %s, %s)\n",
                    appeal.getTrackerName(), appeal.getId(), appeal.getSubject(),
                    appeal.getIncomeCanal(), appeal.getTaskFrom(), appeal.getPriority(),
                    appeal.getLinkedTasksAndId(), appeal.getLinkedTasksChildrenAndId(),
                    assignTaskUser(appeal.getAssignedToTaskInLinkedTasksChildren()), appeal.getDueDate()
            );

        } else if (appeal.getTrackerName().equals("Ошибка")) {

            if (appeal.getPriority().equals("Высокий") || appeal.getPriority().equals("Немедленный")){

                return String.format("%s %s: %s\n(%s,связано с %s)\n",
                        appeal.getTrackerName(), appeal.getId(), appeal.getSubject(),
                        appeal.getAssignedTo(),appeal.getLinkedParent()
                );
            }

            return String.format("%s %s: %s\n(%s, %s, сделать до: %s, %s)\n",
                    appeal.getTrackerName(), appeal.getId(), appeal.getSubject(),
                    appeal.getAssignedTo(), appeal.getPriority(), appeal.getDueDate(),
                    appeal.getLinkedParent()
            );


        } else if (appeal.getTrackerName().equals("Инцидент")) {

            return String.format("%s %s: %s\n(%s, %s, %s)\n(Поставлена задача %s, %s)\n",
                    appeal.getTrackerName(), appeal.getId(), appeal.getSubject(),
                    appeal.getIncomeCanal(), appeal.getCreatedDate(), appeal.getPriority(),
                    appeal.getLinkedTasksAndId(), appeal.getAssignedToTaskInLinkedTasksChildren()
            );

        } else if (appeal.getTrackerName().equals("Request")) {

            return String.format("%s %s: %s\n(%s)\n",
                    appeal.getTrackerName(), appeal.getId(), appeal.getSubject(),
                    appeal.getLinkedParent()

            );
        }

        return "Ошибка, тип заявки :" + appeal.getTrackerName();

    }

    private String getClosedAppeal(Appeal appeal) {

        if (appeal.getTrackerName().equals("Инцидент")) {

            return String.format("%s %s: %s\n(Связано с %s), (%s)\n",
                    appeal.getTrackerName(), appeal.getId(), appeal.getSubject(),
                    appeal.getLinkedParent(), appeal.getReasonFoTask()
            );
        }


        return String.format("%s %s: %s\n(%s, %s,%s)\n(%s)\n",
                appeal.getTrackerName(), appeal.getId(), appeal.getSubject(),
                appeal.getIncomeCanal(), appeal.getTaskFrom(), appeal.getIncomeDate(),
                appeal.getReasonFoTask()
        );
    }

    private String assignTaskUser(String assignTaskUser) {
        if (assignTaskUser.equals(null)) {

            return "Ответственный не назначен";

        }
        if (assignTaskUser.equals("Юра") || assignTaskUser.equals("Преградов Юрий")) {
            return "@pregradov";
        } else if
        (assignTaskUser.equals("Саша") || assignTaskUser.equals("Асташкин Александр")) {
            return "@astashkinav";
        } else {
            return assignTaskUser;
        }

    }

    private void stopItGetSomeHelp() {

    }


}
