package a01a.e1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return new SubsequenceCombiner<Integer,Integer>() {

            @Override
            public List<Integer> combine(List<Integer> list) {
                List<Integer> lRes = new LinkedList<>();
                int counter = 0;
                while (counter < list.size()) {
                    int sum = 0;
                    for (int i = 0; i < 3; i++) {
                        if (counter + i < list.size()) {
                        sum += list.get(counter + i);
                        }
                    }
                    counter += 3;
                    lRes.add(sum);
                }
                return lRes;
            }
            
        };
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return new SubsequenceCombiner<X,List<X>>() {

            @Override
            public List<List<X>> combine(List<X> list) {
                List<List<X>> lRes = new LinkedList<>();
                int counter = 0;
                while (counter < list.size()) {
                    List<X> tmp = new LinkedList<>();
                    for (int i = 0; i < 3; i++) {
                        if (counter + i < list.size()) {
                            tmp.add(list.get(counter + i));
                        }
                    }
                    counter += 3;
                    lRes.add(tmp);
                }
                return lRes;
            }
            
        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return new SubsequenceCombiner<Integer,Integer>() {

            @Override
            public List<Integer> combine(List<Integer> list) {
                List<Integer> lRes = new LinkedList<>();
                int counter = 0;
                while (counter < list.size()) {
                    int sum = 0;
                    int innerCounter = 0;
                    while (counter + innerCounter < list.size() && list.get(counter + innerCounter) != 0) {
                        sum ++;
                        innerCounter++;
                    }
                    counter += innerCounter + 1;
                    lRes.add(sum);
                }
                return lRes;
            }
        };
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return new SubsequenceCombiner<X,Y>() {

            @Override
            public List<Y> combine(List<X> list) {
                return list.stream().map(e -> function.apply(e)).toList();
            }
            
        };
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return new SubsequenceCombiner<Integer,List<Integer>>() {

            @Override
            public List<List<Integer>> combine(List<Integer> list) {
                List<List<Integer>> lRes = new LinkedList<>();
                int counter = 0;
                while (counter < list.size()) {
                    int sum = 0;
                    List<Integer> tmp = new LinkedList<>();
                    while (counter < list.size() && sum < threshold) {
                        tmp.add(list.get(counter));
                        sum += list.get(counter);
                        counter++;
                    }
                    lRes.add(tmp);
                }
                return lRes;
            }
            
        };
    }

}
