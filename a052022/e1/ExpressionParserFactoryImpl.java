package a05.e1;

public class ExpressionParserFactoryImpl implements ExpressionParserFactory {

    @Override
    public ExpressionParser oneSum() {
        return new ExpressionParser() {
            private int stage = 0;

            @Override
            public void acceptNumber(int n) {
                if (stage != 0 && stage != 2) {
                    throw new IllegalStateException();
                } else if (stage == 0) {
                    stage++;
                } else if (stage == 2) {
                    stage++;
                }
            }

            @Override
            public void acceptSum() {
                if (stage != 1) {
                    throw new IllegalStateException();
                } else {
                    stage++;
                }
            }

            @Override
            public void acceptOpenParen() {
                throw new IllegalStateException();
            }

            @Override
            public void acceptCloseParen() {
                throw new IllegalStateException();
            }

            @Override
            public void close() {
                if (stage != 3) {
                    throw new IllegalStateException();
                }
            }
            
        };
    }

    @Override
    public ExpressionParser zeroOrManySums() {
        return new ExpressionParser() {
            private int stage = 0;                                

            @Override
            public void acceptNumber(int n) {
                if (stage%4 != 0 && stage%4 != 2 && stage%2 != 0) {
                    throw new IllegalStateException();
                } else if (stage%4 == 0) {
                    stage++;
                } else if (stage%4 == 2) {
                    stage++;
                }
            }

            @Override
            public void acceptSum() {
                if (stage%4 != 1 && stage%2 != 1) {
                    throw new IllegalStateException();
                } else {
                    stage++;
                }
            }

            @Override
            public void acceptOpenParen() {
                throw new IllegalStateException();
            }

            @Override
            public void acceptCloseParen() {
                throw new IllegalStateException();
            }

            @Override
            public void close() {
                if (stage%4 != 3 && stage%4 != 1) {
                    throw new IllegalStateException();
                }
            }
        };
    }

    @Override
    public ExpressionParser oneLevelParens() {
        return new ExpressionParser() {

            private boolean inParens = false;
            private int stage = 0;


            @Override
            public void acceptNumber(int n) {
                if (stage%2 != 0) {
                    throw new IllegalStateException();
                } else {
                    stage++;
                }
            }

            @Override
            public void acceptSum() {
                if (stage%2 != 1) {
                    throw new IllegalStateException();
                } else {
                    stage++;
                }
            }

            @Override
            public void acceptOpenParen() {
                if (!inParens && stage%2 == 0) {
                    inParens = true;
                } else {
                    throw new IllegalStateException();
                }
            }

            @Override
            public void acceptCloseParen() {
                if (inParens && stage%2 == 1) {
                    inParens = false;
                } else {
                    throw new IllegalStateException();
                }
            }

            @Override
            public void close() {
                if (inParens || stage%4 != 3 && stage%4 != 1) {
                    throw new IllegalStateException();
                }
            }
            
        };
    }

    @Override
    public ExpressionParser manySumsWithBoxingParens() {
        return new ExpressionParser() {

            private int inParens = 0;
            private int stage = 0;
            private boolean nAfterParens = false;


            @Override
            public void acceptNumber(int n) {
                if (stage%2 != 0 || nAfterParens) {
                    throw new IllegalStateException();
                } else {
                    nAfterParens = true;
                    stage++;
                }
            }

            @Override
            public void acceptSum() {
                if (stage%2 != 1 || inParens > 0) {
                    throw new IllegalStateException();
                } else {
                    stage++;
                }
            }

            @Override
            public void acceptOpenParen() {
                if (stage%2 == 0) {
                    inParens++;
                } else {
                    throw new IllegalStateException();
                }
            }

            @Override
            public void acceptCloseParen() {
                if (inParens > 0 && stage%2 == 1) {
                    inParens--;
                    nAfterParens = false;
                } else {
                    throw new IllegalStateException();
                }
            }

            @Override
            public void close() {
                if (inParens > 0 || stage%4 != 3 && stage%4 != 1) {
                    throw new IllegalStateException();
                }
            }
            
        };
    }

}
