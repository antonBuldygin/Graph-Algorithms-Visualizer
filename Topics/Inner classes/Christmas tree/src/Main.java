class ChristmasTree {

    private String color;

    public ChristmasTree(String color) {
        this.color = color;
    }

    void putTreeTopper(String color){
        TreeTopper treeTopper = new TreeTopper(color);
        treeTopper.sparkle();

    }
    // create method putTreeTopper()

    class TreeTopper {

        private String color;        

        public TreeTopper(String color) {
            this.color = color;
        }

        void sparkle(){
            System.out.println("Sparkling "+color+" tree topper looks stunning with "+ChristmasTree.this.color+" Christmas tree!");

        }
        // create method sparkle()
    }
}

// this code should work
class CreateHoliday {

    public static void main(String[] args) {

        ChristmasTree christmasTree = new ChristmasTree("green");
        christmasTree.putTreeTopper("silver");
    }
}