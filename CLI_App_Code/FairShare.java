import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/**
 * FairShare CommandLine Application
 */

public class FairShare {
  
  	//Hashmap containing UserName-Expense Record
    static HashMap<String, Float> userExpenseHashMap = new HashMap<>();
    // List containing Registered users
    static List<String> usersList = new ArrayList<>();
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
            System.out.printf("%.2f\n", calculateReport(str[1]));
        } else {
            System.out.println("Error");
        }
    }
	
  	//Computing the Report for a user
    public static float calculateReport(String str) {
        float total = 0;

        for(Float value: userExpenseHashMap.values()) {
            total += value;
        }

        float share = total / usersList.size();
        if(userExpenseHashMap.get(str) == null) {
            userExpenseHashMap.put(str, 0F);;
        }     
        result = (userExpenseHashMap.get(str) - share);
        return result;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String[] input;
        try {
            while (true) {
                input = in.nextLine().split("\\s+");
                if(input[0].equals("end")) {
                    break;
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