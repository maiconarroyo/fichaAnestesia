package main.java.com.avaliacao.models;
import java.util.Objects;
import java.util.UUID;

/**
 * Classe que representa uma avaliação anestésica de um paciente
 */
public class AvaliacaoAnestesica {
    private String id;
    private String pacienteId;
    private String asa; // Classificação ASA (I a VI)
    private String mallampati; // Classificação de Mallampati (I a IV)
    private String viaAerea;
    private String examesComplementares;
    private String observacoes;
    private String anestesistaResponsavel;
    
    // Construtor padrão
    public AvaliacaoAnestesica() {
        this.id = UUID.randomUUID().toString();
    }
    
    // Construtor com campos obrigatórios
    public AvaliacaoAnestesica(String pacienteId, String asa, String mallampati, String anestesistaResponsavel) {
        this();
        this.pacienteId = Objects.requireNonNull(pacienteId, "ID do paciente não pode ser nulo");
        this.asa = validarClassificacaoASA(asa);
        this.mallampati = validarMallampati(mallampati);
        this.anestesistaResponsavel = Objects.requireNonNull(anestesistaResponsavel, "Anestesista responsável não pode ser nulo");
    }
    
    // Métodos de validação
    private String validarClassificacaoASA(String asa) {
        if (asa == null || !asa.matches("ASA [IVI]+")) {
            throw new IllegalArgumentException("Classificação ASA inválida. Deve ser ASA I, ASA II, ASA III, ASA IV, ASA V ou ASA VI");
        }
        return asa;
    }
    
    private String validarMallampati(String mallampati) {
        if (mallampati == null || !mallampati.matches("Classe [I-IV]+")) {
            throw new IllegalArgumentException("Classificação de Mallampati inválida. Deve ser Classe I, Classe II, Classe III ou Classe IV");
        }
        return mallampati;
    }
    
    // Getters e Setters
    public String getId() {
        return id;
    }
    
    public String getPacienteId() {
        return pacienteId;
    }
    
    public void setPacienteId(String pacienteId) {
        this.pacienteId = Objects.requireNonNull(pacienteId);
    }
    
    public String getAsa() {
        return asa;
    }
    
    public void setAsa(String asa) {
        this.asa = validarClassificacaoASA(asa);
    }
    
    public String getMallampati() {
        return mallampati;
    }
    
    public void setMallampati(String mallampati) {
        this.mallampati = validarMallampati(mallampati);
    }
    
    public String getViaAerea() {
        return viaAerea;
    }
    
    public void setViaAerea(String viaAerea) {
        this.viaAerea = viaAerea;
    }
    
    public String getExamesComplementares() {
        return examesComplementares;
    }
    
    public void setExamesComplementares(String examesComplementares) {
        this.examesComplementares = examesComplementares;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public String getAnestesistaResponsavel() {
        return anestesistaResponsavel;
    }
    
    public void setAnestesistaResponsavel(String anestesistaResponsavel) {
        this.anestesistaResponsavel = Objects.requireNonNull(anestesistaResponsavel);
    }
    
    // Métodos utilitários
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvaliacaoAnestesica that = (AvaliacaoAnestesica) o;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "AvaliacaoAnestesica{" +
                "id='" + id + '\'' +
                ", pacienteId='" + pacienteId + '\'' +
                ", asa='" + asa + '\'' +
                ", mallampati='" + mallampati + '\'' +
                ", anestesistaResponsavel='" + anestesistaResponsavel + '\'' +
                '}';
    }
}