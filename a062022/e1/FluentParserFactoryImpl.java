package a06.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

public class FluentParserFactoryImpl implements FluentParserFactory {

    @Override
    public FluentParser<Integer> naturals() {
        return new FluentParser<Integer>() {
            boolean first = true;
            int next = 0;

            @Override
            public FluentParser<Integer> accept(Integer value) {
                if (first) {
                    if (value == 0) {
                        first = false;
                        next++;
                        return this;
                    } else {
                        throw new IllegalStateException();
                    }
                } else if (value == next) {
                    next++;
                    return this;
                } else {
                    throw new IllegalStateException();
                }
            }
            
        };
    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        return new FluentParser<List<Integer>>() {
            List<Integer> l = new LinkedList<>();
            int current = -1;

            @Override
            public FluentParser<List<Integer>> accept(List<Integer> value) {
                if (value.equals(l)) {
                    current++;
                    l.add(current);
                    return this;
                } else {
                    throw new IllegalStateException();
                }
            }
            
        };
    }

    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        return new FluentParser<Integer>() {
            int current = 0;

            @Override
            public FluentParser<Integer> accept(Integer value) {

                if (value == 0) {
                    current = 1;
                    return this;
                } else {
                
                    if (value == current) {
                        current++;
                        return this;
                    } else {
                        throw new IllegalStateException();
                    }
                }
            }
            
        };
    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(String s) {
        return new FluentParser<String>() {
            String last = s;
            int lastCounter = 0;
            int counter = 0;
            boolean first = true;

            @Override
            public FluentParser<String> accept(String value) {

                if (value.equals(s) && first) {
                    first = false;
                    counter++;
                    return this;
                } else if (value.equals(s) && counter > lastCounter) {
                    lastCounter = counter;
                    counter = 1;
                    last = s;
                    return this;
                } else if (value.equals(last+s)) {
                    last = last+s;
                    counter++;
                    return this;
                } else {
                    throw new IllegalStateException();
                }
            }
            
        };
    }

    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        return new FluentParser<Pair<Integer,List<String>>>() {
            int lastInt = i0;
            List<String> lastString = new LinkedList<>(); 

            @Override
            public FluentParser<Pair<Integer, List<String>>> accept(Pair<Integer, List<String>> value) {

                if (value.getX() == lastInt && value.getY().equals(lastString)) {
                    lastInt = op.apply(lastInt);
                    lastString.clear();
                    for (int i = 0; i < lastInt; i++) {
                        lastString.add(s);
                    }
                    return this;
                } else {
                    throw new IllegalStateException();
                }
            }
            
        };
    }

}
