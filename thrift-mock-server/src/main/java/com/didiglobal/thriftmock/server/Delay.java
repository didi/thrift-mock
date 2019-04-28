package com.didiglobal.thriftmock.server;

public interface Delay {

  default void delay(int delay) {
    if (delay > 0) {
      try {
        Thread.sleep(delay);
      }catch (Exception e) {
        System.out.println("mock delay failed " + e);
      }
    }
  }

}
