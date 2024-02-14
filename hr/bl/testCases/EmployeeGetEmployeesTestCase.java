import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exception.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.enums.*;
public class EmployeeGetEmployeesTestCase
{
public static void main(String gg[ ])
{
try
{
Set<EmployeeInterface> employees;
EmployeeManager employeeManager=EmployeeManager.getEmployeeManager( );
employees=employeeManager.getEmployees( );
for(EmployeeInterface employee:employees)
{
System.out.println("EmployeeId -> "+employee.getEmployeeId( ));
System.out.println("Employee name -> "+employee.getName( ));
System.out.println("Employee designationCode -> "+employee.getDesignation( ).getCode( )+" and designationTitle -> "+employee.getDesignation( ).getTitle( ));
System.out.println("Employee isIndian -> "+employee.getIsIndian( ));
System.out.println("Employee dateOgBirth -> "+new SimpleDateFormat("dd/MM/yyyy").format(employee.getDateOfBirth( )));
System.out.println("Employee gender -> "+employee.getGender( ));
System.out.println("Employee basic salary -> "+employee.getBasicSalary( ).toPlainString( ));
System.out.println("Employee PANNumber -> "+employee.getPANNumber( ));
System.out.println("Eployee AadharCardNumber -> "+employee.getAadharCardNumber( ));
System.out.println("***************************************************************");
}
}
catch(BLException blException)
{
if(blException.hasGenericException( ))System.out.println("Generic exceptoin -> : "+blException.getGenericException( ));
if(blException.hasPropertyExceptions( ))
{
List<String> properties=blException.getProperties( );
properties.forEach(
(property)->{
System.out.println(property+"-"+blException.getPropertyException(property));
}
);
}
}

}
};