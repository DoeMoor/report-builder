import controller.RedmineConnector;
import model.PrivateData;
import model.Report;
import view.GetAppeal;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        PrivateData privateData = new PrivateData(); //TODO refactor config
        printReport();


    }

    private static void printReport() throws IOException {
        RedmineConnector redmineConnector = new RedmineConnector();
        String taskId;
        taskId = redmineConnector.getTaskList("open", "created_on");
        InsertAppeal(taskId);
        taskId = redmineConnector.getTaskList("closed", "closed_on");
        InsertAppeal(taskId);
        System.out.flush();
        for (String i : Report.getListTask()
        ) {
            System.out.println(i);
        }
    }

    private static void InsertAppeal(String taskId) throws IOException {
        if (!taskId.equals("")) {
            String[] taskIdList = taskId.split(" ");
            for (String i : taskIdList) {
                GetAppeal getAppeal = new GetAppeal();
                getAppeal.setAppealInList(i);
            }
        }
    }
}
