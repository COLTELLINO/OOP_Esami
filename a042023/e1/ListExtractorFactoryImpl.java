package a06.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListExtractorFactoryImpl implements ListExtractorFactory {

    @Override
    public <X> ListExtractor<X, Optional<X>> head() {
        return new ListExtractor<X,Optional<X>>() {

            @Override
            public Optional<X> extract(List<X> list) {
                if (list.isEmpty()) {
                    return Optional.empty();
                } else {
                    return Optional.of(list.get(0));
                }
            }
            
        };
    }

    @Override
    public <X, Y> ListExtractor<X, List<Y>> collectUntil(Function<X, Y> mapper, Predicate<X> stopCondition) {
        return new ListExtractor<X,List<Y>>() {

            @Override
            public List<Y> extract(List<X> list) {
                int index = 0;
                if (list.isEmpty()) {
                    return List.of();
                }
                List<Y> res = new LinkedList<>();
                while (!stopCondition.test(list.get(index))) {
                    res.add(mapper.apply(list.get(index)));
                    index++;
                }
                return res;
            }
            
        };
    }

    @Override
    public <X> ListExtractor<X, List<List<X>>> scanFrom(Predicate<X> startCondition) {
        return new ListExtractor<X,List<List<X>>>() {

            @Override
            public List<List<X>> extract(List<X> list) {

                int index = 0;
                List<List<X>> res = new LinkedList<>();

                if (list.isEmpty()) {
                    return List.of();
                }

                while (!startCondition.test(list.get(index))) {
                    index++;
                    if (index == list.size()) {
                        return List.of();
                    }
                }

                int counter = 0;
                int end = list.size()-index-1;

                while (counter <= end) { 

                    List<X> tmp = new LinkedList<>();
                    int j = 0;

                    while (j <= counter) { 
                        tmp.add(list.get(index + j));
                        j++;
                    }

                    counter++;
                    res.add(tmp);   
                }
                return res;
            }
            
        };
    }

    @Override
    public <X> ListExtractor<X, Integer> countConsecutive(X x) {
        return new ListExtractor<X,Integer>() {

            @Override
            public Integer extract(List<X> list) {
                int index = 0;
                int counter = 0;
                while (index < list.size() && list.get(index) != x) {
                    index++;
                }
                while (index < list.size() && list.get(index) == x ) {
                    counter++;
                    index++;
                }
                return counter;
            }
            
        };
    }
    
}
