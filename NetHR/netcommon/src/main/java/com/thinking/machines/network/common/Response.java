package com.thinking.machines.network.common;
public class Response implements java.io.Serializable
{
private boolean success;
private Object exception;
private Object result;
public void setSuccess(boolean success)
{
this.success=success;
}
public boolean getSuccess()
{
return this.success;
}
public void setException(Object exception)
{
this.exception=exception;
}
public Object getException()
{
return this.exception;
}
public void setResult(Object result)
{
this.result=result;
}
public Object getResult()
{
return this.result;
}
public boolean hasException( )
{
return this.success==false;
}
};