import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exception.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.enums.*;
public class EmployeeAddTestCase
{
public static void main(String gg[ ])
{
String employeeId=null;
String name=null;
int designationCode=0;
Date dateOfBirth=null;
char fGender=' ';
GENDER gender=null;
boolean isIndian=false;
BigDecimal basicSalary=null;
String panNumber=null;
String aadharCardNumber=null;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
try
{
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
fGender=br.readLine( ).trim( ).charAt(0);
if(fGender!='M' && fGender!='m' && fGender!='F' && fGender!='f')
{
System.out.println("Invalid gender");
return;
}
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
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
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO( );
employeeDAO.add(employeeDTO);
System.out.println("Added successfully with generated employeeId->"+employeeDTO.getEmployeeId( ));
}
catch(DAOException daoe)
{
System.out.println(daoe.getMessage( ));
}
}
};