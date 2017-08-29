/**
 * Usage: <b> 并发程序的正确性测试 </b>
 * 测试对象：基于信号量的有界缓存 BoundedBuffer
 * 1. 基本状态的测试，full and empty
 * 2. 阻塞操作的测试，应当能够正确阻塞，并且抛出 InterruptedException        {@link concurrency.test.BoundedBufferTest}
 * 3. 安全性测试，并发访问下的数据安全（正确）          {@link concurrency.test.PutTakeTest}
 * 4. 资源管理的测试
 *
 * @author lucifer
 * Date 2016-9-17
 * Device Aurora R5
 */
package concurrency.test;