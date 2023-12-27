import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;

public class DesignationCodeExistsTestCase
{
public static void main(String gg[ ])
{
try
{
int code=Integer.parseInt(gg[0]);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO( );
boolean result=designationDAO.codeExists(code);
System.out.println("Result of existance is : "+result);
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage( ));
}
}
};