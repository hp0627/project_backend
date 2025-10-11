package com.foodhub.service;

import com.foodhub.model.Restaurant;
import com.foodhub.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository repo;
    public RestaurantService(RestaurantRepository repo) { this.repo = repo; }
    public List<Restaurant> getAll() { return repo.findAll(); }
    public Optional<Restaurant> getById(Long id) { return repo.findById(id); }
    public List<Restaurant> getByCuisine(String cuisine) { return repo.findByCuisineIgnoreCase(cuisine); }
    public Restaurant save(Restaurant r) { return repo.save(r); }
    public void delete(Long id) { repo.deleteById(id); }
}
