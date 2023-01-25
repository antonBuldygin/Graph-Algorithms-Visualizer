import java.util.*;

class Originator {
    private List<Integer> numbers = new ArrayList<>();

    public void addNumber(Integer number) {
        numbers.add(number);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public /* specify type */ Memento getMemento() {
        return new Memento(numbers);
    }

    public void setMemento(/* specify type */ Memento memento) {

        this.numbers = new ArrayList<>(memento.numb);
    }

    static class Memento {

        private List<Integer> numb = new ArrayList<>();

        public Memento(List<Integer> numbers) {
            this.numb = new ArrayList<>(numbers);
        }
    }

}

class Caretaker {
    private final Originator originator;
    private /* specify type */ Originator.Memento snapshot = null;

    Caretaker(Originator originator) {
        this.originator = originator;
    }

    public void save() {
        snapshot = originator.getMemento();
    }

    public void restore() {
        if (snapshot != null) {
            originator.setMemento(snapshot);
        }
    }
}


