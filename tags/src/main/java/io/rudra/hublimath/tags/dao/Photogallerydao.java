package io.rudra.hublimath.tags.dao;


import io.rudra.hublimath.tags.entities.Photogallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Photogallerydao extends JpaRepository<Photogallery, Long> {


    Photogallery findByFilename(String filename);
}
