package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exception.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;

import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;

public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationMap;
private Map<String,DesignationInterface> titleWiseDesignationMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManager designationManager=null;
private void populateDataStructure( ) throws BLException
{
this.codeWiseDesignationMap=new HashMap<>( );
this.titleWiseDesignationMap=new HashMap<>( );
this.designationsSet=new TreeSet<>( );
try
{
Set<DesignationDTOInterface> dlDesignations;
dlDesignations=new DesignationDAO( ).getAll( );
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation:dlDesignations)
{
designation=new Designation( );
designation.setCode(dlDesignation.getCode( ));
designation.setTitle(dlDesignation.getTitle( ));
this.codeWiseDesignationMap.put(dlDesignation.getCode( ),designation);
this.titleWiseDesignationMap.put(new String(dlDesignation.getTitle( ).toUpperCase( )),designation);
this.designationsSet.add(designation);
}
}
catch(DAOException daoException)
{
throw new BLException(daoException.getMessage( ));
}
}
private DesignationManager( ) throws BLException
{
populateDataStructure( );
}
public static DesignationManager getDesignationManager( ) throws BLException
{
if(designationManager==null)designationManager=new DesignationManager( );
return designationManager;
}
public void addDesignation(DesignationInterface designation) throws BLException
{
if(designation==null)throw new BLException("Desigantion is null");
int code=designation.getCode( );
String title=designation.getTitle( ).trim( );
BLException blException=new BLException( );
if(code!=0)blException.setPropertyException("code","Code should be zero");
if(title==null)blException.setPropertyException("title","Title cannot be null");
if(title.length( )==0)blException.setPropertyException("title","Length of cannot be zero");
if(blException.hasException( ))throw blException;
boolean found=this.titleWiseDesignationMap.containsKey(title.toUpperCase( ));
if(found)
{
blException.setPropertyException("title", title+" already exists");
throw blException;
}
try
{
DesignationDAOInterface designationDAO=new DesignationDAO( );
DesignationDTOInterface designationDTO=new DesignationDTO( );
designationDTO.setCode(code);
designationDTO.setTitle(title);
designationDAO.add(designationDTO);
code=designationDTO.getCode( );
designation.setCode(code);
DesignationInterface blDesignation=new Designation( );
blDesignation.setCode(code);
blDesignation.setTitle(title);
this.codeWiseDesignationMap.put(code,blDesignation);
this.titleWiseDesignationMap.put(title.toUpperCase( ),blDesignation);
this.designationsSet.add(blDesignation);
}
catch(DAOException dlException)
{
blException.setGenericException(dlException.getMessage( ));
throw blException;
}
}
public void updateDesignation(DesignationInterface designation) throws BLException
{
if(designation==null)throw new BLException("Desigantion is null");
int code=designation.getCode( );
String title=designation.getTitle( ).trim( );
BLException blException=new BLException( );
if(code==0)blException.setPropertyException("code","Code cannot be zero");
if(blException.hasException( ))throw blException;
if(title==null)blException.setPropertyException("title","Title cannot be null");
if(title.trim( ).length( )==0)blException.setPropertyException("title","Length of cannot be zero");
if(blException.hasException( ))throw blException;
DesignationInterface blDesignation;
boolean found=this.codeWiseDesignationMap.containsKey(code);
if(!found)blException.setPropertyException("code",code+" not exist");
if(blException.hasException( ))throw blException;
found=this.titleWiseDesignationMap.containsKey(title.toUpperCase( ));
if(found)
{
blDesignation=this.titleWiseDesignationMap.get(title.toUpperCase( ));
if(blDesignation.getCode( )!=code)blException.setPropertyException("title",title+" already exists");
}
if(blException.hasException( ))throw blException;
try
{
DesignationDAOInterface designationDAO=new DesignationDAO( );
DesignationDTOInterface designationDTO=new DesignationDTO( );
designationDTO.setCode(code);
designationDTO.setTitle(title);
designationDAO.update(designationDTO);
blDesignation=this.codeWiseDesignationMap.get(code);
String tt=blDesignation.getTitle( );
blDesignation.setTitle(title);
this.codeWiseDesignationMap.remove(code);
this.titleWiseDesignationMap.remove(tt);
this.designationsSet.remove(blDesignation);
this.codeWiseDesignationMap.put(code,blDesignation);
this.titleWiseDesignationMap.put(title.toUpperCase( ),blDesignation);
this.designationsSet.add(blDesignation);
}
catch(DAOException dlException)
{
blException.setGenericException(dlException.getMessage( ));
throw blException;
}
}
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException( );
if(code<=0)blException.setPropertyException("code","Invalid code");
if(EmployeeManager.getEmployeeManager( ).isDesignationCodeAlloted(code))blException.setPropertyException("code","This code is currently alloted to employee");
if(blException.hasException( ))throw blException;
DesignationInterface blDesignation;
boolean found=this.codeWiseDesignationMap.containsKey(code);
if(!found)blException.setPropertyException("code",code+" not exist");
if(blException.hasException( ))throw blException;
try
{
DesignationDAOInterface designationDAO=new DesignationDAO( );
designationDAO.delete(code);
blDesignation=this.codeWiseDesignationMap.get(code);
String tt=blDesignation.getTitle( );
this.codeWiseDesignationMap.remove(code);
this.titleWiseDesignationMap.remove(tt);
this.designationsSet.remove(blDesignation);
}
catch(DAOException dlException)
{
blException.setGenericException(dlException.getMessage( ));
throw blException;
}
}
public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException=new BLException( );
if(code<=0)blException.setPropertyException("code","Code cannot be zero");
if(blException.hasException( ))throw blException;
boolean found=this.codeWiseDesignationMap.containsKey(code);
if(!found)blException.setPropertyException("code",code+" not exist");
if(blException.hasException( ))throw blException;
DesignationInterface designation=new Designation( );
DesignationInterface blDesignation;
blDesignation=this.codeWiseDesignationMap.get(code);
designation.setCode(blDesignation.getCode( ));
designation.setTitle(blDesignation.getTitle( ));
return designation;
}
DesignationInterface getDSDesignationByCode(int code)
{
return codeWiseDesignationMap.get(code);
}
public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blException=new BLException( );
if(title==null)blException.setPropertyException("title","Title cannot be null");
if(title.trim( ).length( )==0)blException.setPropertyException("title","Length of title cannot be zero");
if(blException.hasException( ))throw blException;
boolean found=this.titleWiseDesignationMap.containsKey(title.trim( ).toUpperCase( ));
if(!found)blException.setPropertyException("title",title+" not exist");
if(blException.hasException( ))throw blException;
DesignationInterface designation=new Designation( );
DesignationInterface blDesignation;
blDesignation=this.titleWiseDesignationMap.get(title.toUpperCase( ));
designation.setCode(blDesignation.getCode( ));
designation.setTitle(blDesignation.getTitle( ));
return designation;
}

public boolean designationCodeExists(int code) throws BLException
{
if(code<=0)return false;
return this.codeWiseDesignationMap.containsKey(code);
}


public boolean designationTitleExists(String title) throws BLException
{
if(title==null)return false;
if(title.trim( ).length( )==0)return false;
return this.titleWiseDesignationMap.containsKey(title.toUpperCase( ));
}

public int getDesignationCount( ) throws BLException
{
return this.designationsSet.size( );
}
public Set<DesignationInterface> getDesignations( ) throws BLException
{
Set<DesignationInterface> designations=new TreeSet<>( );
this.designationsSet.forEach(
(designation)->{
DesignationInterface blDesignation=new Designation( );
blDesignation.setCode(designation.getCode( ));
blDesignation.setTitle(designation.getTitle( ));
designations.add(blDesignation);
}
);
return designations;
}
};