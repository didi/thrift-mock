package com.didiglobal.thriftmock.server;

import org.apache.thrift.ProcessFunction;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;

import java.util.function.Function;

public class ProcessFunctionMock extends ProcessFunction implements Delay{

  private String methodName;
  private int delay;
  private TBase args;
  private Function<TBase, TBase> mockResultFunction;
  private boolean isOneway = false;
  private boolean isPrimitive = false;

  public ProcessFunctionMock(String methodName, TBase result) {
    this(methodName, result, 0);
  }

  public ProcessFunctionMock(String methodName, TBase result, int delay) {
    this(methodName, result, tBase -> result, delay);
  }

  public ProcessFunctionMock(String methodName,
                             TBase args,
                             Function<TBase, TBase> mockResultFunction) {
    this(methodName, args, mockResultFunction, 0);
  }

  public ProcessFunctionMock(String methodName,
                             TBase args,
                             Function<TBase, TBase> mockResultFunction,
                             int delay) {
    super(methodName);
    this.methodName = methodName;
    this.args = args;
    this.mockResultFunction = mockResultFunction;
    this.delay = delay;
  }

  public ProcessFunctionMock setOneway(boolean oneway) {
    this.isOneway = oneway;

    return this;
  }

  public ProcessFunctionMock setPrimitive() {
    this.isPrimitive = true;

    return this;
  }

  @Override
  protected boolean isOneway() {
    return isOneway;
  }

  @Override
  public TBase getResult(Object iface, TBase args) throws TException {
    delay(delay);
    return isPrimitive ? mockResultFunction.apply(args) : new MockResult(methodName, mockResultFunction.apply(args));
  }

  @Override
  public TBase getEmptyArgsInstance() {
    return args;
  }
}
