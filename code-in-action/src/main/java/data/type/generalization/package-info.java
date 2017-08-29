/**
 * Usage: <b>泛型的基本用法</b>
 * 1. 不使用泛型的替代方法：包装类{@link data.type.generalization.Holder}
 * 2. 泛型类 {@link data.type.generalization.GenericHolder}
 *    协变返回类型，即子类返回比父类方法更窄的类型，since Java 5 {@link data.type.generalization.selfBounded.SelfBounding}
 * 3. 类型擦除
 *  3.1 what's type erase {@link data.type.generalization.ErasedTypeEquivalence}
 *  3.2 defects of type erase {@link data.type.generalization.EraseDefect}
 *  3.3 Compensation for type erase
 *      工厂方法 {@link data.type.generalization.GenericNewInstance} and
 *      模板方法 {@link data.type.generalization.GenericCreator}
 * 4. 协变返回类型，即子类返回比父类方法更窄的类型，since Java 5 {@link data.type.generalization.Covariant}
 *
 * {@reference https://segmentfault.com/a/1190000005179142#articleHeader8}
 * @author lucifer
 * @date 2016-8-27
 * @device Yoga Pro
 */
package data.type.generalization;