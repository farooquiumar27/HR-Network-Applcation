package com.thinking.machines.hr.bl.interfaces.pojo;
import java.util.*;
import java.math.*;
import com.thinking.machines.enums.*;
public interface EmployeeInterface extends Comparable<EmployeeInterface> , java.io.Serializable
{
public void setEmployeeId(java.lang.String employeeId);
public java.lang.String getEmployeeId( );

public void setName(java.lang.String name);
public java.lang.String getName( );

public void setDesignation(DesignationInterface designation);
public DesignationInterface getDesignation( );

public void setDateOfBirth(java.util.Date dateOfBirth);
public java.util.Date getDateOfBirth( );

public void setIsIndian(boolean isIndian);
public boolean getIsIndian( );

public void setGender(GENDER gender);
public java.lang.String getGender( );

public void setBasicSalary(java.math.BigDecimal basicSalary);
java.math.BigDecimal getBasicSalary( );

public void setPANNumber(java.lang.String panNumber);
public java.lang.String getPANNumber( );

public void setAadharCardNumber(java.lang.String aadharCardNumber);
public java.lang.String getAadharCardNumber( );
}