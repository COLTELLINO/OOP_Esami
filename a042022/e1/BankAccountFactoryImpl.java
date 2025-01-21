package a04.e1;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class BankAccountFactoryImpl implements BankAccountFactory {

    @Override
    public BankAccount createBasic() {
        return new BankAccount() {

            private int balance = 0;

            @Override
            public int getBalance() {
                return this.balance;
            }

            @Override
            public void deposit(int amount) {
                this.balance += amount;
            }

            @Override
            public boolean withdraw(int amount) {
                if (amount <= this.balance) {
                    this.balance -= amount;
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    public BankAccount createWithFee(UnaryOperator<Integer> feeFunction) {
        return new BankAccount() {

            private int balance = 0;

            @Override
            public int getBalance() {
                return this.balance;
            }

            @Override
            public void deposit(int amount) {
                this.balance += amount;
            }

            @Override
            public boolean withdraw(int amount) {
                if (amount + feeFunction.apply(amount) <= this.balance) {
                    this.balance -= (amount + feeFunction.apply(amount));
                    return true;
                } else {
                    return false;
                }
            }
            
        };
    }

    @Override
    public BankAccount createWithCredit(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction) {
        return new BankAccount() {

            private int balance = 0;

            @Override
            public int getBalance() {
                return this.balance;
            }

            @Override
            public void deposit(int amount) {
                this.balance += amount;
            }

            @Override
            public boolean withdraw(int amount) {
                if (this.balance >= amount) {
                    this.balance -= amount;
                    return true;
                } else if (allowedCredit.test(Math.abs(this.balance - amount))) {
                    this.balance -= amount + rateFunction.apply(Math.abs(this.balance - amount));
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    public BankAccount createWithBlock(BiPredicate<Integer, Integer> blockingPolicy) {
        return new BankAccount() {

            private int balance = 0;
            private boolean blocked = false;

            @Override
            public int getBalance() {
                return this.balance;
            }

            @Override
            public void deposit(int amount) {
                if (!blocked) {
                    this.balance += amount;
                }
            }

            @Override
            public boolean withdraw(int amount) {
                if (!blocked) {
                    if (blockingPolicy.test(amount, balance)) {
                        this.blocked = true;
                        return false;
                    } else {
                        this.balance -= amount;
                        return true;
                    }
                } else {
                    return false;
                }
            }
            
        };
    }

    @Override
    public BankAccount createWithFeeAndCredit(UnaryOperator<Integer> feeFunction, Predicate<Integer> allowedCredit,
            UnaryOperator<Integer> rateFunction) {
        return new BankAccount() {
            private int balance = 0;

            @Override
            public int getBalance() {
                return this.balance;
            }

            @Override
            public void deposit(int amount) {
                this.balance += amount;
            }

            @Override
            public boolean withdraw(int amount) {
                if (this.balance >=  amount + feeFunction.apply(amount)) {
                    this.balance -= (amount + feeFunction.apply(amount));
                    return true;
                } else if (allowedCredit.test(Math.abs(this.balance - (amount + feeFunction.apply(amount))))) {
                    this.balance -= (amount + feeFunction.apply(amount)) + rateFunction.apply(Math.abs(this.balance - (amount + feeFunction.apply(amount))));
                    return true;
                } else {
                    return false;
                }
            }    
        };
    }

}
