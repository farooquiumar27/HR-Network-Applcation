import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exception.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import java.io.*;
public class DesignationUpdateTestCase
{
public static void main(String gg[ ])
{
String title;
int code;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
try
{
System.out.print("Enter code to update : ");
code=Integer.parseInt(br.readLine( ).trim( ));
System.out.print("Enter title to update : ");
title=br.readLine( );
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
return;
}
try
{
Designation designation=new Designation( );
designation.setCode(code);
designation.setTitle(title);
DesignationManager designationManager=DesignationManager.getDesignationManager( );
designationManager.updateDesignation(designation);
System.out.println("Updated Successfully");
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