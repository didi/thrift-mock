package com.didiglobal.thriftmock.server;

import org.apache.thrift.AsyncProcessFunction;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializable;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.protocol.TMessageType;
import org.apache.thrift.server.AbstractNonblockingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class AsyncProcessFunctionMock extends AsyncProcessFunction implements Delay{

  private static final Logger LOG = LoggerFactory.getLogger(AsyncProcessFunctionMock.class);

  private String methodName;
  private int delay;
  private TBase args;
  private Function<TBase, TBase> mockResultFunction;

  public AsyncProcessFunctionMock(String methodName, TBase result) {
    this(methodName, result, 0);
  }

  public AsyncProcessFunctionMock(String methodName, TBase result, int delay) {
    this(methodName, result, a -> result, delay);
  }

  public AsyncProcessFunctionMock(String methodName,
                                  TBase args,
                                  Function<TBase, TBase> mockResultFunction,
                                  int delay) {

    super(methodName);
    this.methodName = methodName;
    this.delay = delay;
    this.args = args;
    this.mockResultFunction = mockResultFunction;
  }


  @Override
  protected boolean isOneway() {
    return false;
  }

  @Override
  public void start(Object iface, TBase args, AsyncMethodCallback resultHandler) throws TException {
    delay(delay);
    resultHandler.onComplete(new MockResult(methodName, mockResultFunction.apply(args)));
  }

  @Override
  public TBase getEmptyArgsInstance() {
    return args;
  }

  @Override
  public AsyncMethodCallback getResultHandler(AbstractNonblockingServer.AsyncFrameBuffer fb,
                                              int seqid) {
    return new AsyncMethodCallback() {
      @Override
      public void onComplete(Object response) {
        try {
          sendResponse(fb, (TSerializable) response, TMessageType.REPLY, seqid);
          return;
        } catch (Exception e) {
          LOG.error("Exception writing to internal frame buffer", e);
        }
        fb.close();
      }

      @Override
      public void onError(Exception exception) {
        byte msgType;
        TBase msg;
        msgType = TMessageType.EXCEPTION;
        msg = (TBase)new TApplicationException(TApplicationException.INTERNAL_ERROR,
                                               exception.getMessage());
        try {
          sendResponse(fb,msg,msgType,seqid);
          return;
        } catch (Exception ex) {
          LOG.error("Exception writing to internal frame buffer", ex);
        }
        fb.close();
      }
    };
  }
}
