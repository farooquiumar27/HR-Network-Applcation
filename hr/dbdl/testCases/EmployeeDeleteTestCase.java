import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeDeleteTestCase
{
public static void main(String gg[ ])
{
String employeeId=null;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
try
{
System.out.printf("Enter employeeId to delete : ");
employeeId=br.readLine( ).trim( );
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
}
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO( );
employeeDAO.delete(employeeId);
System.out.println("Deleted successfully");
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
};