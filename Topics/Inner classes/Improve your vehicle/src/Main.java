class Vehicle {

    private String name;

    public Vehicle(String name) {
        this.name = name;
    }
// create constructor

    class Engine {

        public Engine(int horsePower) {
            this.horsePower = horsePower;
        }

        int horsePower;
        // add field horsePower
        // create constructor

        void start() {
            System.out.println("RRRrrrrrrr....");
        }

        void printHorsePower(){
            System.out.println("Vehicle "+name+" has "+horsePower+" horsepower.");
        }
        // create method printHorsePower()
    }
}

// this code should work
class EnjoyVehicle {

    public static void main(String[] args) {

        Vehicle vehicle = new Vehicle("Dixi");
        Vehicle.Engine engine = vehicle.new Engine(15);
        engine.printHorsePower();
    }
}