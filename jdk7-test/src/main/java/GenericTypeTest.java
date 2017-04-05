import java.util.ArrayList;
import java.util.List;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/5
 **/
public class GenericTypeTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("A");
        // 由于addAll期望获得Collection<? extends String>类型的参数，因此下面的语句在Java 7中无法通过
        // 不过Java 8已经能对此做自动类型推断
        // list.addAll(new ArrayList<>());
        /* Error:(14, 13) java: 对于addAll(java.util.ArrayList<java.lang.Object>), 找不到合适的方法
        方法 java.util.List.addAll(int,java.util.Collection<? extends java.lang.String>)不适用
                (实际参数列表和形式参数列表长度不同)
        方法 java.util.List.addAll(java.util.Collection<? extends java.lang.String>)不适用
                (无法通过方法调用转换将实际参数java.util.ArrayList<java.lang.Object>转换为java.util.Collection<? extends java.lang.String>)
        方法 java.util.Collection.addAll(java.util.Collection<? extends java.lang.String>)不适用
                (无法通过方法调用转换将实际参数java.util.ArrayList<java.lang.Object>转换为java.util.Collection<? extends java.lang.String>) */
    }
}
