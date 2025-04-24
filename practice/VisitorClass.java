import java.util.*;

// ---- Visitor Interface ----
interface CardSourceVisitor {
    void visitCompositeCardSource(CompositeCardSource pSource);
    void visitDeck(Deck pDeck);
    void visitCardSequence(CardSequence pCardSequence);
}

// ---- Element Interface ----
interface CardSource {
    Card draw();
    boolean isEmpty();
    void accept(CardSourceVisitor pVisitor);
}

// ---- Simple Card class ----
class Card {
    private final String name;

    public Card(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Card(" + name + ")";
    }
}

// ---- Deck (Concrete Element) ----
class Deck implements CardSource, Iterable<Card> {
    private final List<Card> cards = new ArrayList<>();

    public Deck(Card... initialCards) {
        cards.addAll(Arrays.asList(initialCards));
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public Card draw() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    @Override
    public void accept(CardSourceVisitor pVisitor) {
        pVisitor.visitDeck(this);
    }
}

// ---- CardSequence (Concrete Element) ----
class CardSequence implements CardSource {
    private final List<Card> sequence;

    public CardSequence(Card... cards) {
        this.sequence = Arrays.asList(cards);
    }

    @Override
    public boolean isEmpty() {
        return sequence.isEmpty();
    }

    public int size() {
        return sequence.size();
    }

    public Card get(int index) {
        return sequence.get(index);
    }

    @Override
    public Card draw() {
        throw new UnsupportedOperationException("CardSequence does not support draw()");
    }

    @Override
    public void accept(CardSourceVisitor pVisitor) {
        pVisitor.visitCardSequence(this);
    }
}

// ---- CompositeCardSource (Composite Element) ----
class CompositeCardSource implements CardSource {
    private final List<CardSource> aElements = new ArrayList<>();

    public CompositeCardSource(CardSource... sources) {
        aElements.addAll(Arrays.asList(sources));
    }

    @Override
    public boolean isEmpty() {
        for (CardSource src : aElements) {
            if (!src.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public Card draw() {
        for (CardSource src : aElements) {
            if (!src.isEmpty()) return src.draw();
        }
        return null;
    }

    @Override
    public void accept(CardSourceVisitor pVisitor) {
        pVisitor.visitCompositeCardSource(this);
        for (CardSource source : aElements) {
            source.accept(pVisitor);
        }
    }
}

// ---- Concrete Visitor ----
class PrintingVisitor implements CardSourceVisitor {
    @Override
    public void visitCompositeCardSource(CompositeCardSource pSource) {
        System.out.println("Visiting composite card source...");
    }

    @Override
    public void visitDeck(Deck pDeck) {
        System.out.println("Visiting deck:");
        for (Card card : pDeck) {
            System.out.println(card);
        }
    }

    @Override
    public void visitCardSequence(CardSequence pCardSequence) {
        System.out.println("Visiting card sequence:");
        for (int i = 0; i < pCardSequence.size(); i++) {
            System.out.println(pCardSequence.get(i));
        }
    }
}

// ---- Main class for client code ----
public class VisitorClass {
    public static void main(String[] args) {
        Card c1 = new Card("Ace");
        Card c2 = new Card("King");
        Card c3 = new Card("Queen");

        Deck deck = new Deck(c1, c2);
        CardSequence sequence = new CardSequence(c3);
        CompositeCardSource composite = new CompositeCardSource(deck, sequence);

        PrintingVisitor visitor = new PrintingVisitor();

        System.out.println("=== Visiting Deck ===");
        deck.accept(visitor);
        // accept is run (interface type), it's a router, pointing to which implementation.
        // visit has the actual code.

        // hence, deck.accept(visitor) == deck.visitorEquivalent();
        // and deck.accept(visitor) end up doing visitDeck(this).
        // hence, a.accept(b) -> b#_a.visit(a)
        // a.?b -> b.?a
        // Inversion of control.

        System.out.println("\n=== Visiting Sequence ===");
        sequence.accept(visitor);

        System.out.println("\n=== Visiting Composite ===");
        composite.accept(visitor);
    }
}
