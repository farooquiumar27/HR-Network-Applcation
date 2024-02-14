import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;

public class EmployeeIsDesignationCodeAllotedTestCase
{
public static void main(String gg[ ])
{
try
{
int designationCode=0;
boolean found=false;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter Designation Code to check : ");
try
{
designationCode=Integer.parseInt(br.readLine( ).trim( ));
}catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
return;
}
EmployeeDAO employeeDAO=new EmployeeDAO( );
found=employeeDAO.isDesignationCodeAlloted(designationCode);
System.out.println("Result of allotment : "+found);
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
}