package com.didiglobal.thriftmock.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.junit.Assert;
import org.junit.Test;

public class AsyncServerConfigTest {

    @Test
    public void testConstructorWithPort() {
        AsyncServerConfig config = new AsyncServerConfig(8800);
        Assert.assertEquals(8800, config.getPort());
        Assert.assertNotNull(config.getTProtocolFactory());
        Assert.assertNotNull(config.getProcessMap());
        Assert.assertTrue(config.getProcessMap().isEmpty());
    }

    @Test
    public void testConstructorWithPortAndFactory() {
        TProtocolFactory factory = new TBinaryProtocol.Factory();
        AsyncServerConfig config = new AsyncServerConfig(8801, factory);
        Assert.assertEquals(8801, config.getPort());
        Assert.assertEquals(factory, config.getTProtocolFactory());
    }

    @Test
    public void testSetPort() {
        AsyncServerConfig config = new AsyncServerConfig(8800);
        AsyncServerConfig returned = config.setPort(8803);
        Assert.assertEquals(8803, config.getPort());
        Assert.assertSame(config, returned);
    }

    @Test
    public void testSettServerProtocolFactory() {
        AsyncServerConfig config = new AsyncServerConfig(8800);
        TProtocolFactory factory = new TCompactProtocol.Factory();
        AsyncServerConfig returned = config.settServerProtocolFactory(factory);
        Assert.assertEquals(factory, config.getTProtocolFactory());
        Assert.assertSame(config, returned);
    }

    @Test
    public void testProcessMapIsWritable() {
        AsyncServerConfig config = new AsyncServerConfig(8800);
        Assert.assertNotNull(config.getProcessMap());
        Assert.assertEquals(0, config.getProcessMap().size());
    }
}
