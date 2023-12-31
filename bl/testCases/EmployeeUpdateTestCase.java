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
public class EmployeeUpdateTestCase
{
public static void main(String gg[ ])
{
String employeeId;
String name;
DesignationInterface designation=new Designation( );
Date dateOfBirth;
boolean isIndian;
String gender;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
try
{
System.out.print("Enter employeeId : ");
employeeId=br.readLine( );
System.out.print("Enter name : ");
name=br.readLine( );
System.out.print("Enter designation code : ");
designation.setCode(Integer.parseInt(br.readLine( ).trim( )));
System.out.print("Enter designation title : ");
designation.setTitle(br.readLine( ));
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.print("Enter date of birth : ");
dateOfBirth=sdf.parse(br.readLine( ).trim( ));
}catch(ParseException pe)
{
System.out.println(pe.getMessage( ));
return;
}
System.out.print("Enter isIndian : ");
isIndian=Boolean.parseBoolean(br.readLine( ).trim( ));
System.out.print("Enter gender : ");
gender=br.readLine( ).trim( );
System.out.print("Enter basic salary : ");
basicSalary=new BigDecimal(br.readLine( ).trim( ));
System.out.print("Enter PANNumber : ");
panNumber=br.readLine( ).trim( );
System.out.print("Enter Aadhar card number : ");
aadharCardNumber=br.readLine( ).trim( );
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
return;
}
try
{
EmployeeInterface employee=new Employee( );
employee.setEmployeeId(employeeId);
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setIsIndian(isIndian);
employee.setGender(gender.equalsIgnoreCase("Male")?GENDER.MALE:GENDER.FEMALE);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManager employeeManager=EmployeeManager.getEmployeeManager( );
employeeManager.updateEmployee(employee);
System.out.println("Updated Successfully");
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