import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exception.*;

public class DesignationAddTestCase
{
public static void main(String gg[ ])
{
String title=gg[0];
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO( );
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO( );
designationDTO.setTitle(title);
designationDAO.add(designationDTO);
System.out.println("Added Successfully with designationCode->"+designationDTO.getCode( ));
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
};