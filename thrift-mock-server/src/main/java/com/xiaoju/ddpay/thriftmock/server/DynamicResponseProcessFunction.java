package com.xiaoju.ddpay.thriftmock.server;

import org.apache.thrift.ProcessFunction;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;

import java.util.function.Function;

public class DynamicResponseProcessFunction extends ProcessFunction {

  private String methodName;
  private int delay;
  private TBase emtpyArgs;
  private Function<TBase, TBase> mockResultFunction;

  public DynamicResponseProcessFunction(String methodName,
                                        TBase emtpyArgs,
                                        Function<TBase, TBase> mockResultFunction) {
    this(methodName, emtpyArgs, mockResultFunction, 0);
  }

  public DynamicResponseProcessFunction(String methodName,
                                        TBase emtpyArgs,
                                        Function<TBase, TBase> mockResultFunction,
                                        int delay) {
    super(methodName);
    this.methodName = methodName;
    this.emtpyArgs = emtpyArgs;
    this.mockResultFunction = mockResultFunction;
    this.delay = delay;
  }

  @Override
  protected boolean isOneway() {
    return false;
  }

  @Override
  public TBase getResult(Object iface, TBase args) throws TException {
    System.out.println("iface:" + iface + ",method:" + methodName + ",args:" + args);
    if (delay > 0) {
      try {
        Thread.sleep(delay);
      }catch (Exception e) {
        System.out.println("mock delay failed " + e);
      }
    }
    return new MockResult(methodName, mockResultFunction.apply(args));
  }

  @Override
  public TBase getEmptyArgsInstance() {
    return emtpyArgs;
  }
}
