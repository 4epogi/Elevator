package com.chepogi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Elevator {
    public final int ELEVATOR_SIZE = 5;

    private List<Person> passengers;

    public Elevator() {
        this.passengers = new ArrayList<Person>();
    }

    public int getElevatorSize() {
        return ELEVATOR_SIZE;
    }

    public void addPassenger(Person person) {
        passengers.add(person);
    }

    public void removePassenger(int floor) {
        passengers.removeIf(person -> person.getDestinationFloor() == floor);
    }

    public void showPassengers() {
        passengers.forEach(System.out::println);
    }

    public int getNextStop() {
        if (passengers.size() == 0) {
            return -1;
        }
        return passengers.stream()
                .map(person -> person.getDestinationFloor())
                .min(Integer::compare)
                .get();
    }

    public int getEmptyPlaces() {
        return ELEVATOR_SIZE - passengers.size();
    }

    public List<Person> getExitPassengers(int floor) {
        return passengers.stream()
                .filter(person -> person.getDestinationFloor() == floor)
                .collect(Collectors.toList());
    }

    public List<Person> getComePassengers(int floor) {
        return passengers.stream()
                .filter(person -> person.getStartFloor() == floor)
                .collect(Collectors.toList());
    }
}
