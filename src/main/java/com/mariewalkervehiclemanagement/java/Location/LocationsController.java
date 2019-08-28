package com.mariewalkervehiclemanagement.java.Location;

import com.mariewalkervehiclemanagement.java.Car.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class LocationsController {

    @Autowired
    private final LocationsRepository locationsRepository;
    private final CarsRepository carsRepository;

    public LocationsController(LocationsRepository locationsRepository, CarsRepository carsRepository) {
        this.locationsRepository = locationsRepository;
        this.carsRepository = carsRepository;
    }

    // GET ALL LOCATIONS
    @GetMapping("/locations")
    private Iterable<Location> getAllLocations() {
        return this.locationsRepository.findAll();
    }

    // GET A SPECIFIC LOCATION BY ID
    @GetMapping("/locations/{location_id}")
    public Optional<Location> getOneLocation(@PathVariable Long location_id) {
        return this.locationsRepository.findById(location_id);
    }

    // CREATE A NEW LOCATION
    @PostMapping("/locations")
    private Location createNewLocation(@RequestBody Location location) {
        System.out.println("This is the new loction being added: " + location);
        return locationsRepository.save(location);
    }

    // UPDATE A SPECIFIC LOCATION
    @PostMapping("locations/{location_id}")
    public Location updateOneLocation(@RequestBody Location newLocation, @PathVariable Long location_id) {
        return this.locationsRepository.findById(location_id).map(location -> {
            location.setAddress(newLocation.getAddress());
            location.setName(newLocation.getName());
            return this.locationsRepository.save(location);
        }).orElseGet(() -> {
            newLocation.setId(location_id);
            return locationsRepository.save(newLocation);
        });
    }

    // DELETE A SPECIFIC LOCATION
    @DeleteMapping("/locations/{location_id}")
    public Map<String, Object> deleteOneLocation(@PathVariable Long location_id) {
        locationsRepository.deleteById(location_id);

        HashMap<String, Object> result = new HashMap<>();
        result.put("count", locationsRepository.count());
        return result;
    }
}
