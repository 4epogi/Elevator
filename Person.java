public class Person {
    private int startFloor;
    private int destinationFloor;

    public Person(int startFloor, int destinationFloor) {
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
    }

    public int getDestinationFloor(){
        return this.destinationFloor;
    }

    public int getStartFloor(){
        return this.startFloor;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Person{" +
                "startFloor=" + startFloor +
                ", destinationFloor=" + destinationFloor +
                '}';
    }
}
