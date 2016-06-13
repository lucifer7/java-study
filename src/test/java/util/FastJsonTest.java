package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import entity.User;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/13
 * Time: 9:51
 **/
@Log4j
public class FastJsonTest {
    @Test
    public void fastJsonNullTest() {
        User user = new User();
        user.setName("fastjsonlessss");

        log.info(JSON.toJSON(user));
    }
}
