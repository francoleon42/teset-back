package com.teset.repository;

import com.teset.model.Novedad;
import com.teset.model.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INovedadRepository extends JpaRepository<Novedad, Integer> {
}
