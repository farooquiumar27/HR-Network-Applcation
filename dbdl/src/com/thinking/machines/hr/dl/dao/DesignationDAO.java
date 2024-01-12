package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exception.*;
import java.util.*;
import java.io.*;
import java.sql.*;
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
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where title=?;");
preparedStatement.setString(1,title);
ResultSet resultSet=preparedStatement.executeQuery( );
if(resultSet.next( ))
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Designation "+title+" exists");
}
resultSet.close( );
preparedStatement.close( );
preparedStatement=connection.prepareStatement("insert into designation (title) values(?);",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate( );
resultSet=preparedStatement.getGeneratedKeys( );
resultSet.next( );
int code=resultSet.getInt(1);
designationDTO.setCode(code);
resultSet.close( );
preparedStatement.close( );
connection.close( );
}
catch(SQLException ioe)
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
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select title from designation where code=?;");
preparedStatement.setString(1,String.valueOf(code));
ResultSet resultSet=preparedStatement.executeQuery( );
if(!resultSet.next( ))
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Invalid code : "+code);
}
resultSet.close( );
preparedStatement.close( );
preparedStatement=connection.prepareStatement("update designation set title=? where code=?;");
preparedStatement.setString(1,title);
preparedStatement.setString(2,String.valueOf(code));
preparedStatement.executeUpdate( );
preparedStatement.close( );
connection.close( );
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public void delete(int code) throws DAOException
{
if(code<=0)throw new DAOException("Invalid code : "+code);
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement=connection.prepareStatement("select title from designation where code=?;");
preparedStatement.setString(1,String.valueOf(code));
ResultSet resultSet=preparedStatement.executeQuery( );
if(!resultSet.next( ))
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Invalid code : "+code);
}
resultSet.close( );
preparedStatement.close( );
preparedStatement=connection.prepareStatement("select gender from employee where designation_code=?;");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery( );
if(resultSet.next( ))
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Designation is alloted to employee and cannot be deleted");
}
resultSet.close( );
preparedStatement.close( );
preparedStatement=connection.prepareStatement("delete from designation where code=?;");
preparedStatement.setString(1,String.valueOf(code));
preparedStatement.executeUpdate( );
preparedStatement.close( );
connection.close( );
}
catch(SQLException ioe)
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
Connection connection;
connection=DAOConnection.getConnection( );
Statement statement;
statement=connection.createStatement( );
ResultSet resultSet;
resultSet=statement.executeQuery("select * from designation");
DesignationDTOInterface designationDTO;
while(resultSet.next( ))
{
designationDTO=new DesignationDTO( );
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim( ));
designations.add(designationDTO);
}
resultSet.close( );
statement.close( );
connection.close( );
return designations;
}
catch(SQLException ioe)
{
throw new DAOException(ioe.getMessage( ));
}
}
public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0)throw new DAOException("Invalid code : "+code);
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select title from designation where code=?;");
preparedStatement.setString(1,String.valueOf(code));
ResultSet resultSet=preparedStatement.executeQuery( );
if(!resultSet.next( ))
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Invalid code : "+code);
}
String title=resultSet.getString("title");
resultSet.close( );
preparedStatement.close( );
connection.close( );
DesignationDTOInterface designationDTO=new DesignationDTO( );
designationDTO.setCode(code);
designationDTO.setTitle(title);
return designationDTO;
}catch(SQLException ioException)
{
throw new DAOException(ioException.getMessage( ));
}
}
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null || title.trim( ).length( )==0)throw new DAOException("Invalid title : "+title);
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where title=?;");
preparedStatement.setString(1,title);
ResultSet resultSet=preparedStatement.executeQuery( );
if(!resultSet.next( ))
{
resultSet.close( );
preparedStatement.close( );
connection.close( );
throw new DAOException("Invalid code : "+title);
}
int code=resultSet.getInt(1);
resultSet.close( );
preparedStatement.close( );
connection.close( );
DesignationDTOInterface designationDTO=new DesignationDTO( );
designationDTO.setCode(code);
designationDTO.setTitle(title);
return designationDTO;
}catch(SQLException ioException)
{
throw new DAOException(ioException.getMessage( ));
}
}
public boolean codeExists(int code) throws DAOException
{
if(code<=0)return false;
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select title from designation where code=?;");
preparedStatement.setString(1,String.valueOf(code));
ResultSet resultSet=preparedStatement.executeQuery( );
if(!resultSet.next( ))
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
}catch(SQLException ioException)
{
throw new DAOException(ioException.getMessage( ));
}
}
public boolean titleExists(String title) throws DAOException
{
if(title==null || title.trim( ).length( )==0)return false;
try
{
Connection connection=DAOConnection.getConnection( );
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where title=?;");
preparedStatement.setString(1,title);
ResultSet resultSet=preparedStatement.executeQuery( );
if(!resultSet.next( ))
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
}catch(SQLException ioException)
{
throw new DAOException(ioException.getMessage( ));
}
}
public int getCount( ) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection( );
Statement statement=connection.createStatement( );
ResultSet resultSet=statement.executeQuery("select count(*) from designation;");
resultSet.next( );
int count=resultSet.getInt(1);
resultSet.close( );
statement.close( );
connection.close( );
return count;
}
catch(SQLException ioException)
{
throw new DAOException(ioException.getMessage( ));
}
}
};