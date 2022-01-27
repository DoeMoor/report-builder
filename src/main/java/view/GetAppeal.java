package view;

import controller.RedmineConnector;
import model.Appeal;
import model.Report;

import java.io.IOException;


public class GetAppeal {

    Appeal appeal;

//    public void setAppealInList(String id) throws IOException {
//        appeal = new Appeal(new RedmineConnector().getTask(id));
//
//        if (appeal.getStatus().equals("Новая")
//                || appeal.getStatus().equals("Новый")
//                || appeal.getStatus().equals("В работе")
//                || appeal.getStatus().equals("Запланирована")
//                || appeal.getStatus().equals("На доработку")
//                || appeal.getStatus().equals("На паузе")) {
//
//            Report.setListTask(getNewAppeal(appeal));
//        } else {
//            Report.setListTask(getClosedAppeal(appeal));
//        }
//    }


    public void setAppealInList(String id) throws IOException {
        appeal = new Appeal(new RedmineConnector().getTask(id));

        if (appeal.getStatus().equals("Новая")
                || appeal.getStatus().equals("Новый")
                || appeal.getStatus().equals("В работе")
                || appeal.getStatus().equals("Запланирована")
                || appeal.getStatus().equals("На доработку")
                || appeal.getStatus().equals("На паузе")) {

            //    Незакрытые срочные обращения, инциденты и ошибки
            if (!appeal.getPriority().equals("Нормальный")
                    && !appeal.getTrackerName().equals("Request")) {
                Report.setListTaskNewUrgentNotClosed(getNewAppeal(appeal));

                //    Новые ошибки
            } else if (appeal.getTrackerName().equals("Ошибка")) {
                Report.setListTaskNewError(getNewAppeal(appeal));

                //    Новые инциденты
            } else if (appeal.getTrackerName().equals("Инцидент")) {
                Report.setListTaskNewIncident(getNewAppeal(appeal));

                //    Новые обращения
            } else if (appeal.getTrackerName().equals("Обращение")) {
                Report.setListTaskNewAppeal(getNewAppeal(appeal));

                //    Новые реквесты
            } else if (appeal.getTrackerName().equals("Request")) {
                Report.setListTaskNewRequests(getNewAppeal(appeal));
            }

        }else{
            //    Закрытые обращения и инциденты
            Report.setListTaskClosedAppealEndIncident(getClosedAppeal(appeal));
        }

    }


    private String getNewAppeal(Appeal appeal) {
        switch (appeal.getTrackerName()) {
            case "Обращение":
                if (appeal.isChildren()) {
                    return String.format("[%s %s](http://team.progredis.ru/issues/%s):" +
                                    " %s\n(%s, %s, приоритет: %s)\n(%s, %s, %s, %s)\n",
                            appeal.getTrackerName(), appeal.getId(), appeal.getId(), appeal.getSubject(),
                            appeal.getIncomeChannel(), appeal.getTaskFrom(), appeal.getPriority(),
                            appeal.getLinkedTasksAndId(), appeal.getLinkedTasksChildrenAndId(),
                            appeal.getAssignedToTaskInLinkedTasksChildren(), appeal.getDueDate()
                    );
                } else if (!appeal.getRelations().equals("Not have relations")) {
                    return String.format("[%s %s](http://team.progredis.ru/issues/%s):" +
                                    " %s\n(%s, %s, приоритет: %s)\n(Связано с обращением #%s)\n",
                            appeal.getTrackerName(), appeal.getId(), appeal.getId(), appeal.getSubject(),
                            appeal.getIncomeChannel(), appeal.getTaskFrom(), appeal.getPriority(),
                            appeal.getRelations()
                    );
                } else {
                    return String.format("[%s %s](http://team.progredis.ru/issues/%s):" +
                                    " %s\n(%s, %s, приоритет: %s)\n(Обращение не обработано.)\n",
                            appeal.getTrackerName(), appeal.getId(), appeal.getId(), appeal.getSubject(),
                            appeal.getIncomeChannel(), appeal.getTaskFrom(), appeal.getPriority()
                    );
                }

            case "Ошибка":

                if (appeal.getPriority().equals("Высокий") || appeal.getPriority().equals("Немедленный")) {

                    return String.format("[%s %s](http://team.progredis.ru/issues/%s): %s\n(%s, связано: %s)\n",
                            appeal.getTrackerName(), appeal.getId(), appeal.getId(), appeal.getSubject(),
                            appeal.getAssignedTo(), appeal.getLinkedParent()
                    );
                } else {

                    return String.format("[%s %s](http://team.progredis.ru/issues/%s):" +
                                    " %s\n(%s, приоритет: %s, сделать до: %s, %s)\n",
                            appeal.getTrackerName(), appeal.getId(), appeal.getId(), appeal.getSubject(),
                            appeal.getAssignedTo(), appeal.getPriority(), appeal.getDueDate(),
                            appeal.getLinkedParent()
                    );
                }

            case "Инцидент":

                return String.format("[%s %s](http://team.progredis.ru/issues/%s): %s" +
                                "\n(%s, %s, приоритет: %s)" +
                                "\n(Поставлена задача: %s, %s, %s)\n",
                        appeal.getTrackerName(), appeal.getId(), appeal.getId(), appeal.getSubject(),
                        appeal.getIncomeChannel(), appeal.getCreatedDate(), appeal.getPriority(),
                        appeal.getLinkedTasksAndId(), appeal.getAssignedToTaskInLinkedTasksChildren(), appeal.getLinkedParent()
                );

            case "Request":

                return String.format("[%s %s](http://team.progredis.ru/issues/%s): %s\n(%s)\n",
                        appeal.getTrackerName(), appeal.getId(), appeal.getId(), appeal.getSubject(),
                        appeal.getLinkedParent()

                );
        }

        return "Ошибка, тип заявки :" + appeal.getTrackerName();

    }

    private String getClosedAppeal(Appeal appeal) {

        if (appeal.getTrackerName().equals("Инцидент")) {

            return String.format("[%s %s](http://team.progredis.ru/issues/%s): %s" +
                            "\n(Связано: %s)" +
                            "\n(%s)\n",
                    appeal.getTrackerName(), appeal.getId(), appeal.getId(), appeal.getSubject(),
                    appeal.getLinkedParent(),
                    appeal.getReasonFoTask()
            );
        } else {

            return String.format("[%s %s](http://team.progredis.ru/issues/%s): %s" +
                            "\n(%s, %s,%s)" +
                            "\n(%s)\n",
                    appeal.getTrackerName(), appeal.getId(), appeal.getId(), appeal.getSubject(),
                    appeal.getIncomeChannel(), appeal.getTaskFrom(), appeal.getIncomeDate(),
                    appeal.getReasonFoTask()
            );
        }
    }

    private void stopItGetSomeHelp() {

    }


}
