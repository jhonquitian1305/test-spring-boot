package com.api.rest.pruebasunitariasspringboot.controller;

import com.api.rest.pruebasunitariasspringboot.model.Empleado;
import com.api.rest.pruebasunitariasspringboot.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado guardarEmpleado(@RequestBody Empleado empleado){
        return empleadoService.saveEmpleado(empleado);
    }

    @GetMapping
    public List<Empleado> listarEmpleados(){
        return empleadoService.getAllEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable("id") Long empleadoId){
        return empleadoService.getEmpleadoById(empleadoId)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable("id") Long empleadoId, @RequestBody Empleado empleado){
        return empleadoService.getEmpleadoById(empleadoId)
                .map(empleadoGuardado -> {
                    empleadoGuardado.setNombre(empleado.getNombre());
                    empleadoGuardado.setApellido(empleado.getApellido());
                    empleadoGuardado.setEmail(empleado.getEmail());

                    Empleado empleadoActualizado = empleadoService.updateEmpleado(empleadoGuardado);
                    return new ResponseEntity<>(empleadoActualizado, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable("id") Long empleadoId){
        empleadoService.deleteEmpleado(empleadoId);
        return new ResponseEntity<String>("Empleado eliminado exitosamente", HttpStatus.OK);
    }
}
