import java.io.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;

public class EmployeeGetCountByDesignationTestCase
{
public static void main(String gg[ ])
{
try
{
int count=0;
int designationCode=0;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter designation code : ");
try
{
designationCode=Integer.parseInt(br.readLine( ).trim( ));
}catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
return;
}
EmployeeDAO employeeDAO=new EmployeeDAO( );
count=employeeDAO.getCountByDesignation(designationCode);
System.out.println("Employee count for designation code-"+ designationCode +" is --> "+count);
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
} 