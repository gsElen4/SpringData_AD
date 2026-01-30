package com.practica1.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica1.entity.Asignatura;
import com.practica1.repository.AsignaturaRepository;

@Service
public class AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public Asignatura crearAsignatura(String nombre) {
        Asignatura a = new Asignatura(nombre);
        return asignaturaRepository.save(a);
    }

    public Optional<Asignatura> obtenerPorId(Long id) {
        return asignaturaRepository.findById(id);
    }

    public List<Asignatura> listarTodos() {
        return asignaturaRepository.findAll();
    }

    public Asignatura guardar(Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }
}
