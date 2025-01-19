package a06.e1;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimetableFactoryImpl implements TimetableFactory {

    private static <T> Set<T> addToSet(Set<T> s, T t){
        return concatSet(s, Set.of(t));
    }

    private static <T> Set<T> concatSet(Set<T> s, Set<T> s2){
        return Stream.concat(s.stream(), s2.stream()).collect(Collectors.toSet());
    }

    private static record TimetableTemplate(Set<String> activities, Set<String> days, BiFunction<String, String, Integer> data) implements Timetable {

        @Override
        public int getSingleData(String activity, String day) {
            return data.apply(activity, day);
        }

        @Override
        public Timetable addHour(String activity, String day) {
            return new TimetableTemplate(
                addToSet(activities, activity), 
                addToSet(days, day), 
                (a,d) -> data.apply(a,d) + (activity.equals(a) && day.equals(d) ? 1 : 0)
            );
        }

        private int statistics(BiPredicate<String, String> predicate) {
            return activities.stream()
                    .flatMap(a -> days.stream()
                            .filter(d -> predicate.test(a,d))
                            .map(d -> this.getSingleData(a, d)))
                    .collect(Collectors.summingInt(i -> i));
        }

        @Override
        public int sums(Set<String> activies, Set<String> days) {
            return statistics((a,d) -> activies.contains(a) && days.contains(d));
        }
    }

    @Override
    public Timetable empty() {
        return new TimetableTemplate(Set.of(), Set.of(), (a,d) -> 0);
    }

    @Override
    public Timetable single(String activity, String day) {
        return empty().addHour(activity, day);   
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        Set<String> ac = concatSet(table1.activities(), table2.activities());
        Set<String> da = concatSet(table1.days(), table2.days());
        BiFunction<String, String, Integer> f = (a, d) -> table1.getSingleData(a, d) + table2.getSingleData(a, d);
        return new TimetableTemplate(ac, da, f);
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        return new TimetableTemplate(table.activities(), table.days(), (a, d) -> Math.min(table.getSingleData(a, d), bounds.apply(a, d)));
    }

}
