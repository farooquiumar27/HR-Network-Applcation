import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;

public class EmployeeGetByPANNumberTestCase
{
public static void main(String gg[ ])
{
try
{
String panNumber=null;
EmployeeDTOInterface employeeDTO=null;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter PANNumber : ");
try
{
panNumber=br.readLine( ).trim( );
}catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
return;
}
EmployeeDAO employeeDAO=new EmployeeDAO( );
employeeDTO=employeeDAO.getByPANNumber(panNumber);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String simpleDate=sdf.format(employeeDTO.getDateOfBirth( ));
System.out.println(employeeDTO.getEmployeeId( ));
System.out.println(employeeDTO.getName( ));
System.out.println(employeeDTO.getDesignationCode( ));
System.out.println(simpleDate);
System.out.println(employeeDTO.getGender( ));
System.out.println(employeeDTO.getIsIndian( ));
System.out.println(employeeDTO.getBasicSalary( ).toPlainString( ));
System.out.println(employeeDTO.getPANNumber( ));
System.out.println(employeeDTO.getAadharCardNumber( ));
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
}