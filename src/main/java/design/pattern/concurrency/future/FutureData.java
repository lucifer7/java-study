package design.pattern.concurrency.future;

/**
 * Usage: <b>调用Client后立刻取得的数据，是RealData的代理，封装了等待的过程</b>
 *
 * @author lucifer
 * @date 2016-8-22
 * @devide Yoga Pro
 */
public class FutureData implements Data {
    private boolean isReady = false;
    private RealData realData = null;       // Wrapper for RealData

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();                    // RealData is injected, notify getResult();
    }

    @Override
    public String getResult() {         // wait till realData is ready
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.getResult();
    }
}
