package lk.ijse.CherryClothing.dao.impl;

import lk.ijse.CherryClothing.dao.custom.EmployeeDAO;
import lk.ijse.CherryClothing.dto.EmployeeDTO;
import lk.ijse.CherryClothing.entity.Employee;
import lk.ijse.CherryClothing.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> allEmployees = new ArrayList<>();

        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee");

        while (rst.next()) {
            Employee employee = new Employee(rst.getString("emp_id"), rst.getString("emp_name"), rst.getString("emp_address"),rst.getString("contact"));
            allEmployees.add(employee);
        }
        return allEmployees;
    }

    @Override
    public boolean add(Employee entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Employee(emp_id,emp_name,emp_address,contact) VALUES (?,?,?,?)",entity.getId(),entity.getName(),entity.getAddress(),entity.getContact());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Employee SET emp_name=?, emp_address=?, contact=? WHERE emp_id=?", entity.getName(), entity.getAddress(),entity.getContact(), entity.getId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT emp_id FROM Employee WHERE emp_id=?",id);
        return rst.next();
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT emp_id FROM Employee ORDER BY emp_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("emp_id");
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("E0%03d", newCustomerId);
        } else {
            return "E0001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Employee WHERE emp_id=?",id);
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee WHERE emp_id=?",id+ "");
        rst.next();
        return new Employee(id + "", rst.getString("emp_name"), rst.getString("emp_address"), rst.getString("contact"));

    }
}
