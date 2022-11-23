package com.api.rest.pruebasunitariasspringboot.repository;

import com.api.rest.pruebasunitariasspringboot.model.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmpleadoRepositoryTests {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach
    void setup(){
        empleado = Empleado.builder()
                .nombre("Jhon")
                .apellido("Quitian")
                .email("jhonquitian@mail.com")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado(){
        //given - dado o condición previa o configuración
        Empleado empleado1 = Empleado.builder()
                .nombre("Pepe")
                .apellido("Lopez")
                .email("p12@gmail.com")
                .build();

        //when - acción o el comportamiento que vamos a probar
        Empleado empleadoGuardado = empleadoRepository.save(empleado1);

        //then - verificar la salida
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void testListarEmpleados(){
        //given
        Empleado empleado1 = Empleado.builder()
                .nombre("Julen")
                .apellido("Oliva")
                .email("j2@gmail.com")
                .build();
        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado);

        //when
        List<Empleado> listaEmpleados = empleadoRepository.findAll();

        //then
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void testObtenerEmpleadoPorId(){
        empleadoRepository.save(empleado);

        Empleado empleadoBD = empleadoRepository.findById(empleado.getId()).get();

        assertThat(empleadoBD).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        empleadoRepository.save(empleado);

        Empleado empleadoGuardado = empleadoRepository.findById(empleado.getId()).get();
        empleadoGuardado.setEmail("jairomontoya@mail.com");
        empleadoGuardado.setNombre("Jairo");
        empleadoGuardado.setApellido("Montoya");
        Empleado empleadoActualizado = empleadoRepository.save(empleadoGuardado);

        assertThat(empleadoActualizado.getEmail()).isEqualTo("jairomontoya@mail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Jairo");
        assertThat(empleadoActualizado.getApellido()).isEqualTo("Montoya");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){
        empleadoRepository.save(empleado);

        empleadoRepository.deleteById(empleado.getId());
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleado.getId());

        assertThat(empleadoOptional).isEmpty();
    }
}
