package com.practica1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.practica1.entity.Curso;
import com.practica1.repository.CursoRepository;

public class CursoService {

     @Autowired
    private CursoRepository cursoRepository;

      // Crear un curso
    public Curso crearCurso(String nombre) {
        Curso curso = new Curso(nombre);
        return cursoRepository.save(curso);
    }

    // Listar todos los cursos
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    // Obtener un curso por id
    public Optional<Curso> obtenerPorId(Long id) {
        return cursoRepository.findById(id);
    }

    // Eliminar un curso
    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    // Contar cursos
    public long contarCursos() {
        return cursoRepository.count();
    }
}
