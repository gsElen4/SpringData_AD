package com.practica1;
import com.practica1.entity.Alumno;;
import com.practica1.entity.Asignatura; 

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

import com.practica1.entity.Curso;
import com.practica1.service.AlumnoService;
import com.practica1.service.AsignaturaService;
import com.practica1.service.CursoService;

@SpringBootApplication
public class Clase {
    
    public static void main(String[] args) {
        SpringApplication.run(Clase.class, args);
    }

    @Bean
    public CommandLineRunner run(AlumnoService alumnoService, CursoService cursoService, AsignaturaService asignaturaService) {
        return args -> {
            System.out.println("\n========== PRUEBAS DE RELACIÓN MANY-TO-ONE / ONE-TO-MANY ==========\n");

            // 1. Crear cursos
            System.out.println("1. Creando cursos...");
            Curso curso1 = cursoService.crearCurso("Programación en Java");
            Curso curso2 = cursoService.crearCurso("Desarrollo Web");
            System.out.println("   ✓ " + curso1);
            System.out.println("   ✓ " + curso2);
            System.out.println();


            // 2. Crear alumnos SIN curso
            System.out.println("2. Creando alumnos sin asignar a curso...");
            Alumno alumno1 = alumnoService.crearAlumno("Juan García", "juan@example.com");
            Alumno alumno2 = alumnoService.crearAlumno("María López", "maria@example.com");
            Alumno alumno3 = alumnoService.crearAlumno("Carlos Pérez", "carlos@example.com");
            System.out.println("   ✓ " + alumno1);
            System.out.println("   ✓ " + alumno2);
            System.out.println("   ✓ " + alumno3);
            System.out.println();

            // 3. Asignar alumnos a curso usando el helper de Alumno
            System.out.println("3. Asignando alumnos al Curso 1...");
            alumno1.asignarCurso(curso1);
            alumno2.asignarCurso(curso1);
            alumnoService.guardarAlumno(alumno1);
            alumnoService.guardarAlumno(alumno2);
            System.out.println("   ✓ Alumnos asignados");
            System.out.println();


            // 4. Asignar alumno3 a curso2
            System.out.println("4. Asignando alumno 3 al Curso 2...");
            alumno3.asignarCurso(curso2);
            alumnoService.guardarAlumno(alumno3);
            System.out.println("   ✓ Alumno asignado");
            System.out.println();

            
            // 5. Verificar la relación - Obtener curso y ver sus alumnos
            System.out.println("5. Verificando relación OneToMany:");
            Optional<Curso> cursoOpt1 = cursoService.obtenerPorId(curso1.getId());
            if (cursoOpt1.isPresent()) {
                Curso cursoRecuperado = cursoOpt1.get();
                System.out.println("   Curso: " + cursoRecuperado.getNombre());
                System.out.println("   Cantidad de alumnos: " + cursoRecuperado.getAlumnos().size());
                System.out.println("   Alumnos del curso:");
                cursoRecuperado.getAlumnos().forEach(a -> 
                    System.out.println("      - " + a.getNombre() + " (" + a.getEmail() + ")")
                );
            }
            System.out.println();

            // 6. Verificar la relación inversa - Obtener alumno y ver su curso
            System.out.println("6. Verificando relación ManyToOne:");
            Optional<Alumno> alumnoOpt = alumnoService.obtenerPorId(alumno1.getId());
            if (alumnoOpt.isPresent()) {
                Alumno alumnoRecuperado = alumnoOpt.get();
                System.out.println("   Alumno: " + alumnoRecuperado.getNombre());
                if (alumnoRecuperado.getCurso() != null) {
                    System.out.println("   Curso asignado: " + alumnoRecuperado.getCurso().getNombre());
                } else {
                    System.out.println("   Sin curso asignado");
                }
            }
            System.out.println();

            // 7. Listar todos los alumnos con sus cursos
            System.out.println("7. Listando todos los alumnos con sus cursos...");
            List<Alumno> todosAlumnos = alumnoService.listarTodos();
            todosAlumnos.forEach(System.out::println);
            System.out.println();

            // 8. Listar todos los cursos
            System.out.println("8. Listando todos los cursos...");
            List<Curso> todosCursos = cursoService.listarTodos();
            todosCursos.forEach(System.out::println);

            // 9. Crear asignaturas y probar ManyToMany
            System.out.println("\n9. Creando asignaturas y probando ManyToMany...");
            Asignatura a1 = asignaturaService.crearAsignatura("Matemáticas");
            Asignatura a2 = asignaturaService.crearAsignatura("Física");
            System.out.println("   ✓ " + a1);
            System.out.println("   ✓ " + a2);
            System.out.println();

            // 10. Matricular alumno1 en Matemáticas usando helper y guardar alumno (lado propietario)
            System.out.println("10. Matriculando alumno1 en Matemáticas (guardar alumno)...");
            alumno1.addAsignatura(a1);
            alumnoService.guardarAlumno(alumno1);
            System.out.println("   Alumno tras matricular: " + alumno1);
            System.out.println();

            // 11. Intentar añadir un alumno a Asignatura y guardar la Asignatura
            System.out.println("11. Añadiendo alumno2 a Asignatura y guardando Asignatura (no propietario)...");
            a2.addAlumno(alumno2);
            asignaturaService.guardar(a2);
            System.out.println("   Asignatura tras añadir alumno: " + a2);
            System.out.println("   Alumno2 en memoria: " + alumno2);
            // Recuperar desde BD para comprobar si la relación se persistió
            Optional<Alumno> alumno2Rec = alumnoService.obtenerPorId(alumno2.getId());
            if (alumno2Rec.isPresent()) {
                System.out.println("   Alumno2 desde DB: " + alumno2Rec.get());
            }
            System.out.println();

            // 12. Matricular alumno2 en Física correctamente guardando el alumno
            System.out.println("12. Matriculando alumno2 en Física (guardar alumno)...");
            alumnoService.matricularAlumnoEnAsignatura(alumno2.getId(), a2.getId());
            Optional<Alumno> alumno2Rec2 = alumnoService.obtenerPorId(alumno2.getId());
            if (alumno2Rec2.isPresent()) {
                System.out.println("   Alumno2 desde DB tras matricular correctamente: " + alumno2Rec2.get());
            }

            System.out.println("\n========== FIN DE LAS PRUEBAS ==========\n");
        };
    }
}


