package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exception.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exception.*;
import com.thinking.machines.network.client.*;
public class DesignationManager implements DesignationManagerInterface
{
private static DesignationManager designationManager=null;
private DesignationManager( ) throws BLException
{

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
if(title.trim().length( )==0)blException.setPropertyException("title","Length of cannot be zero");
if(blException.hasException( ))throw blException;
Request request=new Request( );
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.ADD_DESIGNATION));
request.setArgument(designation);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException( );
throw blException;
}
DesignationInterface d=(DesignationInterface)response.getResult();
designation.setCode(d.getCode());
}
catch(NetworkException networkException)
{
throw new BLException(networkException.getMessage( ));
}
}
public void updateDesignation(DesignationInterface designation) throws BLException
{
if(designation==null)throw new BLException("Desigantion is null");
int code=designation.getCode( );
String title=designation.getTitle( ).trim( );
BLException blException=new BLException( );
if(code<=0)blException.setPropertyException("code","Invaid code");
if(title==null)blException.setPropertyException("title","Title cannot be null");
if(title.trim().length( )==0)blException.setPropertyException("title","Length of cannot be zero");
if(blException.hasException( ))throw blException;
Request request=new Request( );
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.UPDATE_DESIGNATION));
request.setArgument(designation);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException( );
throw blException;
}
}
catch(NetworkException networkException)
{
throw new BLException(networkException.getMessage( ));
}
}
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException( );
if(code<=0)blException.setPropertyException("code","Invalid code");
if(blException.hasException( ))throw blException;
Request request=new Request( );
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.REMOVE_DESIGNATION));
request.setArgument(code);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException( );
throw blException;
}
}
catch(NetworkException networkException)
{
throw new BLException(networkException.getMessage( ));
}
}
public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException=new BLException( );
if(code<=0)blException.setPropertyException("code","Code cannot be zero");
if(blException.hasException( ))throw blException;
Request request=new Request( );
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_CODE));
request.setArgument(code);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException( );
throw blException;
}
DesignationInterface d=(DesignationInterface)response.getResult();
return d;
}
catch(NetworkException networkException)
{
throw new BLException(networkException.getMessage( ));
}
}
public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blException=new BLException( );
if(title==null)blException.setPropertyException("title","Title cannot be null");
if(title.trim( ).length( )==0)blException.setPropertyException("title","Length of title cannot be zero");
if(blException.hasException( ))throw blException;
Request request=new Request( );
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_TITLE));
request.setArgument(title.trim());
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException( );
throw blException;
}
DesignationInterface designation=(DesignationInterface)response.getResult();
return designation;
}
catch(NetworkException networkException)
{
throw new BLException(networkException.getMessage( ));
}
}

public boolean designationCodeExists(int code) throws BLException
{
if(code<=0)return false;
Request request=new Request( );
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.DESIGNATION_CODE_EXISTS));
request.setArgument(code);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
BLException blException=(BLException)response.getException( );
throw blException;
}
Boolean result=(Boolean)response.getResult();
return result;
}
catch(NetworkException networkException)
{
throw new BLException(networkException.getMessage( ));
}
}


public boolean designationTitleExists(String title) throws BLException
{
if(title==null)return false;
if(title.trim( ).length( )==0)return false;
Request request=new Request( );
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.DESIGNATION_TITLE_EXISTS));
request.setArgument(title);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
BLException blException=(BLException)response.getException( );
throw blException;
}
Boolean result=(Boolean)response.getResult();
return result;
}
catch(NetworkException networkException)
{
throw new BLException(networkException.getMessage( ));
}
}

public int getDesignationCount( ) throws BLException
{
Request request=new Request( );
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_COUNT));
request.setArgument();
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
BLException blException=(BLException)response.getException( );
throw blException;
}
Integer result=(Integer)response.getResult();
return result;
}
catch(NetworkException networkException)
{
throw new BLException(networkException.getMessage( ));
}
}
public Set<DesignationInterface> getDesignations( ) throws BLException
{
Request request=new Request( );
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATIONS));
request.setArgument();
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
BLException blException=(BLException)response.getException( );
throw blException;
}
Set<DesignationInterface> designations=(Set<DesignationInterface>)response.getResult();
return designations;
}
catch(NetworkException networkException)
{
throw new BLException(networkException.getMessage( ));
}
}
};