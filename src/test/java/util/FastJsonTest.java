package util;

import com.alibaba.fastjson.JSON;
import entity.User;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import util.fastjson.FastJsonConfig;

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

        log.info(JSON.toJSONString(user, FastJsonConfig.Feature));    //{"age":0,"name":"fastjsonlessss","nickName":""}
        log.info(JSON.toJSON(user));  //{"name":"fastjsonlessss"}
    }
}
