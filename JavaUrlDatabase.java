package nuchange;

import org.w3c.dom.ls.LSOutput;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class JavaUrlDatabase {


    public static void main(String[] args) {
        System.out.println("For help: help");
        System.out.println("To start the program: run java url database");
        System.out.print("Enter Input: ");
        Scanner sc = new Scanner(System.in);
        String startPgm = sc.nextLine();
        if (startPgm.toLowerCase().equals("help")) {
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("Enter 'storUrl <space> url' to store url (eg: storUrl google.com) ");
            System.out.println("Enter 'get <space> url' to display unique short key assigned to the url");
            System.out.println("Enter 'count <space> url' to display latest usage count of the url");
            System.out.println("Enter 'list' to display all urls and counts.");
            System.out.println("Enter 'delete <space> url' to delete the url details from database");
            System.out.println("Enter 'Exit' to exit the program");
            System.out.println("------------------------------------------------------------------------------");
            main(null);
        } else if (startPgm.toLowerCase().equals("run java url database")) {
            HashMap<String, Integer> urlMap = new HashMap<String, Integer>();
            HashMap<String, String> shortkey = new HashMap<String, String>();
            String userEntry;
            String systemCommand;
            String url = "";
            String newUrl = "";

            do {
                System.out.print("Enter command: ");
                userEntry = sc.nextLine();
                String[] splitValue = userEntry.split(" ");
                systemCommand = splitValue[0];

                if (splitValue.length > 1 && splitValue[1].length() > 1 && !systemCommand.equalsIgnoreCase("list")) {
                    url = splitValue[1];
                } else if (!systemCommand.equalsIgnoreCase("list")) {
                    System.out.println("URL can not be empty  OR invalid entry");
                    continue;
                }
                switch (systemCommand) {
                    case "storeurl":
                        urlMap.put(url, 0);
                        shortkey.put(url, getUniqueStringEveryTime());
                        break;
                    case "get":
                        if (urlMap.containsKey(url)) {
                            System.out.println("Unique Short_key for " + url + " is: " + shortkey.get(url));
                            urlMap.put(url, urlMap.get(url) + 1);
                        } else System.out.println(url + " not found, please check the input");
                        break;
                    case "count":
                        if (urlMap.containsKey(url)) {
                            System.out.println("Usage count for " + url + " is: " + urlMap.get(url));
                        } else System.out.println(url + " not found, please check the input");
                        break;
                    case "delete":
                        if (urlMap.containsKey(url)) {
                            urlMap.remove(url);
                            System.out.println("url " + url + " successfully removed from the database");
                        } else System.out.println(url + " not found, please check the input");
                        break;

                    case "list":
                        toJson(urlMap);
                        break;
                    default:
                        System.out.println("Invalid entry");
                        break;
                }
            } while (!systemCommand.equalsIgnoreCase("exit"));
            sc.close();
        }
    }

    public static synchronized String getUniqueStringEveryTime() {

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String datetime = ft.format(dNow);
        return datetime;

    }

    public static void toJson(HashMap<String, Integer> urlMap) {

        if (!urlMap.isEmpty()) {
            System.out.println("[");
            int commasize = urlMap.size();
            for (String url : urlMap.keySet()) {
                System.out.println("{ \n" +
                        '\u0022' + "url" + '\u0022' + " : "
                        + '\u0022' + url + '\u0022' + ",\n" +
                        '\u0022' + "count" + '\u0022' + " : "
                        + urlMap.get(url));
                if (commasize > 1) {
                    System.out.println("},");
                    commasize--;
                } else System.out.println("}");

            }
            System.out.println("]");
        } else System.out.println("Database is empty rightnow");

    }


}





