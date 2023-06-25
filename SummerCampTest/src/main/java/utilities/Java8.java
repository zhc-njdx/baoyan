package utilities;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 学习Stream API的使用
 * 1. Stream 的创建
 * 2. Stream 的操作
 *    2.1 分为中间操作和终止操作
 *    2.2 Iterating
 *    2.3 Filtering
 *    2.4 Mapping
 *    2.5 Matching
 *    2.6 Reduction
 *    2.7 Collecting
 */
public class Java8 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("zhhc");
        list.add("xzt");
        list.add("wwwhh");
        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);
    }

    public static <T> Stream<T> streamOf(List<T> list){
        return list == null || list.isEmpty() ? Stream.empty() : list.stream();
    }

    public List<Integer> getAllEvens(List<Integer> list) {
        return list.stream().filter(i -> i%2==0).collect(Collectors.toList());
    }

    public String concat(List<String> list) {
        return list.stream().reduce("", (a, b) -> a + b);
    }

    static class Student {
        String name;
        Integer grade;
    }

    public Student getHighest(List<Student> list) {
        return list.stream().max(Comparator.comparingInt(a -> a.grade)).orElse(new Student());
    }

    public double getAverage(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    /**
     * Stream的创建方式
     * @param values T类型的值列表
     * @return 一个Stream
     * @param <T> 泛形参数
     */
    public static <T> Stream<T> createStream(T[] values) {
        // 1. empty stream
        Stream<T> emptyStream = Stream.empty();
        // 2. stream of collection
        Collection<T> collection = Arrays.asList(values);
        Stream<T> streamOfCollection = collection.stream();
        // 3. stream of array
        Stream<T> streamOfArray = Stream.of(values);
        Stream<T> streamOfArrayFull = Arrays.stream(values);
        Stream<T> streamOfArrayPart = Arrays.stream(values, 1, 2);
        // 4. Stream.builder()
        Stream<T> streamBuilder = Stream.<T>builder().add(values[0]).add(values[1]).build();
        // 5. Stream.generate()
        Stream<String> streamGenerated = Stream.generate(() -> "zhhc").limit(10);
        // 6. Stream.iterate()
        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
        // 7. stream of f primitives
        IntStream intStream = IntStream.range(1, 3);
        IntStream intStreamClosed = IntStream.rangeClosed(1, 3);
        // 8. stream of string
        IntStream streamOfChars = "abc".chars();
        Stream<String> streamOfString = Pattern.compile(", ").splitAsStream("a, b, c");
        // 9. stream of file
        Path path = Paths.get("result.txt");
        try (Stream<String> streamOfLines = Files.lines(path);
                Stream<String> streamWithCharset = Files.lines(path, StandardCharsets.UTF_8);) {
            long count = streamOfLines.count();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return emptyStream;
    }
}
