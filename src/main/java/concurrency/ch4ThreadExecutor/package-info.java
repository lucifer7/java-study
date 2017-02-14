/**
 * Usage: <b> 线程执行者 </b>
 * 1. 使用 Executors 框架, cachedPool OR fixedPool, {@link concurrency.ch4ThreadExecutor.threadRunner.RunnerMain}
 * 2. 执行返回结果的任务，有两种：Callable, Future, {@link concurrency.ch4ThreadExecutor.runnerResult.ResultRunnerMain}
 * 3. 运行多个任务，处理首个结果, using ExecutorService.invokeAny(), {@link concurrency.ch4ThreadExecutor.multiRunnerResult.MultiRunnerMain}
 * 4. 运行多个任务，处理所有结果, using ExecutorService.invokeAll(), {@link concurrency.ch4ThreadExecutor.multiRunnerAllResult.RunnerAllResultsMain}
 * 5. 延迟执行任务, ScheduleExecutorService, {@link concurrency.ch4ThreadExecutor.delayedRunner.DelayedRunnerMain}
 *
 * @author lucifer
 * Date 2016-9-27
 * Device Aurora R5
 */
package concurrency.ch4ThreadExecutor;