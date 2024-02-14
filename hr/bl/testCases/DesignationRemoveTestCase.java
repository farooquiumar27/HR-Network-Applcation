import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exception.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import java.io.*;
public class DesignationRemoveTestCase
{
public static void main(String gg[ ])
{
int code;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
try
{
System.out.print("Enter code to remove : ");
code=Integer.parseInt(br.readLine( ).trim( ));
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
return;
}
try
{
DesignationManager designationManager=DesignationManager.getDesignationManager( );
designationManager.removeDesignation(code);
System.out.println("Removed Successfully");
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