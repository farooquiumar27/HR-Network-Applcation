import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;

public class DesignationTitleExistsTestCase
{
public static void main(String gg[ ])
{
try
{
String title=gg[0];
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO( );
boolean result=designationDAO.titleExists(title);
System.out.println("Result of existance - "+result);
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage( ));
}
}
};