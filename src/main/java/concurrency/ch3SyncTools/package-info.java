/**
 * Usage: <b> 本章介绍了常用的线程同步工具 </b>
 * 1. semaphore 资源许可，控制并发访问资源的线程数量
 * 2. CountDownLatch 闭锁，等待所有线程准备完毕后开始执行(await())，比如举行会议，这货好比一扇大门，人到齐就打开，但不能再重新关上了
 * 3. CyclicBarrier 可重用的栅栏，闭锁的加强版，能重用，能回调，能搞 map-reduce
 * 4. Phaser 分段执行，栅栏的加强版，能更改状态、人数，还能自己定义 onAdvance()
 * 5. Exchanger 并发线程间交换数据，只适应于两个线程
 *
 *
 * @author lucifer
 * Date 2016-9-16
 * Device Aurora R5
 */
package concurrency.ch3SyncTools;