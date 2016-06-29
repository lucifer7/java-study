package util.json.fastjson;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/13
 * Time: 13:30
 **/
public class FastJsonFeatures {
    public static final SerializerFeature[] Feature = {
            SerializerFeature.WriteMapNullValue,        //OUTPUT NULL MAP VALUE
            SerializerFeature.WriteNullListAsEmpty,     //OUTPUT NULL LIST
            SerializerFeature.WriteNullStringAsEmpty,   //OUTPUT NULL STRING AS ""
            SerializerFeature.WriteNullNumberAsZero     //OUTPUT NULL NUMBER AS 0
    };
}
