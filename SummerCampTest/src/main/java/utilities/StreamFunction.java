package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamFunction {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("zhhc");
        list.add("xzt");
        list.add("wwwhh");
        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);
    }
}
