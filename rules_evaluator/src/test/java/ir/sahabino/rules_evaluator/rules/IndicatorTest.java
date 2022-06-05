package ir.sahabino.rules_evaluator.rules;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class IndicatorTest {

    @Test
    public void operate() {
    }

    @Test
    public void getFieldToOperateOn() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Indicator indicator = new Indicator();
        Method method = Indicator.class.getDeclaredMethod("getFieldToOperateOn", String.class);
        method.setAccessible(true);
        Method close = (Method) method.invoke(indicator, "close");
        assertEquals(close.getName(), "getClose");
    }
}