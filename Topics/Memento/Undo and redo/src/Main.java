import java.util.*;

class Originator<T> {
    private T value;

    public void setValue(T value) {
        this.value = value;
    }

    public void printValue() {
        System.out.println(value);
    }

    public Memento<T> getMemento() {
        return new Memento<>(value);
    }

    public void setMemento(Memento<T> memento) {
        this.value = memento.value;
    }

    static class Memento<T> {
        private final T value;

        private Memento(T value) {
            this.value = value;
        }
    }
}

class Caretaker<T> {
    private final Originator<T> originator;
    private final Deque<Originator.Memento<T>> undoStack = new ArrayDeque<>();
    private final Deque<Originator.Memento<T>> redoStack = new ArrayDeque<>();

    Caretaker(Originator<T> originator) {
        this.originator = originator;
    }

    public void beforeValueChanged() {
        undoStack.push(originator.getMemento());
        redoStack.clear();
    }

    public void undo() {
        if(!undoStack.isEmpty()){
        redoStack.push(originator.getMemento());

            if(!undoStack.isEmpty()){originator.setMemento( undoStack.pop());}}



        // TODO implement this method
    }

    public void redo() {
        // TODO implement this method
       if(!redoStack.isEmpty()) {undoStack.push(originator.getMemento());
           if(!redoStack.isEmpty()){originator.setMemento(redoStack.pop());}
    }}
}

//class Main{
//    public static void main(String[] args) {
//
//
//        Originator<String> originator = new Originator();
//    originator.setValue("EREE");
////originator.printValue();
//    Caretaker<String> caretaker = new Caretaker<>(originator);
//    caretaker.beforeValueChanged();
//        originator.printValue();
//        originator.setValue("EwwwREE");
////        caretaker.beforeValueChanged();
//        caretaker.redo();
//        originator.printValue();
//caretaker.undo();
////        caretaker.undo();
//originator.printValue();
//caretaker.redo();
//originator.printValue();
//        caretaker.undo();
////        caretaker.undo();
//        originator.printValue();
//        caretaker.redo();
//        originator.printValue();
//        caretaker.undo();
////        caretaker.undo();
//        originator.printValue();
//        caretaker.redo();
//        originator.printValue();
//        caretaker.undo();
////        caretaker.undo();
//        originator.printValue();
//        caretaker.redo();
//        originator.printValue();
//    }
//}