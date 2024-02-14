package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.sql.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO)throws DAOException
{
if(employeeDTO==null)throw new DAOException("Employee is null");
String employeeId=null;
String name=employeeDTO.getName( );
if(name==null)throw new DAOException("Name is null");
if(name.length( )==0)throw new DAOException("Length of name is 0");

int designationCode=employeeDTO.getDesignationCode( );
if(designationCode<=0)throw new DAOException("Designation Code is invalid");
Connection connection=null;
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
connection=DAOConnection.getConnection( );
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Invalid designation code : "+designationCode);
}
resultSet.close( );
preparedStatement.close( );
}catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}

java.util.Date dateOfBirth=employeeDTO.getDateOfBirth( );
if(dateOfBirth==null)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Date of Birth is null");
}
char gender=employeeDTO.getGender( );
if(gender==' ')
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Gender is null");
}
boolean isIndian=employeeDTO.getIsIndian( );
BigDecimal basicSalary=employeeDTO.getBasicSalary( );
if(basicSalary==null)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Basic salary is null");
}
if(basicSalary.signum( )==-1)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Basic salary is negative");
}

String panNumber=employeeDTO.getPANNumber( );
if(panNumber==null)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("PAN Number is null");
}

if(panNumber.length( )==0)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Length of PAN Number is 0");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber( );
if(aadharCardNumber==null)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Aadhar card number is null");
}
if(aadharCardNumber.length( )<=0)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Length of aadhar number is 0");
}
try
{
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?;");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery( );
boolean panNumberExists=resultSet.next( );
preparedStatement.close( );
resultSet.close( );
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?;");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery( );
boolean aadharCardNumberExists=resultSet.next( );
preparedStatement.close( );
resultSet.close( );
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("PANNumber and AadharCardNumber already exists");
}
if(panNumberExists)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("PANNumber already exists");
}
if(aadharCardNumberExists)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Aadhar card number exists");
}
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,gender,is_indian,basic_salary,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear( ),dateOfBirth.getMonth( ),dateOfBirth.getDate( ));
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setString(4,String.valueOf(gender));
preparedStatement.setBoolean(5,isIndian);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate( );
resultSet=preparedStatement.getGeneratedKeys( );
resultSet.next( );
int generatedCode=resultSet.getInt(1);
resultSet.close( );
preparedStatement.close( );
connection.close( );
employeeDTO.setEmployeeId("A"+(100000+generatedCode));
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public void update(EmployeeDTOInterface employeeDTO)throws DAOException
{
if(employeeDTO==null)throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId( );
if(employeeId==null)throw new DAOException("EmployeeId is null");
if(employeeId.trim( ).length( )==0)throw new DAOException("Length of employeeId is 0");
String name=employeeDTO.getName( );
if(name==null)throw new DAOException("Name is null");
if(name.length( )==0)throw new DAOException("Length of name is 0");
int designationCode=employeeDTO.getDesignationCode( );
if(designationCode<=0)throw new DAOException("Designation Code is invalid");
Connection connection=null;
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
connection=DAOConnection.getConnection( );
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Invalid designation code : "+designationCode);
}
resultSet.close( );
preparedStatement.close( );
}catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}

java.util.Date dateOfBirth=employeeDTO.getDateOfBirth( );
if(dateOfBirth==null)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Date of Birth is null");
}
char gender=employeeDTO.getGender( );
if(gender==' ')
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Gender is null");
}
boolean isIndian=employeeDTO.getIsIndian( );
BigDecimal basicSalary=employeeDTO.getBasicSalary( );
if(basicSalary==null)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Basic salary is null");
}
if(basicSalary.signum( )==-1)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Basic salary is negative");
}

String panNumber=employeeDTO.getPANNumber( );
if(panNumber==null)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("PAN Number is null");
}

if(panNumber.length( )==0)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Length of PAN Number is 0");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber( );
if(aadharCardNumber==null)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Aadhar card number is null");
}
if(aadharCardNumber.length( )<=0)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Length of aadhar number is 0");
}

try
{
preparedStatement=connection.prepareStatement("select count(*) from employee;");
resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false || resultSet.getInt(1)==0)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("No record found");
}
resultSet.close( );
preparedStatement.close( );

preparedStatement=connection.prepareStatement("select employee_id from employee;");
resultSet=preparedStatement.executeQuery( );
boolean employeeIdExists=false;
int id=0;
String empid=null;
while(resultSet.next( ))
{
id=resultSet.getInt(1);
empid="A"+(100000+id);
if(empid.equals(employeeId))
{
employeeIdExists=true;
break;
}
}
resultSet.close( );
preparedStatement.close( );
if(employeeIdExists==false)
{
connection.close( );
throw new DAOException("Employee_Id not exist");
}

preparedStatement=connection.prepareStatement("select gender from employee where pan_number=? and employee_id!=?;");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,id);
resultSet=preparedStatement.executeQuery( );
boolean panNumberExists=resultSet.next( );
preparedStatement.close( );
resultSet.close( );
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=? and employee_id!=?;");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,id);
resultSet=preparedStatement.executeQuery( );
boolean aadharCardNumberExists=resultSet.next( );
preparedStatement.close( );
resultSet.close( );
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("PANNumber and AadharCardNumber already exists");
}
if(panNumberExists)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("PANNumber already exists");
}
if(aadharCardNumberExists)
{
try
{
connection.close( );
}
catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
throw new DAOException("Aadhar card number exists");
}
preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,gender=?,is_indian=?,basic_salary=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear( ),dateOfBirth.getMonth( ),dateOfBirth.getDate( ));
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setString(4,String.valueOf(gender));
preparedStatement.setBoolean(5,isIndian);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,id);
preparedStatement.executeUpdate( );
preparedStatement.close( );
connection.close( );
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public void delete(String employeeId)throws DAOException
{
if(employeeId==null)throw new DAOException("EmployeeId is null");
if(employeeId.trim( ).length( )==0)throw new DAOException("Length of employeeId is 0");
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select count(*) from employee;");
ResultSet resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false || resultSet.getInt(1)==0)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("No record found");
}
resultSet.close( );
preparedStatement.close( );

preparedStatement=connection.prepareStatement("select employee_id from employee;");
resultSet=preparedStatement.executeQuery( );
boolean employeeIdExists=false;
int id=0;
String empid=null;
while(resultSet.next( ))
{
id=resultSet.getInt(1);
empid="A"+(100000+id);
if(empid.equals(employeeId))
{
employeeIdExists=true;
break;
}
}
resultSet.close( );
preparedStatement.close( );
if(employeeIdExists==false)
{
connection.close( );
throw new DAOException("Employee_Id not exist");
}
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?;");
preparedStatement.setInt(1,id);
preparedStatement.executeUpdate( );
preparedStatement.close( );
connection.close( );
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public Set<EmployeeDTOInterface> getAll( )throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>( );
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select * from employee;");
ResultSet resultSet=preparedStatement.executeQuery( );
String fEmployeeId=null;
String fName=null;
int fDesignationCode=0;
java.util.Date fDateOfBirth=null;
java.sql.Date sqlDateOfBirth=null;
char fGender=' ';
GENDER gender;
boolean fIsIndian=false;
BigDecimal fBasicSalary=null;
String fPanNumber=null;
String fAadharCardNumber=null;
EmployeeDTOInterface employeeDTO;
int id=0;
while(resultSet.next( ))
{
id=resultSet.getInt("employee_id");
fEmployeeId="A"+(100000+id);
fName=resultSet.getString("name");
fDesignationCode=resultSet.getInt("designation_code");
sqlDateOfBirth=resultSet.getDate("date_of_birth");
fDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear( ),sqlDateOfBirth.getMonth( ),sqlDateOfBirth.getDate( ));
fGender=resultSet.getString("gender").charAt(0);
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
fIsIndian=resultSet.getBoolean("is_indian");
fBasicSalary=resultSet.getBigDecimal("basic_salary");
fPanNumber=resultSet.getString("pan_number");
fAadharCardNumber=resultSet.getString("aadhar_card_number");
employeeDTO=new EmployeeDTO( );
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
employees.add(employeeDTO);
}
resultSet.close( );
preparedStatement.close( );
connection.close( );
return employees;
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
if(designationCode<=0)throw new DAOException("Designation Code is invalid");
Connection connection=null;
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
connection=DAOConnection.getConnection( );
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Invalid designation code : "+designationCode);
}
resultSet.close( );
preparedStatement.close( );
}catch(SQLException exception)
{
throw new DAOException(exception.getMessage( ));
}
Set<EmployeeDTOInterface> employees=new TreeSet<>( );
try
{
connection=DAOConnection.getConnection( );
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?;");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery( );
String fEmployeeId=null;
String fName=null;
int fDesignationCode=0;
java.util.Date fDateOfBirth=null;
java.sql.Date sqlDateOfBirth=null;
char fGender=' ';
GENDER gender;
boolean fIsIndian=false;
BigDecimal fBasicSalary=null;
String fPanNumber=null;
String fAadharCardNumber=null;
EmployeeDTOInterface employeeDTO;
int id=0;
while(resultSet.next( ))
{
id=resultSet.getInt("employee_id");
fEmployeeId="A"+(100000+id);
fName=resultSet.getString("name");
fDesignationCode=resultSet.getInt("designation_code");
sqlDateOfBirth=resultSet.getDate("date_of_birth");
fDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear( ),sqlDateOfBirth.getMonth( ),sqlDateOfBirth.getDate( ));
fGender=resultSet.getString("gender").charAt(0);
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
fIsIndian=resultSet.getBoolean("is_indian");
fBasicSalary=resultSet.getBigDecimal("basic_salary");
fPanNumber=resultSet.getString("pan_number");
fAadharCardNumber=resultSet.getString("aadhar_card_number");
employeeDTO=new EmployeeDTO( );
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
employees.add(employeeDTO);
}
resultSet.close( );
preparedStatement.close( );
connection.close( );
return employees;
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public boolean isDesignationCodeAlloted(int designationCode) throws DAOException
{
if(designationCode<=0)return false;
Connection connection=null;
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
connection=DAOConnection.getConnection( );
preparedStatement=connection.prepareStatement("select gender from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
return false;
}
resultSet.close( );
preparedStatement.close( );
connection.close( );
return true;
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null)throw new DAOException("Invalid employeeId");
if(employeeId.trim( ).length( )==0)throw new DAOException("Invalid employeeId");
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select count(*) from employee;");
ResultSet resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false || resultSet.getInt(1)==0)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("No record found");
}
resultSet.close( );
preparedStatement.close( );
preparedStatement=connection.prepareStatement("select employee_id from employee;");
resultSet=preparedStatement.executeQuery( );
boolean employeeIdExists=false;
int id=0;
String empid=null;
while(resultSet.next( ))
{
id=resultSet.getInt(1);
empid="A"+(100000+id);
if(empid.equals(employeeId))
{
employeeIdExists=true;
break;
}
}
resultSet.close( );
preparedStatement.close( );
if(employeeIdExists==false)
{
connection.close( );
throw new DAOException("Employee_Id not exist");
}
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?;");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery( );
String fEmployeeId=null;
String fName=null;
int fDesignationCode=0;
java.util.Date fDateOfBirth=null;
java.sql.Date sqlDateOfBirth=null;
char fGender=' ';
GENDER gender;
boolean fIsIndian=false;
BigDecimal fBasicSalary=null;
String fPanNumber=null;
String fAadharCardNumber=null;
EmployeeDTOInterface employeeDTO;
resultSet.next( );
fEmployeeId=employeeId;
fName=resultSet.getString("name");
fDesignationCode=resultSet.getInt("designation_code");
sqlDateOfBirth=resultSet.getDate("date_of_birth");
fDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear( ),sqlDateOfBirth.getMonth( ),sqlDateOfBirth.getDate( ));
fGender=resultSet.getString("gender").charAt(0);
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
fIsIndian=resultSet.getBoolean("is_indian");
fBasicSalary=resultSet.getBigDecimal("basic_salary");
fPanNumber=resultSet.getString("pan_number");
fAadharCardNumber=resultSet.getString("aadhar_card_number");
employeeDTO=new EmployeeDTO( );
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
resultSet.close( );
preparedStatement.close( );
connection.close( );
return employeeDTO;
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null)throw new DAOException("Invalid PAN Number");
if(panNumber.trim( ).length( )==0)throw new DAOException("Invalid PAN Number");
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select count(*) from employee;");
ResultSet resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false || resultSet.getInt(1)==0)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("No record found");
}
resultSet.close( );
preparedStatement.close( );
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?;");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Invalid PAN Number : "+panNumber);
}
String fEmployeeId=null;
String fName=null;
int fDesignationCode=0;
java.util.Date fDateOfBirth=null;
java.sql.Date sqlDateOfBirth=null;
char fGender=' ';
GENDER gender;
boolean fIsIndian=false;
BigDecimal fBasicSalary=null;
String fPanNumber=null;
String fAadharCardNumber=null;
EmployeeDTOInterface employeeDTO;
int id=resultSet.getInt("employee_id");
fEmployeeId="A"+(100000+id);
fName=resultSet.getString("name");
fDesignationCode=resultSet.getInt("designation_code");
sqlDateOfBirth=resultSet.getDate("date_of_birth");
fDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear( ),sqlDateOfBirth.getMonth( ),sqlDateOfBirth.getDate( ));
fGender=resultSet.getString("gender").charAt(0);
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
fIsIndian=resultSet.getBoolean("is_indian");
fBasicSalary=resultSet.getBigDecimal("basic_salary");
fPanNumber=resultSet.getString("pan_number");
fAadharCardNumber=resultSet.getString("aadhar_card_number");
employeeDTO=new EmployeeDTO( );
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
resultSet.close( );
preparedStatement.close( );
connection.close( );
return employeeDTO;
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null)throw new DAOException("Invalid AadharCardNumber");
if(aadharCardNumber.trim( ).length( )==0)throw new DAOException("Invalid AadharCardNumber");
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select count(*) from employee;");
ResultSet resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false || resultSet.getInt(1)==0)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("No record found");
}
resultSet.close( );
preparedStatement.close( );
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?;");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Invalid AadharCard Number : "+aadharCardNumber);
}
String fEmployeeId=null;
String fName=null;
int fDesignationCode=0;
java.util.Date fDateOfBirth=null;
java.sql.Date sqlDateOfBirth=null;
char fGender=' ';
GENDER gender;
boolean fIsIndian=false;
BigDecimal fBasicSalary=null;
String fPanNumber=null;
String fAadharCardNumber=null;
EmployeeDTOInterface employeeDTO;
int id=resultSet.getInt("employee_id");
fEmployeeId="A"+(100000+id);
fName=resultSet.getString("name");
fDesignationCode=resultSet.getInt("designation_code");
sqlDateOfBirth=resultSet.getDate("date_of_birth");
fDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear( ),sqlDateOfBirth.getMonth( ),sqlDateOfBirth.getDate( ));
fGender=resultSet.getString("gender").charAt(0);
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
fIsIndian=resultSet.getBoolean("is_indian");
fBasicSalary=resultSet.getBigDecimal("basic_salary");
fPanNumber=resultSet.getString("pan_number");
fAadharCardNumber=resultSet.getString("aadhar_card_number");
employeeDTO=new EmployeeDTO( );
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
resultSet.close( );
preparedStatement.close( );
connection.close( );
return employeeDTO;
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null)return false;
if(employeeId.trim( ).length( )==0)return false;
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select count(*) from employee;");
ResultSet resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false || resultSet.getInt(1)==0)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
return false;
}
resultSet.close( );
preparedStatement.close( );
preparedStatement=connection.prepareStatement("select employee_id from employee;");
resultSet=preparedStatement.executeQuery( );
boolean employeeIdExists=false;
int id=0;
String empid=null;
while(resultSet.next( ))
{
id=resultSet.getInt(1);
empid="A"+(100000+id);
if(empid.equals(employeeId))
{
employeeIdExists=true;
break;
}
}
resultSet.close( );
preparedStatement.close( );
connection.close( );
return employeeIdExists;
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null)return false;
if(panNumber.length( )==0)return false;
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select count(*) from employee;");
ResultSet resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false || resultSet.getInt(1)==0)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
return false;
}
resultSet.close( );
preparedStatement.close( );
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?;");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery( );
boolean panNumberExits=resultSet.next( );
resultSet.close( );
preparedStatement.close( );
connection.close( );
return panNumberExits;
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null)return false;
if(aadharCardNumber.length( )==0)return false;
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select count(*) from employee;");
ResultSet resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false || resultSet.getInt(1)==0)
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
return false;
}
resultSet.close( );
preparedStatement.close( );
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?;");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery( );
boolean aadharCardNumberExists=resultSet.next( );
resultSet.close( );
preparedStatement.close( );
connection.close( );
return aadharCardNumberExists;
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public int getCount( ) throws DAOException
{
int count=0;
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select count(*) from employee;");
ResultSet resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false)return 0;
count=resultSet.getInt(1);
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
return count;
}
public int getCountByDesignation(int designationCode) throws DAOException
{
int count=0;
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select count(*) from employee where designation_code=?;");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet=preparedStatement.executeQuery( );
if(resultSet.next( )==false)return 0;
count=resultSet.getInt(1);
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
return count;
}

};