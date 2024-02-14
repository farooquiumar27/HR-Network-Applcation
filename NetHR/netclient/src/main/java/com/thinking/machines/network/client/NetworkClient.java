package com.thinking.machines.network.client;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exception.*;
import java.net.*;
import java.io.*;
public class NetworkClient
{
public Response send(Request requestObject)throws NetworkException 
{
try
{
ByteArrayOutputStream baos=new ByteArrayOutputStream( );
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(requestObject);
oos.flush( );
byte request[ ]=baos.toByteArray( );
int requestLength=request.length;

byte header[ ]=new byte[1024];
int i,x;
i=requestLength;
x=1023;
while(i>0)
{
header[x]=(byte)(i%10);
i=i/10;
x--;
}

Socket socket=new Socket(Configuration.getHost(),Configuration.getPort());
OutputStream os=socket.getOutputStream( );
os.write(header,0,1024);
os.flush( );

InputStream is=socket.getInputStream( );
int bytesReadCount=0;
byte ack[ ]=new byte[1];
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1)continue;
break;
}

int chunkSize=1024;
i=0;
x=0;
while(x<requestLength)
{
if(requestLength-x<chunkSize)chunkSize=requestLength-x;
os.write(request,x,chunkSize);
os.flush( );
x=x+chunkSize;
}

byte tmp[]=new byte[1024];
i=0;
int k=0;
int responseLength=1024;
int j=0;
while(j<responseLength)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}

os.write(ack,0,1);
os.flush( );

i=1023;
j=1;
int len=0;
while(i>0)
{
len=len+(header[i]*j);
i--;
j=j*10;
}

byte response[ ]=new byte[len];
i=0;
responseLength=len;
j=0;
while(j<responseLength)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)continue;
for(k=0;k<bytesReadCount;k++)
{
response[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}

os.write(ack,0,1);
os.flush( );

ByteArrayInputStream bais=new ByteArrayInputStream(response);
ObjectInputStream ois=new ObjectInputStream(bais);
Response responseObject=(Response)ois.readObject();
System.out.println("Response recived");
socket.close( );
return responseObject;
}
catch(Exception e)
{
throw new NetworkException(e.getMessage( ));
}
}

};