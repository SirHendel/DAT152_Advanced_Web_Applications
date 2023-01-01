package no.hvl.dat152.Assignment4.repository;

import no.hvl.dat152.Assignment4.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
