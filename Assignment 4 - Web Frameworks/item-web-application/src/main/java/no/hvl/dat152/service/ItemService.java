package no.hvl.dat152.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import no.hvl.dat152.model.Item;

@Service
public class ItemService {
    
    private final String BASE_URL = "http://localhost:8299/items/";

    @Autowired
    private RestTemplate template;

    public List<Item> getAll(){
        ResponseEntity<Item[]> response;
        try {
            response = template.getForEntity(BASE_URL, Item[].class);
        } catch(Exception e) {
            return new ArrayList<>();
        }
        return Arrays.asList(response.getBody());
    }

    public Item getById(Long id){
        Item item;
        try {
            item = template.getForObject(BASE_URL + id, Item.class);
        }
        catch(Exception e){
            return null;
        }
        return item;
    }

    public Item save(Item item){
        Item saved;
        try {
            HttpEntity<Item> request = new HttpEntity<>(item);
            saved = template.postForObject(BASE_URL, request, Item.class);
        } catch (Exception e) {
            saved = null;
        }
        return saved;
    }

    public boolean delete(Long id) {
        try {
            template.delete(BASE_URL + id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(Long id, Item item) {
        try {
            HttpEntity<Item> request = new HttpEntity<>(item);
            template.exchange(BASE_URL + id, HttpMethod.PUT, request, Void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
