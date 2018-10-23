package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Statement;


public class HomePageController {

    public static int index = 1;
    public static String usernametext="" ;

    @FXML
    private PasswordField PassText;

    @FXML
    private ImageView ButtonLogin;

    @FXML
    private ImageView ButtonRegister;

    @FXML
    private CheckBox checremember;

    @FXML
    private ImageView closebutton;

    @FXML
    private ImageView collapsebutton;

    @FXML
    private ImageView resetbutton;


    @FXML
    private TextField UsernameText;



    @FXML
    void initialize() {





        resetbutton.setOnMouseClicked(event -> {
            resetbutton.getScene().getWindow().hide();
            HomePageController homepagecontrollerobject = new HomePageController();
            homepagecontrollerobject.setRememberBool("no");
            try {
                homepagecontrollerobject.NextWindow("fxmlfiles/homepage.fxml");
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        });

        collapsebutton.setOnMouseClicked(event -> {
            Stage stage = (Stage) collapsebutton.getScene().getWindow();
            stage.setIconified(true);
        });

        closebutton.setOnMouseClicked(event -> {
            closebutton.getScene().getWindow().hide();


        });

        ButtonLogin.setOnMouseClicked(event -> {
            if (PassText.getText().isEmpty() == false) {
                ConnectToBase ob = new ConnectToBase();
                HomePageController.index = ob.PasswordMatchChecker(PassText.getText());

                HomePageController.usernametext = UsernameText.getText();
                if (ob.confirmation == true) {

                    if (checremember.isSelected()) {
                        setRememberPassword(PassText.getText());
                        setRememberBool("yes");
                    }
                    ButtonLogin.getScene().getWindow().hide();
                    try {
                        NextWindow("fxmlfiles/user.fxml");

                    } catch (Exception e) {
                    }
                } else {
                    ErrorWindowController d = new ErrorWindowController();
                    try {
                        ErrorWindowController.errorText = "Incorrect password. Try again";
                        NextWindow("fxmlfiles/errorwindow.fxml");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
            }

        });

        ButtonRegister.setOnMouseClicked(event -> {
            ButtonRegister.getScene().getWindow().hide();

            try {
                NextWindow("fxmlfiles/Registr.fxml");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }


    public void NextWindow(String paht) throws IOException {
        Stage stage = new Stage();
        stage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource(paht));
        Scene scene = new Scene(root);
        stage.setAlwaysOnTop(true);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        //

        stage.setScene(scene);
        stage.show();
    }

    public void createTablePassword() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
            Statement state = connection.createStatement();
            String stringSQL = "CREATE TABLE PasswordTable ( ID INTEGER PRIMARY KEY AUTOINCREMENT, Password Varchar (30))";
            state.executeUpdate(stringSQL);
            connection.close();

        } catch (Exception e) {
            System.out.print(e.getMessage() + "  1212");
        }
    }

    public void createTableRemember() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
            Statement state = connection.createStatement();
            String stringSQL = "CREATE TABLE RememberTable ( ID INTEGER PRIMARY KEY, Password Varchar (30),Remember VARCHAR (3));" +
                    "INSERT INTO RememberTable (ID) VALUES (1)";
            state.executeUpdate(stringSQL);

            connection.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public String getRememberPassword() {
        String pass = "";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:DataBase.db");

            Statement state = conn.createStatement();


            String stringSQL = "Select  Password  From  RememberTable   WHERE id=1";

            state.executeUpdate(stringSQL);
            //   conn.close();
            try {
                pass = state.executeQuery(stringSQL).getString(1);
            } catch (Exception e) {
            }
            System.out.println("Remember PAss = " + pass);
            conn.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return pass;
    }

    private void setRememberPassword(String str) {
        try {
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
            Statement state = conn.createStatement();


            String stringSQL = "UPDATE  RememberTable SET Password = '" + str + "' WHERE ID=1";
            state.executeUpdate(stringSQL);

            conn.close();


        } catch (Exception e) {
            System.out.print(e.getMessage() + " | setRememberPassword | ");
        }
    }

    public void setRememberBool(String str) {
        try {
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
            Statement state = conn.createStatement();
            UsController ob = new UsController();


            String stringSQL = "Update  RememberTable SET Remember ='" + str + "' Where id=" + 1;
            state.executeUpdate(stringSQL);

            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage() + "| setRememberBool |");
        }
    }

    public String getRememberBool() {
        String rem = "";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
            Statement state = conn.createStatement();


            String stringSQL = "Select  Remember  From  RememberTable   WHERE id=" + 1;
            // state.executeUpdate(stringSQL);
            String sql = null;
            try {
                sql = state.executeQuery(stringSQL).getString(1);
            } catch (Exception e) {
            }


            if (sql.equals("yes")) {
                rem = "yes";

                System.out.println("HHHHHH");
            } else {
                rem = "no";
                System.out.println("AAAAAAAA");
            }

            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage() + " | getRememberBool |");

        }
        return rem;
    }


}
