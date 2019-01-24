package com.xiaoju.ddpay.thriftmock.server;

import com.xiaoju.ddpay.thriftmock.test.source.ByeService;
import com.xiaoju.ddpay.thriftmock.test.source.HelloService;
import com.xiaoju.ddpay.thriftmock.test.source.Request;
import com.xiaoju.ddpay.thriftmock.test.source.Response;
import com.xiaoju.ddpay.thriftmock.test.source.TProtocolUtil;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinServerTest {

  private static final Logger LOG = LoggerFactory.getLogger(JoinServerTest.class);

  /**
   * bind 2 different service into one mock server
   * @throws Exception
   */
  @Test
  public void testJoinServer() throws TException, InterruptedException {
    ThriftMockServer server = new ThriftMockServer(9988);
    Thread t = new Thread(server::start);
    t.start();
    Thread.sleep(10);

    String name = "guest";
    Response expectHelloResponse = new Response();
    expectHelloResponse.setCode(200);
    expectHelloResponse.setResponseMsg("hello:" + name);

    Response expectByeResponse = new Response();
    expectByeResponse.setCode(201);
    expectByeResponse.setResponseMsg("bye," + name);

    //Join two different service into one server
    //helloService
    server.setExpectReturn("sayHello", expectHelloResponse);
    //byeService
    server.setExpectReturn("sayBye", expectByeResponse);

    Request helloRequest = new Request();
    helloRequest.setMsg("hello, i'm " + name);

    TProtocol protocol = TProtocolUtil.initTProtocol("127.0.0.1", 9988);
    HelloService.Iface helloService = new HelloService.Client(protocol);
    Response actualHelloResponse = helloService.sayHello(helloRequest);
    LOG.info("actualHelloResponse: " + actualHelloResponse);
    Assert.assertTrue(actualHelloResponse.getCode() == 200);

    Request byeRequest = new Request();
    helloRequest.setMsg("bye, i'm " + name);

    ByeService.Iface byeService = new ByeService.Client(protocol);
    Response actualByeResponse = byeService.sayBye(byeRequest);
    LOG.info("actualByeResponse: " + actualByeResponse);
    Assert.assertEquals(actualByeResponse.getCode(), 201);
  }
}
