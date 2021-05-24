import controller.GetAppeal;
import model.PrivateData;
import model.Report;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {


        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        while (true) {
            System.out.print("Ввести номеа задач : ");
            String taskId = bufferedReader.readLine();

            if (taskId.equals("q")) {
                break;
            }
            if (taskId.equals("print")){

                for (String i : Report.getListTask()
                ) {
                    System.out.println(i);
                }

                break;

            }

            String[] listTaskId = taskId.split(",");

            for (String i : listTaskId
            ) {
                GetAppeal appeal = new GetAppeal();
                appeal.setAppealInList(i);
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
