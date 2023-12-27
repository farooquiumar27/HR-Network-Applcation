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
String designationCode=String.valueOf(employeeDTO.getDesignationCode( ));
if(Integer.parseInt(designationCode)<=0)throw new DAOException("Invalid designation code");
DesignationDAOInterface designationDAO=new DesignationDAO( );
if(!designationDAO.codeExists(Integer.parseInt(designationCode)))throw new DAOException("Invalid designation code");
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
if(employeeDTO.getDateOfBirth( )==null)throw new DAOException("Date is null");
String dateOfBirth=sdf.format(employeeDTO.getDateOfBirth( ));
String gender=String.valueOf(employeeDTO.getGender( ));
String isIndian=String.valueOf(employeeDTO.getIsIndian( ));
if(employeeDTO.getBasicSalary( )==null)throw new DAOException("Basic salary is null");
if(employeeDTO.getBasicSalary( ).signum( )==-1)throw new DAOException("Basic salary is negative");
String basicSalary=employeeDTO.getBasicSalary( ).toPlainString( );
String panNumber=employeeDTO.getPANNumber( );
if(panNumber==null)throw new DAOException("PAN Number is null");
if(panNumber.length( )==0)throw new DAOException("Length of PAN Number is 0");
String aadharCardNumber=employeeDTO.getAadharCardNumber( );
if(aadharCardNumber==null)throw new DAOException("Aadhar card number is null");
if(aadharCardNumber.length( )<=0)throw new DAOException("Length of aadhar number is 0");
int lastGeneratedCode=0;
int recordCount=0;
String lastGeneratedCodeString;
String recordCountString;
boolean foundP=false;
boolean foundA=false;
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)
{
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length( )<10)lastGeneratedCodeString+=" ";
recordCountString=String.valueOf(recordCount);
while(recordCountString.length( )<10)recordCountString+=" ";
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
}
else
{
lastGeneratedCode=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
recordCount=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
}
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
if(panNumber.equalsIgnoreCase(randomAccessFile.readLine( )))foundP=true;
if(aadharCardNumber.equalsIgnoreCase(randomAccessFile.readLine( )))foundA=true;
if(foundP && foundA)break;
}
if(foundP && foundA)
{
randomAccessFile.close( );
throw new DAOException("PANNumber and AadharCardNumber already exxists");
}
if(foundP)
{
randomAccessFile.close( );
throw new DAOException("PANNumber already exists");
}
if(foundA)
{
randomAccessFile.close( );
throw new DAOException("Aadhar card number already exists");
}
int base=100000;
int code=base+(lastGeneratedCode+1);
employeeId="A"+String.valueOf(code);
employeeDTO.setEmployeeId(employeeId);
randomAccessFile.writeBytes(employeeId);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(name);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(designationCode);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(dateOfBirth);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(gender);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(isIndian);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(basicSalary);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(panNumber);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(aadharCardNumber);
randomAccessFile.writeBytes("\n");
recordCount++;
lastGeneratedCode++;
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length( )<10)lastGeneratedCodeString+=" ";
recordCountString=String.valueOf(recordCount);
while(recordCountString.length( )<10)recordCountString+=" ";
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
randomAccessFile.close( );
}
catch(IOException ioe)
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
String designationCode=String.valueOf(employeeDTO.getDesignationCode( ));
if(Integer.parseInt(designationCode)<=0)throw new DAOException("Invalid designation code");
DesignationDAOInterface designationDAO=new DesignationDAO( );
if(!designationDAO.codeExists(Integer.parseInt(designationCode)))throw new DAOException("Invalid designation code");
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
if(employeeDTO.getDateOfBirth( )==null)throw new DAOException("Date is null");
String dateOfBirth=sdf.format(employeeDTO.getDateOfBirth( ));
String gender=String.valueOf(employeeDTO.getGender( ));
String isIndian=String.valueOf(employeeDTO.getIsIndian( ));
if(employeeDTO.getBasicSalary( )==null)throw new DAOException("Basic salary is null");
if(employeeDTO.getBasicSalary( ).signum( )==-1)throw new DAOException("Basic salary is negative");
String basicSalary=employeeDTO.getBasicSalary( ).toPlainString( );
String panNumber=employeeDTO.getPANNumber( );
if(panNumber==null)throw new DAOException("PAN Number is null");
if(panNumber.trim( ).length( )==0)throw new DAOException("Length of PAN Number is 0");
String aadharCardNumber=employeeDTO.getAadharCardNumber( );
if(aadharCardNumber==null)throw new DAOException("Aadhar card number is null");
if(aadharCardNumber.trim( ).length( )<=0)throw new DAOException("Length of aadhar number is 0");
File file=new File(FILE_NAME);
if(!(file.exists( )))throw new DAOException("No records found");
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)
{
randomAccessFile.close( );
throw new DAOException("No records found");
}
randomAccessFile.readLine( );
if(Integer.parseInt(randomAccessFile.readLine( ).trim( ))==0)
{
randomAccessFile.close( );
throw new DAOException("No records found");
}
boolean foundE=false;
boolean foundP=false;
boolean foundA=false;
String id;
long foundAt=0;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
if(foundE==false)foundAt=randomAccessFile.getFilePointer( );
id=randomAccessFile.readLine( );
if(id.equals(employeeId))foundE=true;
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
if(randomAccessFile.readLine( ).equals(panNumber))if(!(employeeId.equals(id)))foundP=true;
if(randomAccessFile.readLine( ).equals(aadharCardNumber))if(!(employeeId.equals(id)))foundA=true;
if(foundP &&foundA && foundE)break;
}
if(foundE==false)
{
randomAccessFile.close( );
throw new DAOException("EmployeeId does not exists");
}
if(foundP &&foundA)
{
randomAccessFile.close( );
throw new DAOException("PANNumber and Aadhar Card number already exists");
}
if(foundP)
{
randomAccessFile.close( );
throw new DAOException("PANNumber already exists");
}
if(foundA)
{
randomAccessFile.close( );
throw new DAOException("Aadhar card number already exists");
}
File tmpFile=new File("tmp.data");
if(tmpFile.exists( ))tmpFile.delete( );
randomAccessFile.seek(foundAt);
for(int i=0;i<9;i++)randomAccessFile.readLine( );
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(employeeId+"\n");
tmpRandomAccessFile.writeBytes(name+"\n");
tmpRandomAccessFile.writeBytes(designationCode+"\n");
tmpRandomAccessFile.writeBytes(dateOfBirth+"\n");
tmpRandomAccessFile.writeBytes(gender+"\n");
tmpRandomAccessFile.writeBytes(isIndian+"\n");
tmpRandomAccessFile.writeBytes(basicSalary+"\n");
tmpRandomAccessFile.writeBytes(panNumber+"\n");
tmpRandomAccessFile.writeBytes(aadharCardNumber+"\n");
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( )+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( )+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( )+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( )+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( )+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( )+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( )+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( )+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( )+"\n");
}
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer( )<tmpRandomAccessFile.length( ))
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine( )+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer( ));
tmpRandomAccessFile.setLength(0);
randomAccessFile.close( );
tmpRandomAccessFile.close( );
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public void delete(String employeeId)throws DAOException
{
if(employeeId==null)throw new DAOException("EmployeeId is null");
if(employeeId.trim( ).length( )==0)throw new DAOException("Length of employeeId is 0");
File file=new File(FILE_NAME);
if(!(file.exists( )))throw new DAOException("No records found");
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)
{
randomAccessFile.close( );
throw new DAOException("No records found");
}
int lastGeneratedCode=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
int recordCount=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
if(recordCount==0)
{
randomAccessFile.close( );
throw new DAOException("No records found");
}
boolean foundE=false;
long foundAt=0;
String id;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
if(foundE==false)foundAt=randomAccessFile.getFilePointer( );
id=randomAccessFile.readLine( );
if(id.equals(employeeId))foundE=true;
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
if(foundE)break;
}
if(foundE==false)
{
randomAccessFile.close( );
throw new DAOException("EmployeeId does not exists");
}
File tmpFile=new File("tmp.data");
if(tmpFile.exists( ))tmpFile.delete( );
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( )+"\n");
}
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer( )<tmpRandomAccessFile.length( ))
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine( )+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer( ));
tmpRandomAccessFile.setLength(0);
randomAccessFile.seek(0);
randomAccessFile.readLine( );
recordCount--;
String recordCountString=String.valueOf(recordCount);
while(recordCountString.length( )<10)recordCountString+=" ";
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close( );
tmpRandomAccessFile.close( );
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public Set<EmployeeDTOInterface> getAll( )throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>( );
File file=new File(FILE_NAME);
if(!(file.exists( )))return employees;
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)return employees;
randomAccessFile.readLine( );
if(0==Integer.parseInt(randomAccessFile.readLine( ).trim( )))return employees;
String fEmployeeId=null;
String fName=null;
int fDesignationCode=0;
Date fDateOfBirth=null;

char fGender=' ';
GENDER gender;

boolean fIsIndian=false;
BigDecimal fBasicSalary=null;
String fPanNumber=null;
String fAadharCardNumber=null;
EmployeeDTOInterface employeeDTO;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fEmployeeId=randomAccessFile.readLine( );
fName=randomAccessFile.readLine( );
fDesignationCode=Integer.parseInt(randomAccessFile.readLine( ));
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
fDateOfBirth=sdf.parse(randomAccessFile.readLine( ));
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage( ));
}

fGender=randomAccessFile.readLine( ).charAt(0);
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;

fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine( ));
fBasicSalary=new BigDecimal(randomAccessFile.readLine( ));
fPanNumber=randomAccessFile.readLine( );
fAadharCardNumber=randomAccessFile.readLine( );
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
randomAccessFile.close( );
return employees;
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
if(new DesignationDAO( ).codeExists(designationCode)==false)throw new DAOException("Designation code does not exists");
Set<EmployeeDTOInterface> employees=new TreeSet<>( );
File file=new File(FILE_NAME);
if(!(file.exists( )))return employees;
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)return employees;
randomAccessFile.readLine( );
if(0==Integer.parseInt(randomAccessFile.readLine( ).trim( )))return employees;
String fEmployeeId=null;
String fName=null;
int fDesignationCode=0;
Date fDateOfBirth=null;
char fGender=' ';
GENDER gender;

boolean fIsIndian=false;
BigDecimal fBasicSalary=null;
String fPanNumber=null;
String fAadharCardNumber=null;
EmployeeDTOInterface employeeDTO;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fEmployeeId=randomAccessFile.readLine( );
fName=randomAccessFile.readLine( );
fDesignationCode=Integer.parseInt(randomAccessFile.readLine( ));
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
fDateOfBirth=sdf.parse(randomAccessFile.readLine( ));
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage( ));
}
fGender=randomAccessFile.readLine( ).charAt(0);
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine( ));
fBasicSalary=new BigDecimal(randomAccessFile.readLine( ));
fPanNumber=randomAccessFile.readLine( );
fAadharCardNumber=randomAccessFile.readLine( );
if(fDesignationCode==designationCode)
{
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
}
randomAccessFile.close( );
return employees;
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}

}
public boolean isDesignationCodeAlloted(int designationCode) throws DAOException
{
if(designationCode<=0)return false;
File file=new File(FILE_NAME);
if(!file.exists( ))return false;
String fDesignationCode=null;
String sdc=String.valueOf(designationCode);
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)return false;
randomAccessFile.readLine( );
if(0==Integer.parseInt(randomAccessFile.readLine( ).trim( )))return false;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
randomAccessFile.readLine( );
randomAccessFile.readLine( );
fDesignationCode=randomAccessFile.readLine( );
if(fDesignationCode.equals(sdc))
{
randomAccessFile.close( );
return true;
}
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
}
randomAccessFile.close( );
return false;
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null)throw new DAOException("Invalid employeeId");
if(employeeId.trim( ).length( )==0)throw new DAOException("Invalid employeeId");
File file=new File(FILE_NAME);
if(!file.exists( ))throw new DAOException("No records found");
String fEmployeeId=null;
String fName=null;
int fDesignationCode=0;
Date fDateOfBirth=null;
char fGender=' ';
GENDER gender;
boolean fIsIndian=false;
BigDecimal fBasicSalary=null;
String fPanNumber=null;
String fAadharCardNumber=null;
EmployeeDTOInterface employeeDTO;
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)throw new DAOException("No records found");
randomAccessFile.readLine( );
if(0==Integer.parseInt(randomAccessFile.readLine( ).trim( )))throw new DAOException("No records found");
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fEmployeeId=randomAccessFile.readLine( );
fName=randomAccessFile.readLine( );
fDesignationCode=Integer.parseInt(randomAccessFile.readLine( ));
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
fDateOfBirth=sdf.parse(randomAccessFile.readLine( ));
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage( ));
}
fGender=randomAccessFile.readLine( ).charAt(0);
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine( ));
fBasicSalary=new BigDecimal(randomAccessFile.readLine( ));
fPanNumber=randomAccessFile.readLine( );
fAadharCardNumber=randomAccessFile.readLine( );
if(fEmployeeId.equals(employeeId))
{
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
randomAccessFile.close( );
return employeeDTO;
}
}
randomAccessFile.close( );
throw new DAOException("No record found with the employeeId->"+employeeId);
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null)throw new DAOException("Invalid PAN Number");
if(panNumber.trim( ).length( )==0)throw new DAOException("Invalid PAN Number");
File file=new File(FILE_NAME);
if(!file.exists( ))throw new DAOException("No records found");
String fEmployeeId=null;
String fName=null;
int fDesignationCode=0;
Date fDateOfBirth=null;
char fGender=' ';
GENDER gender;
boolean fIsIndian=false;
BigDecimal fBasicSalary=null;
String fPanNumber=null;
String fAadharCardNumber=null;
EmployeeDTOInterface employeeDTO;
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)throw new DAOException("No records found");
randomAccessFile.readLine( );
if(0==Integer.parseInt(randomAccessFile.readLine( ).trim( )))throw new DAOException("No records found");
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fEmployeeId=randomAccessFile.readLine( );
fName=randomAccessFile.readLine( );
fDesignationCode=Integer.parseInt(randomAccessFile.readLine( ));
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
fDateOfBirth=sdf.parse(randomAccessFile.readLine( ));
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage( ));
}
fGender=randomAccessFile.readLine( ).charAt(0);
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine( ));
fBasicSalary=new BigDecimal(randomAccessFile.readLine( ));
fPanNumber=randomAccessFile.readLine( );
fAadharCardNumber=randomAccessFile.readLine( );
if(fPanNumber.equals(panNumber))
{
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
randomAccessFile.close( );
return employeeDTO;
}
}
randomAccessFile.close( );
throw new DAOException("No record found with the PAN Number->"+panNumber);
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null)throw new DAOException("Invalid AadharCardNumber");
if(aadharCardNumber.trim( ).length( )==0)throw new DAOException("Invalid AadharCardNumber");
File file=new File(FILE_NAME);
if(!file.exists( ))throw new DAOException("No records found");
String fEmployeeId=null;
String fName=null;
int fDesignationCode=0;
Date fDateOfBirth=null;
char fGender=' ';
GENDER gender;
boolean fIsIndian=false;
BigDecimal fBasicSalary=null;
String fPanNumber=null;
String fAadharCardNumber=null;
EmployeeDTOInterface employeeDTO;
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)throw new DAOException("No records found");
randomAccessFile.readLine( );
if(0==Integer.parseInt(randomAccessFile.readLine( ).trim( )))throw new DAOException("No records found");
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fEmployeeId=randomAccessFile.readLine( );
fName=randomAccessFile.readLine( );
fDesignationCode=Integer.parseInt(randomAccessFile.readLine( ));
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
fDateOfBirth=sdf.parse(randomAccessFile.readLine( ));
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage( ));
}
fGender=randomAccessFile.readLine( ).charAt(0);
if(fGender=='M' || fGender=='m')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine( ));
fBasicSalary=new BigDecimal(randomAccessFile.readLine( ));
fPanNumber=randomAccessFile.readLine( );
fAadharCardNumber=randomAccessFile.readLine( );
if(fAadharCardNumber.equals(aadharCardNumber))
{
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
randomAccessFile.close( );
return employeeDTO;
}
}
randomAccessFile.close( );
throw new DAOException("No record found with the AadharCardNumber->"+aadharCardNumber);
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null)return false;
if(employeeId.length( )==0)return false;
File file=new File(FILE_NAME);
if(!file.exists( ))return false;
String fEmployeeId=null;
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)return false;
randomAccessFile.readLine( );
if(0==Integer.parseInt(randomAccessFile.readLine( ).trim( )))return false;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fEmployeeId=randomAccessFile.readLine( );
if(fEmployeeId.equals(employeeId))
{
randomAccessFile.close( );
return true;
}
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
}
randomAccessFile.close( );
return false;
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null)return false;
if(panNumber.length( )==0)return false;
File file=new File(FILE_NAME);
if(!file.exists( ))return false;
String fPanNumber=null;
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)return false;
randomAccessFile.readLine( );
if(0==Integer.parseInt(randomAccessFile.readLine( ).trim( )))return false;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
fPanNumber=randomAccessFile.readLine( );
if(fPanNumber.equals(panNumber))
{
randomAccessFile.close( );
return true;
}
randomAccessFile.readLine( );
}
randomAccessFile.close( );
return false;
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null)return false;
if(aadharCardNumber.length( )==0)return false;
File file=new File(FILE_NAME);
if(!file.exists( ))return false;
String fAadharCardNumber=null;
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)return false;
randomAccessFile.readLine( );
if(0==Integer.parseInt(randomAccessFile.readLine( ).trim( )))return false;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
fAadharCardNumber=randomAccessFile.readLine( );
if(fAadharCardNumber.equals(aadharCardNumber))
{
randomAccessFile.close( );
return true;
}
}
randomAccessFile.close( );
return false;
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public int getCount( ) throws DAOException
{
int count=0;
try
{
File file=new File(FILE_NAME);
if(!file.exists( ))return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)return 0;
randomAccessFile.readLine( );
count=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
randomAccessFile.close( );
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
return count;
}
public int getCountByDesignation(int designationCode) throws DAOException
{
int count=0;
if(designationCode<=0)throw new DAOException("Invalid desognation code");
try
{
File file=new File(FILE_NAME);
if(!file.exists( ))return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)return 0;
randomAccessFile.readLine( );
randomAccessFile.readLine( );
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
randomAccessFile.readLine( );
randomAccessFile.readLine( );
if(designationCode==Integer.parseInt(randomAccessFile.readLine( )))count++;
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
randomAccessFile.readLine( );
}
randomAccessFile.close( );
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
return count;
}

};