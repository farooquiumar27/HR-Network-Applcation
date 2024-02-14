package com.thinking.machines.network.server;
import java.nio.file.*;
import java.io.*;
import com.google.gson.*;
import com.thinking.machines.network.common.exception.*;
import com.thinking.machines.network.common.*;
class configInfo
{
private int port;
public void setPort(int port)
{
this.port=port;
}
public int getPort( )
{
return this.port;
}
};
public class Configuration
{
private static int port;
static
{
try
{
File file=new File("server.cfg");
if(file.exists()==false)
{
System.out.println("server.cfg missing , read documentation");
System.exit(-1);
}
if(file.isDirectory())
{
System.out.println("server.cfg missing , read documentation");
System.exit(-1);
}
String name=file.getName();
String content = new String(Files.readAllBytes(Paths.get(name)));
Gson gson=new Gson();
configInfo info=gson.fromJson(content,configInfo.class);
if(info.getPort()<0 || info.getPort()>=49151)throw new NetworkException("server.cfg is not configured according to the documentation");
port=info.getPort( );
}
catch(Exception exception)
{
System.out.println(exception.getMessage());
System.exit(-1);
}
}
public static int getPort()
{
return port;
}
};