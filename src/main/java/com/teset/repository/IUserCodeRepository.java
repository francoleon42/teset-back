package com.teset.repository;

import com.teset.model.UserCode;
import com.teset.utils.enums.PropositoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserCodeRepository extends JpaRepository<UserCode, Integer> {

    @Query("Select uc from  UserCode uc where uc.usuario.usuario =:username and uc.propositoCode =:propositoCode ")
    Optional<UserCode> findByPropositoAndUsername(@Param("username") String username,@Param("propositoCode") PropositoCode propositoCode);
}
