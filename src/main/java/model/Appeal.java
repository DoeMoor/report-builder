package model;


import com.fasterxml.jackson.databind.JsonNode;


public class Appeal {

    public Appeal(JsonNode answer) throws NullPointerException {

        JsonNode issues = answer.get("issue");

        trackerName = issues.get("tracker").get("name").asText();
        id = "#" + issues.get("id").asText();
        status = issues.get("status").get("name").asText();
        subject = issues.get("subject").asText();
        priority = issues.get("priority").get("name").asText();
        incomeCanal = issues.get("custom_fields").get(0).get("value").asText();
        taskFrom = issues.get("custom_fields").get(3).get("value").asText();
        dueDate = issues.get("due_date").asText();

        reasonFoTask = issues.get("custom_fields").get(5).get("value").asText();
        incomeDate = issues.get("custom_fields").get(1).get("value").asText();
        createdDate = issues.get("created_on").asText().replace("T"," ").replace("Z"," ");


        if (trackerName.equals("Ошибка")) {
            assignedTo = issues.get("assigned_to").get("name").asText();
            parentId = issues.get("parent").get("id").asText();
        } else {
            assignedTo = "";
            parentId = "";
        }

        if (issues.findValue("children") != null) {

            linkedTasks = issues.get("children").get(0).get("tracker").get("name").asText()
                    + " #" +
                    issues.get("children").get(0).get("id").asText();

            if (issues.get("children").get(0).findValue("children") != null) {

                linkedTasksChildren = issues.get("children").get(0).get("children")
                        .get(0).get("tracker").get("name").asText()
                        + " #" +
                        issues.get("children").get(0).get("children")
                                .get(0).get("id").asText();

            } else {
                linkedTasksChildren = "";
            }


        } else {
            linkedTasks = "";
            linkedTasksChildren = "";
        }

    }

    private final String id;
    private final String trackerName;
    private final String status;
    private final String subject;
    private final String incomeCanal;
    private final String taskFrom;
    private final String priority;
    private final String linkedTasks;
    private final String linkedTasksChildren;
    private final String dueDate;

    private final String reasonFoTask;
    private final String incomeDate;
    private final String assignedTo;
    private final String parentId;

    private final String createdDate;

    public String getStatus() {
        return status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getTrackerName() {
        return trackerName;
    }

    public String getId() {
        return id;
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

    public String getLinkedTasks() {
        return linkedTasks;
    }

    public String getLinkedTasksChildren() {
        return linkedTasksChildren;
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

    public String getParentId() {
        return parentId;
    }
}
