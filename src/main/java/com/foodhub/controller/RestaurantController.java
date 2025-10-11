package com.foodhub.controller;

import com.foodhub.model.Restaurant;
import com.foodhub.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "http://localhost:5173")
public class RestaurantController {
    private final RestaurantService service;
    public RestaurantController(RestaurantService service) { this.service = service; }
    @GetMapping
    public List<Restaurant> getAll() { return service.getAll(); }
    @GetMapping("/cuisine/{cuisine}")
    public List<Restaurant> getByCuisine(@PathVariable String cuisine) { return service.getByCuisine(cuisine); }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> addRestaurant(@RequestBody Restaurant r, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) return ResponseEntity.status(403).body("Forbidden: Admins only");
        return ResponseEntity.ok(service.save(r));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) return ResponseEntity.status(403).body("Forbidden: Admins only");
        service.delete(id);
        return ResponseEntity.ok(Map.of("message","Deleted"));
    }
}
