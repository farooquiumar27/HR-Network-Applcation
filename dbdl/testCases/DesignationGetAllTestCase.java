import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
public class DesignationGetAllTestCase
{
public static void main(String gg[ ])
{
try
{
Set<DesignationDTOInterface> designations;
//DesignationDTOInterface designationDTO;
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO( );
designations=designationDAO.getAll( );
designations.forEach(
(designationDTO)->{System.out.printf("Code- %d and Title- %s\n",designationDTO.getCode( ),designationDTO.getTitle( ));}
);
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
};