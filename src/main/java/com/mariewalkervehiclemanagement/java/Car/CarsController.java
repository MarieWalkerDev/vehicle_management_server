package com.mariewalkervehiclemanagement.java.Car;

import com.mariewalkervehiclemanagement.java.Location.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CarsController {

    @Autowired
    private final CarsRepository carsRepository;
    private final LocationsRepository locationsRepository;

    public CarsController(CarsRepository carsRepository, LocationsRepository locationsRepository) {
        this.carsRepository = carsRepository;
        this.locationsRepository = locationsRepository;
    }

    @GetMapping("/")
    // TODO: still need to add functionality to the login page
    public String loginPage() {
        return "This is the login/sign up page!";
    }

    // GET ALL CARS WITH RESPECTIVE LOCATION
    @GetMapping("/cars")
    public Iterable<Car> getAllCars() {
        return this.carsRepository.findAll();
    }

    // GET A SPECIFIC CAR BY ID WITH RESPECTIVE LOCATION
    @GetMapping("/cars/{car_id}")
    public Optional<Car> getOneCar(@PathVariable Long car_id) {
        return this.carsRepository.findById(car_id);
    }

    // CREATE A NEW CAR FROM THE LOCATION
    // TODO: need to fix the route to be @PostMapping("/cars")  <-- how is the location added?
    @PostMapping("/locations/{location_id}/new-car")
    public Optional<Car> createNewCar(@PathVariable long location_id, @RequestBody Car newCar) {
        System.out.println("This is the new car being added: " + newCar.toString());
        return locationsRepository.findById(location_id).map(location -> {
            newCar.setLocation(location);
            return carsRepository.save(newCar);
        });
    }

    // UPDATE A SPECIFIC CAR
    // TODO: make it possible to be able to update the location as well
    @PatchMapping("/cars/{car_id}")
    public Car updateOneCar(@RequestBody Car newCar, @PathVariable Long car_id) {
        return this.carsRepository.findById(car_id).map(car -> {
            car.setMake(newCar.getMake());
            car.setModel(newCar.getModel());
            car.setMiles(newCar.getMiles());
            car.setPhotoUrl(newCar.getPhotoUrl());
            car.setPrice(newCar.getPrice());
            car.setVin(newCar.getVin());
            car.setYear(newCar.getYear());
            locationsRepository.findById(newCar.getLocation().getId()).map(location -> {
                newCar.setLocation(location);
                return null;
            });
            return this.carsRepository.save(car);
        }).orElseGet(() -> {
            newCar.setId(car_id);
            return carsRepository.save(newCar);
        });
    }

    // DELETE A SPECIFIC CAR AND ITS RELATIONSHIP WITH ITS ASSIGNED LOCATION
    @DeleteMapping("/cars/{car_id}")
    public Map<String, Object> deleteOneCar(@PathVariable Long car_id) {
        carsRepository.deleteById(car_id);

        HashMap<String, Object> result = new HashMap<>();
        result.put("count", carsRepository.count());
        return result;
    }


}
