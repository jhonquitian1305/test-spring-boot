package com.api.rest.pruebasunitariasspringboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.api.rest.pruebasunitariasspringboot.exception.ResourceNotFoundException;
import com.api.rest.pruebasunitariasspringboot.model.Empleado;
import com.api.rest.pruebasunitariasspringboot.repository.EmpleadoRepository;
import com.api.rest.pruebasunitariasspringboot.service.impl.EmpleadoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

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
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.empty());
        given(empleadoRepository.save(empleado)).willReturn(empleado);

        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);

        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para guardar un empleado con Throw Exception")
    @Test
    void testGuardarEmpleadoConThrowException(){
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.of(empleado));

        assertThrows(ResourceNotFoundException.class, () -> {
            empleadoService.saveEmpleado(empleado);
        });

        verify(empleadoRepository, never()).save(any(Empleado.class));
    }

    @DisplayName("Test para listar los empleados")
    @Test
    void testListarEmpleados(){
        Empleado empleado1 = Empleado.builder()
                .id(1L)
                .nombre("Julen")
                .apellido("Oliva")
                .email("j2@gmail.com")
                .build();

        given(empleadoRepository.findAll()).willReturn(List.of(empleado, empleado1));

        List<Empleado> empleados = empleadoService.getAllEmpleados();

        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }
}
