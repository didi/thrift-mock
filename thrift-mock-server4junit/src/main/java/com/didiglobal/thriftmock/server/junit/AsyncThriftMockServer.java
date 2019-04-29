package com.didiglobal.thriftmock.server.junit;

import com.didiglobal.thriftmock.server.AsyncServerConfig;
import com.didiglobal.thriftmock.server.MockServer;
import com.didiglobal.thriftmock.server.ServerConfig;

import org.apache.thrift.TBase;
import org.junit.rules.ExternalResource;

import java.util.function.Function;

public class AsyncThriftMockServer extends ExternalResource implements MockServer {

  private com.didiglobal.thriftmock.server.MockServer mockServer;

  public AsyncThriftMockServer(int port) {
    this(new AsyncServerConfig(port));
  }

  public AsyncThriftMockServer(AsyncServerConfig serverConfig) {
    this.mockServer = new com.didiglobal.thriftmock.server.AsyncThriftMockServer(serverConfig);
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
  public void setExpectReturn(String methodName, TBase emptyArgs,
                              Function<TBase, TBase> mockResultFunction) {
    mockServer.setExpectReturn(methodName, emptyArgs, mockResultFunction);
  }
}
