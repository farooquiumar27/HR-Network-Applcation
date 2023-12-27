package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exception.*;
import java.util.*;
import java.io.*;
public class DesignationDAO implements DesignationDAOInterface
{
private final static String FILE_NAME="designation.data";
public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null)throw new DAOException("Designation not found");
String title=designationDTO.getTitle( );
if(title==null)throw new DAOException("Designation not found");
title=title.trim( );
if(title.length( )==0)throw new DAOException("Title required");
try
{
File file;
file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
int lastGeneratedCode=0;
int recordCount=0;
String stringLastGeneratedCode="";
String stringRecordCount="";
if(randomAccessFile.length( )==0)
{
stringLastGeneratedCode="0";
while(stringLastGeneratedCode.length( )<10)stringLastGeneratedCode+=" ";
stringRecordCount="0";
while(stringRecordCount.length( )<10)stringRecordCount+=" ";
randomAccessFile.writeBytes(stringLastGeneratedCode);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(stringRecordCount);
randomAccessFile.writeBytes("\n");
}
else
{
stringLastGeneratedCode=randomAccessFile.readLine( ).trim( );
stringRecordCount=randomAccessFile.readLine( ).trim( );
lastGeneratedCode=Integer.parseInt(stringLastGeneratedCode);
recordCount=Integer.parseInt(stringRecordCount);
}
String fTitle;
String fCode;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fCode=randomAccessFile.readLine( );
fTitle=randomAccessFile.readLine( );
if(fTitle.equalsIgnoreCase(title))
{
randomAccessFile.close( );
throw new DAOException("Title "+title+" already exists");
}
}
int code=lastGeneratedCode+1;
String _code=String.valueOf(code);
randomAccessFile.writeBytes(_code);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(title);
randomAccessFile.writeBytes("\n");
designationDTO.setCode(code);
lastGeneratedCode++;
recordCount++;
stringLastGeneratedCode=String.valueOf(lastGeneratedCode);
while(stringLastGeneratedCode.length( )<10)stringLastGeneratedCode+=" ";
stringRecordCount=String.valueOf(recordCount);
while(stringRecordCount.length( )<10)stringRecordCount+=" ";
randomAccessFile.seek(0);
randomAccessFile.writeBytes(stringLastGeneratedCode);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(stringRecordCount);
randomAccessFile.writeBytes("\n");
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null)throw new DAOException("Designation in null");
int code=designationDTO.getCode( );
String title=designationDTO.getTitle( );
if(code<=0)throw new DAOException("Invalid code : "+code);
if(title==null)throw new DAOException("Designation is null");
if(title.length( )==0)throw new DAOException("Designation is null");
try
{
File file=new File(FILE_NAME);
if(!file.exists( ))throw new DAOException("No records found");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)throw new DAOException("No records found");
int recordCount;
randomAccessFile.readLine( );
recordCount=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
if(recordCount==0)throw new DAOException("No records found");
int fCode;
String fTitle;
boolean codeFound=false;
boolean titleFound=false;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fCode=Integer.parseInt(randomAccessFile.readLine( ));
fTitle=randomAccessFile.readLine( );
if(code==fCode)codeFound=true;
if(title.equalsIgnoreCase(fTitle))
{
if(fCode!=code)
{
titleFound=true;
break;
}
}
}
if(codeFound==false)throw new DAOException("Code not found");
if(titleFound==true)throw new DAOException("Title already exists");
File tmpFile=new File("tmp.data");
if(tmpFile.exists( ))tmpFile.delete( );
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(0);
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( ));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( ));
tmpRandomAccessFile.writeBytes("\n");
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fCode=Integer.parseInt(randomAccessFile.readLine( ));
fTitle=randomAccessFile.readLine( );
if(fCode==code)
{
tmpRandomAccessFile.writeBytes(String.valueOf(code));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(title);
tmpRandomAccessFile.writeBytes("\n");
}
else
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer( )<tmpRandomAccessFile.length( ))
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine( ));
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length( ));
tmpRandomAccessFile.setLength(0);
randomAccessFile.close( );
tmpRandomAccessFile.close( );
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public void delete(int code) throws DAOException
{
if(code<=0)throw new DAOException("Invalid code : "+code);
try
{
File file=new File(FILE_NAME);
if(!file.exists( ))throw new DAOException("No records found");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)throw new DAOException("No records found");
randomAccessFile.readLine( );
int recordCount=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
if(recordCount==0)throw new DAOException("No records found");
int fCode=0;
String fTitle="";
boolean codeFound=false;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fCode=Integer.parseInt(randomAccessFile.readLine( ));
fTitle=randomAccessFile.readLine( );
if(code==fCode)
{
codeFound=true;
break;
}
}
if(codeFound==false)throw new DAOException("Code not found");
if(new EmployeeDAO( ).isDesignationCodeAlloted(code))throw new DAOException("Employee exists with designation : "+fTitle);
File tmpFile=new File("tmp.data");
if(tmpFile.exists( ))tmpFile.delete( );
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(0);
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine( ));
tmpRandomAccessFile.writeBytes("\n");
recordCount--;
String sr=String.valueOf(recordCount);
while(sr.length( )<10)sr=sr+" ";
randomAccessFile.readLine( );
tmpRandomAccessFile.writeBytes(sr);
tmpRandomAccessFile.writeBytes("\n");
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fCode=Integer.parseInt(randomAccessFile.readLine( ));
fTitle=randomAccessFile.readLine( );
if(fCode!=code)
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer( )<tmpRandomAccessFile.length( ))
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine( ));
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length( ));
tmpRandomAccessFile.setLength(0);
randomAccessFile.close( );
tmpRandomAccessFile.close( );
}

catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public Set<DesignationDTOInterface> getAll( ) throws DAOException
{
Set<DesignationDTOInterface> designations;
designations=new TreeSet<>( );
try
{
File file=new File(FILE_NAME);
if(!file.exists( ))return designations;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)
{
randomAccessFile.close( );
return designations;
}
randomAccessFile.readLine( );
int count=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
if(count==0)return designations;
DesignationDTOInterface designationDTO;
int code;
String title;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
code=Integer.parseInt(randomAccessFile.readLine( ));
title=randomAccessFile.readLine( );
designationDTO=new DesignationDTO( );
designationDTO.setCode(code);
designationDTO.setTitle(title);
designations.add(designationDTO);
}
randomAccessFile.close( );
return designations;
}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0)throw new DAOException("Invalid code : "+code);
try
{
File file=new File(FILE_NAME);
if(file.exists( )==false)throw new DAOException("Invalid code : "+code);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)
{
randomAccessFile.close( );
throw new DAOException("Invalid code : "+code);
}
randomAccessFile.readLine( );
int recordCount=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
if(recordCount==0)
{
randomAccessFile.close( );
throw new DAOException("Invalid code : "+code);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fCode=Integer.parseInt(randomAccessFile.readLine( ));
fTitle=randomAccessFile.readLine( );
if(code==fCode)
{
randomAccessFile.close( );
DesignationDTO designationDTO;
designationDTO=new DesignationDTO( );
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close( );
throw new DAOException("Invalid code : "+code);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage( ));
}
}
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null || title.trim( ).length( )==0)throw new DAOException("Invalid title : "+title);
try
{
File file=new File(FILE_NAME);
if(file.exists( )==false)throw new DAOException("Invalid title : "+title);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)
{
randomAccessFile.close( );
throw new DAOException("Invalid title : "+title);
}
randomAccessFile.readLine( );
int recordCount=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
if(recordCount==0)
{
randomAccessFile.close( );
throw new DAOException("Invalid title : "+title);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fCode=Integer.parseInt(randomAccessFile.readLine( ));
fTitle=randomAccessFile.readLine( );
if(title.equalsIgnoreCase(fTitle))
{
randomAccessFile.close( );
DesignationDTO designationDTO;
designationDTO=new DesignationDTO( );
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close( );
throw new DAOException("Invalid title : "+title);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage( ));
}
}
public boolean codeExists(int code) throws DAOException
{
if(code<=0)return false;
try
{
File file=new File(FILE_NAME);
if(file.exists( )==false)throw new DAOException("Invalid code : "+code);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)
{
randomAccessFile.close( );
throw new DAOException("Invalid code : "+code);
}
randomAccessFile.readLine( );
int recordCount=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
if(recordCount==0)
{
randomAccessFile.close( );
throw new DAOException("Invalid code : "+code);
}
int fCode;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
fCode=Integer.parseInt(randomAccessFile.readLine( ));
randomAccessFile.readLine( );
if(code==fCode)
{
randomAccessFile.close( );
return true;
}
}
randomAccessFile.close( );
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage( ));
}
}
public boolean titleExists(String title) throws DAOException
{
if(title==null || title.trim( ).length( )==0)return false;
try
{
File file=new File(FILE_NAME);
if(file.exists( )==false)throw new DAOException("Invalid title : "+title);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)
{
randomAccessFile.close( );
throw new DAOException("Invalid title : "+title);
}
randomAccessFile.readLine( );
int recordCount=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
if(recordCount==0)
{
randomAccessFile.close( );
return false;
}
String fTitle;
while(randomAccessFile.getFilePointer( )<randomAccessFile.length( ))
{
randomAccessFile.readLine( );
fTitle=randomAccessFile.readLine( );
if(title.equalsIgnoreCase(fTitle))
{
randomAccessFile.close( );
return true;
}
}
randomAccessFile.close( );
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage( ));
}
}
public int getCount( ) throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists( )==false)return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length( )==0)return 0;
randomAccessFile.readLine( );
int recordCount=Integer.parseInt(randomAccessFile.readLine( ).trim( ));
if(recordCount==0)return 0;
return recordCount;
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage( ));
}
}
};