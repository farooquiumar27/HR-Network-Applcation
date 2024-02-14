package com.thinking.machines.hr.server;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.server.*;
import com.thinking.machines.hr.bl.exception.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;

public class RequestHandler implements RequestHandlerInterface 
{
private DesignationManagerInterface designationManager;
private EmployeeManagerInterface employeeManager;
public RequestHandler( )
{
try
{
this.designationManager=DesignationManager.getDesignationManager( );
}catch(BLException blException)
{
//do nothing
}
}
public Response processEmployee(Request request)
{
return null;
}
public Response processDesignation(Request request)
{
String action=request.getAction();
Response response=null;
if(designationManager==null)
{
//will implement later on.
}
if(action.equalsIgnoreCase("addDesignation"))
{
response=new Response();
try
{
Object[ ] arguments=request.getArgument();
DesignationInterface designation=(DesignationInterface)arguments[0];
designationManager.addDesignation(designation);
response.setSuccess(true);
response.setResult(designation);
}
catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException.getMessage( ));
}
return response;
}
if(action.equalsIgnoreCase("updateDesignation"))
{
response=new Response();
try
{
Object[ ] arguments=request.getArgument();
DesignationInterface designation=(DesignationInterface)arguments[0];
designationManager.updateDesignation(designation);
response.setSuccess(true);
}
catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException.getMessage( ));
}
return response;
}
if(action.equalsIgnoreCase("removeDesignation"))
{
response=new Response();
try
{
Object[ ] arguments=request.getArgument();
int code=(Integer)arguments[0];
designationManager.removeDesignation(code);
response.setSuccess(true);
}
catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException.getMessage( ));
}
return response;
}
if(action.equalsIgnoreCase("getDesignationByCode"))
{
response=new Response();
try
{
Object[ ] arguments=request.getArgument();
int code=(Integer)arguments[0];
DesignationInterface designation=designationManager.getDesignationByCode(code);
response.setSuccess(true);
response.setResult(designation);
}
catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException.getMessage( ));
}
return response;
}
if(action.equalsIgnoreCase("getDesignationByTitle"))
{
response=new Response();
try
{
Object[ ] arguments=request.getArgument();
String title=(String)arguments[0];
DesignationInterface designation=designationManager.getDesignationByTitle(title);
response.setSuccess(true);
response.setResult(designation);
}
catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException.getMessage( ));
}
return response;
}
if(action.equalsIgnoreCase("getDesignationCount"))
{
response=new Response();
try
{
int count=designationManager.getDesignationCount( );
response.setSuccess(true);
response.setResult(count);
}
catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException.getMessage( ));
}
return response;
}
if(action.equalsIgnoreCase("designationCodeExists"))
{
response=new Response();
try
{
Object[ ] arguments=request.getArgument();
int code=(Integer)arguments[0];
boolean found=designationManager.designationCodeExists(code);
response.setSuccess(true);
response.setResult(found);
}
catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException.getMessage( ));
}
return response;
}
if(action.equalsIgnoreCase("designationTitleExists"))
{
response=new Response();
try
{
Object[ ] arguments=request.getArgument();
String title=(String)arguments[0];
boolean found=designationManager.designationTitleExists(title);
response.setSuccess(true);
response.setResult(found);
}
catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException.getMessage( ));
}
return response;
}
if(action.equalsIgnoreCase("getDesignations"))
{
response=new Response();
try
{
Set<DesignationInterface> designations=designationManager.getDesignations();
response.setSuccess(true);
response.setResult(designations);
}
catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException.getMessage( ));
}
return response;
}
return response;
}
public Response process(Request request)
{
String manager=request.getManager( );
Response response=null;
if(manager.equalsIgnoreCase("DesignationManager"))response=processDesignation(request);
else response=processEmployee(request);
return response;
}
};