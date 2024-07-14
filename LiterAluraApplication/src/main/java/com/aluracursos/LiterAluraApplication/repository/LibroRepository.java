package com.aluracursos.LiterAluraApplication.repository;

import com.aluracursos.LiterAluraApplication.Model.Autor;
import com.aluracursos.LiterAluraApplication.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository <Libro,Long>{



}
