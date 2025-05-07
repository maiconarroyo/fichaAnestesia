package main.java.com.avaliacao;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.avaliacao.models.Paciente;
import com.avaliacao.models.AvaliacaoAnestesica;
import com.avaliacao.services.PdfService;
import com.avaliacao.services.DatabaseService;

public class Main {
    public static void main(String[] args) {
        // Configurar porta (opcional)
        port(4567);
        
        // Habilitar CORS
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "GET,POST,PUT,DELETE");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });
        
        Gson gson = new Gson();
        DatabaseService dbService = new DatabaseService();
        PdfService pdfService = new PdfService();
        
        // Rotas para pacientes
        post("/pacientes", (req, res) -> {
            Paciente paciente = gson.fromJson(req.body(), Paciente.class);
            dbService.salvarPaciente(paciente);
            return gson.toJson(paciente);
        });
        
        get("/pacientes", (req, res) -> {
            return gson.toJson(dbService.listarPacientes());
        });
        
        // Rotas para avaliação anestésica
        post("/avaliacoes", (req, res) -> {
            AvaliacaoAnestesica avaliacao = gson.fromJson(req.body(), AvaliacaoAnestesica.class);
            dbService.salvarAvaliacao(avaliacao);
            return gson.toJson(avaliacao);
        });
        
        get("/avaliacoes/:id", (req, res) -> {
            String id = req.params(":id");
            return gson.toJson(dbService.buscarAvaliacao(id));
        });
        
        // Rota para gerar PDF
        get("/gerar-pdf/:id", (req, res) -> {
            String id = req.params(":id");
            AvaliacaoAnestesica avaliacao = dbService.buscarAvaliacao(id);
            byte[] pdf = pdfService.gerarPdf(avaliacao);
            
            res.type("application/pdf");
            res.header("Content-Disposition", "attachment; filename=avaliacao_" + id + ".pdf");
            return pdf;
        });
    }
}