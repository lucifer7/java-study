package util.fastjson;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/13
 * Time: 13:30
 **/
public class FastJsonConfig {
    public static final SerializerFeature[] Feature = {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteNullNumberAsZero
    };
}
