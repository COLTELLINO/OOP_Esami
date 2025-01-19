package a06.e1;

import java.util.List;
import java.util.stream.Stream;

public class ListBuilderFactoryImpl implements ListBuilderFactory {

    private class ListBuilderImpl<T> implements ListBuilder<T> {

        private final List<T> list;

        public ListBuilderImpl(Stream<T> Stream) {
            this.list = Stream.toList();
        }

        @Override
        public ListBuilder<T> add(List<T> list) {
            return new ListBuilderImpl<>(Stream.concat(this.list.stream(), list.stream()));
            }

        @Override
        public ListBuilder<T> concat(ListBuilder<T> lb) {
            return add(lb.build());
        }

        @Override
        public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
            return new ListBuilderImpl<>(this.list.stream()
            .flatMap(e -> e.equals(t) ? lb.build().stream() : Stream.of(e)));
        }

        @Override
        public ListBuilder<T> reverse() {
            return new ListBuilderImpl<>(this.list.reversed().stream());
        }

        @Override
        public List<T> build() {
            return this.list;
        };
    }

    @Override
    public <T> ListBuilder<T> empty() {
        return new ListBuilderImpl<>(Stream.of());
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
        return new ListBuilderImpl<>(Stream.of(t));
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        return new ListBuilderImpl<>(list.stream());
    }

    @Override
    public <T> ListBuilder<T> join(T start, T stop, List<ListBuilder<T>> list) {
        return this.fromElement(start).add(list.stream().flatMap(lb -> lb.build().stream()).toList()).add(List.of(stop));
    }
    
}
