package com.thinking.machines.network.server;
import java.net.*;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exception.*;
public class NetworkServer
{
private RequestHandlerInterface rqhd;
public NetworkServer(RequestHandlerInterface rqhd)throws NetworkException
{
if(rqhd==null)throw new NetworkException("Request handler missing");
this.rqhd=rqhd;
}
public void start( )throws NetworkException
{
ServerSocket serverSocket=null;
int port=Configuration.getPort();
try
{
serverSocket=new ServerSocket(port);
}catch(Exception exception)
{
throw new NetworkException("Unable to start sever on port : "+port);
}
try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server is ready to accept on port "+port+" ....");
socket=serverSocket.accept( );
requestProcessor=new RequestProcessor(socket,rqhd);
}
}
catch(Exception e)
{
System.out.println(e.getMessage( ));
}
}
};
