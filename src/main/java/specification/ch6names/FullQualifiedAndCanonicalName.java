package specification.ch6names;

/**
 * Usage: <b> the difference between fully qualified name and canonical name </b>
 *
 * @see jvm.ClassNames
 * @author Jingyi.Yang
 *         Date 2017/4/27
 **/
public class FullQualifiedAndCanonicalName {
}

/**
 * both specification.ch6names.01.I and specification.ch6names.02.I are fully qualified names denote member class I,
 * but only the first is its canonical name
 */
class O1 { class I {} }
class O2 extends O1 {}
