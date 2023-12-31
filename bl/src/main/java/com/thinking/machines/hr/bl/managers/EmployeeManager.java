package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exception.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.text.*;
import java.math.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeeMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeeMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeeMap;
private Set<EmployeeInterface> employeesSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeeMap;
private static EmployeeManager employeeManager=null;
private EmployeeManager( ) throws BLException
{
populateDataStructure( );
}
public void populateDataStructure( ) throws BLException
{
this.employeeIdWiseEmployeeMap=new HashMap<>( );
this.panNumberWiseEmployeeMap=new HashMap<>( );
this.aadharCardNumberWiseEmployeeMap=new HashMap<>( );
this.employeesSet=new TreeSet<>( );
this.designationCodeWiseEmployeeMap=new HashMap<>( );
try
{
Set<EmployeeDTOInterface> dlEmployees;
dlEmployees=new EmployeeDAO( ).getAll( );
EmployeeInterface employee;
char gen=' ';
GENDER gend=null;
DesignationManager designationManager=DesignationManager.getDesignationManager( );
DesignationInterface designation=null;
Set<EmployeeInterface> employees=null;
for(EmployeeDTOInterface dlEmployee:dlEmployees)
{
employee=new Employee( );
employee.setEmployeeId(new String(dlEmployee.getEmployeeId( )));
employee.setName(new String(dlEmployee.getName( )));
designation=designationManager.getDSDesignationByCode(dlEmployee.getDesignationCode( ));
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dlEmployee.getDateOfBirth( ).clone( ));
employee.setIsIndian(dlEmployee.getIsIndian( ));
gen=dlEmployee.getGender( );
gend=(gen=='M')?GENDER.MALE:GENDER.FEMALE;
employee.setGender(gend);
employee.setBasicSalary(new BigDecimal(dlEmployee.getBasicSalary( ).toPlainString( )));
employee.setPANNumber(new String(dlEmployee.getPANNumber( )));
employee.setAadharCardNumber(new String(dlEmployee.getAadharCardNumber( )));
this.employeeIdWiseEmployeeMap.put(dlEmployee.getEmployeeId( ).toUpperCase( ),employee);
this.panNumberWiseEmployeeMap.put(dlEmployee.getPANNumber( ).toUpperCase( ),employee);
this.aadharCardNumberWiseEmployeeMap.put(dlEmployee.getAadharCardNumber( ).toUpperCase( ),employee);
this.employeesSet.add(employee);

employees=this.designationCodeWiseEmployeeMap.get(dlEmployee.getDesignationCode( ));
if(employees==null)
{
employees=new TreeSet<>( );
this.designationCodeWiseEmployeeMap.put(dlEmployee.getDesignationCode( ),employees);
}
employees.add(employee);
}
}
catch(DAOException daoException)
{
throw new BLException(daoException.getMessage( ));
}
}
public static EmployeeManager getEmployeeManager( ) throws BLException
{
if(employeeManager==null)employeeManager=new EmployeeManager( );
return employeeManager;
}
public void addEmployee(EmployeeInterface employee) throws BLException
{
if(employee==null)throw new BLException("Employee is null");
BLException blException=new BLException( );
GENDER gend=null;
DesignationInterface designation=null;
if(employee.getEmployeeId( )!=null)blException.setPropertyException("EmployeeId","Employee_Id is not null");
if(employee.getName( )==null)blException.setPropertyException("Name","Name is null");
else if(employee.getName( ).trim( ).length( )==0)blException.setPropertyException("Name","Length of name is zero");
try
{
if(employee.getDesignation( )==null)blException.setPropertyException("Designation","Designtion is null");
else
{
if(this.designationCodeWiseEmployeeMap.containsKey(employee.getDesignation( ).getCode( ))==true)
{
designation=DesignationManager.getDesignationManager( ).getDesignationByCode(employee.getDesignation( ).getCode( ));
if(designation.getTitle( ).equalsIgnoreCase(employee.getDesignation( ).getTitle( ))==false)blException.setPropertyException("Designation","Designtion title is invalid");
}
else
{
blException.setPropertyException("Designation","Designtion code is invalid");
}
}
}
catch(BLException bl)
{
blException.setGenericException(bl.getMessage( ));
}
if(employee.getDateOfBirth( )==null)blException.setPropertyException("Date_Of_Birth","DateOfBirth is null");
if(employee.getGender( )==null)blException.setPropertyException("gender","Gender is null");
if(employee.getBasicSalary( )==null)blException.setPropertyException("Basic_Salary","BasicSalary is null");
else if(employee.getBasicSalary( ).signum( )<=0)blException.setPropertyException("Basic_Salary","Invalid Salary");
if(employee.getPANNumber( )==null)blException.setPropertyException("PAN_Number","PANNumber is null");
else if(employee.getPANNumber( ).trim( ).length( )==0)blException.setPropertyException("PAN_Number","Length of PANNumber is zero");
if(employee.getAadharCardNumber( )==null)blException.setPropertyException("Aadhar_Card_Number","AadharCardNumber is null");
else if(employee.getAadharCardNumber( ).trim( ).length( )==0)blException.setPropertyException("Aadhar_Card_Number","Length of AadharCardNumber is zero");
if(blException.hasException( ))throw blException;
if(this.panNumberWiseEmployeeMap.containsKey(employee.getPANNumber( ).trim( ).toUpperCase( )))blException.setPropertyException("PAN_Number","PANNumber already exists");
if(this.aadharCardNumberWiseEmployeeMap.containsKey(employee.getAadharCardNumber( ).trim( ).toUpperCase( )))blException.setPropertyException("Aadhar_Card_Number","AadharCardNumber already exists");
if(blException.hasPropertyExceptions( ))throw blException;
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO( );
employeeDTO.setName(employee.getName( ));
employeeDTO.setDesignationCode(designation.getCode( ));   //
employeeDTO.setDateOfBirth(employee.getDateOfBirth( ));
employeeDTO.setIsIndian(employee.getIsIndian( ));
gend=(employeeDTO.getGender( )=='M')?GENDER.MALE:GENDER.FEMALE;
employeeDTO.setGender(gend);
employeeDTO.setBasicSalary(employee.getBasicSalary( ));
employeeDTO.setPANNumber(employee.getPANNumber( ));
employeeDTO.setAadharCardNumber(employee.getAadharCardNumber( ));
EmployeeDAOInterface employeeDAO=new EmployeeDAO( );
employeeDAO.add(employeeDTO);
employee.setEmployeeId(employeeDTO.getEmployeeId( ));
}
catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage( ));
}
EmployeeInterface blEmployee=new Employee( );
blEmployee.setEmployeeId(employee.getEmployeeId( ));
blEmployee.setName(employee.getName( ));
blEmployee.setDesignation(DesignationManager.getDesignationManager( ).getDSDesignationByCode(employee.getDesignation( ).getCode( )));  //
blEmployee.setDateOfBirth((Date)employee.getDateOfBirth( ).clone( )); //
blEmployee.setIsIndian(employee.getIsIndian( ));
blEmployee.setGender(gend);
blEmployee.setBasicSalary(employee.getBasicSalary( ));
blEmployee.setPANNumber(employee.getPANNumber( ));
blEmployee.setAadharCardNumber(employee.getAadharCardNumber( ));
this.employeeIdWiseEmployeeMap.put(blEmployee.getEmployeeId( ).toUpperCase( ),blEmployee);
this.panNumberWiseEmployeeMap.put(blEmployee.getPANNumber( ).toUpperCase( ),blEmployee);
this.aadharCardNumberWiseEmployeeMap.put(blEmployee.getAadharCardNumber( ).toUpperCase( ),blEmployee);
this.employeesSet.add(blEmployee);
Set<EmployeeInterface> employees=this.designationCodeWiseEmployeeMap.get(designation.getCode( ));
employees.add(blEmployee);
}

public void updateEmployee(EmployeeInterface employee) throws BLException
{
if(employee==null)throw new BLException("Employee is null");
BLException blException=new BLException( );
GENDER gend=null;
DesignationInterface designation=null;
if(employee.getEmployeeId( )==null)blException.setPropertyException("Employee_Id","EmployeeId is null");
else if(employee.getEmployeeId( ).trim( ).length( )==0)blException.setPropertyException("Employee_Id","Length of EmployeeId is zero");
if(!(this.employeeIdWiseEmployeeMap.containsKey(employee.getEmployeeId( ).trim( ).toUpperCase( ))))
{
blException.setPropertyException("Employee_Id","Employee_Id not exists");
throw blException;
}
if(employee.getName( )==null)blException.setPropertyException("Name","Name is null");
else if(employee.getName( ).trim( ).length( )==0)blException.setPropertyException("Name","Length of name is zero");
try
{
if(employee.getDesignation( )==null)blException.setPropertyException("Designation","Designtion is null");
else
{
if(this.designationCodeWiseEmployeeMap.containsKey(employee.getDesignation( ).getCode( ))==true)
{
designation=DesignationManager.getDesignationManager( ).getDesignationByCode(employee.getDesignation( ).getCode( ));
if(designation.getTitle( ).equalsIgnoreCase(employee.getDesignation( ).getTitle( ))==false)blException.setPropertyException("Designation","Designtion title is invalid");
}
else
{
blException.setPropertyException("Designation","Designtion code is invalid");
}
}
}
catch(BLException bl)
{
blException.setGenericException(bl.getMessage( ));
}
if(employee.getDateOfBirth( )==null)blException.setPropertyException("Date_Of_Birth","DateOfBirth is null");
if(employee.getGender( )==null)blException.setPropertyException("gender","Gender is null");
if(employee.getBasicSalary( )==null)blException.setPropertyException("Basic_Salary","BasicSalary is null");
else if(employee.getBasicSalary( ).signum( )<=0)blException.setPropertyException("Basic_Salary","Invalid Salary");
if(employee.getPANNumber( )==null)blException.setPropertyException("PAN_Number","PANNumber is null");
else if(employee.getPANNumber( ).trim( ).length( )==0)blException.setPropertyException("PAN_Number","Length of PANNumber is zero");
if(employee.getAadharCardNumber( )==null)blException.setPropertyException("Aadhar_Card_Number","AadharCardNumber is null");
else if(employee.getAadharCardNumber( ).trim( ).length( )==0)blException.setPropertyException("Aadhar_Card_Number","Length of AadharCardNumber is zero");
if(blException.hasException( ))throw blException;
if(this.panNumberWiseEmployeeMap.containsKey(employee.getPANNumber( ).trim( ).toUpperCase( )))
{
EmployeeInterface tempEmployee=this.panNumberWiseEmployeeMap.get(employee.getPANNumber( ).trim( ).toUpperCase( ));
if(!(tempEmployee.getEmployeeId( ).equalsIgnoreCase(employee.getEmployeeId( ))))blException.setPropertyException("PAN_Number","PANNumber already exists");
}
if(this.aadharCardNumberWiseEmployeeMap.containsKey(employee.getAadharCardNumber( ).trim( ).toUpperCase( )))
{
EmployeeInterface tempEmployee=this.aadharCardNumberWiseEmployeeMap.get(employee.getAadharCardNumber( ).trim( ).toUpperCase( ));
if(!(tempEmployee.getEmployeeId( ).equalsIgnoreCase(employee.getEmployeeId( ))))blException.setPropertyException("Aadhar_Card_Number","AadharCardNumber already exists");
}
if(blException.hasPropertyExceptions( ))throw blException;
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO( );
employeeDTO.setEmployeeId(employee.getEmployeeId( ));
employeeDTO.setName(employee.getName( ));
employeeDTO.setDesignationCode(designation.getCode( ));
employeeDTO.setDateOfBirth((Date)employee.getDateOfBirth( ).clone( ));
employeeDTO.setIsIndian(employee.getIsIndian( ));
gend=(employeeDTO.getGender( )=='M')?GENDER.MALE:GENDER.FEMALE;
employeeDTO.setGender(gend);
employeeDTO.setBasicSalary(employee.getBasicSalary( ));
employeeDTO.setPANNumber(employee.getPANNumber( ));
employeeDTO.setAadharCardNumber(employee.getAadharCardNumber( ));
EmployeeDAOInterface employeeDAO=new EmployeeDAO( );
employeeDAO.update(employeeDTO);
//employee.setEmployeeId(employeeDTO.getEmployeeId( ));  //koi kaam ki nahi
}
catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage( ));
}
EmployeeInterface blEmployee=new Employee( );
blEmployee.setEmployeeId(employee.getEmployeeId( ));
blEmployee.setName(employee.getName( ));
blEmployee.setDesignation(DesignationManager.getDesignationManager( ).getDSDesignationByCode(employee.getDesignation( ).getCode( )));  //
blEmployee.setDateOfBirth((Date)employee.getDateOfBirth( ).clone( ));
blEmployee.setIsIndian(employee.getIsIndian( ));
blEmployee.setGender(gend);
blEmployee.setBasicSalary(employee.getBasicSalary( ));
blEmployee.setPANNumber(employee.getPANNumber( ));
blEmployee.setAadharCardNumber(employee.getAadharCardNumber( ));
employee=this.employeeIdWiseEmployeeMap.get(employee.getEmployeeId( ).toUpperCase( ));
this.employeeIdWiseEmployeeMap.remove(employee.getEmployeeId( ).toUpperCase( ));
this.panNumberWiseEmployeeMap.remove(employee.getPANNumber( ).toUpperCase( ));
this.aadharCardNumberWiseEmployeeMap.remove(employee.getAadharCardNumber( ).toUpperCase( ));
this.employeesSet.remove(employee);
Set<EmployeeInterface> employees=this.designationCodeWiseEmployeeMap.get(designation.getCode( ));
employees.remove(employee);
this.employeeIdWiseEmployeeMap.put(blEmployee.getEmployeeId( ).toUpperCase( ),blEmployee);
this.panNumberWiseEmployeeMap.put(blEmployee.getPANNumber( ).toUpperCase( ),blEmployee);
this.aadharCardNumberWiseEmployeeMap.put(blEmployee.getAadharCardNumber( ).toUpperCase( ),blEmployee);
this.employeesSet.add(blEmployee);
employees.add(blEmployee);
}

public void removeEmployee(String employeeId) throws BLException
{
BLException blException=new BLException( );
if(employeeId==null)blException.setPropertyException("Employee_Id","EmployeeId is null");
if(employeeId.trim( ).length( )==0)blException.setPropertyException("Employee_Id","Length of EmployeeId is zero");
if(this.employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase( ))==false)blException.setPropertyException("Employee_Id","Employee_Id not exist");
if(blException.hasException( ))throw blException;
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO( );
employeeDAO.delete(employeeId.toUpperCase( ));
}
catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage( ));
}
EmployeeInterface employee=this.employeeIdWiseEmployeeMap.get(employeeId.trim( ).toUpperCase( ));
this.employeeIdWiseEmployeeMap.remove(employee.getEmployeeId( ).toUpperCase( ));
this.panNumberWiseEmployeeMap.remove(employee.getPANNumber( ).toUpperCase( ));
this.aadharCardNumberWiseEmployeeMap.remove(employee.getAadharCardNumber( ).toUpperCase( ));
this.employeesSet.remove(employee);
Set<EmployeeInterface> employees=this.designationCodeWiseEmployeeMap.get(employee.getDesignation( ).getCode( ));
employees.remove(employee);
}
public Set<EmployeeInterface> getEmployees( ) throws BLException
{
Set<EmployeeInterface> employees=new TreeSet<>( );
EmployeeInterface _employee;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
for(EmployeeInterface employee:this.employeesSet)
{
_employee=new Employee( );
_employee.setEmployeeId(new String(employee.getEmployeeId( )));
_employee.setName(new String(employee.getName( )));
_employee.setDesignation(DesignationManager.getDesignationManager( ).getDesignationByCode(employee.getDesignation( ).getCode( )));
_employee.setIsIndian(employee.getIsIndian( ));
_employee.setDateOfBirth((Date)employee.getDateOfBirth( ).clone( ));
_employee.setGender(employee.getGender( ).equalsIgnoreCase("Male")?GENDER.MALE:GENDER.FEMALE);
_employee.setBasicSalary(new BigDecimal(employee.getBasicSalary( ).toPlainString( )));
_employee.setPANNumber(new String(employee.getPANNumber( )));
_employee.setAadharCardNumber(new String(employee.getAadharCardNumber( )));
employees.add(_employee);
}
return employees;
}
public Set<EmployeeInterface> getEmployeeByDesignation(int designationCode) throws BLException
{
Set<EmployeeInterface> employees=new TreeSet<>( );
Set<EmployeeInterface> _employees=this.designationCodeWiseEmployeeMap.get(designationCode);
if(_employees==null)return employees;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
EmployeeInterface _employee;
for(EmployeeInterface employee:_employees)
{
_employee=new Employee( );
_employee.setEmployeeId(new String(employee.getEmployeeId( )));
_employee.setName(new String(employee.getName( )));
_employee.setDesignation(DesignationManager.getDesignationManager( ).getDesignationByCode(employee.getDesignation( ).getCode( )));
_employee.setIsIndian(employee.getIsIndian( ));
_employee.setDateOfBirth((Date)employee.getDateOfBirth( ).clone( ));
_employee.setGender(employee.getGender( ).equalsIgnoreCase("Male")?GENDER.MALE:GENDER.FEMALE);
_employee.setBasicSalary(new BigDecimal(employee.getBasicSalary( ).toPlainString( )));
_employee.setPANNumber(new String(employee.getPANNumber( )));
_employee.setAadharCardNumber(new String(employee.getAadharCardNumber( )));
employees.add(_employee);
}
return employees;
}
public int getEmployeeCount( ) throws BLException
{
return this.employeesSet.size( );
}
public int getEmployeeCountByDesignation(int designationCode) throws BLException
{
return (this.designationCodeWiseEmployeeMap.containsKey(designationCode))?this.designationCodeWiseEmployeeMap.get(designationCode).size( ):0;
}
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
BLException blException=new BLException( );
if(this.employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase( ))==false)blException.setPropertyException("Employee_Id","Employee_Id not exist");
if(blException.hasException( ))throw blException;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
EmployeeInterface _employee=new Employee( );
EmployeeInterface employee=this.employeeIdWiseEmployeeMap.get(employeeId.toUpperCase( ));
_employee.setEmployeeId(new String(employee.getEmployeeId( )));
_employee.setName(new String(employee.getName( )));
_employee.setDesignation(DesignationManager.getDesignationManager( ).getDesignationByCode(employee.getDesignation( ).getCode( )));
_employee.setIsIndian(employee.getIsIndian( ));
_employee.setDateOfBirth((Date)employee.getDateOfBirth( ).clone( ));
_employee.setGender(employee.getGender( ).equalsIgnoreCase("Male")?GENDER.MALE:GENDER.FEMALE);
_employee.setBasicSalary(new BigDecimal(employee.getBasicSalary( ).toPlainString( )));
_employee.setPANNumber(new String(employee.getPANNumber( )));
_employee.setAadharCardNumber(new String(employee.getAadharCardNumber( )));
return _employee;
}
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
BLException blException=new BLException( );
if(this.panNumberWiseEmployeeMap.containsKey(panNumber.toUpperCase( ))==false)blException.setPropertyException("PAN_Number","PAN_Number not exist");
if(blException.hasException( ))throw blException;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
EmployeeInterface _employee=new Employee( );
EmployeeInterface employee=this.panNumberWiseEmployeeMap.get(panNumber.toUpperCase( ));
_employee.setEmployeeId(new String(employee.getEmployeeId( )));
_employee.setName(new String(employee.getName( )));
_employee.setDesignation(DesignationManager.getDesignationManager( ).getDesignationByCode(employee.getDesignation( ).getCode( )));
_employee.setIsIndian(employee.getIsIndian( ));
_employee.setDateOfBirth((Date)employee.getDateOfBirth( ).clone( ));
_employee.setGender(employee.getGender( ).equalsIgnoreCase("Male")?GENDER.MALE:GENDER.FEMALE);
_employee.setBasicSalary(new BigDecimal(employee.getBasicSalary( ).toPlainString( )));
_employee.setPANNumber(new String(employee.getPANNumber( )));
_employee.setAadharCardNumber(new String(employee.getAadharCardNumber( )));
return _employee;
}
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException=new BLException( );
if(this.aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase( ))==false)blException.setPropertyException("Aadhar_Card_Number","Aadhar_Card_Number not exist");
if(blException.hasException( ))throw blException;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
EmployeeInterface _employee=new Employee( );
EmployeeInterface employee=this.aadharCardNumberWiseEmployeeMap.get(aadharCardNumber.toUpperCase( ));
_employee.setEmployeeId(new String(employee.getEmployeeId( )));
_employee.setName(new String(employee.getName( )));
_employee.setDesignation(DesignationManager.getDesignationManager( ).getDesignationByCode(employee.getDesignation( ).getCode( )));
_employee.setIsIndian(employee.getIsIndian( ));
_employee.setDateOfBirth((Date)employee.getDateOfBirth( ).clone( ));
_employee.setGender(employee.getGender( ).equalsIgnoreCase("Male")?GENDER.MALE:GENDER.FEMALE);
_employee.setBasicSalary(new BigDecimal(employee.getBasicSalary( ).toPlainString( )));
_employee.setPANNumber(new String(employee.getPANNumber( )));
_employee.setAadharCardNumber(new String(employee.getAadharCardNumber( )));
return _employee;
}
public boolean employeeEmployeeIdExists(String employeeId) throws BLException
{
return this.employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase( ));
}
public boolean employeePANNumberExists(String panNumber) throws BLException
{
return this.panNumberWiseEmployeeMap.containsKey(panNumber.toUpperCase( ));
}
public boolean employeeAadharCardNumberExists(String aadharCardNumber) throws BLException
{
return this.aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase( ));
}
public boolean isDesignationCodeAlloted(int designationCode) throws BLException
{
return this.designationCodeWiseEmployeeMap.containsKey(designationCode);
}
};