package  com.practica1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica1.entity.Alumno;
import com.practica1.entity.Asignatura;
import com.practica1.repository.AlumnoRepository;
import com.practica1.repository.AsignaturaRepository;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    // Crear un alumno
    public Alumno crearAlumno(String nombre, String email) {
        Alumno alumno = new Alumno(nombre, email);
        return alumnoRepository.save(alumno);
    }

    // Listar todos los alumnos
    public List<Alumno> listarTodos() {
        return alumnoRepository.findAll();
    }

    // Obtener un alumno por id
    public Optional<Alumno> obtenerPorId(Long id) {
        return alumnoRepository.findById(id);
    }

    // Actualizar un alumno
    public Alumno actualizarAlumno(Long id, String nombre, String email) {
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(id);
        if (alumnoOpt.isPresent()) {
            Alumno alumno = alumnoOpt.get();
            alumno.setNombre(nombre);
            alumno.setEmail(email);
            return alumnoRepository.save(alumno);
        }
        return null;
    }

    // Eliminar un alumno
    public void eliminarAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }

    // Contar alumnos
    public long contarAlumnos() {
        return alumnoRepository.count();
    }

    // Guardar alumno (guardar entidad completa, útil tras asignar curso)
    public Alumno guardarAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    // Matricular alumno en asignatura usando helpers (guardar owning side)
    public void matricularAlumnoEnAsignatura(Long alumnoId, Long asignaturaId) {
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(alumnoId);
        Optional<Asignatura> asigOpt = asignaturaRepository.findById(asignaturaId);
        if (alumnoOpt.isPresent() && asigOpt.isPresent()) {
            Alumno alumno = alumnoOpt.get();
            Asignatura asignatura = asigOpt.get();
            alumno.addAsignatura(asignatura);
            alumnoRepository.save(alumno);
        }
    }

    public void desmatricularAlumnoDeAsignatura(Long alumnoId, Long asignaturaId) {
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(alumnoId);
        Optional<Asignatura> asigOpt = asignaturaRepository.findById(asignaturaId);
        if (alumnoOpt.isPresent() && asigOpt.isPresent()) {
            Alumno alumno = alumnoOpt.get();
            Asignatura asignatura = asigOpt.get();
            alumno.removeAsignatura(asignatura);
            alumnoRepository.save(alumno);
        }
    }
}
