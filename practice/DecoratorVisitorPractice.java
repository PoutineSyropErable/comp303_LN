import java.util.ArrayList;
import java.util.List;

public class DecoratorVisitorPractice {

    public interface Decorator {
        void partialDecorate(ConcreteClass pElement);
        void preMethod1();
        void postMethod1();
        void preMethod2();
        void postMethod2();
    }

    public interface SplitDecorator {
        void partialDecorate(ConcreteClass pElement);
        void method1();
        void method2();
    }

    public static class ConcreteClass {
        private final String name;
        private final List<SplitDecorator> preDecorators = new ArrayList<>();
        private final List<SplitDecorator> postDecorators = new ArrayList<>();
        private final List<Decorator> mergedDecorators = new ArrayList<>();

        public ConcreteClass(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addPreDecorator(SplitDecorator dec) {
            preDecorators.add(dec);
            dec.partialDecorate(this);
        }

        public void addPostDecorator(SplitDecorator dec) {
            postDecorators.add(dec);
            dec.partialDecorate(this);
        }

        public void addDecorator(Decorator dec) {
            mergedDecorators.add(dec);
            dec.partialDecorate(this);
        }

        public void method1() {
            for (SplitDecorator dec : preDecorators) dec.method1();
            for (Decorator dec : mergedDecorators) dec.preMethod1();

            System.out.println("Executing method1 core logic...");

            for (Decorator dec : mergedDecorators) dec.postMethod1();
            for (SplitDecorator dec : postDecorators) dec.method1();
        }

        public void method2() {
            for (SplitDecorator dec : preDecorators) dec.method2();
            for (Decorator dec : mergedDecorators) dec.preMethod2();

            System.out.println("Executing method2 core logic...");

            for (Decorator dec : mergedDecorators) dec.postMethod2();
            for (SplitDecorator dec : postDecorators) dec.method2();
        }
    }

    public static class ConcreteSplitDecorator implements SplitDecorator {
        private ConcreteClass aElement;

        public void partialDecorate(ConcreteClass pElement) {
            aElement = pElement;
        }

        public void method1() {
            System.out.println("SplitDecorator method1 on " + aElement.getName());
        }

        public void method2() {
            System.out.println("SplitDecorator method2 on " + aElement.getName());
        }
    }

    public static class MergedDecorator implements Decorator {
        private final SplitDecorator decPre;
        private final SplitDecorator decPost;
        private ConcreteClass aElement;

        public MergedDecorator(SplitDecorator decPre, SplitDecorator decPost) {
            this.decPre = decPre;
            this.decPost = decPost;
        }

        public void partialDecorate(ConcreteClass pElement) {
            aElement = pElement;
            decPre.partialDecorate(pElement);
            decPost.partialDecorate(pElement);
        }

        public void preMethod1() {
            System.out.println("MergedDecorator PRE method1 on " + aElement.getName());
            decPre.method1();
        }

        public void postMethod1() {
            System.out.println("MergedDecorator POST method1 on " + aElement.getName());
            decPost.method1();
        }

        public void preMethod2() {
            System.out.println("MergedDecorator PRE method2 on " + aElement.getName());
            decPre.method2();
        }

        public void postMethod2() {
            System.out.println("MergedDecorator POST method2 on " + aElement.getName());
            decPost.method2();
        }
    }

    public static class ConcreteDecorator1 implements Decorator {
        private ConcreteClass aElement;

        public void partialDecorate(ConcreteClass pElement) {
            aElement = pElement;
        }

        public void preMethod1() {
            System.out.println("ConcreteDecorator1 PRE method1 on " + aElement.getName());
        }

        public void postMethod1() {
            System.out.println("ConcreteDecorator1 POST method1 on " + aElement.getName());
        }

        public void preMethod2() {
            System.out.println("ConcreteDecorator1 PRE method2 on " + aElement.getName());
        }

        public void postMethod2() {
            System.out.println("ConcreteDecorator1 POST method2 on " + aElement.getName());
        }
    }

    public static class ConcreteDecorator2 implements Decorator {
        private ConcreteClass aElement;

        public void partialDecorate(ConcreteClass pElement) {
            aElement = pElement;
        }

        public void preMethod1() {
            System.out.println("ConcreteDecorator2 PRE method1 on " + aElement.getName());
        }

        public void postMethod1() {
            System.out.println("ConcreteDecorator2 POST method1 on " + aElement.getName());
        }

        public void preMethod2() {
            System.out.println("ConcreteDecorator2 PRE method2 on " + aElement.getName());
        }

        public void postMethod2() {
            System.out.println("ConcreteDecorator2 POST method2 on " + aElement.getName());
        }
    }

    public static void main(String[] args) {
        ConcreteClass concrete = new ConcreteClass("My concrete instance");


        // Create and add split decorators
        ConcreteSplitDecorator preSplit = new ConcreteSplitDecorator();
        ConcreteSplitDecorator postSplit = new ConcreteSplitDecorator();
        concrete.addPreDecorator(preSplit);
        concrete.addPostDecorator(postSplit);

        // Create decorators
        ConcreteDecorator1 cd1 = new ConcreteDecorator1();
        ConcreteDecorator2 cd2 = new ConcreteDecorator2();
        concrete.addDecorator(cd1);
        concrete.addDecorator(cd2);

        // Create and add merged decorator
        MergedDecorator merged = new MergedDecorator(
                new ConcreteSplitDecorator(),
                new ConcreteSplitDecorator()
        );
        concrete.addDecorator(merged);


        System.out.println("\n=== METHOD 1 ===");
        concrete.method1();

        System.out.println("\n=== METHOD 2 ===");
        concrete.method2();
    }
}
