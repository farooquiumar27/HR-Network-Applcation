import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;

public class DesignationGetByTitleTestCase
{
public static void main(String gg[ ])
{
try
{
String title=gg[0];
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO( );
DesignationDTOInterface designationDTO;
designationDTO=designationDAO.getByTitle(title);
System.out.printf("Code is %d -- Title is %s\n ",designationDTO.getCode( ),designationDTO.getTitle( ));
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage( ));
}
}
};