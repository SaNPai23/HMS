package utilities;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Login{
    private int pid;
    private String f_name;

    public Login() {

    }

    boolean autheticate(String l_name, String dob, String city) throws Exception{
        ResultSet rs = null;
        String query = "Select p_id, fname from Patient where lname = '" + l_name + "' and dob = TO_DATE('" +
                dob +"', 'dd/MM/yyyy') and city = '"+ city +"'";
        SQLExec db = new SQLExec();
        db.connect();

        try{
            rs = db.execQuery(query);
        }
        catch(Exception e){
            System.out.println("Error retrieving data from the DB: "+e);
        }
        if (!rs.next())
        {
            return false;
        }
        else
        {
            pid = rs.getInt("p_id");
            f_name = rs.getString("fname");
        }
        db.terminate();
        return true;
    }

    public void signIn() throws Exception{
        String l_name, dob, city;
        boolean invalidDate;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        System.out.println("\n\t\tPatient Sign In");
        System.out.print("Enter Last Name: ");
        l_name = StaticFunctions.nextLine();
        System.out.print("Enter Date of Birth (dd/mm/yyyy): ");
        do {
            dob = StaticFunctions.next();
            StaticFunctions.nextLine();
            invalidDate = false;
            try{
                dateFormat.parse(dob.trim());
            }
            catch (Exception pe) {
                System.out.print("Invalid Date. Enter again: ");
                invalidDate = true;
            }
        } while(invalidDate);
        System.out.print("Enter City: ");
        city = StaticFunctions.nextLine();
        if(autheticate(l_name,dob,city))
        {
            Patient p = new Patient(pid,f_name);
            p.MainView();
        }
        else
        {
            System.out.println("\nInvalid Credentials. Try again.");
            TimeUnit.SECONDS.sleep(3);
            MainView();
        }
    }

    public void MainView() throws Exception{
        int choice;

        System.out.println("\n\t\tSign In");
        System.out.println("1. Sign In");
        System.out.println("2. Go Back");
        do{
            System.out.print("Enter Choice (1-2): ");
            choice = StaticFunctions.nextInt();
            StaticFunctions.nextLine();
            if(choice != 1 && choice != 2)
            {
                System.out.println("Invalid Choice");
            }
        }while(choice != 1 && choice != 2);
        switch(choice) {
            case 1:
                signIn();
                break;
            case 2:
                return;
        };
    }

    public static void main(String[] args) throws Exception
    {
        Login ob = new Login();
        ob.MainView();
    }
}