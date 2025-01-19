package a02a.e1;

import java.util.LinkedList;
import java.util.List;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        int[] index = {0};
        if (!list.isEmpty()) {
        return new RecursiveIterator<X>() {
            @Override
            public X getElement() {
                return list.get(index[0]);
            }

            @Override
            public RecursiveIterator<X> next() {
                index[0]++;
                if (index[0] >= list.size()) {
                    return null;
                } else {
                    return this;
                }
            }
        };} else {
            return null;
        }
        };

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        List<X> lRes = new LinkedList<>();
        int counter = 0;
        RecursiveIterator<X> iterator = input;
        while (counter < max && !(iterator == null)) {
            lRes.add(iterator.getElement());
            iterator = iterator.next();
            counter++;
        }
        return lRes;
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        return new RecursiveIterator<Pair<X,Y>>() {

            @Override
            public Pair<X, Y> getElement() {
                if (first != null && second != null) {
                    return new Pair<X,Y>(first.getElement(), second.getElement());
                } else {
                    return null;
                }
            }

            @Override
            public RecursiveIterator<Pair<X, Y>> next() {       
                if (first.next() != null && second.next() != null) {
                    return this;
                } else {
                    return null;
                }
            }
        };
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return new RecursiveIterator<Pair<X,Integer>>() {
            int index = 0;

            @Override
            public Pair<X, Integer> getElement() {
                if (iterator != null) {
                    return new Pair<X, Integer>(iterator.getElement(), index);
                } else {
                    return null;
                }
            }

            @Override
            public RecursiveIterator<Pair<X, Integer>> next() {
                if (iterator.next() != null) {
                    index++;
                    return this;
                } else {
                    return null;
                }
            }            
        };
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        return new RecursiveIterator<X>() {
            X element = first.getElement();
            boolean alternate = false;
            boolean onlySecond = false;
            boolean onlyFirst = false;

            @Override
            public X getElement() {
                return this.element;
            }

            @Override
            public RecursiveIterator<X> next() {
                if (onlyFirst) {
                    if (first.next() != null) {
                    this.element = first.getElement();
                    return this;
                    } else {
                        return null;
                    }
                } else if (onlySecond) {
                    if (second.next() != null) {
                    this.element = second.getElement();
                    return this;
                    } else {
                        return null;
                    }
                } 
                if (!alternate) {
                    if (first.next() != null) {
                        this.alternate = true;
                        this.element = second.getElement();
                    } else {
                        this.alternate = true;
                        this.element = second.getElement();
                        this.onlySecond = true;
                    }
                } else {
                    if (second.next() != null) {
                        this.alternate = false;
                        this.element = first.getElement();
                    } else {
                        this.onlyFirst = true;
                    }
                }
                return this;
            }
            
        };
    }

}
