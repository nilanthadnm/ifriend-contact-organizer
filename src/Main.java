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
    private static final LocalDate TODAY = LocalDate.now();
    private static String[][] contacts = new String[1000][6];

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
            case 2:
                sc.nextLine();
                updateContacts(sc);
                break;
            case 3:
                sc.nextLine();
                deleteContacts(sc);
                break;
            case 4:
                sc.nextLine();
                searchContacts(sc);
                break;
            case 5:
                listContacts(sc);
                break;
            case 6:
                System.exit(0);
            default:
                homePage();
        }
    }

    public static void listContacts(Scanner sc) {
        System.out.println("***************");
        System.out.println("SORT Contacts");
        System.out.println("*************\n");

        System.out.println("\n\t[01] SORTED BY NAME");
        System.out.println("\t[02] SORTED BY SALARY");
        System.out.println("\t[03] SORTED BY DOB");

        System.out.print("\nEnter option to continue ->");
        int option = sc.nextInt();

        switch (option) {
            case 1:
                clearConsole();
                byName(sc);
                break;
            case 2:
                clearConsole();
                bySalary(sc);
                break;
//            case 3:clearConsole();byDOB(sc);break;
        }
    }

    private static void bySalary(Scanner sc) {
        System.out.println("**************");
        System.out.println("Sorted by Salary");
        System.out.println("**************");

        String[][] temp = getSortedSalary();
        print(temp);
        listSuccess(sc);
    }

    private static String[][] getSortedSalary() {
        String[][] temp = getClone();

        double[] tempSalary = getSalary(temp);//get salary values in to double array

        for (int i = 1; i < index; i++) {
            for (int j = 0; j < i; j++) {
                if (tempSalary[j] > tempSalary[i]) {
                    double value = tempSalary[j];
                    tempSalary[j] = tempSalary[i];
                    tempSalary[i] = value;

                    //sorting contacts
                    String[] contact = temp[j];
                    temp[j] = temp[i];
                    temp[i] = contact;
                }
            }
        }
        return temp;
    }

    private static double[] getSalary(String[][] temp) {
        /*
         * this method store  string value of salary
         *  in a contact to double array
         * */
        double[] tempSalary = new double[temp.length];

        for (int i = 0; i < index; i++) {
            tempSalary[i] = Double.parseDouble(temp[i][SALARY]);
        }
        System.out.println(Arrays.toString(tempSalary));
        return tempSalary;
    }

    private static void byName(Scanner sc) {
        System.out.println("**************");
        System.out.println("Sorted by Name");
        System.out.println("**************");

        String[][] temp = getSortedName();
        print(temp);
        listSuccess(sc);
    }

    private static void listSuccess(Scanner sc) {
        System.out.print("Do you want to go back (Y/N)-> ");
        String option = sc.nextLine();

        if (option.equalsIgnoreCase("y")) {
            clearConsole();
            listContacts(sc);
        } else if (option.equalsIgnoreCase("n")) {
            clearConsole();
            homePage();
        } else {
            //TODO:handel this with clearConsoleEightLines()
        }
    }

    private static String[][] getSortedName() {
        String[][] temp = getClone();
        for (int i = 0; i < index; i++) {
            for (int j = 0; j < index - 1; j++) {
                for (int k = j + 1; k < index; k++) {
                    if (temp[j][CONTACT_NAME].compareTo(temp[k][CONTACT_NAME]) > 0) {
                        String[] contact = temp[j];
                        temp[j] = temp[k];
                        temp[k] = contact;
                    }
                }
            }
        }
        return temp;
    }

    private static String[][] getClone() {
        return contacts.clone();
    }

    public static void searchContacts(Scanner sc) {
        System.out.println("***************");
        System.out.println("Search Contacts");
        System.out.println("*************\n");

        //get contact details from the array and print it
        String[] contact = getContact(sc);
        printContact(contact);

        //search success
        System.out.print("Do you want to search another contact (Y/N)-> ");
        String option = sc.nextLine();

        if (option.equalsIgnoreCase("y")) {
            clearConsole();
            searchContacts(sc);
        } else if (option.equalsIgnoreCase("n")) {
            clearConsole();
            homePage();
        } else {
            System.out.println("\nIncorrect Input, Enter again!");
        }
    }

    public static void deleteContacts(Scanner sc) {
        System.out.println("***************");
        System.out.println("Delete Contacts");
        System.out.println("*************\n");

        //get contact details from the array and print it
        String[] contact = getContact(sc);
        printContact(contact);

        //get contact index
        int i = search(contact[CONTACT_NUMBER]);

        System.out.print("\nDo you want to delete this contact(Y/N) -> ");
        String option = sc.nextLine();

        if (option.equalsIgnoreCase("y")) {
            removeContact(i);
        } else if (option.equalsIgnoreCase("n")) {
            clearConsole();
            homePage();
        } else {
            //TODO: Handel else statement
        }
        deleteSuccess(sc);
    }

    private static void deleteSuccess(Scanner sc) {
        System.out.println("\n\tContact successfully has been deleted! ");

        System.out.print("Do you want to delete another contact (Y/N)-> ");
        String option = sc.nextLine();

        if (option.equalsIgnoreCase("y")) {
            clearConsole();
            deleteContacts(sc);
        } else if (option.equalsIgnoreCase("n")) {
            clearConsole();
            homePage();
        } else {
            //TODO:handel this with clearConsoleEightLines()
        }
    }

    private static void removeContact(int i) {
        String[][] temp = new String[contacts.length - 1][6];

        for (int j = i; j < contacts.length - 1; j++) {
            contacts[i] = contacts[i + 1];
        }
        for (int j = 0; j < temp.length; j++) {
            temp[j] = contacts[j];
        }
        index--;
        idCount--;
        contacts = temp;
    }

    public static void updateContacts(Scanner sc) {
        System.out.println("***************");
        System.out.println("Update Contacts");
        System.out.println("*************\n");

        String[] contact = getContact(sc); // get contact details as an array
        printContact(contact); //print contact details

        System.out.print("\nWhat do you want to update -> ");
        System.out.println("\n\t[01] NAME");
        System.out.println("\t[02] PHONE NUMBER");
        System.out.println("\t[03] COMPANY NAME");
        System.out.println("\t[04] SALARY");
        System.out.println("\t[05] GO BACK\n");

        System.out.print("Enter option to continue -> ");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                clearConsoleEightLines();
                sc.nextLine();
                updateName(sc, contact);
                break;
            case 2:
                clearConsoleEightLines();
                sc.nextLine();
                updatePhoneNumber(sc, contact);
                break;
            case 3:
                clearConsoleEightLines();
                sc.nextLine();
                updateCompanyName(sc, contact);
                break;
            case 4:
                clearConsoleEightLines();
                sc.nextLine();
                UpdateSalary(sc, contact);
                break;
            case 5:
                clearConsole();
                homePage();
        }

    }

    private static void UpdateSalary(Scanner sc, String[] contact) {
        System.out.println("UPDATE SALARY\n=============");
        System.out.print("\nEnter new ");
        String salary = setSalary(sc); //new company name

        //get contact index
        int i = search(contact[CONTACT_NUMBER]);

        //update contact details-company name
        contacts[i][SALARY] = salary;

        updateSuccess(sc);
    }

    private static void updateCompanyName(Scanner sc, String[] contact) {
        System.out.println("UPDATE COMPANY NAME\n===================");
        System.out.print("\nEnter new ");
        String company = setCompanyName(sc); //new company name

        //get contact index
        int i = search(contact[CONTACT_NUMBER]);

        //update contact details-company name
        contacts[i][COMPANY_NAME] = company;

        updateSuccess(sc);
    }

    private static void updatePhoneNumber(Scanner sc, String[] contact) {
        System.out.println("UPDATE PHONE NUMBER\n===================");
        System.out.print("\nEnter new ");
        String number = setContactNumber(sc);

        //get contact index
        int i = search(contact[CONTACT_NUMBER]);

        //update contact details-phone number
        contacts[i][CONTACT_NUMBER] = number;


        updateSuccess(sc);
    }


    private static void updateName(Scanner sc, String[] contact) {
        System.out.println("UPDATE NAME\n===========");
        System.out.print("\nEnter new ");
        String name = setContactName(sc); //new name

        //get contact index
        int i = search(contact[CONTACT_NUMBER]); //don't get negative value because given contact already in the list

        //update contact details
        contacts[i][CONTACT_NAME] = name;

        updateSuccess(sc);
    }

    private static void updateSuccess(Scanner sc) {
        System.out.println("\n\tContact has been successfully updated!\n");

        System.out.print("Do you want to update another contact (Y/N)-> ");
        String option = sc.nextLine();

        if (option.equalsIgnoreCase("y")) {
            clearConsole();
            updateContacts(sc);
        } else if (option.equalsIgnoreCase("n")) {
            clearConsole();
            homePage();
        } else {
            //TODO:handel this with clearConsoleEightLines()
        }
    }

    private static void printContact(String[] arr) {
        System.out.println();
        String[] fieldNames = {"CONTACT ID\t", "NAME\t\t", "PHONE NUMBER\t", "COMPANY\t\t", "SALARY\t\t", "DOB\t\t"};

        for (int i = 0; i < arr.length; i++) {
            System.out.println("\t" + fieldNames[i] + ": " + arr[i]);
        }
    }

    private static String[] getContact(Scanner sc) {
        System.out.print("Enter phone number or name: ");
        String data = sc.nextLine();

        String[] contact;
        if (data.charAt(0) == '0') {
            contact = search(data, CONTACT_NUMBER);
        } else {
            contact = search(data, CONTACT_NAME);
        }

        if (contact == null) {
            System.out.println("\n\tSearch failed!");
            System.out.print("\nDo you want to search again? (Y/N): ");

            String option = sc.nextLine();
            if (option.equalsIgnoreCase("y")) {
                clearConsoleFiveLines();
                contact = getContact(sc);
            } else if (option.equalsIgnoreCase("n")) {
                homePage();
            } else {
                System.out.print("Enter Correct option to continue -> ");
                //TODO: Handel the else statement
            }
        }
        return contact;
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

        //check for repeat numbers
        if (number == null) {
            System.out.println("\n\tInvalid Number!");
            System.out.print("\nDo you want to add number again(Y/N) -> ");

            String option = sc.nextLine();
            if (option.equalsIgnoreCase("y")) {
                clearConsoleFiveLines();
                number = setContactNumber(sc);
            } else if (option.equalsIgnoreCase("n")) {
                clearConsole();
                homePage();
            } else {
                System.out.print("Enter Correct option to continue -> ");
                //TODO: Handel the else statement
            }
        }

        // set company
        String company = setCompanyName(sc);

        //set salary
        String salary = setSalary(sc);

        //set DOB
        sc.nextLine();
        String dob = setDOB(sc);
        //check date
        if (dob == null) {
            System.out.println("\n\tInvalid Birthday!");
            System.out.print("\nDo you want to add birthday again(Y/N) -> ");

            String option = sc.nextLine();
            if (option.equalsIgnoreCase("y")) {
                clearConsoleFiveLines();
                dob = setDOB(sc);
            } else if (option.equalsIgnoreCase("n")) {
                homePage();
            } else {
                System.out.print("Enter Correct option to continue -> ");
                //TODO: Handel the else statement
            }
        }

        toContacts(contactID, name, number, company, salary, dob);

        System.out.print("\nDo you want to add a new contact again? (Y/N) ->");
        String option = sc.nextLine();

        if (option.equalsIgnoreCase("y")) {
            clearConsole();
            addContacts(sc);
        } else if (option.equalsIgnoreCase("n")) {
            clearConsole();
            homePage();
        } else {
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
        String[][] temp = new String[index + 500][6];
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

    private static String setCompanyName(Scanner sc) {
        System.out.print("COMPANY\t\t: ");
        return sc.nextLine();
    }

    private static String setContactNumber(Scanner sc) {
        System.out.print("PHONE NUMBER\t: ");
        String number = sc.nextLine();

        if (validateNumber(number) && !isContact(number)) {
            return number;
        } else {
            return null;
        }
    }

    private static boolean validateNumber(String number) {
        return number.length() == 10 && number.charAt(0) == '0';
    }

    private static boolean isContact(String data) {
        try {
            for (int i = 0; i <= index; i++) {
                if (contacts[i][CONTACT_NUMBER].equals(data)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private static int search(String data) {
        try {
            for (int i = 0; i <= index; i++) {
                if (contacts[i][CONTACT_NUMBER].equals(data)) {
                    return i;
                }
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    private static String[] search(String data, int filed) {
        try {
            for (int i = 0; i <= index; i++) {
                if (contacts[i][filed].equals(data)) {
                    return contacts[i];
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
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

    public static void clearConsoleEightLines() {
        System.out.print("\033[8A"); // Move the cursor up eight lines
        System.out.print("\033[0J");       // Clear the lines
    }

    public static void clearConsoleFiveLines() {
        System.out.print("\033[5A");    // Move the cursor up five lines
        System.out.print("\033[0J");       // Clear the lines
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

    public static void print(String[][] arr) {
        for (int i = 0; i < index; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }
}