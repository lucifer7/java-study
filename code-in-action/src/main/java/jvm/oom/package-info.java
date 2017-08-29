/**
 * Usage: <b> JVM OOM and SOF Error testing </b>
 * 1. Heap OOM: by creating numerous objects
 * 2. VM Stack SOF: by depth of recursive methods
 * 3. VM Stack OOM: by creating numerous thread, DO NOT run on windows
 * 4. Runtime Constant Pool OOM: by String intern
 * 5. Method Area OOM: using CGLib, generating multiple classes
 * 6. Direct Memory OOM: unsafe.allocateMemory(), which actually apply for memory, while DirectByteBuffer throws OOM by caculating
 *
 * Failed test: 3, 4, 5
 *
 * @author lucifer
 * Date 2016-10-16
 * Device Aurora R5
 */
package jvm.oom;