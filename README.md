# thrift-mock    
A lightweight java unit test library for mocking thrift service

## Features
* Init thrift mock server without any pre-define thrift idl, only a port is needed
* Dynamic binding thrift interface to its response
* Configurable response delays
* Support binding different interfaces from different thrift service to one server 
* Embedded with junit4

## Getting Started

### 1. import maven dependency
The official groupId is not ready yet, please fork and release it temporarily. 
We will release to maven central repo as soon as possible.

### 2. start server 
```java
   //working without junit, starting a non-blocking server
   ThriftMockServer server = new ThriftMockServer(9999);
   Thread t = new Thread(server::start);
   t.start();
 
   //working with junit 
   @Rule
   public ThriftMockServer server = new ThriftMockServer(9999);

```  
### 3. bind interface with expect response   
If you have a thrift file like this:

```thrift
namespace java com.xiaoju.ddpay.thriftmock.server

struct Request{
    1:optional string msg,
}

struct Response{
    1:required i32 code,
    2:required string responseMsg,
}


service HelloService {
    Response sayHello(1:required Request request);
}
service ByeService {
    Response sayBye(1:required Request request);
}
```


#### 3.1 bind interface to fixed response
```java
    //define expect response
    Response expectHelloResponse = new Response(200, "hello");
    //bind response
    server.setExpectReturn("sayHello", expectHelloResponse);
    //bind response with specific delay(ms)
    server.setExpectReturn("sayHello", expectHelloResponse, 100);
```
#### 3.2 bind interface to dynamic response
```java
    //bind interface to dynamic response 
    TBase emptyArgs = new HelloService.sayHello_args();
    Function<TBase, TBase> returnLogic = (tBase) -> {
      HelloService.sayHello_args sayHello_args = (HelloService.sayHello_args)tBase;
      if (sayHello_args.getRequest().getMsg().contains("fail")) {
        return new Response(500, "fail");
      }
      return new Response(200, "success");
    };
    server.setExpectReturn("sayHello", emptyArgs, returnLogic);
```

#### 3.3 request server
```java   
    //init a thrift client connect to mock server 
    TTransport transport = new TSocket("127.0.0.1", 9999);
    transport.open();
    TProtocol protocol = new TBinaryProtocol(transport);
    HelloService.Iface client = new HelloService.Client(protocol);
    //request interface sayHello
    Response msg = client.sayHello(request);
    //get the expect response
    Assert.assertEquals(msg.getCode(), 200);
    Assert.assertEquals(msg.getMsg(), "hello");

```
## Todo
* thrift-mock don't support aysnc call yet, but we will support it in next release version. 

## Contributing    
Welcome to contribute by creating issues or sending pull requests. See [Contributing Guide](./CONTRIBUTING.md) for guidelines.

## License        
thrift-mock is licensed under the Apache License 2.0. See the [LICENSE](./LICENSE) file.

## Note    
This is not an official Didi product (experimental or otherwise), it is just code that happens to be owned by Didi.


