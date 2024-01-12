import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.io.*;

public class DesignationDeleteTestCase
{
public static void main(String gg[ ])
{
try
{
int code=0;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter code : ");
try
{
code=Integer.parseInt(br.readLine( ));
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
}
DesignationDAO designationDAO=new DesignationDAO( );
designationDAO.delete(code);
System.out.println("Deleted successfully.");
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
};