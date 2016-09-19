/**
 * Usage: <b>数组的协变与通配符的使用</b>
 * 协变： {@link data.type.generalization.wildcards.GenericAndCovariance}
 * 协变数组： {@link data.type.generalization.wildcards.CovariantArrays}
 * 通配符，上界与下界    {@link data.type.generalization.wildcards.Holder}
 * 另有无边界通配符， List<?> list， 貌似并卵用，啥都加不了
 *
 * Put and Get principle: EJ Item 1
 * Upper wildcards: List<? extends Base>    A data structure produces data, like this <b>list</b>
 * Lower wildcards: List<? super Sub>       A data structure consumes data, like this <b>list</b>
 *
 * Mind:
 * Producer and consumer is variable(data structure) level, not method or class
 *
 * @url https://segmentfault.com/a/1190000005337789
 * @author lucifer
 * @date 2016-8-27
 * @device Yoga Pro
 */
package data.type.generalization.wildcards;