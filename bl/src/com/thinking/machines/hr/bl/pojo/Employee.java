package com.thinking.machines.hr.bl.pojo;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
public class Employee implements EmployeeInterface
{
private String employeeId;
private String name;
private DesignationInterface designation;
private Date dateOfBirth;
private boolean isIndian;
private String gender;
private BigDecimal basicSalary;
private String panNumber;
private String aadharCardNumber;
public Employee( )
{
this.employeeId=null;
this.name=null;
this.designation=null;
this.dateOfBirth=null;
this.isIndian=false;
this.gender=null;
this.basicSalary=null;
this.panNumber=null;
this.aadharCardNumber=null;
}
public void setEmployeeId(java.lang.String employeeId)
{
this.employeeId=employeeId;
}
public java.lang.String getEmployeeId( )
{
return this.employeeId;
}
public void setName(java.lang.String name)
{
this.name=name;
}
public java.lang.String getName( )
{
return this.name;
}
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
}
public DesignationInterface getDesignation( )
{
return this.designation;
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public java.util.Date getDateOfBirth( )
{
return this.dateOfBirth;
}
public void setIsIndian(boolean isIndian)
{
this.isIndian=isIndian;
}
public boolean getIsIndian( )
{
return this.isIndian;
}
public void setGender(GENDER gender)
{
if(gender==GENDER.MALE)this.gender="Male";
else this.gender="Female";
}
public String getGender( )
{
return this.gender;
}
public void setBasicSalary(java.math.BigDecimal basicSalary)
{
this.basicSalary=basicSalary;
}
public java.math.BigDecimal getBasicSalary( )
{
return this.basicSalary;
}
public void setPANNumber(java.lang.String panNumber)
{
this.panNumber=panNumber;
}
public java.lang.String getPANNumber( )
{
return this.panNumber;
}
public void setAadharCardNumber(java.lang.String aadharCardNumber)
{
this.aadharCardNumber=aadharCardNumber;
}
public java.lang.String getAadharCardNumber( )
{
return this.aadharCardNumber;
}
public int hashCode( )
{
return this.employeeId.toUpperCase( ).hashCode( );
}
public boolean equals(Object other)
{
if(!(other instanceof EmployeeInterface))return false;
EmployeeInterface employee;
employee=(EmployeeInterface)other;
return this.employeeId.equalsIgnoreCase(employee.getEmployeeId( ));
}
public int compareTo(EmployeeInterface employee)
{
return this.employeeId.compareToIgnoreCase(employee.getEmployeeId( ));
}
};