package a06.e1;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class ZipCombinerFactoryImpl implements ZipCombinerFactory {

    @Override
    public <X, Y> ZipCombiner<X, Y, Pair<X, Y>> classical() {
        return new ZipCombiner<X,Y,Pair<X,Y>>() {

            @Override
            public List<Pair<X, Y>> zipCombine(List<X> l1, List<Y> l2) {
                List<Pair<X, Y>> lRes = new LinkedList<>();
                for (int i = 0; i < l1.size(); i++) {
                    lRes.add(new Pair<X,Y>(l1.get(i), l2.get(i)));
                }
                return lRes;
            }
            
        };
    }

    @Override
    public <X, Y, Z> ZipCombiner<X, Y, Z> mapFilter(Predicate<X> predicate, Function<Y, Z> mapper) {
        return new ZipCombiner<X,Y,Z>() {

            @Override
            public List<Z> zipCombine(List<X> l1, List<Y> l2) {
                List<Z> lRes = new LinkedList<>();
                for (int i = 0; i < l1.size(); i++) {
                    if (predicate.test(l1.get(i))) {
                    lRes.add(mapper.apply(l2.get(i)));
                    }
                }
                return lRes;
            }
            
        };
    }

    @Override
    public <Y> ZipCombiner<Integer, Y, List<Y>> taker() {
        return new ZipCombiner<Integer,Y,List<Y>>() {

            @Override
            public List<List<Y>> zipCombine(List<Integer> l1, List<Y> l2) {
                List<List<Y>> lRes = new LinkedList<>();
                int counter = 0;
                int internCounter = 0;
                for (int i = 0; i < l1.size(); i++) {
                    List<Y> tmp = new LinkedList<>();
                    for (int j = counter; j < l1.get(i) + counter; j++) {
                        tmp.add(l2.get(j));
                        internCounter++;
                    }
                    counter = internCounter;
                    lRes.add(tmp);
                }
                return lRes;
            }
        };
    }

    @Override
    public <X> ZipCombiner<X, Integer, Pair<X, Integer>> countUntilZero() {
        return new ZipCombiner<X,Integer,Pair<X,Integer>>() {

            @Override
            public List<Pair<X, Integer>> zipCombine(List<X> l1, List<Integer> l2) {

                List<Pair<X, Integer>> lRes = new LinkedList<>();
                int counter = 0;

                for (int i = 0; i < l1.size(); i++) {
                    int j = 0;
                    while (l2.get(j + counter) != 0) {
                        j++;
                    }
                    counter += j + 1;
                    lRes.add(new Pair<X,Integer>(l1.get(i), j));
                }
                return lRes;
            }
             
        };
    }

}
