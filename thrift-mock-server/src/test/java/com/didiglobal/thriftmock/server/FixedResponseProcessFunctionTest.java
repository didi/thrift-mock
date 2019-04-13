package com.didiglobal.thriftmock.server;

import com.didiglobal.thriftmock.test.source.HelloService;
import com.didiglobal.thriftmock.test.source.Request;
import com.didiglobal.thriftmock.test.source.Response;
import com.didiglobal.thriftmock.test.source.TProtocolUtil;

import org.apache.thrift.protocol.TProtocol;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixedResponseProcessFunctionTest {

  private static final Logger LOG = LoggerFactory.getLogger(JoinServerTest.class);

  Response expectHelloResponse = new Response(200, "hello");

  @Test
  public void testFixedResponse() throws Exception {
    ThriftMockServer server = new ThriftMockServer(9999);
    Thread t = new Thread(server::start);
    t.start();

    server.setExpectReturn("sayHello", expectHelloResponse);

    Request request = new Request();
    request.setMsg("hello, i'm guest");
    TProtocol protocol = TProtocolUtil.initTProtocol("127.0.0.1", 9999);
    HelloService.Iface helloService = new HelloService.Client(protocol);
    Response  actualResponse = helloService.sayHello(request);
    LOG.info("actualResponse: " + actualResponse);
    Assert.assertTrue(actualResponse.getCode() == 200);
    server.stop();
  }

  @Test(expected = org.apache.thrift.transport.TTransportException.class)
  public void testTimeout() throws Exception {
    ThriftMockServer server = new ThriftMockServer(9998);
    Thread t = new Thread(server::start);
    t.start();
    server.setExpectReturn("sayHello", expectHelloResponse, 1000);

    Request request = new Request();
    request.setMsg("hello, i'm guest");
    TProtocol protocol = TProtocolUtil.initTProtocol("127.0.0.1", 9998, 800);
    HelloService.Iface helloService = new HelloService.Client(protocol);
    helloService.sayHello(request);
    server.stop();
  }
}
