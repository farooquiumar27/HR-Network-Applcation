import java.io.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;

public class EmployeeGetCountTestCase
{
public static void main(String gg[ ])
{
try
{
int count=0;
EmployeeDAO employeeDAO=new EmployeeDAO( );
count=employeeDAO.getCount( );
System.out.println("Employee count is : "+count);
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
} 