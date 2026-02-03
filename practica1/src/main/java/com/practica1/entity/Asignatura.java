package com.practica1.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany(mappedBy = "asignaturas", fetch = FetchType.EAGER)
    private Set<Alumno> alumnos = new HashSet<>();

    public Asignatura() {
    }

    public Asignatura(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Set<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    // Helpers para mantener la relación sincronizada
    public void addAlumno(Alumno alumno) {
        if (alumnos.add(alumno)) {
            alumno.getAsignaturas().add(this);
        }
    }

    public void removeAlumno(Alumno alumno) {
        if (alumnos.remove(alumno)) {
            alumno.getAsignaturas().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", alumnos=" + alumnos.size() +
                '}';
    }
}
