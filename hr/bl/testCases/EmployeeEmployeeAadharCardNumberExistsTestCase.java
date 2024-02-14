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
public class EmployeeEmployeeAadharCardNumberExistsTestCase
{
public static void main(String gg[ ])
{
String aadharCardNumber;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
try
{
System.out.print("Enter aadharCardNumber : ");
aadharCardNumber=br.readLine( ).trim( );
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
return;
}
try
{
EmployeeManager employeeManager=EmployeeManager.getEmployeeManager( );
boolean found=employeeManager.employeeAadharCardNumberExists(aadharCardNumber);
System.out.println("Result of existance -> "+found);
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