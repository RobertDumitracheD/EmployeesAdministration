package sample;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Driver;
import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

public class Main extends Application {
    Scene scene1, scene2, scene3,scene4;
    Connection con;
    PreparedStatement st;
    @Override
    public void start(Stage window) throws Exception{

        window.setTitle("Administration");
        Label username = new Label("Username");
        Label password = new Label("Password");
        TextField adminField = new TextField();
        PasswordField adminTextField = new PasswordField();
        Button loginButton = new Button("Login");

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(125);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(username,0,0);
        gridPane.add(adminField,1,0);
        gridPane.add(password,0,1);
        gridPane.add(adminTextField,1,1);
        gridPane.add(loginButton,1,2);

        loginButton.setOnAction(r->{

            try{
                Admin admin = new Admin(adminField.getText().toString(), adminTextField.getText().toString());
                System.out.println(admin.getUserAdmin() + " " +admin.getUserPass());
                String query = "SELECT* FROM admin WHERE admin_user= '"+admin.getUserAdmin()  +"' AND admin_pass = '" +admin.getUserPass() + "'";
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Succesfully registered in LoginButton");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/administration","root","rahatul49");
                System.out.println("Connected in loginButton");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.next()){
                    Alert a1 = new Alert(Alert.AlertType.NONE,
                            "Succesfully logged in",ButtonType.OK);
                    a1.show();
                    window.setScene(scene2);
                }else{
                    Alert a2 = new Alert(Alert.AlertType.NONE,
                            "Wrong username or password",ButtonType.OK);
                    a2.show();
                }

            }catch(ClassNotFoundException | SQLException a){

                System.out.println("Catch from loginButton ->" + a);

            }

        });

        GridPane gridPane2 = new GridPane();
        scene2 = new Scene(gridPane2,1170,600);
        Label idLabel = new Label("Employee id");
        Label nameLabel = new Label("Employee name");
        Label carLabel = new Label("Car");
        Label insLabel = new Label("Insurance");
        Label pitLabel = new Label("PIT availability");
        Button addEmployeeButton = new Button("Add employee");
        Button removeEmployeeButton = new Button ("Remove employee");
        Button viewEmployees = new Button("View employees");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(addEmployeeButton, viewEmployees);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(20);

        hBox.getChildren().addAll( idLabel,nameLabel, carLabel, insLabel, pitLabel);
        gridPane2.add(vBox, 1, 0);
        gridPane2.add(hBox,0,0);
        addEmployeeButton.setOnAction(e->{
            try{

                HBox hbox = new HBox();
                hbox.setPadding(new Insets(10, 10, 10, 10));
                hbox.setSpacing(20);
                TextField idField = new TextField();
                TextField nameField = new TextField();
                TextField carField = new TextField();
                TextField insField = new TextField();
                System.out.println(insField.getText());
                TextField pitField = new TextField();
                Button saveButton = new Button("Save");
                hbox.getChildren().addAll( idField, nameField, carField, insField, pitField, saveButton);
                gridPane2.add(hbox,0,1);


                saveButton.setOnAction(w->{
                    try{
                        int parsID = parseInt(idField.getText());
                        Employee employee = new Employee(parsID, nameField.getText(),carField.getText(), insField.getText(), pitField.getText());
                        System.out.println(employee.getEmployeeName());

                        String query = "INSERT INTO employees VALUES (?,?,?,?,?)";
                        Class.forName("com.mysql.jdbc.Driver");
                        System.out.println("Succesfully registered in SaveButton");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/administration","root","rahatul49");
                        System.out.println("Connected in saveButton");
                        st = con.prepareStatement(query);
                        st.setInt(1, parsID);
                        st.setString(2, employee.getEmployeeName());
                        st.setString(3, employee.getEmployeeCar());
                        st.setString(4, employee.getCarInsurance());
                        st.setString(5, employee.getCarPit());
                        int count =st.executeUpdate();
                        Alert saveAlert= new Alert(Alert.AlertType.NONE,"Succesfully saved in your database",ButtonType.OK);
                        con.close();
                        st.close();

                    }catch(Exception a){
                        a.printStackTrace();
                    }

                });

            }catch(Exception a){
                a.printStackTrace();
            }
        });

        viewEmployees.setOnAction(t->{
            try{
                VBox vbox = new VBox();
                vbox.setPadding(new Insets(20,20,20,20));
                TableView<Employee> tableView = new TableView();
                //Con + rs + query
                String query = "SELECT* FROM administration.employees";
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Succesfully registered in viewEmployees");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/administration","root","rahatul49");
                System.out.println("Connected in viewEmployees button");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                ObservableList results = FXCollections.observableArrayList();
                if(rs.next()){
                    System.out.println("in rs.next()");
                    rs.previous();
                    while(rs.next()){

                        results.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));

                    }
                    tableView.setItems(results);
                    TableColumn<Employee, Integer> idColumn = new TableColumn<>("Employee id");
                    idColumn.setMinWidth(50);
                    idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

                    TableColumn<Employee, String> nameColumn = new TableColumn<>("Employee name");
                    nameColumn.setMinWidth(250);
                    nameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

                    TableColumn<Employee, String> carColumn = new TableColumn<>("Employee's car");
                    carColumn.setMinWidth(250);
                    carColumn.setCellValueFactory(new PropertyValueFactory<>("employeeCar"));

                    TableColumn<Employee, String> insColumn = new TableColumn<>("Insurance validity");
                    insColumn.setMinWidth(200);
                    insColumn.setCellValueFactory(new PropertyValueFactory<>("carInsurance"));

                    TableColumn<Employee, String> pitColumn = new TableColumn<>("Pit validity");
                    pitColumn.setMinWidth(200);
                    pitColumn.setCellValueFactory(new PropertyValueFactory<>("carPit"));

                    tableView.getColumns().addAll(idColumn, nameColumn, carColumn, insColumn, pitColumn);

                    vbox.getChildren().add(tableView);
                    Button backButtonScene3 = new Button("Back");
                    Button backButtonScene2 = new Button("Back");
                    backButtonScene2.setOnAction(o->window.setScene(scene2));
                    GridPane gridPane3 = new GridPane();
                    gridPane3.setHgap(20);
                    gridPane.setVgap(20);
                    gridPane3.add(vbox,0,0);

                    removeEmployeeButton.setOnAction(q->{
                        try{
                            Button submitButton = new Button("Remove");
                            HBox removeBox= new HBox();
                            removeBox.setPadding(new Insets(10, 10, 10, 10));
                            removeBox.setSpacing(20);
                            GridPane removePane = new GridPane();
                            removePane.setHgap(20);
                            removePane.setVgap(20);
                            Label removeLabel = new Label("Enter the id you want to remove: ");
                            TextField removeTextField = new TextField();
                            removePane.add(removeLabel,0,0);
                            removePane.add(removeTextField,1,0);
                            removePane.add(submitButton,0,1);
                            removePane.add(backButtonScene3,1,1);

                            submitButton.setOnAction(u->{
                                try{

                                    String query1 = "DELETE FROM employees WHERE employee_id=" +parseInt(removeTextField.getText());
                                    Class.forName("com.mysql.jdbc.Driver");
                                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/administration","root","rahatul49");
                                    Statement statement = con.prepareStatement(query1);
                                    statement.executeUpdate(query1);

                                }catch (Exception i){
                                    System.out.println("Exception in remove button " + i);
                                }


                            });

                            removeBox.getChildren().addAll(removePane);
                            scene4 = new Scene(removeBox,500,300);
                            window.setScene(scene4);
                            backButtonScene3.setOnAction(y-> window.setScene(scene3));

                        }catch(Exception a){
                            System.out.println("remove button error " + a);
                        }


                    });

                    gridPane3.add(removeEmployeeButton,1,0);
                    gridPane3.add(backButtonScene2,1,1);
                    scene3 = new Scene(gridPane3, 1350,500);
                    window.setScene(scene3);
                }

            }catch(Exception e){
                System.out.println("Exception from view Button " + e);
            }


        });

        scene1 = new Scene(gridPane,500 ,300);
        window.setScene(scene1);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);

    }



}