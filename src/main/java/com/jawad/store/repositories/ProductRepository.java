package com.jawad.store.repositories;

import com.jawad.store.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //To have just a single query instead of 3
    @EntityGraph(attributePaths = "category")
    List<Product> findByCategoryId(Byte categoryId);

    //To have just a single query instead of 3
    @Query("SELECT p FROM Product p JOIN FETCH p.category")
    List<Product> findAllWithCategory();
}
