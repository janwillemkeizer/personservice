package nl.janwillemkeizer.personservice.repository;

import nl.janwillemkeizer.personservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
} 