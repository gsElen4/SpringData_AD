package com.practica1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practica1.entity.Curso;
public interface CursoRepository extends JpaRepository <Curso, Long> {

}
