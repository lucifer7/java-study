package design.pattern.concurrency.future;

/**
 * Usage: <b>通过client 取得的真实数据</b>
 *
 * @author lucifer
 * @date 2016-8-22
 * @device Yoga Pro
 */
public class RealData implements Data {
    private final String result;

    public RealData(String query) {    // 模拟获取真实数据的耗时
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer ret = null;
        try {
            ret = Integer.parseInt(query);
        } catch (NumberFormatException e) {
            ret = query.length();
        }
        this.result = ret*10 + "";
    }

    @Override
    public String getResult() {
        return result;
    }
}
