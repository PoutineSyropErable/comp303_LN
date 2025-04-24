import java.util.List;
import java.util.ArrayList;

public class P {

	// MergedDecoratorVisotor uses decorator (kinda) due to accepting a decorator
	// (though its split, so hence kinda), and then implement the
	// no op wrapping in this case

	// Visitor is the usage of accept this

	public interface DecoratorVisitor {
		void accept(ConcreteClass pElement);

		void preMethod1();

		void postMethod1();

		void preMethod2();

		void postMethod2();
	}

	public interface SplitDecoratorVisitor {
		// You'd use two decorators for pre and post
		void accept(ConcreteClass pElement);

		void method1();

		void method2();
	}

	public class ConcreteSplitDecoratorVisitor implements SplitDecoratorVisitor {
		private ConcreteClass aElement;

		public void accept(ConcreteClass pElement) {
			aElement = pElement;
		}

		public ConcreteSplitDecoratorVisitor() {

		}

		public void method1() {

		}

		public void method2() {

		}

	}

	public class MergedDecoratorVisotor implements DecoratorVisitor {
		private SplitDecoratorVisitor decPre;
		private SplitDecoratorVisitor decPost;
		private ConcreteClass aElement;

		public void accept(ConcreteClass pElement) {
			aElement = pElement;
		}

		public MergedDecoratorVisotor(SplitDecoratorVisitor decPre, SplitDecoratorVisitor decPost) {
			this.decPre = decPre;
			this.decPost = decPost;
		}

		public void preMethod1() {
			this.decPre.method1();
		}

		public void preMethod2() {
			this.decPre.method2();
		}

		public void postMethod1() {
			this.decPost.method1();
		}

		public void postMethod2() {
			this.decPost.method2();
		}
	}

	public class ConcreteDecorator1 implements DecoratorVisitor {
		private ConcreteClass aElement;

		public void accept(ConcreteClass pElement) {
			aElement = pElement;
		}

		public void preMethod1() {
		}

		public void postMethod1() {
		}

		public void preMethod2() {
		}

		public void postMethod2() {
		}

	}

	public class ConcreteDecorator2 implements DecoratorVisitor {
		private ConcreteClass aElement;

		public void accept(ConcreteClass pElement) {
			aElement = pElement;
		}

		public void preMethod1() {
		}

		public void postMethod1() {
		}

		public void preMethod2() {
		}

		public void postMethod2() {
		}

	}

	public class ConcreteClass {

		List<SplitDecoratorVisitor> preDecorators = new ArrayList<SplitDecoratorVisitor>();
		List<SplitDecoratorVisitor> postDecorators = new ArrayList<SplitDecoratorVisitor>();

		List<DecoratorVisitor> mergedDecorator = new ArrayList<DecoratorVisitor>();

		public void addPreDecorator(SplitDecoratorVisitor dec) {
			preDecorators.add(dec);
			dec.accept(this);

		}

		public void removePreDecorator(SplitDecoratorVisitor dec) {
			preDecorators.remove(dec);

		}

		public void addPostDecorator(SplitDecoratorVisitor dec) {
			postDecorators.add(dec);
			dec.accept(this);

		}

		public void removePostDecorator(SplitDecoratorVisitor dec) {
			postDecorators.remove(dec);

		}

		public void addDecorator(DecoratorVisitor dec) {
			mergedDecorator.add(dec);
			dec.accept(this);
		}

		public void removeDecorator(DecoratorVisitor dec) {
			mergedDecorator.remove(dec);
			dec.accept(this);
		}

		// Example methods to demonstrate usage
		public void method1() {
			for (SplitDecoratorVisitor preDec : preDecorators) {
				preDec.method1();
			}
			for (DecoratorVisitor dec : mergedDecorator) {
				dec.preMethod1();
			}

			System.out.println("Executing method1...");
			for (DecoratorVisitor dec : mergedDecorator) {
				dec.postMethod1();
			}

			for (SplitDecoratorVisitor postDec : postDecorators) {
				postDec.method1();
			}
		}

		public void method2() {

			for (SplitDecoratorVisitor preDec : preDecorators) {
				preDec.method2();
			}
			for (DecoratorVisitor dec : mergedDecorator) {
				dec.preMethod2();
			}

			System.out.println("Executing method2...");
			for (DecoratorVisitor dec : mergedDecorator) {
				dec.postMethod2();
			}

			for (SplitDecoratorVisitor postDec : postDecorators) {
				postDec.method2();
			}

		}
	}

	public void main(String[] args) {
		ConcreteClass obj = new ConcreteClass();

		ConcreteDecorator1 cd1 = new ConcreteDecorator1();
		ConcreteDecorator2 cd2 = new ConcreteDecorator2();

		ConcreteSplitDecoratorVisitor preSD = new ConcreteSplitDecoratorVisitor();

		ConcreteSplitDecoratorVisitor postSD = new ConcreteSplitDecoratorVisitor();

		obj.addDecorator(cd1);
		obj.addDecorator(cd2);

		obj.addPreDecorator(preSD);
		obj.addPostDecorator(postSD);

		obj.method1();
		obj.method2();

		obj.removePreDecorator(preSD);
		obj.removePostDecorator(postSD);

		obj.removeDecorator(cd1);
		obj.removeDecorator(cd2);

		// end of program

	}
}
