package com.xiaoju.ddpay.thriftmock.server;

import com.xiaoju.ddpay.thriftmock.test.source.HelloService;
import com.xiaoju.ddpay.thriftmock.test.source.Request;
import com.xiaoju.ddpay.thriftmock.test.source.Response;

import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class DynamicResponseProcessFunctionTest {

  @Test
  public void testDispatchMock() throws Exception {
    ThriftMockServer server = new ThriftMockServer(9999);
    Thread t = new Thread(server::start);
    t.start();
    TBase emptyArgs = new HelloService.sayHello_args();
    Function<TBase, TBase> returnLogic = (tBase) -> {
      HelloService.sayHello_args sayHello_args = (HelloService.sayHello_args)tBase;
      if (sayHello_args.getRequest().getMsg().contains("fail")) {
        return new Response(500, "fail");
      }
      return new Response(200, "success");
    };

    server.setExpectReturn("sayHello", emptyArgs, returnLogic);
    //server.setExpectReturn("sayHello", new Response(200, "success"));

    Request fail = new Request();
    fail.setMsg("fail");
    TTransport transport = new TSocket("127.0.0.1", 9999);
    transport.open();
    TProtocol protocol = new TBinaryProtocol(transport);
    HelloService.Iface helloService = new HelloService.Client(protocol);
    Response actualResponse = helloService.sayHello(fail);
    System.out.println("actualResponse:" + actualResponse);
    Assert.assertTrue(actualResponse.getCode() == 500);
    Request success = new Request();
    success.setMsg("test Success");
    actualResponse = helloService.sayHello(success);
    System.out.println("actualResponse:" + actualResponse);
    Assert.assertTrue(actualResponse.getCode() == 200);
    transport.close();
    server.stop();
  }
}
