import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static int idCount = 0;
    public static int index = 0;
    public static final int CONTACT_ID = 0;
    public static final int CONTACT_NAME = 1;
    public static final int CONTACT_NUMBER = 2;
    public static final int COMPANY_NAME = 3;
    public static final int SALARY = 4;
    public static final int DOB = 5;
    public static final LocalDate TODAY = LocalDate.now();
    public static String[][] contacts = new String[1][6];

    public static void main(String[] args) {
        clearConsole();
        homePage();

    }

    public static void homePage() {
        Scanner sc = new Scanner(System.in);

        System.out.println("******************");
        System.out.println("iFRIEND");
        System.out.println("Contacts Organizer");
        System.out.println("******************");
        System.out.println();

        System.out.println("\t[01] ADD Contacts");
        System.out.println("\t[02] UPDATE Contacts");
        System.out.println("\t[03] DELETE Contacts");
        System.out.println("\t[04] SEARCH Contacts");
        System.out.println("\t[05] LIST Contacts");
        System.out.println("\t[06] Exit\n");
        System.out.print("Enter an option to continue -> ");

        int option = sc.nextInt();

        clearConsole();
        switch (option) {
            case 1:
                sc.nextLine();

                addContacts(sc);
                break;
//            case 2:
//                updateContacts(sc);
//                break;
//            case 1: deleteContacts();break;
//            case 1: searchContacts();break;
//            case 1: listContacts();break;
            case 6:
                System.exit(0);
            default:
                homePage();
        }


    }

    public static void addContacts(Scanner sc) {
        System.out.println("************");
        System.out.println("Add Contacts");
        System.out.println("************\n");

        String contactID = setContactID();
        System.out.println(contactID + "\n=====\n");

        String name = setContactName(sc);  //set contact name

        //set contact number
        String number = setContactNumber(sc);
        System.out.println(number);
        if (number == null) {
            System.out.println("\n\tInvalid Number!");
            System.out.print("\nDo you want to add number again(Y/N) -> ");

            String option = sc.nextLine();
            if (option.equalsIgnoreCase("y")) {
                // Move the cursor up five lines
                System.out.print("\033[5A");
                // Clear the lines
                System.out.print("\033[0J");
                number = setContactNumber(sc);
                System.out.println(number);//TODO: Remove this line
            } else if (option.equalsIgnoreCase("n")) {
                clearConsole();
                homePage();
            } else {
                System.out.print("Enter Correct option to continue -> ");
                //TODO: Handel the else statement
            }
        }

        // set company
        String company = setCompany(sc);

        //set salary
        String salary = setSalary(sc);

        //set DOB
        sc.nextLine();
        String dob = setDOB(sc);
        if (dob == null) {
            System.out.println("\n\tInvalid Birthday!");
            System.out.print("\nDo you want to add birthday again(Y/N) -> ");

            String option = sc.nextLine();
            if (option.equalsIgnoreCase("y")) {
                // Move the cursor up five lines
                System.out.print("\033[5A");
                // Clear the lines
                System.out.print("\033[0J");
                dob = setDOB(sc);
            } else if (option.equalsIgnoreCase("n")) {
                homePage();
            } else {
                System.out.print("Enter Correct option to continue -> ");
                //TODO: Handel the else statement
            }
        }
//        System.out.println(contactID);
//        System.out.println(name);
//        System.out.println(number);
//        System.out.println(company);
//        System.out.println(salary);
//        System.out.println(dob);
        toContacts(contactID, name, number, company, salary, dob);
        //TODO:Remove this for loop
        for (String[] contact:contacts){
            System.out.println(Arrays.toString(contact));
        }

        System.out.print("\nDo you want to add a new contact again? (Y/N) ->");
        String option=sc.nextLine();

        if(option.equalsIgnoreCase("y")){
            clearConsole();
            addContacts(sc);
        } else if (option.equalsIgnoreCase("n")) {
            clearConsole();
            homePage();
        }else {
            //TODO:handel the else statement
        }
    }

    private static void toContacts(String contactID, String name, String number, String company, String salary, String dob) {
        if (index == contacts.length) {
            extendContacts();
        }
        contacts[index][CONTACT_ID] = contactID;
        contacts[index][CONTACT_NAME] = name;
        contacts[index][CONTACT_NUMBER] = number;
        contacts[index][COMPANY_NAME] = company;
        contacts[index][SALARY] = salary;
        contacts[index][DOB] = dob;

        index++;
    }

    private static void extendContacts() {
        String[][] temp = new String[index + 1][6];
        for (int i = 0; i < contacts.length; i++) {
            for (int j = 0; j < contacts[i].length; j++) {
                temp[i][j] = contacts[i][j];
            }
        }
        contacts = temp;
    }

    private static String setDOB(Scanner sc) {
        System.out.print("DOB(YYYY-MM-DD)\t: ");
        String dob = sc.nextLine();

        boolean isValidDOB = isValidDate(dob); //validate date in the correct format and if it is a future date
        if (isValidDOB) {
            return dob;
        } else {
            return null;
        }
    }

    private static boolean isValidDate(String date) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate d1 = LocalDate.parse(date, dtf);
            return d1.isBefore(TODAY);// return true or false whether it is future date or not
        } catch (DateTimeException dte) {
            return false; // return false date not in the correct format
        }
    }

    private static String setSalary(Scanner sc) {
        System.out.print("Salary\t\t: ");
        double salary = sc.nextDouble();
        return Double.toString(salary);
        //TODO:InputMismatchException
    }

    private static String setCompany(Scanner sc) {
        System.out.print("COMPANY\t\t: ");
        return sc.nextLine();
    }

    private static String setContactNumber(Scanner sc) {
        System.out.print("PHONE NUMBER\t: ");
        String number = sc.nextLine();

        System.out.println(validateNumber(number));
        System.out.println(searchContacts(number));

        if (validateNumber(number) && searchContacts(number)) {
            return number;
        } else {
            return null;
        }
    }

    private static boolean validateNumber(String number) {
        return number.length() == 5 && number.charAt(0) == '0';
    }

    private static boolean searchContacts(String data) {
        try {
            for (String[] contact : contacts) {
                if (contact[CONTACT_NUMBER].equals(data)) {
                    return true; // if contact is present
                }
            }
        } catch (Exception e) {
            System.out.println("In exception");//TODO: Remove this line
            return true; // for exception
        }
        return false; // if contact is not present
    }

    private static String setContactName(Scanner sc) {
        System.out.print("NAME\t\t: ");
        return sc.nextLine();
    }

    private static String setContactID() {
        String str = String.format("C%04d", idCount + 1);
        idCount++;
        return str;
    }


    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c",
                        "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
            // Handle any exceptions.
        }
    }
}