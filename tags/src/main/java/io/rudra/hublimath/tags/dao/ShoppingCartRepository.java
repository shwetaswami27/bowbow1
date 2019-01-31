package io.rudra.hublimath.tags.dao;

import io.rudra.hublimath.tags.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//import com.douglas.carvalho.shoppingcart.domain.ShoppingCart;

/**
 * 11/07/2017
 * @author doug
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    List<ShoppingCart> findByStatus(String status);
}
