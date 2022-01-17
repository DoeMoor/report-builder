package model;

import com.fasterxml.jackson.databind.JsonNode;
import controller.RedmineConnector;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Appeal {

    private final String id;
    private final String trackerName;
    private final String status;
    private final String subject;
    private String incomeChannel;
    private final String taskFrom;
    private final String priority;
    private final String linkedTasksAndId;
    private final String linkedTasksChildrenAndId;
    private final String dueDate;

    private String reasonFoTask;
    private final String incomeDate;
    private final String assignedTo;
    private final String parentId;

    private final String createdDate;
    private final JsonNode issues;
    private final String assignedToTaskInLinkedTasksChildren;
    private final String linkedParent;
    private final String relations;
    private final boolean children;

    public Appeal(JsonNode answer) throws NullPointerException, IOException {

        String initAssignedUser = "Ответственный не назначен";
        String initLinkedParent = "Связанный, родительский объект не нашёлся";
        issues = answer.get("issue");

        trackerName = issues.get("tracker").get("name").asText();
        id = "#" + issues.get("id").asText();
        status = issues.get("status").get("name").asText();
        subject = issues.get("subject").asText();
        priority = issues.get("priority").get("name").asText();

        children = issues.hasNonNull("children");

        if (Objects.equals(issues.get("due_date").asText(), "null")) {
            dueDate = "крайнего срока нет";
        } else {
            dueDate = issues.get("due_date").asText();
        }

        createdDate = issues.get("created_on").asText().replace("T", " ")
                .replace("Z", "");

        if (!trackerName.equals("Request")
                && !trackerName.equals("Инцидент")) {
            if (trackerName.equals("Обращение")) {
                taskFrom = issues.get("custom_fields").get(5).get("value").asText();
            } else {
                taskFrom = issues.get("custom_fields").get(3).get("value").asText();
            }

            if (trackerName.equals("Ошибка") || trackerName.equals("Сопровождение")){
                incomeChannel = issues.get("custom_fields").get(0).get("value").asText();
            } else {
                incomeChannel = issues.get("custom_fields").get(1).get("value").asText();
            }

            if (issues.get("project").get("name").asText().equals("ДО1")
                    || trackerName.equals("Сопровождение")) {
                reasonFoTask = "";
            } else {
                reasonFoTask = issues.get("custom_fields").get(7).get("value").asText();
            }
            incomeDate = issues.get("custom_fields").get(1).get("value").asText();

        } else if (trackerName.equals("Инцидент")
                && !issues.get("project").get("name").asText().equals("ДО1")) {
            try {
            initAssignedUser = initAssignedUser();
            initLinkedParent = initLinkedParent();

            reasonFoTask = new Appeal(new RedmineConnector()
                    .getTask(issues.get("parent")
                            .get("id")
                            .asText()))
                    .getReasonFoTask();
            incomeChannel = new Appeal(new RedmineConnector()
                    .getTask(issues.get("children")
                            .get(0)
                            .get("id")
                            .asText()))
                    .getIncomeChannel();
            } catch (Exception e) {
                reasonFoTask = "";
                incomeChannel = "";
            }
            taskFrom = "";
            incomeDate = "";

        } else {

            reasonFoTask = "";
            incomeChannel = "";
            taskFrom = "";
            incomeDate = "";

        }

        if (!trackerName.equals("Обращение")) {

            if (trackerName.equals("Ошибка")) {
                assignedTo = issues.get("assigned_to").get("name").asText();
            } else {
                assignedTo = "";
            }

            if (issues.findValue("parent") != null) {
                parentId = issues.get("parent").get("id").asText();
                initLinkedParent = setLinkedParent(parentId);
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
            initAssignedUser = setAssignedInLinkedTasksChildren(initAssignedUser);

            if (issues.get("children").get(0).findValue("children") != null) {

                linkedTasksChildrenAndId = issues.get("children").get(0).get("children")
                        .get(0).get("tracker").get("name").asText()
                        + " #" +
                        issues.get("children").get(0).get("children")
                                .get(0).get("id").asText();

            } else {
                linkedTasksChildrenAndId = "";

            }
        } else {
            linkedTasksAndId = "";
            linkedTasksChildrenAndId = "";
        }

        if (issues.hasNonNull("relations")) {
            relations = issues.get("relations").get(0).get("issue_id").asText();
        } else {
            relations = "Not have relations";
        }

        assignedToTaskInLinkedTasksChildren = initAssignedUser;
        linkedParent = initLinkedParent;

    }

    private String setAssignedInLinkedTasksChildren(String initAssignedUser) throws IOException {
        if (issues.get("children").get(0).findValue("children") != null) {
            JsonNode jsonNode = new RedmineConnector().getTask(issues.get("children").get(0).get("children")
                    .get(0).get("id").asText());

            if (jsonNode.get("issue").hasNonNull("assigned_to")) {
                initAssignedUser = jsonNode
                        .findPath("assigned_to")
                        .get("name")
                        .asText();
            }
        }
        return initAssignedUser;
    }

    private String setLinkedParent(String parentId) throws IOException {

        JsonNode jsonNode = new RedmineConnector().getTask(parentId);
        if (jsonNode.get("issue").findValue("parent") != null) {
            String linkedParentId = jsonNode.get("issue").get("parent").get("id").asText();
            jsonNode = new RedmineConnector().getTask(linkedParentId);
            return jsonNode.get("issue").get("tracker").get("name").asText()
                    + " #" + jsonNode.get("issue").get("id").asText();
        } else {
            return jsonNode.get("issue").get("tracker").get("name").asText()
                    + " #" + jsonNode.get("issue").get("id").asText();
        }

    }

    private String initAssignedUser() throws IOException {
        JsonNode jsonNode = new RedmineConnector().getTask(issues.get("children").get(0).get("id").asText());
        return jsonNode.get("issue").get("assigned_to").get("name").asText();
    }

    private String initLinkedParent() throws IOException {
        JsonNode jsonNode = new RedmineConnector().getTask(issues.get("children").get(0).get("id").asText());
        return jsonNode.get("issue").get("custom_fields").get(0).get("value").asText();
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

    public String getIncomeChannel() {
        return incomeChannel;
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

    public String getRelations() {
        return relations;
    }

    public boolean isChildren() {
        return children;
    }
}
