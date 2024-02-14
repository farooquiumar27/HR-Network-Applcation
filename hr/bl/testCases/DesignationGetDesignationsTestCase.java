import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exception.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import java.io.*;
public class DesignationGetDesignationsTestCase
{
public static void main(String gg[ ])
{
try
{
Set<DesignationInterface> designations;
//DesignationInterface designation;
DesignationManager designationManager=DesignationManager.getDesignationManager( );
designations=designationManager.getDesignations( );
for(DesignationInterface designation:designations)
{
System.out.println("Designation code - "+designation.getCode( )+" and Designation title - "+designation.getTitle( ));
}
}
catch(BLException blException)
{
if(blException.hasGenericException( ))System.out.println("Generic exceptoin -> : "+blException.getGenericException( ));
if(blException.hasPropertyExceptions( ))
{
List<String> properties=blException.getProperties( );
properties.forEach(
(property)->{
System.out.println(property+"-"+blException.getPropertyException(property));
}
);
}
}
}
};