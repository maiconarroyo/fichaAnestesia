package main.java.com.avaliacao.services;
import com.avaliacao.models.Paciente;
import com.avaliacao.models.AvaliacaoAnestesica;
import com.avaliacao.models.AvaliacaoAnestesica;
import java.util.*;

public class DatabaseService {
    private Map<String, Paciente> pacientes = new HashMap<>();
    private Map<String, AvaliacaoAnestesica> avaliacoes = new HashMap<>();
    
    public void salvarPaciente(Paciente paciente) {
        if (paciente.getId() == null) {
            paciente.setId(UUID.randomUUID().toString());
        }
        pacientes.put(paciente.getId(), paciente);
    }
    
    public List<Paciente> listarPacientes() {
        return new ArrayList<>(pacientes.values());
    }
    
    public void salvarAvaliacao(AvaliacaoAnestesica avaliacao) {
        if (avaliacao.getId() == null) {
            avaliacao.setId(UUID.randomUUID().toString());
        }
        avaliacoes.put(avaliacao.getId(), avaliacao);
    }
    
    public AvaliacaoAnestesica buscarAvaliacao(String id) {
        return avaliacoes.get(id);
    }
}