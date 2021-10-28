package model;


import com.fasterxml.jackson.databind.JsonNode;
import controller.GetAppeal;

import java.io.IOException;


public class Appeal {

    private final String id;
    private final String trackerName;
    private final String status;
    private final String subject;
    private final String incomeCanal;
    private final String taskFrom;
    private final String priority;
    private final String linkedTasksAndId;
    private final String linkedTasksChildrenAndId;
    private final String dueDate;

    private final String reasonFoTask;
    private final String incomeDate;
    private final String assignedTo;
    private final String parentId;

    private final String createdDate;

    private String assignedToTaskInLinkedTasksChildren;
    private String linkedParent;

    private final JsonNode issues;

    public Appeal(JsonNode answer) throws NullPointerException, IOException {

        issues = answer.get("issue");

        trackerName = issues.get("tracker").get("name").asText();
        id = "#" + issues.get("id").asText();
        status = issues.get("status").get("name").asText();
        subject = issues.get("subject").asText();
        priority = issues.get("priority").get("name").asText();


        if (issues.get("due_date").asText() == "null"){
            dueDate = "крайнего срока нет";
        } else {
            dueDate = issues.get("due_date").asText();
        }


        createdDate = issues.get("created_on").asText().replace("T", " ")
                .replace("Z", "");


        if (!trackerName.equals("Request") && !trackerName.equals("Инцидент")) {
            taskFrom = issues.get("custom_fields").get(3).get("value").asText();
            incomeCanal = issues.get("custom_fields").get(0).get("value").asText();
            if (issues.get("project").get("name").asText().equals("ДО1")){
                reasonFoTask = "";
            } else {
                reasonFoTask = issues.get("custom_fields").get(5).get("value").asText();
            }
            incomeDate = issues.get("custom_fields").get(1).get("value").asText();

        } else if (trackerName.equals("Инцидент") && !issues.get("project").get("name").asText().equals("ДО1") ){

            reasonFoTask = issues.get("custom_fields").get(2).get("value").asText();
            incomeCanal = "";
            taskFrom = "";
            incomeDate = "";

        } else {

            reasonFoTask = "";
            incomeCanal = "";
            taskFrom = "";
            incomeDate = "";

        }


        if (!trackerName.equals("Обращение")) {

            if (trackerName.equals("Ошибка")) {
                assignedTo = issues.get("assigned_to").get("name").asText();
            } else {
                assignedTo = "";
            }

            if(issues.findValue("parent") != null) {
                parentId = issues.get("parent").get("id").asText();
                setLinkedParent(parentId);
            } else {
                parentId = "";
            }


        } else {
            assignedTo = "";
            parentId = "";
        }


        if (issues.findValue("children") != null) {

            linkedTasksAndId = issues.get("children").get(0).get("tracker").get("name").asText()
                    + " #" +
                    issues.get("children").get(0).get("id").asText();
            setAssignedInLinkedTasksChildren();
            if (issues.get("children").get(0).findValue("children") != null) {

                linkedTasksChildrenAndId = issues.get("children").get(0).get("children")
                        .get(0).get("tracker").get("name").asText()
                        + " #" +
                        issues.get("children").get(0).get("children")
                                .get(0).get("id").asText();

                setAssignedInLinkedTasksChildren();

            } else {
                linkedTasksChildrenAndId = "";

            }
        } else {
            linkedTasksAndId = "";
            linkedTasksChildrenAndId = "";
            assignedToTaskInLinkedTasksChildren = "";

        }

    }



    private void setLinkedParent(String parentId) throws IOException {

        GetAppeal getAppeal = new GetAppeal();
        JsonNode jsonNode = getAppeal.getAppealFromRedmine(parentId);
        if (jsonNode.get("issue").findValue("parent") != null) {
            String linkedParentId = jsonNode.get("issue").get("parent").get("id").asText();
            jsonNode = getAppeal.getAppealFromRedmine(linkedParentId);
            this.linkedParent = jsonNode.get("issue").get("tracker").get("name").asText()
                    + " #" + jsonNode.get("issue").get("id").asText();
        } else {
            this.linkedParent = jsonNode.get("issue").get("tracker").get("name").asText()
                    + " #" + jsonNode.get("issue").get("id").asText();
        }

    }

    private void setAssignedInLinkedTasksChildren() throws IOException {

        GetAppeal getAppeal = new GetAppeal();


        if (issues.get("children").get(0).findValue("children") != null) {

            JsonNode jsonNode = getAppeal.getAppealFromRedmine(issues.get("children").get(0).get("children")
                    .get(0).get("id").asText());

            this.assignedToTaskInLinkedTasksChildren = jsonNode.get("issue").get("assigned_to").get("name").asText();

        } else {

            JsonNode jsonNode = getAppeal.getAppealFromRedmine(issues.get("children").get(0).get("id").asText());

            if (jsonNode.get("issue").findValue("children") != null) {

                this.assignedToTaskInLinkedTasksChildren = jsonNode.get("issue").get("assigned_to").get("name").asText();

            }
            this.assignedToTaskInLinkedTasksChildren = "";

        }

    }

    public String getLinkedParent() {
        return linkedParent;
    }

    public String getId() {
        return id;
    }

    public String getTrackerName() {
        return trackerName;
    }

    public String getStatus() {
        return status;
    }

    public String getSubject() {
        return subject;
    }

    public String getIncomeCanal() {
        return incomeCanal;
    }

    public String getTaskFrom() {
        return taskFrom;
    }

    public String getPriority() {
        return priority;
    }

    public String getLinkedTasksAndId() {
        return linkedTasksAndId;
    }

    public String getLinkedTasksChildrenAndId() {
        return linkedTasksChildrenAndId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getReasonFoTask() {
        return reasonFoTask;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getAssignedToTaskInLinkedTasksChildren() {
        return assignedToTaskInLinkedTasksChildren;
    }

    public String getParentId() {
        return parentId;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
