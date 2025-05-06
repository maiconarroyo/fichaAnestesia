package com.seuapp.controller;

import com.seuapp.dto.PacienteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class PacienteController {

    private final List<PacienteDTO> pacientes = new ArrayList<>();

    @PostMapping("/salvar-paciente")
    public ResponseEntity<Void> salvarPaciente(@RequestBody PacienteDTO paciente) {
        pacientes.add(paciente); // Em produção, salve no banco de dados
        System.out.println("Paciente salvo: " + paciente.getNome());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pacientes")
    public List<PacienteDTO> listarPacientes() {
        return pacientes;
    }

    @GetMapping("/pacientes/{nome}")
    public ResponseEntity<PacienteDTO> buscarPorNome(@PathVariable String nome) {
        return pacientes.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
