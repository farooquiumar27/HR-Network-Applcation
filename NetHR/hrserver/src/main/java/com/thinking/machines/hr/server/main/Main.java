package com.thinking.machines.hr.server.main;
import com.thinking.machines.hr.server.*;
import com.thinking.machines.network.common.exception.*;
import com.thinking.machines.network.server.*;
public class Main
{
public static void main(String gg[ ])
{
try
{
RequestHandlerInterface requestHandler=new RequestHandler();
NetworkServer networkServer;
networkServer=new NetworkServer(requestHandler);
networkServer.start();
}
catch(NetworkException networkException)
{
System.out.println(networkException.getMessage());
}
}
};