package com.solomon.nkwor.solomon.bank.Repository;

import com.solomon.nkwor.solomon.bank.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String > {
    Boolean existsByEmail(String email);

}
