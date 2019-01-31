package io.rudra.hublimath.tags.dao;


import io.rudra.hublimath.tags.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import com.douglas.carvalho.shoppingcart.domain.Product;

/**
 * 10/07/2017
 * @author doug
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
