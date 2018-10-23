package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class ConnectToBase {

    public boolean confirmation;

    public int PasswordMatchChecker(String pass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/maximtr?useSSL=false","maxturovets","database33");

            Statement state = connection.createStatement();
            ResultSet res;

            String stringSQL = "SELECT ID FROM PasswordTable WHERE Password = '" + pass + "';";


            res = state.executeQuery(stringSQL);
            int id = res.getInt(1);
            this.confirmation = true;
            connection.close();
            return id;
        } catch (Exception e) {
            this.confirmation = false;
            System.out.print("ERROR");
            return 0;
        }

    }

    public int Register(String RegisterPass) {
        if (RegisterPass.isEmpty() == false) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/maximtr?useSSL=false","maxturovets","database33");

                Statement state = connection.createStatement();

                String stringSQL = "INSERT INTO PasswordTable (ID,Password) VALUES ('2','" + RegisterPass + "')";

                state.executeUpdate(stringSQL);

                System.out.println(HomePageController.index);


                connection.close();

            } catch (Exception e) {
                System.out.println("!!!"+e.getMessage());
            }
        }
        return PasswordMatchChecker(RegisterPass);
    }

    public void CreateTable(int index) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/maximtr?useSSL=false","maxturovets","database33");

            Statement state = conn.createStatement();
            String stringSQL = "CREATE TABLE UserTable2 ( id INTEGER PRIMARY KEY  ,login Varchar (30),   password Varchar (30),   email Varchar (30))";

            state.executeUpdate(stringSQL);
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
