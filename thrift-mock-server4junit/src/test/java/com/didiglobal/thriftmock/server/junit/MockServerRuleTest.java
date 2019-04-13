package com.didiglobal.thriftmock.server.junit;


import com.didiglobal.thriftmock.test.source.HelloService;
import com.didiglobal.thriftmock.test.source.Request;
import com.didiglobal.thriftmock.test.source.Response;
import com.didiglobal.thriftmock.test.source.TProtocolUtil;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class MockServerRuleTest {

  @Rule
  public ThriftMockServer server = new ThriftMockServer(9999);

  @Test
  public void testServerRule() throws TException {
    Response expectHelloResponse = new Response(200, "hello");
    server.setExpectReturn("sayHello", expectHelloResponse);

    Request request = new Request();
    request.setMsg("hello, i'm guest");

    TProtocol protocol = TProtocolUtil.initTProtocol("127.0.0.1", 9999, 800);
    HelloService.Iface helloService = new HelloService.Client(protocol);
    Response  actualResponse = helloService.sayHello(request);
    Assert.assertTrue(actualResponse.getCode() == 200);
  }

}
