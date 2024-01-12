import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class EmployeeGetAllTestCase
{
public static void main(String gg[ ])
{
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO( );
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
Set<EmployeeDTOInterface> employees;
employees=employeeDAO.getAll( );
employees.forEach((employeeDTO)->{
System.out.println(employeeDTO.getEmployeeId( ));
System.out.println(employeeDTO.getName( ));
System.out.println(employeeDTO.getDesignationCode( ));
System.out.println(sdf.format(employeeDTO.getDateOfBirth( )));
System.out.println(employeeDTO.getGender( ));
System.out.println(employeeDTO.getIsIndian( ));
System.out.println(employeeDTO.getBasicSalary( ).toPlainString( ));
System.out.println(employeeDTO.getPANNumber( ));
System.out.println(employeeDTO.getAadharCardNumber( ));
System.out.println("***********");
});
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
};