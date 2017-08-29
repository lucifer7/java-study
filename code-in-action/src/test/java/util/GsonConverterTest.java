package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.entity.User;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import util.json.adapter.StringAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/12
 * Time: 17:01
 **/
@Log4j
public class GsonConverterTest {
    @Test
    public void stringConverterTest() {
        User user = new User();
        user.setName("googlessss");

        //log.info(JsonUtils.toJson(user));
        //log.info(JsonUtils.toNoFormatJson(user));

        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(String.class, new StringAdapter())
            .serializeNulls();
        Gson gson = gb.create();


        log.info(gson.toJson(user));    //{"name":"googlessss","age":null,"nickName":null}
    }

}
