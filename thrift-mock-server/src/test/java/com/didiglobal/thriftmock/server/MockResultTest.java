package com.didiglobal.thriftmock.server;

import com.didiglobal.thriftmock.test.source.Response;

import org.junit.Assert;
import org.junit.Test;

public class MockResultTest {

    private final Response response1 = new Response(200, "hello");
    private final Response response2 = new Response(500, "error");

    // ---- construction --------------------------------------------------------

    @Test
    public void testConstructorAndGetSuccess() {
        MockResult result = new MockResult("sayHello", response1);
        Assert.assertEquals(response1, result.getSuccess());
        Assert.assertEquals("sayHello", result.methodName);
    }

    @Test
    public void testCopyConstructor() {
        MockResult original = new MockResult("sayHello", response1);
        MockResult copy = new MockResult(original);
        Assert.assertEquals(response1, copy.getSuccess());
        Assert.assertEquals("sayHello", copy.methodName);
    }

    // ---- success field accessors ---------------------------------------------

    @Test
    public void testIsSetSuccess_true() {
        MockResult result = new MockResult("sayHello", response1);
        Assert.assertTrue(result.isSetSuccess());
    }

    @Test
    public void testIsSetSuccess_falseAfterClear() {
        MockResult result = new MockResult("sayHello", response1);
        result.clear();
        Assert.assertFalse(result.isSetSuccess());
    }

    @Test
    public void testClear() {
        MockResult result = new MockResult("sayHello", response1);
        result.clear();
        Assert.assertNull(result.getSuccess());
    }

    @Test
    public void testUnsetSuccess() {
        MockResult result = new MockResult("sayHello", response1);
        result.unsetSuccess();
        Assert.assertNull(result.getSuccess());
    }

    @Test
    public void testSetSuccessIsSet_false() {
        MockResult result = new MockResult("sayHello", response1);
        result.setSuccessIsSet(false);
        Assert.assertNull(result.getSuccess());
    }

    @Test
    public void testSetSuccessIsSet_true_noOp_whenNull() {
        MockResult result = new MockResult("sayHello", response1);
        result.clear();
        result.setSuccessIsSet(true);
        // true branch does nothing in the implementation
        Assert.assertNull(result.getSuccess());
    }

    @Test
    public void testSetSuccess() {
        MockResult result = new MockResult("sayHello", response1);
        MockResult returned = result.setSuccess(response2);
        Assert.assertEquals(response2, result.getSuccess());
        Assert.assertSame(result, returned);
    }

    // ---- deepCopy / toString -------------------------------------------------

    @Test
    public void testDeepCopy_returnsSelf() {
        MockResult result = new MockResult("sayHello", response1);
        MockResult copy = result.deepCopy();
        Assert.assertSame(result, copy);
    }

    @Test
    public void testToString_containsSuccessValue() {
        MockResult result = new MockResult("sayHello", response1);
        String str = result.toString();
        Assert.assertNotNull(str);
        Assert.assertTrue(str.contains("MockResult"));
        Assert.assertTrue(str.contains("success"));
    }

    @Test
    public void testToString_nullSuccess() {
        MockResult result = new MockResult("sayHello", response1);
        result.clear();
        String str = result.toString();
        Assert.assertTrue(str.contains("null"));
    }

    // ---- equals --------------------------------------------------------------

    @Test
    public void testEqualsObject_sameInstance() {
        MockResult result = new MockResult("sayHello", response1);
        Assert.assertTrue(result.equals((Object) result));
    }

    @Test
    public void testEqualsObject_null() {
        MockResult result = new MockResult("sayHello", response1);
        Assert.assertFalse(result.equals((Object) null));
    }

    @Test
    public void testEqualsObject_differentType() {
        MockResult result = new MockResult("sayHello", response1);
        Assert.assertFalse(result.equals("someString"));
    }

    @Test
    public void testEquals_MockResult_null() {
        MockResult result = new MockResult("sayHello", response1);
        Assert.assertFalse(result.equals((MockResult) null));
    }

    @Test
    public void testEquals_equalObjects() {
        MockResult result1 = new MockResult("sayHello", response1);
        MockResult result2 = new MockResult("sayHello", response1);
        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void testEquals_differentSuccess() {
        MockResult result1 = new MockResult("sayHello", response1);
        MockResult result2 = new MockResult("sayHello", response2);
        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void testEquals_oneSuccessNull() {
        MockResult result1 = new MockResult("sayHello", response1);
        MockResult result2 = new MockResult("sayHello", response1);
        result2.clear();
        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void testEquals_bothSuccessNull() {
        MockResult result1 = new MockResult("sayHello", response1);
        MockResult result2 = new MockResult("sayHello", response1);
        result1.clear();
        result2.clear();
        Assert.assertTrue(result1.equals(result2));
    }

    // ---- hashCode ------------------------------------------------------------

    @Test
    public void testHashCode_doesNotThrow() {
        MockResult result = new MockResult("sayHello", response1);
        int hashCode = result.hashCode();
        Assert.assertEquals(hashCode, result.hashCode()); // stable: same object returns same value
    }

    @Test
    public void testHashCode_equalObjectsHaveEqualHashCode() {
        MockResult result1 = new MockResult("sayHello", response1);
        MockResult result2 = new MockResult("sayHello", response1);
        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    // ---- compareTo -----------------------------------------------------------

    @Test
    public void testCompareTo_equalObjects() {
        MockResult result1 = new MockResult("sayHello", response1);
        MockResult result2 = new MockResult("sayHello", response1);
        Assert.assertEquals(0, result1.compareTo(result2));
    }

    @Test
    public void testCompareTo_firstHasSuccessSecondDoesNot() {
        MockResult result1 = new MockResult("sayHello", response1);
        MockResult result2 = new MockResult("sayHello", response1);
        result2.clear();
        Assert.assertNotEquals(0, result1.compareTo(result2));
    }

    @Test
    public void testCompareTo_bothSuccessNull() {
        MockResult result1 = new MockResult("sayHello", response1);
        MockResult result2 = new MockResult("sayHello", response1);
        result1.clear();
        result2.clear();
        Assert.assertEquals(0, result1.compareTo(result2));
    }

    // ---- TBase field operations ----------------------------------------------

    @Test
    public void testFieldForId_zero_returnsSuccess() {
        MockResult result = new MockResult("sayHello", response1);
        Assert.assertEquals(MockResult._Fields.SUCCESS, result.fieldForId(0));
    }

    @Test
    public void testFieldForId_nonZero_returnsNull() {
        MockResult result = new MockResult("sayHello", response1);
        Assert.assertNull(result.fieldForId(99));
    }

    @Test
    public void testGetFieldValue_success() {
        MockResult result = new MockResult("sayHello", response1);
        Assert.assertEquals(response1, result.getFieldValue(MockResult._Fields.SUCCESS));
    }

    @Test
    public void testSetFieldValue_success() {
        MockResult result = new MockResult("sayHello", response1);
        result.setFieldValue(MockResult._Fields.SUCCESS, response2);
        Assert.assertEquals(response2, result.getSuccess());
    }

    @Test
    public void testSetFieldValue_null_clearsSuccess() {
        MockResult result = new MockResult("sayHello", response1);
        result.setFieldValue(MockResult._Fields.SUCCESS, null);
        Assert.assertNull(result.getSuccess());
    }

    @Test
    public void testIsSet_successField() {
        MockResult result = new MockResult("sayHello", response1);
        Assert.assertTrue(result.isSet(MockResult._Fields.SUCCESS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsSet_nullField_throws() {
        MockResult result = new MockResult("sayHello", response1);
        result.isSet(null);
    }

    // ---- _Fields enum --------------------------------------------------------

    @Test
    public void testFieldsEnum_getThriftFieldId() {
        Assert.assertEquals(0, MockResult._Fields.SUCCESS.getThriftFieldId());
    }

    @Test
    public void testFieldsEnum_getFieldName() {
        Assert.assertEquals("success", MockResult._Fields.SUCCESS.getFieldName());
    }

    @Test
    public void testFieldsEnum_findByThriftId_zero() {
        Assert.assertEquals(MockResult._Fields.SUCCESS, MockResult._Fields.findByThriftId(0));
    }

    @Test
    public void testFieldsEnum_findByThriftId_nonZero_returnsNull() {
        Assert.assertNull(MockResult._Fields.findByThriftId(1));
    }

    @Test
    public void testFieldsEnum_findByThriftIdOrThrow_success() {
        Assert.assertEquals(MockResult._Fields.SUCCESS, MockResult._Fields.findByThriftIdOrThrow(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFieldsEnum_findByThriftIdOrThrow_throws() {
        MockResult._Fields.findByThriftIdOrThrow(1);
    }

    @Test
    public void testFieldsEnum_findByName_found() {
        Assert.assertEquals(MockResult._Fields.SUCCESS, MockResult._Fields.findByName("success"));
    }

    @Test
    public void testFieldsEnum_findByName_notFound() {
        Assert.assertNull(MockResult._Fields.findByName("unknown"));
    }
}
