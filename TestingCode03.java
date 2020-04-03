package modernjavainaction.chap03;

import java.applet.AppletContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public class TestingCode03 {
    static Map<String, Function<Integer, Apple>> map = new HashMap<>();
    static {
        map.put("apple",Apple::new);
        map.put("orange",Apple::new);
        map.put("kiwi",Apple::new);
    }

    public static void main(String... args) {

        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        ));

        //01
        BiFunction<Integer, Color, Apple> c1 = ((integer, color) -> new Apple(integer, color));
        c1.apply(100, Color.GREEN);

        //02
        List<Integer> appleWeight = Arrays.asList(7,3,5,10);
        List<Apple> appleList = mapApple(appleWeight, Apple::new);

        //03
        List<Integer> appleWeights = mapWeights(inventory, (apple) -> apple.getWeight());

        //04
        Apple kiwi = giveMeApple("kiwi", 50);

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        Function<Integer, Integer> k = f.compose(g);

        int resultH = h.apply(1);
        int resultK = k.apply(1);


        Function<String, String> fS = x -> x + " Hi ";
        Function<String , String> gS = x -> x + " World ";
        Function<String, String> hS = fS.andThen(gS);
        Function<String, String> kS = fS.compose(gS);

        String resultS = hS.apply("");
        String resultS1 = kS.apply("");

    }

    private static List<Integer> mapWeights(List<Apple> appleList, Function<Apple, Integer> f){
        List<Integer> weights = new ArrayList<>();

        for (Apple e : appleList){
            weights.add(f.apply(e));
        }
        return weights;
    }

    public static List<Apple> mapApple(List<Integer> list, Function<Integer, Apple> f){
        List<Apple> applesWeights = new ArrayList<>();
        for(Integer i : list){
            applesWeights.add(f.apply(i));
        }
        return applesWeights;
    }

    private static Apple giveMeApple(String fruit, Integer weight){
        return map.get(fruit).apply(weight);
    }
}
