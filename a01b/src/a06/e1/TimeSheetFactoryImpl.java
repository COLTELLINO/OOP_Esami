package a06.e1;

import java.security.KeyStore.Entry;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    private static record TimeSheetRecord(List<String> activities, List<String> days, BiFunction<String, String, Integer> data) implements TimeSheet {

        public int getSingleData(String activity, String day){
            if (activities.contains(activity) && days.contains(day)) {
            return data.apply(activity, day);
            } else {
                return 0;
            }
        }
        
        public Map<String, Integer> sumsPerActivity(){
            Map<String, Integer> map = new HashMap<>();
            for (String activity : activities) {
                map.put(activity, 0);
                for (String day : days) {
                    map.replace(activity, map.get(activity) + data.apply(activity, day));
                }
            }
            return map;
        }
            
        public Map<String, Integer> sumsPerDay(){
            Map<String, Integer> map = new HashMap<>();
            for (String day : days) {
                map.put(day, 0);
                for (String activity : activities) {
                    map.replace(day, map.get(day) + data.apply(activity, day));
                }
            }
            return map;
        }
    }

    
    public List<String> createActivities(int numActivities) {
        return Stream.iterate(1, i -> i + 1).map(i -> "act"+i).limit(numActivities).collect(Collectors.toList());
    }
    
    public List<String> createDays(int numDays) {
        return Stream.iterate(1, i -> i + 1).map(i -> "day"+i).limit(numDays).collect(Collectors.toList());
    }
        
    @Override
    public TimeSheet flat(int numActivities, int numDays, int hours) {
        List<String> activities = createActivities(numActivities);
        List<String> days = createDays(numDays);
        return new TimeSheetRecord(activities, days, (a,d) -> hours);
    }   

    @Override
    public TimeSheet ofListsOfLists(List<String> activities, List<String> days, List<List<Integer>> data) {
        return new TimeSheetRecord(activities, days, (a,d) -> data.get(activities.indexOf(a)).get(days.indexOf(d)));
    }

    @Override
    public TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer, Integer>> data) {
        List<String> activities = createActivities(numActivities);
        List<String> days = createDays(numDays);
        return new TimeSheetRecord(activities, days, (a,d) -> (int)data.stream().
                                    filter(p -> p.get1() == activities.indexOf(a) &&
                                    p.get2() == days.indexOf(d)).count());
    }

    @Override
    public TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String, String>, Integer> data) {
        return new TimeSheetRecord(activities, days, (a,d) -> data.getOrDefault(new Pair<>(a, d), 0));
    }
}