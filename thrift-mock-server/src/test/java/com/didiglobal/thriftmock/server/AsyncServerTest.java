package com.didiglobal.thriftmock.server;

import com.google.common.base.Stopwatch;

import com.didiglobal.thriftmock.test.source.HelloService;
import com.didiglobal.thriftmock.test.source.Request;
import com.didiglobal.thriftmock.test.source.Response;

import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class AsyncServerTest {

  AsyncServerConfig config = new AsyncServerConfig(9000, TBinaryProtocol::new);
  MockServer mockServer;

  @Before
  public void before() {
    mockServer = new AsyncThriftMockServer(config);
    new Thread(mockServer::start).start();
  }

  @After
  public void after() {
    mockServer.stop();
  }

  @Test
  public void testAsyncServer() throws Exception {
    Response response = new Response();
    response.setCode(200);
    String responseMsg = "asyncResponse";
    response.setResponseMsg(responseMsg);
    mockServer.setExpectReturn("sayHello", response);

    TNonblockingTransport transport = new TNonblockingSocket("127.0.0.1",
                                                             config.getPort(),
                                                             4000);
    HelloService.AsyncIface client = new HelloService.AsyncClient(config.getTProtocolFactory(),
                                                                  new TAsyncClientManager(),
                                                                  transport);
    Request request = new Request();
    request.setMsg("hello async!");
    Response result = new Response();
    Stopwatch stopwatch = Stopwatch.createStarted();
    client.sayHello(request, new AsyncMethodCallback() {
      @Override
      public void onComplete(Object response) {
        //TODO according to the test result, async call seems cost 30~70m. That's strange!
        System.out.println("async call cost:" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        result.setCode(((Response)response).getCode());
        result.setResponseMsg(((Response)response).getResponseMsg());
      }

      @Override
      public void onError(Exception exception) {
        System.out.println(exception.getLocalizedMessage());
      }
    });
    System.out.println("final result:" + result);
    Thread.sleep(100);
    Assert.assertTrue(result.getCode() == 200);
    Assert.assertTrue(responseMsg.equals(result.getResponseMsg()));
  }
}
