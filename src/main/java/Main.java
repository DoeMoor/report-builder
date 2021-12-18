import controller.RedmineConnector;
import model.PrivateData;
import model.Report;
import view.GetAppeal;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        RedmineConnector gt = new RedmineConnector();
        PrivateData privateData = new PrivateData(); //TODO refactor config

        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while (true) {

            System.out.printf("PrivateData.getKeyValue() = %s \nPrivateData.getURL() = %s \n",
                    PrivateData.getKeyValue(), PrivateData.getURL()
            );

            System.out.print("1 = открытые \n2 = закрытые \nВвести типы задач :");

            String taskId = bufferedReader.readLine();

            switch (taskId) {
                case "1":
                    taskId = gt.getTaskList("open", "created_on");
                    break;

                case "2":
                    taskId = gt.getTaskList("closed", "closed_on");
                    break;

                case "q":
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.exit(0);

                case "":
                    for (String i : Report.getListTask()
                    ) {
                        System.out.println(i);
                    }
                    break;
                default:
                    break;
            }

            if (!taskId.equals("")) {
                String[] listTaskId = taskId.split(" ");
                for (String i : listTaskId
                ) {
                    GetAppeal appeal = new GetAppeal();
                    appeal.setAppealInList(i);
                }
            }
        }


























    /*    InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        System.out.print("Ввести номер задачи : ");

        Integer taskId = Integer.valueOf(bufferedReader.readLine());

        System.out.print("Ввести имя пользователя : ");

        String assignTaskUser = bufferedReader.readLine();
        if (assignTaskUser.equals("Юра")){
            assignTaskUser ="@pregradov";
        } else if
            (assignTaskUser.equals("Саша")) {
            assignTaskUser = "@astashkinav";
        } else if (!assignTaskUser.equals("@pregradov") || !assignTaskUser.equals("Юра") ){
            System.out.println("Пользователь не найден");
        }


       *//* System.out.print("Ввести номер хотфикса :");

        Integer taskErrorId = Integer.valueOf(bufferedReader.readLine());*//*

        HttpResponse<String> response = Unirest.get("http://team.progredis.ru/issues/{taskId}.json")
                .routeParam("taskId",String.valueOf(taskId))
                .queryString("key","46fde5cffcec5a129961b2b99bc904c161d52c4c")
                .queryString("include","children")
                .asString();
        ObjectMapper respMap = new ObjectMapper();
        JsonNode answer  = respMap.readTree(response.getBody());

        String trackerName = answer.get("issue").get("tracker").get("name").asText();
        String id ="#" + answer.get("issue").get("id").asText();
        String subject = answer.get("issue").get("subject").asText();


        String priority = answer.get("issue").get("priority").get("name").asText();

        String incomeCanal = answer.get("issue").get("custom_fields").get(0).get("value").asText();
        String taskFrom = answer.get("issue").get("custom_fields").get(3).get("value").asText();
        String due_date = answer.get("issue").get("due_date").asText();

        String linkedTasks = answer.get("issue").get("children").get(0).get("tracker").get("name").asText()
                + " #" +
                answer.get("issue").get("children").get(0).get("id") .asText() ;

        String linkedTasksChildren = answer.get("issue").get("children").get(0).get("children").get(0).get("tracker").get("name").asText()
                + " #" +
                answer.get("issue").get("children").get(0).get("children").get(0).get("id").asText() ;

*/

    }
}
