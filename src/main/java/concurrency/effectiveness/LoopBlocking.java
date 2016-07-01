package concurrency.effectiveness;

import lombok.extern.log4j.Log4j;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/7/1
 * Time: 13:59
 *
 * 尽可能减少缓存失效
 * Refi：https://software.intel.com/en-us/articles/how-to-use-loop-blocking-to-optimize-memory-use-on-32-bit-intel-architecture
 **/
@Log4j
public class LoopBlocking {
    private final static int BLOCK_SIZE = 128;               //L1 data 32k?, L2 is 1024k, L3 is 6144k
    private final static int ARRAY_SIZE = BLOCK_SIZE * 64;
    private static int[][] a = new int[ARRAY_SIZE][ARRAY_SIZE];  //4 byte per cell
    private static int[][] b = new int[ARRAY_SIZE][ARRAY_SIZE];

    public static void main(String[] args) {
        _init();

        final long start = System.nanoTime();

        //noLoopBlocking();     //普通循环
        loopBlocking();     //使用缓存

        //Duration: 1291487198      16 * 512
        //Duration: 721869

        //Duration: 484975879       16 * 512
        //Duration: 757636          16 * 12
        //Duration: 484628473       8 * 512 * 2
        //Duration: 437663109       32 * 256
        //Duration: 414532186       64 * 128
        //Duration: 391411527       128 * 64
        //Duration: 784678889       256 * 32

        log.info("Duration: " + (System.nanoTime() - start));
    }

    private static void loopBlocking() {
        for (int i = 0; i < ARRAY_SIZE; i += BLOCK_SIZE) {
            for (int j = 0; j < ARRAY_SIZE; j += BLOCK_SIZE) {
                for (int ii = i; ii < i + BLOCK_SIZE; ii++) {
                    for (int jj = j; jj < j + BLOCK_SIZE; jj++) {
                        a[ii][jj] = a[ii][jj] + b[jj][ii];
                    }
                }
            }
        }
    }

    private static void noLoopBlocking() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            for (int j = 0; j < ARRAY_SIZE; j++) {
                a[i][j] = a[i][j] + b[j][i];
            }
        }
    }

    private static void _init() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            for (int j = 0; j < ARRAY_SIZE; j++) {
                a[i][j] = i;
                b[j][i] = j;
            }
        }
    }
}
