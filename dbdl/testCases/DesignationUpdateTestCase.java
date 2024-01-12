import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.io.*;

public class DesignationUpdateTestCase
{
public static void main(String gg[ ])
{
try
{
int code=0;
String title=null;
DesignationDTO designationDTO=new DesignationDTO( );
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter code : ");
try
{
code=Integer.parseInt(br.readLine( ));
System.out.print("Enter title : ");
title=br.readLine( );
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
}
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAO designationDAO=new DesignationDAO( );
designationDAO.update(designationDTO);
System.out.println("Updated successfully.");
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
};