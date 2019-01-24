package com.xiaoju.ddpay.thriftmock.server.junit;

import com.xiaoju.ddpay.thriftmock.server.FixedResponseProcessFunction;
import com.xiaoju.ddpay.thriftmock.server.ServerConfig;
import com.xiaoju.ddpay.thriftmock.server.MockServer;

import org.apache.thrift.TBase;
import org.junit.rules.ExternalResource;

import java.util.function.Function;

public class ThriftMockServer extends ExternalResource implements MockServer {

  private com.xiaoju.ddpay.thriftmock.server.ThriftMockServer mockServer;


  public ThriftMockServer(int port) {
    this(ServerConfig.createServerConfig(port));
  }

  public ThriftMockServer(ServerConfig serverConfig) {
    this.mockServer = new com.xiaoju.ddpay.thriftmock.server.ThriftMockServer(serverConfig);
  }

  @Override
  protected void before() throws Throwable {
    Thread thread = new Thread(this::start);
    thread.start();
  }

  @Override
  protected void after() {
    mockServer.stop();
  }

  @Override
  public void start() {
    mockServer.start();

  }

  @Override
  public void stop() {
    mockServer.stop();
  }

  @Override
  public void setExpectReturn(String methodName, TBase result) {
    mockServer.setExpectReturn(methodName, result);
  }

  @Override
  public void setExpectReturn(String methodName, TBase result, int delay) {
    mockServer.setExpectReturn(methodName, result, delay);
  }

  @Override
  public void setExpectReturn(String methodName, FixedResponseProcessFunction processFunction) {
    mockServer.setExpectReturn(methodName, processFunction);
  }

  @Override
  public void setExpectReturn(String methodName, TBase emptyArgs,
                              Function<TBase, TBase> mockResultFunction) {
    mockServer.setExpectReturn(methodName, emptyArgs, mockResultFunction);
  }
}
