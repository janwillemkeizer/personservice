package nl.janwillemkeizer.personservice.repository;

import nl.janwillemkeizer.personservice.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByUser_Id(Long userId);
} 