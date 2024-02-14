import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class EmployeeGetByDesignationCodeTestCase
{
public static void main(String gg[ ])
{
try
{
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
int designationCode=0;
EmployeeDAOInterface employeeDAO=new EmployeeDAO( );
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
Set<EmployeeDTOInterface> employees;
System.out.print("Enter designation : ");
try
{
designationCode=Integer.parseInt(br.readLine( ).trim( ));
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
}
employees=employeeDAO.getByDesignationCode(designationCode);
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