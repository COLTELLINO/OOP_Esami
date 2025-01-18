package a06.e1;

public class BankAccountFactoryImpl implements BankAccountFactory {

    @Override
    public BankAccount simple() {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                return true;
            }

            @Override
            protected void onDisallowedDeposit() {
                
            }

            @Override
            protected void onDisallowedWithdraw() {
                
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return this.balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                if (amount >= this.balance()) {
                    return false;
                } else {
                    return true;
                }
            }
            
        };
    }

    @Override
    public BankAccount withFee(int fee) {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                return true;
            }

            @Override
            protected void onDisallowedDeposit() {
                
            }

            @Override
            protected void onDisallowedWithdraw() {
          
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return this.balance() - amount - fee;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                if (amount + fee >= this.balance()) {
                    return false;
                } else {
                    return true;
                }
            }
            
        };
    }

    @Override
    public BankAccount checked() {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                if (amount > 0) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            protected void onDisallowedDeposit() {
                throw new IllegalStateException();
            }

            @Override
            protected void onDisallowedWithdraw() {
                throw new IllegalStateException();               
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return this.balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                if (amount <= 0 || amount >= this.balance()) {
                    return false;
                } else {
                    return true;
                }
            }
            
        };
    }

    @Override
    public BankAccount gettingBlocked() {
        return new AbstractBankAccount() {

            private boolean blocked = false;

            @Override
            protected boolean canDeposit(int amount) {
                if (amount > 0 && !blocked) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            protected void onDisallowedDeposit() {
                blocked = true;
            }

            @Override
            protected void onDisallowedWithdraw() {
                blocked = true;          
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return this.balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                if (amount <= 0 || amount >= this.balance() || blocked) {
                    return false;
                } else {
                    return true;
                }
            }
            
        };
    }

    @Override
    public BankAccount pool(BankAccount primary, BankAccount secondary) {

        return new BankAccount() {

            @Override
            public final int balance() {
                return primary.balance() + secondary.balance();
            }
        
            @Override
            public void deposit(int amount) {
                if (primary.balance() <= secondary.balance()) {
                    primary.deposit(amount);
                } else {
                    secondary.deposit(amount);
                }
            }
        
            @Override
            public boolean withdraw(int amount) {
                if (primary.balance() >= amount) {
                    primary.withdraw(amount);
                    return true;
                } else if (secondary.balance() >= amount){
                    secondary.withdraw(amount);
                    return true;
                } else {
                    return false;
                }
            }

        };
    }

}
