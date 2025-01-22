package com.teset.repository;

import com.teset.model.Contacto;
import com.teset.model.Novedad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContactoRepository  extends JpaRepository<Contacto, Integer> {
}
