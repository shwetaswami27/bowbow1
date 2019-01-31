package io.rudra.hublimath.tags.dao;


import io.rudra.hublimath.tags.entities.FileUploadMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileUploaddao extends JpaRepository<FileUploadMetaData, Long> {

    FileUploadMetaData findByFilename(String filename);

    FileUploadMetaData findByFilefullpath(String s);

    List<FileUploadMetaData> findByCategory(String category);
}
