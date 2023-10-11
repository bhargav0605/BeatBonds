package io.beatbonds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.beatbonds.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
