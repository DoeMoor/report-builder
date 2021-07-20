package model;

import java.util.ArrayList;

public class Report {
   private static final ArrayList<String> listTask = new ArrayList<>();

    public static void setListTask(String reportPrinter) {
        listTask.add(reportPrinter);
    }

    public static ArrayList<String> getListTask() {
        return listTask;
    }
}