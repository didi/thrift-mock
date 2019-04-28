package com.didiglobal.thriftmock.server;

import com.didiglobal.thriftmock.test.source.HelloService;
import com.didiglobal.thriftmock.test.source.Request;
import com.didiglobal.thriftmock.test.source.Response;

import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.junit.Assert;
import org.junit.Test;

public class AsyncServerTest {


  @Test
  public void testAsyncServer() throws Exception {
    AsyncServerConfig config = new AsyncServerConfig(9000, TBinaryProtocol::new);
    MockServer mockServer = new AsyncThriftMockServer(config);
    new Thread(mockServer::start).start();
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
    Thread.sleep(100);
    Request request = new Request();
    request.setMsg("hello async!");
    Response result = new Response();
    client.sayHello(request, new AsyncMethodCallback() {
      @Override
      public void onComplete(Object response) {
        System.out.println("sayHello onComplete:" + response);
        result.setCode(((Response)response).getCode());
        result.setResponseMsg(((Response)response).getResponseMsg());
      }

      @Override
      public void onError(Exception exception) {
        System.out.println(exception);
      }
    });
    Thread.sleep(100);
    System.out.println("final result:" + result);
    mockServer.stop();
    Assert.assertTrue(result.getCode() == 200);
    Assert.assertTrue(responseMsg.equals(result.getResponseMsg()));

  }
}
