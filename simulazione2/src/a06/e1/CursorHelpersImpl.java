package a06.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class CursorHelpersImpl implements CursorHelpers {

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return new Cursor<X>() {

            int index = 0;

            @Override
            public X getElement() {
                return list.get(index);
            }

            @Override
            public boolean advance() {
                if (list.size() > ++index) {
                    return true;
                } else {
                    index--;
                    return false;
                }
            }
            
        };
    }

    @Override
    public Cursor<Integer> naturals() {
        return new Cursor<Integer>() {

            int num = 0;

            @Override
            public Integer getElement() {
                return num;
            }

            @Override
            public boolean advance() {
                num++;
                return true;
            }
            
        };
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return new Cursor<X>() {

            int counter = 0;

            @Override
            public X getElement() {
                return input.getElement();
            }

            @Override
            public boolean advance() {
                counter++;
                if (counter < max && input.advance()) {
                    return true;
                } else {
                    return false;
                }
            }
            
        };
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        consumer.accept(input.getElement());
        while(input.advance()) {
            consumer.accept(input.getElement());
        }
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        List<X> l = new LinkedList<>();
        l.add(input.getElement());
        int counter = 1;
        while (input.advance() && counter < max) {
            l.add(input.getElement());
            counter++;
        }
        return l;
    }

}
