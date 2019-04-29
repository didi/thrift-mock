package com.didiglobal.thriftmock.server.junit;


import com.google.common.base.Stopwatch;

import com.didiglobal.thriftmock.test.source.HelloService;
import com.didiglobal.thriftmock.test.source.Request;
import com.didiglobal.thriftmock.test.source.Response;

import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class AsyncMockServerRuleTest {

  @Rule
  public AsyncThriftMockServer server = new AsyncThriftMockServer(9999);

  @Test
  public void testAsyncServerRule() throws Exception {
    String responseMsg = "asyncHello";
    Response expectHelloResponse = new Response(200, responseMsg);
    server.setExpectReturn("sayHello", expectHelloResponse);

    TNonblockingTransport transport = new TNonblockingSocket("127.0.0.1",
                                                             9999,
                                                             3000);
    HelloService.AsyncIface client = new HelloService.AsyncClient(new TBinaryProtocol.Factory(),
                                                                  new TAsyncClientManager(),
                                                                  transport);
    Request request = new Request();
    request.setMsg("hello async!");
    Response result = new Response();
    Stopwatch stopwatch = Stopwatch.createStarted();
    client.sayHello(request, new AsyncMethodCallback() {
      @Override
      public void onComplete(Object response) {
        System.out.println("cost:" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        result.setCode(((Response)response).getCode());
        result.setResponseMsg(((Response)response).getResponseMsg());
      }

      @Override
      public void onError(Exception exception) {
        System.out.println(exception.getMessage());
      }
    });
    Thread.sleep(50);
    System.out.println("final result:" + result);
    Assert.assertTrue(result.getCode() == 200);
    Assert.assertTrue(responseMsg.equals(result.getResponseMsg()));
  }

}
