import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.enums.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeUpdateTestCase
{
public static void main(String gg[ ])
{
String employeeId=null;
String name=null;
int designationCode=0;
Date dateOfBirth=null;
char tGender=' ';
GENDER gender=null;
boolean isIndian=false;
BigDecimal basicSalary=null;
String panNumber=null;
String aadharCardNumber=null;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
try
{
System.out.printf("Enter employeeId : ");
employeeId=br.readLine( ).trim( );
System.out.printf("Enter name : ");
name=br.readLine( ).trim( );
System.out.printf("Enter designation code : ");
designationCode=Integer.parseInt(br.readLine( ).trim( ));
System.out.printf("Enter date of birth : ");
//br.readLine( ).trim( );
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
dateOfBirth=sdf.parse(br.readLine( ).trim( ));
}
catch(ParseException pe)
{
System.out.println(pe.getMessage( ));
return ;
}
System.out.printf("Enter gender : ");
tGender=br.readLine( ).trim( ).charAt(0);
if(tGender!='M' && tGender!='m' && tGender!='F' && tGender!='f')
{
System.out.println("Invalid gender");
return;
}
if(tGender=='M' || tGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
System.out.printf("IsIndian : ");
isIndian=Boolean.parseBoolean(br.readLine( ).trim( ));
System.out.printf("Enter basic salary : ");
//br.readLine( );
basicSalary=new BigDecimal(br.readLine( ).trim( ));
System.out.printf("Enter PAN Number : ");
panNumber=br.readLine( ).trim( );
System.out.printf("Enter Aadhar Card Number : ");
aadharCardNumber=br.readLine( ).trim( );
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage( ));
}
try
{
EmployeeDTO employeeDTO=new EmployeeDTO( );
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO( );
employeeDAO.update(employeeDTO);
System.out.println("Updated successfully");
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
};