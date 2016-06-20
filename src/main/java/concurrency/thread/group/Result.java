package concurrency.thread.group;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/20
 * Time: 15:33
 **/
@Data
public class Result {
    private String name;    //记录最后一个正常执行的searcher线程的名称
}
