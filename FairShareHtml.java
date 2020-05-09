import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
 * FairShare CommandLine Application with Html output
 */

public class FairShareHtml {
  
  	//Hashmap containing UserName-Expense Record
    static HashMap<String, Float> userExpenseHashMap = new HashMap<>();
    // List containing Registered users
    static List<String> usersList = new ArrayList<>();
    static List<Float> reportsList = new ArrayList<>();
    static float result;
    
  	//This Method processes the input of commandline for different operations
  	public static void doStuff(String[] str) {
        String mode = str[0];
        if(mode.equalsIgnoreCase("Register") || mode.equalsIgnoreCase("reg"))
            doRegistration(str);
        else if(mode.equalsIgnoreCase("Expense") || mode.equalsIgnoreCase("exp"))
            doExpenses(str);
        else if(mode.equalsIgnoreCase("Report") || mode.equalsIgnoreCase("rep"))
            doReport(str);
        else
            System.out.println("System has an Invalid Mode Entered: " + mode);
    }
  	//Register a user
    public static void doRegistration(String[] str){
        for(int i = 1; i < str.length; i++) {
            usersList.add(str[i]);
        }
    }
  	//Store the Expense
    public static void doExpenses(String[] str){
        if(usersList.contains(str[1])) {
            try {
                if(userExpenseHashMap.containsKey(str[1])) {
                    userExpenseHashMap.put(str[1], Float.parseFloat(str[2]) + userExpenseHashMap.get(str[1]));
                } else {
                    userExpenseHashMap.put(str[1], Float.parseFloat(str[2]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error");
        }
    }
	
  	//Report the fair Amount
    public static void doReport(String[] str){
        if(usersList.contains(str[1])) {
            reportsList.add(calculateReport(str[1]));
        } else {
            System.out.println("Error");
        }
    }
	
  	//Computing the Report for a user
    public static Float calculateReport(String str) {
        float total = 0;

        for(Float value: userExpenseHashMap.values()) {
            total += value;
        }

        float share = total / usersList.size();
        if(userExpenseHashMap.get(str) == null) {
            userExpenseHashMap.put(str, 0F);;
        }     
        result = (userExpenseHashMap.get(str) - share);
        return new Float(result);
    }
    public static void computeHtml(Float result){
        // Header Part of the HTML String
        String outputMsg = 
            "<!DOCTYPE html>\n"+
            "<html lang='en'>\n"+
            "<head>\n"+
                "<meta charset='UTF-8'>\n"+
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n"+
                "<meta http-equiv='X-UA-Compatible' content='ie=edge'>\n"+
                "<title>FairShare Application</title>\n"+
                "<style>* {\n"+
                    "padding: 0px;\n"+
                    "margin: 0px;\n"+
                    "box-sizing: border-box;\n"+
                    "}\n"+
                    "header {\n"+
                        "margin-top : 0px;\n"+
                        "margin-bottom : 0px;\n"+
                        "color: teal;\n"+
                        "text-align: center;\n"+
                        "padding : 5px;\n"+
                        "background-color: #fff;\n"+
                        "box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);\n"+  
                        "}\n"+
                        "header p {\n"+
                            "color: black;\n"+
                            "}\n"+
                            "body{\n"+
                                "font-family: 'Roboto', sans-serif;\n"+
                                "background-color: #ececec;\n"+
                                "font-size: 16px;\n"+
                                "}\n"+
                                "section{\n"+
                                    "background-color: #fff;\n"+
                                    "margin: 3% auto;\n"+
                                    "width: 96%;\n"+
                                    "border: none;\n"+
                                    "padding: 10px 35px;\n"+
                                    "transition: all 0.5s;\n"+
                                    "box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);\n"+
                                    "}\n"+
                                    "section h2{\n"+
                                        "color: teal;\n"+
                                        "text-align: left;\n"+
                                        " margin-bottom: 5px;\n"+
                                        "}\n"+
                                        "section ol{\n"+
                                            "list-style-type: none;\n"+
                                            "}\n"+
                                            "section ol li{\n"+
                                                "padding: 5px;\n"+
                                                "}\n"+
                                                ".info{\n"+
                                                    "padding-top: 15px;\n"+
                                                    "color: red;\n"+
                                                    "font-style: italic;\n"+
                                                    "}\n"+
                                                    ".footer{\n"+
                                                        "padding: 10px;\n"+
                                                        "text-align: center;\n"+
                                                        "background-color: #fff;\n"+
                                                        "margin-top: 135px;\n"+
                                                        "margin-bottom: 10px;\n"+
                                                        "box-shadow: 0px 0px 10px 5px rgba(0,0,0,0.1);\n"+
                                                        "}\n"+
                                                        "</style>\n"+
            "</head>\n"+
            "<body>\n"+
                "<header>\n"+
                    "<h1>FairShare Application</h1>\n"+
                    "<p>Welcome! This is the command line version of the app with HTML + CSS output.</p>\n"+
                "</header>\n";
        // Register section of the HTML String        
        outputMsg += "<section class='register'>\n"+
                        "<h2>RoomMate Registrations Record</h2>\n";
        if(!usersList.isEmpty()){
            outputMsg += "<p>Registered Roommates are:</p>\n"+
                        "<ol>\n";
            for(int i=0; i < usersList.size(); i++)
                outputMsg += "%s".format("<li>" + usersList.get(i) + "</li>"+ "\n");
            outputMsg += "</ol>\n"+
                    "</section>\n";
        }
        else{
            outputMsg += "<p>No Roommates Registered</p>\n"+
                        "</section>\n";
        }
        // Expenses Record section of the HTML String
        outputMsg += "<section class='expenses'>\n"+
                            "<h2>Expenses Record</h2>\n";                            
        if(!userExpenseHashMap.isEmpty() ){
            outputMsg += "<ol>\n";
            for(String name : userExpenseHashMap.keySet())
                outputMsg += "%s".format("<li>" + name +" "+ userExpenseHashMap.get(name) + "</li>"+ "\n");
            outputMsg += "</ol>\n"+
                        "</section>\n";
        }
        else{
            outputMsg += "<p>No Expenses Recorded</p>\n"+
                        "</section>\n";
        }
        //Report Section of the HTML String 
            outputMsg += "<section class='report'>\n"+
                            "<h2>Report</h2>\n";
            if(!reportsList.isEmpty()){
                outputMsg += "<p>All the pending dues are:</p>\n"+
                            "<ol>\n";
                for(int i=0; i < reportsList.size(); i++)
                    outputMsg += "%s".format("<li>"+ " " + reportsList.get(i) + "</li>"+ "\n");
                outputMsg += "</ol>\n";
            }
            else{
                 outputMsg += "<p>No expenses to Report</p>\n";
            }
                outputMsg += "<p class='info'>Negative values here refer to the amount owed, Positive values refer to Values to be recieved.</p>\n"+
                            "</section>\n";
        // Footer Section of the HTML String
            outputMsg +=  "<footer class = 'footer'>\n"+
                            "<p>Made with &heartsuit; by Jaskirat Singh</p>\n"+
                        "</footer>\n"+
                    "</body>\n"+
                    "</html>\n";
        System.out.println(outputMsg);
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] input;
        try {
            while (true) {
                input = in.nextLine().split("\\s+");
                if(input[0].equals("end")) {
                    computeHtml(result);
                } else {
                    doStuff(input);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
    }
}