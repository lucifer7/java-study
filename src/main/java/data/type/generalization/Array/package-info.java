/**
 * Usage: <b>泛型数组无法直接创建，但可以定义引用</b>
 * 1. 使用T[] array, 直接创建报错
 * 2. 使用Object[] array，取出元素的时候再转型 {@link data.type.generalization.Array.GenericArray}
 * 3. *[RECOMMEND]使用类型标识 TypeToken，构造器中传入Class<T> 对象 {@link data.type.generalization.Array.GenericArrayWithTypeToken}
 *
 * {@reference https://segmentfault.com/a/1190000005179147#articleHeader5}
 * @author lucifer
 * @date 2016-8-27
 * @device Yoga Pro
 */
package data.type.generalization.Array;

