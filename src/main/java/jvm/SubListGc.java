package jvm;

import com.google.common.collect.Lists;
import common.entity.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/7/28
 * Time: 10:10
 **/
public class SubListGc {

    private static final int ARRAY_SIZE = 15000;

    public static void main(String[] args) throws InterruptedException {
        List<User> backList = Lists.newArrayListWithCapacity(ARRAY_SIZE);
        for (int i = 0; i < ARRAY_SIZE; i++) {
            backList.add(new User());
        }
        //System.out.println(backList.size());
        //List<User> subList = backList.subList(20, 30);
        List<User> subList = Lists.newArrayList();
        //SoftReference softReference = new SoftReference();
        //WeakReference weakReference = new WeakReference(backList);
        //System.gc();
        Runtime.getRuntime().gc();
        TimeUnit.SECONDS.sleep(10L);
        System.out.println(subList);
        System.out.println(backList);
        //System.out.println(weakReference);
    }
}
