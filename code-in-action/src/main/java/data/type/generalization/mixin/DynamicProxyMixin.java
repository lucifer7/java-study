package data.type.generalization.mixin;

import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import net.mindview.util.TwoTuple;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import static net.mindview.util.Tuple.tuple;

/**
 * Usage: <b> 使用动态代理产生混型 </b>
 *
 * @author lucifer
 *         Date 2017-2-7
 *         Device Aurora R5
 */
@Log4j
public class DynamicProxyMixin {
    public static void main(String[] args) {
        Object mixin = MixinProxy.newInstance(tuple(new BasicImpl(), Basic.class),
                tuple(new TimestampedImpl(), Timestamped.class),
                tuple(new SerialNumImpl(), SerialNum.class));
        Basic b = (Basic) mixin;
        Timestamped t = (Timestamped) mixin;
        SerialNum s = (SerialNum) mixin;
        b.set("hello");
        log.info(b.get());
        log.info(t.getStamp());
        log.info(s.getSerialNum());
    }
}

class MixinProxy implements InvocationHandler {
    // Map<MethodName --> Instance>
    Map<String, Object> delegatesByMethod;

    public MixinProxy(TwoTuple<Object, Class<?>>... pairs) {
        delegatesByMethod = Maps.newHashMap();
        for (TwoTuple<Object, Class<?>> tuple : pairs) {
            for (Method method : tuple.second.getMethods()) {
                String methodName = method.getName();
                if (!delegatesByMethod.containsKey(methodName))
                    delegatesByMethod.put(methodName, tuple.first);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object delegate = delegatesByMethod.get(method.getName());
        return method.invoke(delegate, args);
    }

    public static Object newInstance(TwoTuple... pairs) {
        Class[] interfaces = new Class[pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            interfaces[i] = (Class) pairs[i].second;
        }
        ClassLoader cl = pairs[0].first.getClass().getClassLoader();
        return Proxy.newProxyInstance(cl, interfaces, new MixinProxy(pairs));
    }
}
