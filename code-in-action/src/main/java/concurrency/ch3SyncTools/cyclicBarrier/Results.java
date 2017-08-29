package concurrency.ch3SyncTools.cyclicBarrier;

import lombok.extern.log4j.Log4j;

/**
 * Usage: <b> 辅助类2，保存被查找的数字在矩阵每行出现的次数 </b>
 *
 * @user lucifer
 * @date 2016-9-10
 * @device Aurora R5
 */
@Log4j
public class Results {
    private int data[];

    public Results(int size) {
        this.data = new int[size];
    }

    public int[] getData() {
        return data;
    }

    public void setData(int position, int value) {
        this.data[position] = value;
    }
}
