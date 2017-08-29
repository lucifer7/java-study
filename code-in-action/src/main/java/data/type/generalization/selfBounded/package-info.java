/**
 * Usage: <b> 自限定的类型 </b>
 * 1. 古怪的循环泛型 {@link data.type.generalization.selfBounded.CRGWithBasicHolder}
 * CRG： 基类用导出类替代其参数，即所产生的类中将使用确切类型而非基类型
 * 2. 自限定
 * 未限定的BasicHolder may accept any class as its generic param {@link data.type.generalization.selfBounded.Unconstrained}
 * 自限定将强制泛型当作其自己的边界参数来使用，可以保证类型参数与正在被定义的类相同 {@link data.type.generalization.selfBounded.SelfBounding}
 *
 * @author lucifer
 * Date 2017-1-23
 * Device Aurora R5
 */
package data.type.generalization.selfBounded;