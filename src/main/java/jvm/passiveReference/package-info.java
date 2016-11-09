/**
 * Usage: <b> 对类的被动引用不会触发其初始化，3个例子 </b>
 * 1. 通过子类引用父类的静态字段，不会初始化子类
 * 2. 数组定义引用的类，不会初始化
 * 3. 引用类常量，类不会初始化
 *
 * @author lucifer
 * Date 2016-11-9
 * Device Aurora R5
 */
package jvm.passiveReference;