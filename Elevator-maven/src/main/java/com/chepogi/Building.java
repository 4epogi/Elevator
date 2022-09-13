package com.chepogi;

import java.util.*;
import java.util.stream.Collectors;

public class Building {
    private final static int FIRST_FLOOR = 1;
    private final static int MIN_FLOORS_COUNT = 5;
    private final static int MAX_FLOORS_COUNT = 20;
    private final static int MAX_PERSONS_ON_FLOOR = 10;

    private Elevator elevator;
    private int floorsCount;
    private Map<Integer, List<Person>> personsOnAllFloors;

    public Building() {
        this.elevator = new Elevator();
        fillFloorsWithPersons();
    }

    private void fillFloorsWithPersons() {
        personsOnAllFloors = new HashMap<>();
        Random randomGenerator = new Random();
        this.floorsCount = randomGenerator.nextInt(MIN_FLOORS_COUNT, MAX_FLOORS_COUNT + 1);

        for (int floor = 1; floor <= floorsCount; floor++) {
            int numberPersonsOnFloor = randomGenerator.nextInt(MAX_PERSONS_ON_FLOOR + 1);
            List<Person> personsOnFloor = new ArrayList<>();

            for (int i = 0; i < numberPersonsOnFloor; i++) {
                personsOnFloor.add(new Person(floor, generateDestinationFloor(randomGenerator, floor)));
            }
            personsOnAllFloors.put(floor, personsOnFloor);
        }
    }

    private int generateDestinationFloor(Random randomGenerator, int floor) {
        int destinationFloor;
        do {
            destinationFloor = randomGenerator.nextInt(1, floorsCount + 1);
        } while (destinationFloor == floor);
        return destinationFloor;
    }

    public void showFloors() {
        personsOnAllFloors.entrySet().forEach(entry -> {
            System.out.println("floor " + entry.getKey());
            entry.getValue().forEach(System.out::println);
        });
    }

    public void runElevator() {
        int elevatorStopFloor = FIRST_FLOOR;
        do {
            exitPersonsFromElevator(elevatorStopFloor);
            comePersonsToElevator(elevatorStopFloor);
            printSeparatorLine();
            elevatorStopFloor = elevator.getNextStop();
        } while (elevatorStopFloor != -1);
    }

    private void printSeparatorLine() {
        System.out.println("---------------------------------");
    }

    private void comePersonsToElevator(int floor) {
        List<Person> personsGoUp = personsOnAllFloors.get(floor)
                .stream()
                .filter(person -> person.getDestinationFloor() > floor)
                .toList();
        if (personsGoUp.isEmpty()) {
            return;
        }

        int availablePersonsNumbers;
        if (personsGoUp.size() >= elevator.getEmptyPlaces()) {
            availablePersonsNumbers = elevator.getEmptyPlaces();
        } else {
            availablePersonsNumbers = personsGoUp.size();
        }
        for (int i = 0; i < availablePersonsNumbers; i++) {
            elevator.addPassenger(personsGoUp.get(i));
        }
        printComePersonsResult(elevator.getComePassengers(floor), floor);
    }

    private void printComePersonsResult(List<Person> comePersons, int floor) {
        System.out.println("Persons came to the elevator:");
        comePersons.forEach(person ->
                System.out.println(String.format("Person from %d to %d",
                        person.getStartFloor(),
                        person.getDestinationFloor())));
    }

    private void exitPersonsFromElevator(int floor) {
        List<Person> exitPersons = elevator.getExitPassengers(floor);
        printLeavingPersonsResult(exitPersons, floor);
        elevator.removePassenger(floor);
    }

    private void printLeavingPersonsResult(List<Person> exitPersons, int floor) {
        System.out.println(String.format("Elevator is on the floor number %d", floor));
        System.out.println("Persons left the elevator:");
        exitPersons.forEach(person ->
                System.out.println(String.format("Person from %d to %d",
                        person.getStartFloor(),
                        person.getDestinationFloor())));
    }
}
