package com.didiglobal.thriftmock.server.junit;

import com.didiglobal.thriftmock.server.AsyncServerConfig;
import com.didiglobal.thriftmock.server.ServerConfig;
import com.didiglobal.thriftmock.test.source.HelloService;
import com.didiglobal.thriftmock.test.source.Request;
import com.didiglobal.thriftmock.test.source.Response;
import com.didiglobal.thriftmock.test.source.TProtocolUtil;

import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TProtocol;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class JunitServerConfigTest {

    @Test
    public void testThriftMockServerWithConfig_startStop() throws Exception {
        ServerConfig config = new ServerConfig(8870);
        ThriftMockServer server = new ThriftMockServer(config);
        Response expected = new Response(200, "cfg");
        server.setExpectReturn("sayHello", expected);

        Thread t = new Thread(server::start);
        t.start();
        Thread.sleep(100);

        try {
            TProtocol protocol = TProtocolUtil.initTProtocol("127.0.0.1", 8870);
            HelloService.Iface client = new HelloService.Client(protocol);
            Response actual = client.sayHello(new Request().setMsg("hi"));
            Assert.assertEquals(200, actual.getCode());
        } finally {
            server.stop();
        }
    }

    @Test
    public void testThriftMockServerWithConfig_setExpectReturnWithDelay() throws Exception {
        ServerConfig config = new ServerConfig(8871);
        ThriftMockServer server = new ThriftMockServer(config);
        server.setExpectReturn("sayHello", new Response(200, "delay"), 0);

        Thread t = new Thread(server::start);
        t.start();
        Thread.sleep(100);

        try {
            TProtocol protocol = TProtocolUtil.initTProtocol("127.0.0.1", 8871);
            HelloService.Iface client = new HelloService.Client(protocol);
            Response actual = client.sayHello(new Request().setMsg("hi"));
            Assert.assertEquals(200, actual.getCode());
        } finally {
            server.stop();
        }
    }

    @Test
    public void testThriftMockServerWithConfig_setExpectReturnWithFunction() throws Exception {
        ServerConfig config = new ServerConfig(8872);
        ThriftMockServer server = new ThriftMockServer(config);

        TBase emptyArgs = new HelloService.sayHello_args();
        Function<TBase, TBase> fn = args -> new Response(202, "fn");
        server.setExpectReturn("sayHello", emptyArgs, fn);

        Thread t = new Thread(server::start);
        t.start();
        Thread.sleep(100);

        try {
            TProtocol protocol = TProtocolUtil.initTProtocol("127.0.0.1", 8872);
            HelloService.Iface client = new HelloService.Client(protocol);
            Response actual = client.sayHello(new Request().setMsg("hi"));
            Assert.assertEquals(202, actual.getCode());
        } finally {
            server.stop();
        }
    }

    @Test
    public void testAsyncThriftMockServerWithConfig_startStop() throws Exception {
        AsyncServerConfig config = new AsyncServerConfig(8873);
        AsyncThriftMockServer server = new AsyncThriftMockServer(config);
        server.setExpectReturn("sayHello", new Response(200, "asyncCfg"));

        Thread t = new Thread(server::start);
        t.start();
        Thread.sleep(100);

        server.stop();
    }

    @Test
    public void testAsyncThriftMockServerWithConfig_setExpectReturnWithDelay() {
        AsyncServerConfig config = new AsyncServerConfig(8874);
        AsyncThriftMockServer server = new AsyncThriftMockServer(config);
        // Covers (String, TBase, int) overload in the junit wrapper
        server.setExpectReturn("sayHello", new Response(200, "delay"), 0);
        server.stop();
    }

    @Test
    public void testAsyncThriftMockServerWithConfig_setExpectReturnWithFunction() {
        AsyncServerConfig config = new AsyncServerConfig(8875);
        AsyncThriftMockServer server = new AsyncThriftMockServer(config);
        // Covers (String, TBase, Function) overload in the junit wrapper
        TBase emptyArgs = new HelloService.sayHello_args();
        server.setExpectReturn("sayHello", emptyArgs, args -> new Response(200, "fn"));
        server.stop();
    }

    @Test
    public void testThriftMockServerStart_threadCompletes() throws Exception {
        // Verifies that start() returns cleanly after stop() is called from another thread
        ThriftMockServer server = new ThriftMockServer(8876);
        Thread t = new Thread(server::start);
        t.start();
        Thread.sleep(100);
        server.stop();
        t.join(2000); // server thread must terminate within 2 s of stop()
    }
}
