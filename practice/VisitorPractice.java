// Visitor interface
interface ComputerPartVisitor {
    void visit(CPU cpu);
    void visit(GPU gpu);
    void visit(RAM ram);
    void visit(Computer computer);
}

// Element interface
interface ComputerPart {
    void accept(ComputerPartVisitor visitor);
}

// Concrete Elements
class CPU implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor visitor) {
        visitor.visit(this);
        // visit is called in accept
    }
    public int getCores() { return 8; }
    public int getCost() { return 300; }
}

class GPU implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor visitor) {
        visitor.visit(this);
    }
    public String getModel() { return "RTX 4090"; }
    public int getCost() { return 1200; }
}

class RAM implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor visitor) {
        visitor.visit(this);
    }
    public int getGB() { return 32; }
    public int getCost() { return 150; }
}

// Composite element
class Computer implements ComputerPart {
    private final ComputerPart[] parts;

    public Computer() {
        parts = new ComputerPart[] { new CPU(), new GPU(), new RAM() };
    }

    @Override
    public void accept(ComputerPartVisitor visitor) {
        for (ComputerPart part : parts) {
            part.accept(visitor); // delegation
        }
        visitor.visit(this); // visit whole system last
    }
}

// Concrete Visitor 1: Cost calculation
class PriceCalculatorVisitor implements ComputerPartVisitor {
    private int totalPrice = 0;

    public int getTotalPrice() {
        return totalPrice;
    }

    public void visit(CPU cpu) {
        totalPrice += cpu.getCost();
    }

    public void visit(GPU gpu) {
        totalPrice += gpu.getCost();
    }

    public void visit(RAM ram) {
        totalPrice += ram.getCost();
    }

    public void visit(Computer computer) {
        System.out.println("Total price of computer: $" + totalPrice);
    }
}

// Concrete Visitor 2: Diagnostics
class DiagnosticVisitor implements ComputerPartVisitor {
    public void visit(CPU cpu) {
        System.out.println("Diagnosing CPU with " + cpu.getCores() + " cores... OK");
    }

    public void visit(GPU gpu) {
        System.out.println("Diagnosing GPU model " + gpu.getModel() + "... OK");
    }

    public void visit(RAM ram) {
        System.out.println("Testing RAM capacity: " + ram.getGB() + "GB... OK");
    }

    public void visit(Computer computer) {
        System.out.println("Full system check... All components passed.");
    }
}

// Main class to run the example
public class VisitorPractice {
    public static void main(String[] args) {
        Computer myComputer = new Computer();

        // First visitor
        DiagnosticVisitor diagnostics = new DiagnosticVisitor();
        System.out.println("=== Running Diagnostics ===");
        myComputer.accept(diagnostics);

        // Second visitor
        PriceCalculatorVisitor pricing = new PriceCalculatorVisitor();
        System.out.println("\n=== Calculating Price ===");
        myComputer.accept(pricing);
    }
}
