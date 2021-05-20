package model;


import com.fasterxml.jackson.databind.JsonNode;
import controller.GetAppeal;

import java.io.IOException;


public class Appeal {

    private final JsonNode issues;

    public Appeal(JsonNode answer) throws NullPointerException, IOException {

        issues = answer.get("issue");

        trackerName = issues.get("tracker").get("name").asText();
        id = "#" + issues.get("id").asText();
        status = issues.get("status").get("name").asText();
        subject = issues.get("subject").asText();
        priority = issues.get("priority").get("name").asText();
        dueDate = issues.get("due_date").asText();

        createdDate = issues.get("created_on").asText().replace("T", " ").replace("Z", "");

        if (!trackerName.equals("Request") && !trackerName.equals("Инцидент")) {
            taskFrom = issues.get("custom_fields").get(3).get("value").asText();
            incomeCanal = issues.get("custom_fields").get(0).get("value").asText();
            reasonFoTask = issues.get("custom_fields").get(5).get("value").asText();
            incomeDate = issues.get("custom_fields").get(1).get("value").asText();

        } else {
            taskFrom = "";
            incomeCanal = "";
            reasonFoTask = "";
            incomeDate = "";
        }


        if (trackerName.equals("Ошибка") || trackerName.equals("Request")) {

            if (!trackerName.equals("Request")) {
                assignedTo = issues.get("assigned_to").get("name").asText();
            } else {
                assignedTo = "";
            }

            parentId = issues.get("parent").get("id").asText();
            setLinkedParent(parentId);
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

            this.assignedToTaskInLinkedTasksChildren = jsonNode.get("issue").get("assigned_to").get("name").asText();
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
