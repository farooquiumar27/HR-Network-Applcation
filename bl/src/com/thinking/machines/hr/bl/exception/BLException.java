package com.thinking.machines.hr.bl.exception;
import java.util.*;
public class BLException extends Exception
{
private String genericMessage;
private Map<String,String> propertyException;
public BLException( )
{
this.genericMessage=null;
this.propertyException=new HashMap<>( );
}
public BLException(String genericMessage)
{
this.genericMessage=genericMessage;
this.propertyException=new HashMap<>( );
}
public boolean hasException( )
{
return (genericMessage!=null || this.propertyException.size( )>0)?true:false;
}
public boolean hasGenericException( )
{
return (genericMessage!=null)?true:false;
}
public boolean hasPropertyExceptions( )
{
return (this.propertyException.size( )>0)?true:false;
}
public boolean hasPropertyException(String property)
{
return this.propertyException.containsKey(property.toUpperCase( ));
}
public void setGenericException(String genericMessage)
{
this.genericMessage=genericMessage;
}
public String getGenericException( )
{
return (this.genericMessage!=null)?this.genericMessage:"";
}
public String getMessage( )
{
return (this.genericMessage!=null)?this.genericMessage:"";
}
public void setPropertyException(String property,String exception)
{
this.propertyException.put(property.toUpperCase( ),exception);
}
public String getPropertyException(String property)
{
return this.propertyException.get(property.toUpperCase( ));
}
public int getExceptionsCount( )
{
return (this.genericMessage!=null)?this.propertyException.size( )+1:this.propertyException.size( );
}
public List<String> getProperties( )
{
List<String> properties=new ArrayList<>( );
this.propertyException.forEach(
(k,v)->{properties.add(k);}
);
return properties;
}
};