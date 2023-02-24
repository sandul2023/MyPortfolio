package lk.ijse.CherryClothing.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.CherryClothing.bo.BOFactory;
import lk.ijse.CherryClothing.bo.Custom.EmployeeBo;
import lk.ijse.CherryClothing.dto.EmployeeDTO;
import lk.ijse.CherryClothing.util.Navigation;
import lk.ijse.CherryClothing.util.RegexUtil;
import lk.ijse.CherryClothing.util.Routes;
import lk.ijse.CherryClothing.view.tm.EmployeeTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeFormController {
    public JFXTextField txtContact;
    public JFXTextField txtID;
    public JFXTextField txtAddress;
    public JFXTextField txtName;
    public Label lblEmailWarning;
    public JFXButton btnAdd;
    public TableColumn ColId;
    public TableColumn ColName;
    public TableColumn ColAddress;
    public TableColumn ColContact;
    public JFXButton btnDelete;
    public JFXButton btnAddNew;
    public AnchorPane ancEmployee;
    public TableView<EmployeeTM> tblEmployee;


    EmployeeBo employeeBo = (EmployeeBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize() throws SQLException, ClassNotFoundException {
        ColId.setCellValueFactory(new PropertyValueFactory("Id"));
        ColName.setCellValueFactory(new PropertyValueFactory("name"));
        ColAddress.setCellValueFactory(new PropertyValueFactory("address"));
        ColContact.setCellValueFactory(new PropertyValueFactory("contact"));


        initUI();

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnAdd.setText(newValue != null ? "Update" : "Save");
            btnAdd.setDisable(newValue == null);


            if (newValue != null) {
                txtID.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());

                txtID.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtContact.setDisable(false);


            }
        });

        txtContact.setOnAction(event -> btnAdd.fire());
        loadAllEmployees();
    }


    private void initUI() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtID.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtContact.setDisable(true);
        txtID.setEditable(false);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadAllEmployees() {
        tblEmployee.getItems().clear();

        try {
            ArrayList<EmployeeDTO> allCustomers = employeeBo.getAllEmployee();

            for (EmployeeDTO employee : allCustomers) {
                tblEmployee.getItems().add(new EmployeeTM(employee.getId(), employee.getName(), employee.getAddress(), employee.getContact()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void OnActionAdd(ActionEvent actionEvent) {
        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        try {
            if (existEmployee(id)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Added!").show();
            }
            employeeBo.addEmployee(new EmployeeDTO(id, name, address, contact));
            tblEmployee.getItems().add(new EmployeeTM(id, name, address, contact));

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the employee " + e.getMessage()).show();
            System.out.println(e);
        }
        {

            /*Update employee*/

            try {
                if (!existEmployee(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + id).show();
                }

                employeeBo.updateEmployee(new EmployeeDTO(id, name, address, contact));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the employee " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeBo.existEmployee(id);
    }

    public void OnActionDelete(ActionEvent actionEvent) {
        String id = tblEmployee.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existEmployee(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + id).show();
            }

            employeeBo.deleteEmployee(id);

            tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
            tblEmployee.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtID.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtName.requestFocus();
        btnAdd.setDisable(false);
        btnAdd.setText("Save");
        txtID.setText(generateNewId());
        tblEmployee.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            return employeeBo.generateNewEmployeeID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblEmployee.getItems().isEmpty()) {
            return "E0001";
        } else {
            String id = getLastEmployeeId();
            int newCustomerId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E0%03d", newCustomerId);
        }
    }

    private String getLastEmployeeId() {
        List<EmployeeTM> tempEmployeesList = new ArrayList<>(tblEmployee.getItems());
        Collections.sort(tempEmployeesList);
        return tempEmployeesList.get(tempEmployeesList.size() - 1).getId();
    }

    public void OnActionCDash(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CASHIER, ancEmployee);
    }

    public void OnActionCustomer(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER, ancEmployee);
    }

    public void OnActionCClothes(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CCLOTHES, ancEmployee);
    }

    public void OnActionPlace(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PLACEORDER, ancEmployee);
    }

    public void OnActionOut(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN, ancEmployee);
    }

    public void addressK(KeyEvent keyEvent) {
        if (RegexUtil.regex(txtAddress.getText(), "\\b([a-z]|[A-Z])+")) {
            btnAdd.setDisable(false);
            txtAddress.setFocusColor(Paint.valueOf("blue"));
        } else {
            btnAdd.setDisable(true);
            txtAddress.setFocusColor(Paint.valueOf("red"));

        }
    }

    public void nameK(KeyEvent keyEvent) {
        if (RegexUtil.regex(txtName.getText(), "\\b([a-z]|[A-Z])+")) {
            btnAdd.setDisable(false);
            txtName.setFocusColor(Paint.valueOf("blue"));
        } else {
            btnAdd.setDisable(true);
            txtName.setFocusColor(Paint.valueOf("red"));

        }
    }

    public void mobileK(KeyEvent keyEvent) {
        if (RegexUtil.regex(txtContact.getText(), "0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}")) {
            btnAdd.setDisable(false);
            txtContact.setFocusColor(Paint.valueOf("blue"));
        } else {
            btnAdd.setDisable(true);
            txtContact.setFocusColor(Paint.valueOf("red"));

        }
    }

    public void txtEmployeeIdOnAction(ActionEvent actionEvent) {
    }


}
