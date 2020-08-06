package basecommon.serializer;

/**
 * @author 张富华
 * @date 2020/8/6 17:28
 */
public interface PbConvertor<T, S> {

    public S convert2Proto(T t);

    public T convert2Model(S s);
}