package util;

import com.google.gson.Gson;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/8/11
 **/
// GSON 解析复杂类型
public class JsonParseArbitraryType {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        //Container container = gson.fromJson(new FileReader("/home/input.json"), Container.class);
        /*Container container = gson.fromJson("{\"context\":\"context\",\"cpuUsage\":\"cpuUsageValue\",\"name\":\"thename\",\"rates\":{\"definition\":[{\"key\":\"name\",\"type\":\"string\"},{\"key\":\"rate\",\"type\":\"double\"}],\"rows\":[{\"name\":\"thename1\",\"rate\":\"therate\"},{\"name\":\"thename2\",\"rate\":\"therate2\"}]}}",
                Container.class);*/
        Container container = gson.fromJson("{\n" +
                "    \"context\": \"context\", \n" +
                "    \"cpuUsage\": \"cpuUsageValue\",  \n" +
                "    \"name\": \"thename\",\n" +
                "    \"rates\": {\n" +
                "        \"definition\": [\n" +
                "            {\n" +
                "                \"key\": \"name\",\n" +
                "                \"type\": \"string\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"rate\",\n" +
                "                \"type\": \"double\"\n" +
                "            }       \n" +
                "        ],\n" +
                "        \"rows\": [\n" +
                "            {\n" +
                "                \"name\": \"thename1\",\n" +
                "                \"rate\": \"therate\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"thename2\",\n" +
                "                \"rate\": \"therate2\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}", Container.class);
        System.out.println(gson.toJson(container));
    }
}

class Container {
    private String context;
    private String cpuUsage;
    private String name;
    private Rates rates;
}

class Rates {
    private List<Definition> definition;
    private List<Row> rows;
}

class Definition {
    private String key;
    private String type;
}

class Row {
    private String name;
    private String rate;
}
