package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.application.port.in.VehicleUseCase;
import com.bykeeasy.domain.model.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleRestController {

    private final VehicleUseCase vehicleUseCase;

    @PostMapping
    public ResponseEntity<Vehicle> registerVehicle(
            @RequestParam String driverId,
            @RequestParam String licensePlate,
            @RequestParam String type,
            @RequestParam String model,
            @RequestParam String color,
            @RequestParam String brand,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        
        Vehicle vehicle = vehicleUseCase.registerVehicle(
                driverId,
                licensePlate,
                type,
                model,
                color,
                brand,
                image != null ? image.getInputStream() : null,
                image != null ? image.getOriginalFilename() : null
        );
        
        return ResponseEntity.ok(vehicle);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable String id,
            @RequestParam String licensePlate,
            @RequestParam String type,
            @RequestParam String model,
            @RequestParam String color,
            @RequestParam String brand,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        
        // Using the same register logic but with existing ID if the service supports it
        // Or we update the UseCase. Let's add update to UseCase.
        Vehicle vehicle = vehicleUseCase.updateVehicle(
                id,
                licensePlate,
                type,
                model,
                color,
                brand,
                image != null ? image.getInputStream() : null,
                image != null ? image.getOriginalFilename() : null
        );
        
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Vehicle>> getVehicles(@PathVariable String driverId) {
        return ResponseEntity.ok(vehicleUseCase.getVehiclesByDriver(driverId));
    }
}
