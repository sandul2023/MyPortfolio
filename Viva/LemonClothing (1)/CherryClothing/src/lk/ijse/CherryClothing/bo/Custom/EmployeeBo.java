package lk.ijse.CherryClothing.bo.Custom;

import lk.ijse.CherryClothing.bo.SuperBO;
import lk.ijse.CherryClothing.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBo extends SuperBO {
    ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;
    boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;
    boolean existEmployee(String id) throws SQLException, ClassNotFoundException ;
    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
    String generateNewEmployeeID () throws SQLException, ClassNotFoundException;
    EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException;
}
