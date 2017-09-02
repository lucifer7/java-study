package concurrency.ch5ForkJoin;

import com.google.common.collect.Lists;
import common.entity.User;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/9/2
 **/
public class UserListGenerator {
    public List<User> generate(int size) {
        List<User> users = Lists.newArrayListWithCapacity(size);
        for (int i = 0; i < size; i++) {
            User user = new User();
            user.setName("User-" + i);
            user.setAge(RandomUtils.nextInt(i, 10 * i));
            users.add(user);
        }
        return users;
    }
}
