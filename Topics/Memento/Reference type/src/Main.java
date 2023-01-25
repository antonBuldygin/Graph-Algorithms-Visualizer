import java.util.*;
import java.util.stream.Collectors;

class Originator {
    private int[][] array;

    public Originator(int[][] array) {
        this.array = array.clone();
    }

    public void setNumber(int position, int number) {
        array[position / array[0].length][position % array[0].length] = number;
    }

    public int[][] getArray() {
        return array.clone();
    }

    public Memento getMemento() {
        // TODO implement this method
        return new Memento(array);
    }

    public void setMemento(Memento memento) {
        // TODO implement this method
//        System.arraycopy(this.array, 0, memento.array, 0, memento.array.length);
//        for (int i = 0; i < array.length; i++) {
//            for (int j = 0; j < array[0].length; j++) {
//                this.array[i][j] =  memento.array[i][j];
//            }
//        }

        this.array = memento.array.clone();
    }

    static class Memento {
        int[][] array;

        public Memento(int[][] array) {
            this.array = new int[array.length][array[0].length];
//            System.arraycopy(this.array, 0, array, 0, array.length);
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    this.array[i][j] = array[i][j];
                }
//            this.array = array.clone();
            }


            // TODO implement this class
        }
    }
}



    class Caretaker {
        private final Originator originator;
        private Originator.Memento snapshot = null;

        public Caretaker(Originator originator) {
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

