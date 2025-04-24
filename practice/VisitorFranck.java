import static java.lang.Math.min;

interface MoneyCounter {
    void deposit(float amount);

    float withdraw(float amount);

    void sendTo(float amount, MoneyCounter other);

    void accept(MoneyCounterVisitor visitor);
}

class BankAccount implements MoneyCounter {

    float total;

    @Override
    public void deposit(float amount) {
        total = total + amount;
    }

    @Override
    public float withdraw(float amount) {
        return min(0, amount);
    }

    @Override
    public void sendTo(float amount, MoneyCounter other) {
        other.deposit(withdraw(amount));
    }

    @Override
    public void accept(MoneyCounterVisitor visitor) {
        visitor.visitBankAccount(this);
    }

    public float getTotal() {
        return total;
    }
}

class GovernmentPrinter implements MoneyCounter {


    @Override
    public void deposit(float amount) {

    }

    @Override
    public float withdraw(float amount) {
        return amount;
    }

    @Override
    public void sendTo(float amount, MoneyCounter other) {
        other.deposit(withdraw(amount));
    }

    @Override
    public void accept(MoneyCounterVisitor visitor) {
        visitor.visitGovernmentPrinter(this);
    }
}

interface MoneyCounterVisitor {
    void visitGovernmentPrinter(GovernmentPrinter governmentPrinter);

    void visitBankAccount(BankAccount bankAccount);

    void visitAbstract(MoneyCounter moneyCounter);
}

class TotalMoneyVisitor implements MoneyCounterVisitor {

    @Override
    public void visitGovernmentPrinter(GovernmentPrinter governmentPrinter) {
        System.out.println("Government has infinite money");
    }

    @Override
    public void visitBankAccount(BankAccount bankAccount) {
        System.out.printf("Bank Account has %f $\n", bankAccount.getTotal());
    }

    @Override
    public void visitAbstract(MoneyCounter moneyCounter) {
        System.out.println("Abstract visiting");
    }
}


public class VisitorFranck {


    public static void main() {

        MoneyCounter myBA = new BankAccount();
        MoneyCounter govAccount = new GovernmentPrinter();
        // because it uses MoneyCounter for the compile time type, then the visitor.visit will not work
        // if the name are not the same. It will try the abstract visitAbstract for all of them.
        // So, we need to re-add dynamic dispatch for inversion of control.

        myBA.deposit(100);

        MoneyCounterVisitor howRich = new TotalMoneyVisitor();
        // Since the type of the visitor is the money couter visitor.
        // It needs dynamic dispatch for visitor itself. Which java has so no problem.
        // Essentialy adding double dispatch manually. 


        myBA.accept(howRich); // calls howRich.RouteToCorrectMethod(myMBA)
        govAccount.accept(howRich);


    }

}
