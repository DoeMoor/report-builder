package model;

import java.util.ArrayList;

public class Report {
    private static final ArrayList<String> ReportList = new ArrayList<>();
//TODO refactor list
    private static final ArrayList<String> ListTaskNewUrgentNotClosed = new ArrayList<>();
    private static final ArrayList<String> NewError = new ArrayList<>();
    private static final ArrayList<String> NewIncident = new ArrayList<>();
    private static final ArrayList<String> NewAppeal = new ArrayList<>();
    private static final ArrayList<String> NewRequests = new ArrayList<>();
    private static final ArrayList<String> ClosedAppealEndIncident = new ArrayList<>();
    private static final ArrayList<String> Other = new ArrayList<>();
    private static final ArrayList<String> PlanOnTomorrow = new ArrayList<>();

    private static final ArrayList<String> HeaderForReport = new ArrayList<>();


    public static void setListTask(String issuesString) {
        ReportList.add(issuesString);
    }

    public static ArrayList<String> getListTask() {
        ReportList.add(":exclamation: *Незакрытые срочные обращения, инциденты и ошибки:*"+"\n");
        for (String i : ListTaskNewUrgentNotClosed) {
            setListTask(i);
        }
        ReportList.add(":exclamation: *Новые ошибки:*"+"\n");
        for (String i : NewError) {
            setListTask(i);
        }
        ReportList.add(":warning: *Новые инциденты:*"+"\n");
        for (String i : NewIncident) {
            setListTask(i);
        }
        ReportList.add(":warning: *Новые обращения:*"+"\n");
        for (String i : NewAppeal) {
            setListTask(i);
        }
        ReportList.add("*Новые реквесты:*"+"\n");
        for (String i : NewRequests) {
            setListTask(i);
        }
        ReportList.add("*Закрытые обращения и инциденты:*"+"\n");
        for (String i : ClosedAppealEndIncident) {
            setListTask(i);
        }
        ReportList.add("*Другое (что я сделал еще):*"+"\n");
        for (String i : Other) {
            setListTask(i);
        }
        ReportList.add("*План на завтра:*"+"\n");
        for (String i : PlanOnTomorrow) {
            setListTask(i);
        }
        return ReportList;
    }

    /*Незакрытые срочные обращения, инциденты и ошибки*/

    public static void setListTaskNewUrgentNotClosed(String issuesString) {
        ListTaskNewUrgentNotClosed.add(issuesString);
    }
    /*Новые ошибки*/

    public static void setListTaskNewError(String issuesString) {
        NewError.add(issuesString);
    }
    /*Новые инциденты*/

    public static void setListTaskNewIncident(String issuesString) {
        NewIncident.add(issuesString);
    }
    /*Новые обращения*/

    public static void setListTaskNewAppeal(String issuesString) {
        NewAppeal.add(issuesString);
    }
    /*Закрытые обращения и инциденты*/

    public static void setListTaskClosedAppealEndIncident(String issuesString) {
        ClosedAppealEndIncident.add(issuesString);
    }
    /*Новые реквесты*/

    public static void setListTaskNewRequests(String issuesString) {
        NewRequests.add(issuesString);
    }
    /*Другое (что я сделал еще)*/

    public static void setListTaskOther(String issuesString) {
        Other.add(issuesString);
    }
    /*План на завтра*/

    public static void setListTaskPlanOnTomorrow(String issuesString) {
        PlanOnTomorrow.add(issuesString);
    }

    public static ArrayList<String> getListTaskNewUrgentNotClosed() {
        return ListTaskNewUrgentNotClosed;
    }

    public static ArrayList<String> getNewError() {
        return NewError;
    }

    public static ArrayList<String> getNewIncident() {
        return NewIncident;
    }

    public static ArrayList<String> getNewAppeal() {
        return NewAppeal;
    }

    public static ArrayList<String> getClosedAppealEndIncident() {
        return ClosedAppealEndIncident;
    }

    public static ArrayList<String> getNewRequests() {
        return NewRequests;
    }

    public static ArrayList<String> getOther() {
        return Other;
    }

    public static ArrayList<String> getPlanOnTomorrow() {
        return PlanOnTomorrow;
    }
}