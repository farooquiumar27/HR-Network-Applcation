import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exception.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import java.io.*;
public class DesignationGetByCodeTestCase
{
public static void main(String gg[ ])
{
int code;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
try
{
System.out.print("Enter code to seach : ");
code=Integer.parseInt(br.readLine( ).trim( ));
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
return;
}
try
{
DesignationInterface designation;
DesignationManager designationManager=DesignationManager.getDesignationManager( );
designation=designationManager.getDesignationByCode(code);
System.out.println("Successfully fetched by code");
System.out.println("Designation code - "+designation.getCode( )+"and title - "+designation.getTitle());
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