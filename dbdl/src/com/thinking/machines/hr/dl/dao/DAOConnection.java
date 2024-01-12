package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.exception.*;
import java.sql.*;
public class DAOConnection
{
private DAOConnection( ){ }
static public Connection getConnection( ) throws DAOException
{
Connection connection=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb","hr","hr");
}
catch(Exception exception)
{
throw new DAOException(exception.getMessage( ));
}
return connection;
}
};