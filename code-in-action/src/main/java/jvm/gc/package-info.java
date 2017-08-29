/**
 * Usage: <b> Garbage collect, algorithm and collector config </b>
 * 1. 对象在GC时的自救, finalize() {@link jvm.gc.FinalizeEscapeGc}
 * 2. GC and objects in Yong and Tenure Generation，分配担保 {@link jvm.gc.GenerationGc}
 *
 * @author lucifer
 * Date 2016-10-25
 * Device Aurora R5
 */
package jvm.gc;