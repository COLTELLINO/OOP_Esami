package a03a.e1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class ParserFactoryImpl implements ParserFactory {

    @Override
    public <X> Parser<X> fromFinitePossibilities(Set<List<X>> acceptedSequences) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                List<X> tmp = new LinkedList<>();
                while (iterator.hasNext()) {
                    tmp.add(iterator.next());
                }
                if (acceptedSequences.contains(tmp)) {
                    return true;
                } else {
                    return false;
                }
            }
            
        };
    }

    @Override
    public <X> Parser<X> fromGraph(X x0, Set<Pair<X, X>> transitions, Set<X> acceptanceInputs) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                X lastElem = x0;
                while (iterator.hasNext()) {
                    X currentElem = iterator.next();
                    if (transitions.contains(new Pair<X,X>(lastElem, currentElem))) {
                        lastElem = currentElem;
                    } else {
                        return false;
                    }
                }
                if (acceptanceInputs.contains(lastElem)) {
                    return true;
                }
            return false;
            }
        };
    }

    @Override
    public <X> Parser<X> fromIteration(X x0, Function<X, Optional<X>> next) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                X lastElem = x0;
                if (iterator.next().equals(x0)) {
                    while (iterator.hasNext()) {
                        X currentElem = iterator.next();
                        if (next.apply(lastElem).isPresent() && currentElem.equals(next.apply(lastElem).get())) {
                            lastElem = currentElem;
                        } else {
                            return false;
                        }
                    }
                    if (next.apply(lastElem).equals(Optional.empty())) {
                        return true;
                    }
                } else {
                    return false;
                }
                return false;
            }
        };
    }

    @Override
    public <X> Parser<X> recursive(Function<X, Optional<Parser<X>>> nextParser, boolean isFinal) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                if (iterator.hasNext()) {
                    Optional<Parser<X>> OptionalParser = nextParser.apply(iterator.next());
                if (!OptionalParser.equals(Optional.empty())) {
                    Parser<X> parser = OptionalParser.get();
                    return parser.accept(iterator); 
                } else {
                    return false;
                }
            } else {
                return isFinal;
            }
            }
        };
    }

    @Override
    public <X> Parser<X> fromParserWithInitial(X x, Parser<X> parser) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                if (iterator.hasNext()) {
                X lastElem = iterator.next();
                if (lastElem.equals(x)) {
                    return parser.accept(iterator);
                } else {
                    return false;
                }
            } else {
                return false;
            }
            }
            
        };
    }

}
