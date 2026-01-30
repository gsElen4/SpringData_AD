package com.practica1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practica1.entity.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long>{
  
}


