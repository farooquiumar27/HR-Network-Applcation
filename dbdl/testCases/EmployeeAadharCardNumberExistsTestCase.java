import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;

public class EmployeeAadharCardNumberExistsTestCase
{
public static void main(String gg[ ])
{
try
{
String aadharCardNumber=null;
boolean found=false;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter AadharCardNumber to check : ");
try
{
aadharCardNumber=br.readLine( ).trim( );
}catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
return;
}
EmployeeDAO employeeDAO=new EmployeeDAO( );
found=employeeDAO.aadharCardNumberExists(aadharCardNumber);
System.out.println("Result of existance : "+found);
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
}