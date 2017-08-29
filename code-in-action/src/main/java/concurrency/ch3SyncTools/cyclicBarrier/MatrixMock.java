package concurrency.ch3SyncTools.cyclicBarrier;

import lombok.extern.log4j.Log4j;

import java.util.Random;

/**
 * Usage: <b> 辅助类1，数字矩阵，随机生成 </b>
 *
 * @user lucifer
 * @date 2016-9-10
 * @device Aurora R5
 */
@Log4j
public class MatrixMock {
    private int data[][];

    public MatrixMock(int size, int length, int number) {
        int counter = 0;
        this.data = new int[size][length];

        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < length; j++) {
                data[i][j] = random.nextInt(10);
                if (data[i][j] == number) {     // 查找数字 number
                    counter++;
                }
            }
        }

      log.info(String.format("Matrix: %d occurred %d times in generated data.", number, counter));
    }

    public int[] getRow(int row) {
        if (row >= 0 && row < data.length) {
            return data[row];
        }
        return null;
    }
}
