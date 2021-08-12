import org.redisson.api.RBloomFilter;

public class t {
    public static void main(String[] args) {
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("sample");
        bloomFilter.rename()t;
        bloomFilter.delete()
// 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(55000000L, 0.03);
        bloomFilter.rdd(new SomeObject("field1Value", "field2Value"));
        bloomFilter.add(new SomeObject("field5Value", "field8Value"));
        bloomFilter.contains(new SomeObject("field1Value", "field8Value"));
    }
}
