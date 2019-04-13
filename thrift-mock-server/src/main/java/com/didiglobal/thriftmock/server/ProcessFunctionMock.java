package com.didiglobal.thriftmock.server;

import org.apache.thrift.ProcessFunction;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessFunctionMock extends ProcessFunction {

  private static final Logger LOG = LoggerFactory.getLogger(ProcessFunctionMock.class);

  private String methodName;
  private int delay;
  private MockResult mockResult;

  public ProcessFunctionMock(String methodName, TBase result) {
    this(methodName, result, 0);
  }

  public ProcessFunctionMock(String methodName, TBase result, int delay) {
    super(methodName);
    this.methodName = methodName;
    this.delay = delay;
    this.mockResult = new MockResult(methodName, result);
  }

  @Override
  protected boolean isOneway() {
    return false;
  }

  @Override
  public TBase getResult(Object iface, TBase args) throws TException {
    LOG.info("iface:{}, method:{}, args:{}", iface, methodName, args);
    if (delay > 0) {
      try {
        Thread.sleep(delay);
      }catch (Exception e) {
        LOG.warn("mock delay failed",  e);
      }
    }
    return mockResult;
  }

  @Override
  public TBase getEmptyArgsInstance() {
    return mockResult;
  }
}
