class StringOperations {

    // create static nested class EngString

    static class EngString{
        public EngString(boolean english, String string) {
            this.english = english;
            this.string = string;
        }

        public boolean isEnglish() {
            return english;
        }

        public String getString() {
            return string;
        }

        boolean english;
        String string;



    }

}