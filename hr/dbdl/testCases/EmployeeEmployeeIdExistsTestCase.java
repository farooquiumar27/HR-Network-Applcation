import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;

public class EmployeeEmployeeIdExistsTestCase
{
public static void main(String gg[ ])
{
try
{
String employeeId=null;
boolean found=false;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter employeeId to check : ");
try
{
employeeId=br.readLine( ).trim( );
}catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
return;
}
EmployeeDAO employeeDAO=new EmployeeDAO( );
found=employeeDAO.employeeIdExists(employeeId);
System.out.println("Result of existance : "+found);
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
}