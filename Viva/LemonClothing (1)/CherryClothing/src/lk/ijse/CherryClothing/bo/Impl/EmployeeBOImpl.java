package lk.ijse.CherryClothing.bo.Impl;

import lk.ijse.CherryClothing.bo.Custom.EmployeeBo;
import lk.ijse.CherryClothing.dao.DAOFactory;
import lk.ijse.CherryClothing.dao.custom.EmployeeDAO;
import lk.ijse.CherryClothing.dto.EmployeeDTO;
import lk.ijse.CherryClothing.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBo {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> allEmployees = new ArrayList<>();

        ArrayList<Employee> all = employeeDAO.getAll();
        for (Employee e : all) {
            allEmployees.add(new EmployeeDTO(e.getId(), e.getName(), e.getAddress(),e.getContact()));
        }
        return allEmployees;
    }

    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new Employee(dto.getId(), dto.getName(), dto.getAddress(),dto.getContact()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getId(), dto.getName(), dto.getAddress(),dto.getContact()));
    }

    @Override
    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.exist(id);
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewId();
    }

    @Override
    public EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee e = employeeDAO.search(id);
        return new EmployeeDTO(e.getId(), e.getName(), e.getAddress(),e.getContact());
    }
}
