package a06.e1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class WindowingFactoryImpl implements WindowingFactory {

    @Override
    public <X> Windowing<X, X> trivial() {
        return new Windowing<X,X>() {

            @Override
            public Optional<X> process(X x) {
                return Optional.of(x);
            }
            
        };
    }

    @Override
    public <X> Windowing<X, Pair<X, X>> pairing() {
        final boolean[] second = {false};
        List<X> l = new ArrayList<>();
        return new Windowing<X,Pair<X,X>>() {
            @Override
            public Optional<Pair<X, X>> process(X x) {
                l.add(x);
                if (second[0]) {
                    Optional<Pair<X, X>> res = Optional.of(new Pair<X, X>(l.get(0), x));
                    l.set(0, x);
                    return res;
                } else {
                    second[0] = true;
                    l.set(0, x);
                    return Optional.empty();
                }
            }
            
        };
    }

    @Override
    public Windowing<Integer, Integer> sumLastFour() {
        return new Windowing<Integer,Integer>() {
            final int[] counter = {0};
            final int[] numbers = new int[1000];

            @Override
            public Optional<Integer> process(Integer x) {
                if (counter[0] < 3) {
                    numbers[counter[0]] = x;
                    counter[0]++;
                    return Optional.empty();
                } else {
                    numbers[counter[0]] = x;
                    Optional<Integer> res = Optional.of(numbers[counter[0]] + numbers[counter[0] - 1] + numbers[counter[0] - 2] + numbers[counter[0] - 3]);
                    counter[0]++;
                    return res;
                }
            }
            
        };
    }

    @Override
    public <X> Windowing<X, List<X>> lastN(int n) {
        return new Windowing<X,List<X>>() {
            final int[] counter = {0};
            List<X> l = new LinkedList<>();

            @Override
            public Optional<List<X>> process(X x) {
                if (counter[0] < (n-1)) {
                    l.add(x);
                    counter[0]++;
                    return Optional.empty();
                } else {
                    l.add(x);
                    Optional<List<X>> res = Optional.of(List.of(l.get(counter[0]-3), l.get(counter[0]-2), l.get(counter[0]-1), l.get(counter[0])));
                    counter[0]++;
                    return res;
                }
            }
            
        };
    }

    @Override
    public Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n) {
        return new Windowing<Integer,List<Integer>>() {
            final int[] sum = {0};
            final int[] sum2 = {0};
            List<Integer> res = new LinkedList<>();
            List<Integer> res2 = new LinkedList<>();

            @Override
            public Optional<List<Integer>> process(Integer x) {
                sum[0] += x;
                res.add(x);
                if (sum[0] < n) {
                    return Optional.empty();
                } else {
                    sum2[0] = 0;
                    res2.clear();
                    for (Integer integer : res.reversed()) {
                        sum2[0] += integer;
                        res2.add(integer);
                        if (sum2[0] >= n) {
                            return Optional.of(res2.reversed());
                        }
                    }
                    return Optional.empty();   
                }
            }
            
        };
    }

}
