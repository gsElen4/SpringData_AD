package com.practica1.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "alumno_asignatura",
            joinColumns = @JoinColumn(name = "alumno_id"),
            inverseJoinColumns = @JoinColumn(name = "asignatura_id"))
    private Set<Asignatura> asignaturas = new HashSet<>();

    // Constructores
    public Alumno() {
    }

    public Alumno(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public Alumno(String nombre, String email, Curso curso) {
        this.nombre = nombre;
        this.email = email;
        this.curso = curso;
    }

    // Getters y Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    // Helper para asignar un curso (mantiene sincronización)
    public void asignarCurso(Curso curso) {
        if (this.curso != null && !this.curso.equals(curso)) {
            this.curso.getAlumnos().remove(this);
        }
        this.curso = curso;
        if (curso != null && !curso.getAlumnos().contains(this)) {
            curso.getAlumnos().add(this);
        }
    }

    // Helper para remover el curso
    public void eliminarCurso() {
        if (this.curso != null) {
            this.curso.getAlumnos().remove(this);
            this.curso = null;
        }
    }

    // Helpers para asignaturas ManyToMany
    public Set<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(Set<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public void addAsignatura(Asignatura asignatura) {
        if (asignaturas.add(asignatura)) {
            asignatura.getAlumnos().add(this);
        }
    }

    public void removeAsignatura(Asignatura asignatura) {
        if (asignaturas.remove(asignatura)) {
            asignatura.getAlumnos().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", curso=" + (curso != null ? curso.getNombre() : "Sin curso") +
                ", asignaturas=" + asignaturas.size() +
                '}';
    }
}

