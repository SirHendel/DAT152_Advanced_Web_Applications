package no.hvl.dat152.Assignment4.controller;

import no.hvl.dat152.Assignment4.model.Item;
import no.hvl.dat152.Assignment4.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    ResponseEntity<List<Item>> all() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @GetMapping("/items/{id}")
    ResponseEntity detail(@PathVariable Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()) return ResponseEntity.ok().body(item.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/items")
    ResponseEntity create(@RequestBody Item item) {
        try {
            return ResponseEntity.ok(itemRepository.save(item));
        } catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/items/{id}")
    ResponseEntity update(@RequestBody Item newItem, @PathVariable Long id) {
        return itemRepository.findById(id)
            .map(item -> {
                item.setName(newItem.getName());
                item.setDescription(newItem.getDescription());
                item.setPrice(newItem.getPrice());
                try {
                    return ResponseEntity.ok(itemRepository.save(item));
                } catch(Exception e) {
                    return ResponseEntity.badRequest().build();
                }
            })
            .orElseGet(() -> {
                try {
                    return ResponseEntity.ok(itemRepository.save(newItem));
                } catch(Exception e) {
                    return ResponseEntity.badRequest().build();
                }
            });
    }

    @DeleteMapping("/items/{id}")
    ResponseEntity delete(@PathVariable Long id) {
        try {
            itemRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
