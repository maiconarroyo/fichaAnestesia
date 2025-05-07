document.addEventListener('DOMContentLoaded', function() {
    // Elementos da interface
    const btnPaciente = document.getElementById('btn-paciente');
    const btnMedico = document.getElementById('btn-medico');
    const contentDiv = document.getElementById('content');
    
    // Event listeners para os botões de navegação
    btnPaciente.addEventListener('click', () => loadPacienteForm());
    btnMedico.addEventListener('click', () => loadMedicoDashboard());
    
    // Carregar formulário do paciente por padrão
    loadPacienteForm();
    
    function loadPacienteForm() {
        contentDiv.innerHTML = `
            <h2>Formulário do Paciente</h2>
            <form id="form-paciente">
                <div class="form-group">
                    <label for="nome">Nome Completo:</label>
                    <input type="text" id="nome" required>
                </div>
                
                <div class="form-group">
                    <label for="nomeSocial">Nome Social (se aplicável):</label>
                    <input type="text" id="nomeSocial">
                </div>
                
                <div class="form-group">
                    <label for="nomeMae">Nome da Mãe:</label>
                    <input type="text" id="nomeMae" required>
                </div>
                
                <div class="form-group">
                    <label for="dataNascimento">Data de Nascimento:</label>
                    <input type="date" id="dataNascimento" required>
                </div>
                
                <div class="form-group">
                    <label for="sexo">Sexo:</label>
                    <select id="sexo" required>
                        <option value="">Selecione</option>
                        <option value="Masculino">Masculino</option>
                        <option value="Feminino">Feminino</option>
                        <option value="Outro">Outro</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="peso">Peso (kg):</label>
                    <input type="number" id="peso" step="0.1" required>
                </div>
                
                <div class="form-group">
                    <label for="altura">Altura (cm):</label>
                    <input type="number" id="altura" required>
                </div>
                
                <div class="form-group">
                    <label>Tem algum problema de saúde?</label>
                    <div>
                        <input type="radio" name="problemaSaude" id="problemaSaudeSim" value="sim">
                        <label for="problemaSaudeSim" style="display: inline;">Sim</label>
                        <input type="radio" name="problemaSaude" id="problemaSaudeNao" value="nao" checked>
                        <label for="problemaSaudeNao" style="display: inline;">Não</label>
                    </div>
                    <div id="problemasSaudeContainer" class="dynamic-field hidden">
                        <label for="problemasSaude">Quais problemas de saúde?</label>
                        <textarea id="problemasSaude" rows="3"></textarea>
                    </div>
                </div>
                
                <div class="form-group">
                    <label>Tem alergia a algum medicamento?</label>
                    <div>
                        <input type="radio" name="alergia" id="alergiaSim" value="sim">
                        <label for="alergiaSim" style="display: inline;">Sim</label>
                        <input type="radio" name="alergia" id="alergiaNao" value="nao" checked>
                        <label for="alergiaNao" style="display: inline;">Não</label>
                    </div>
                    <div id="alergiasContainer" class="dynamic-field hidden">
                        <label for="alergias">Quais alergias?</label>
                        <textarea id="alergias" rows="3"></textarea>
                    </div>
                </div>
                
                <div class="form-group">
                    <label>Já fez alguma cirurgia anteriormente?</label>
                    <div>
                        <input type="radio" name="antecedenteCirurgico" id="antecedenteSim" value="sim">
                        <label for="antecedenteSim" style="display: inline;">Sim</label>
                        <input type="radio" name="antecedenteCirurgico" id="antecedenteNao" value="nao" checked>
                        <label for="antecedenteNao" style="display: inline;">Não</label>
                    </div>
                    <div id="antecedentesContainer" class="dynamic-field hidden">
                        <label for="antecedentesCirurgicos">Quais cirurgias?</label>
                        <textarea id="antecedentesCirurgicos" rows="3"></textarea>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="cirurgiaoResponsavel">Cirurgião Responsável:</label>
                    <input type="text" id="cirurgiaoResponsavel" required>
                </div>
                
                <div class="form-group">
                    <label for="cirurgiaProposta">Cirurgia Proposta:</label>
                    <input type="text" id="cirurgiaProposta" required>
                </div>
                
                <button type="submit">Enviar Dados</button>
            </form>
        `;
        
        // Adicionar eventos para campos dinâmicos
        document.getElementById('problemaSaudeSim').addEventListener('change', function() {
            document.getElementById('problemasSaudeContainer').classList.toggle('hidden', !this.checked);
        });
        
        document.getElementById('alergiaSim').addEventListener('change', function() {
            document.getElementById('alergiasContainer').classList.toggle('hidden', !this.checked);
        });
        
        document.getElementById('antecedenteSim').addEventListener('change', function() {
            document.getElementById('antecedentesContainer').classList.toggle('hidden', !this.checked);
        });
        
        // Enviar formulário
        document.getElementById('form-paciente').addEventListener('submit', function(e) {
            e.preventDefault();
            submitPacienteForm();
        });
    }
    
    function loadMedicoDashboard() {
        // Buscar lista de pacientes do servidor
        fetch('http://localhost:4567/pacientes')
            .then(response => response.json())
            .then(pacientes => {
                let html = `
                    <h2>Área do Médico Anestesista</h2>
                    <div class="pacientes-list">
                        <h3>Pacientes para Avaliação</h3>
                `;
                
                if (pacientes.length === 0) {
                    html += `<p>Nenhum paciente aguardando avaliação.</p>`;
                } else {
                    html += `<ul>`;
                    pacientes.forEach(paciente => {
                        html += `
                            <li>
                                <strong>${paciente.nome}</strong> - Cirurgião: ${paciente.cirurgiaoResponsavel}
                                <button onclick="loadAvaliacaoForm('${paciente.id}')">Avaliar</button>
                            </li>
                        `;
                    });
                    html += `</ul>`;
                }
                
                html += `</div>`;
                contentDiv.innerHTML = html;
            })
            .catch(error => {
                console.error('Erro ao carregar pacientes:', error);
                contentDiv.innerHTML = `<p>Erro ao carregar a lista de pacientes. Tente novamente.</p>`;
            });
    }
    
    // Função global para ser chamada pelo onclick
    window.loadAvaliacaoForm = function(pacienteId) {
        fetch(`http://localhost:4567/pacientes/${pacienteId}`)
            .then(response => response.json())
            .then(paciente => {
                contentDiv.innerHTML = `
                    <h2>Avaliação Anestésica</h2>
                    <div class="paciente-info">
                        <h3>Dados do Paciente</h3>
                        <p><strong>Nome:</strong> ${paciente.nome}</p>
                        <p><strong>Idade:</strong> ${paciente.idade}</p>
                        <p><strong>Peso:</strong> ${paciente.peso} kg</p>
                        <p><strong>Altura:</strong> ${paciente.altura} cm</p>
                        ${paciente.temProblemaSaude ? `<p><strong>Problemas de saúde:</strong> ${paciente.problemasSaude}</p>` : ''}
                        ${paciente.temAlergia ? `<p><strong>Alergias:</strong> ${paciente.alergias}</p>` : ''}
                        ${paciente.temAntecedenteCirurgico ? `<p><strong>Antecedentes cirúrgicos:</strong> ${paciente.antecedentesCirurgicos}</p>` : ''}
                        <p><strong>Cirurgião:</strong> ${paciente.cirurgiaoResponsavel}</p>
                        <p><strong>Cirurgia proposta:</strong> ${paciente.cirurgiaProposta}</p>
                    </div>
                    
                    <form id="form-avaliacao">
                        <input type="hidden" id="pacienteId" value="${paciente.id}">
                        
                        <div class="form-group">
                            <label for="asa">Classificação ASA:</label>
                            <select id="asa" required>
                                <option value="">Selecione</option>
                                <option value="ASA I">ASA I - Paciente saudável</option>
                                <option value="ASA II">ASA II - Doença sistêmica leve</option>
                                <option value="ASA III">ASA III - Doença sistêmica grave</option>
                                <option value="ASA IV">ASA IV - Doença sistêmica grave e constante ameaça à vida</option>
                                <option value="ASA V">ASA V - Paciente moribundo</option>
                                <option value="ASA VI">ASA VI - Paciente com morte cerebral</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="mallampati">Classificação de Mallampati:</label>
                            <select id="mallampati" required>
                                <option value="">Selecione</option>
                                <option value="Classe I">Classe I</option>
                                <option value="Classe II">Classe II</option>
                                <option value="Classe III">Classe III</option>
                                <option value="Classe IV">Classe IV</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="viaAerea">Via Aérea:</label>
                            <textarea id="viaAerea" rows="3" required></textarea>
                        </div>
                        
                        <div class="form-group">
                            <label for="examesComplementares">Exames Complementares:</label>
                            <textarea id="examesComplementares" rows="3"></textarea>
                        </div>
                        
                        <div class="form-group">
                            <label for="observacoes">Observações:</label>
                            <textarea id="observacoes" rows="3"></textarea>
                        </div>
                        
                        <div class="form-group">
                            <label for="anestesistaResponsavel">Anestesista Responsável:</label>
                            <input type="text" id="anestesistaResponsavel" required>
                        </div>
                        
                        <button type="submit">Salvar Avaliação</button>
                        <button type="button" id="btn-gerar-pdf">Gerar PDF</button>
                    </form>
                `;
                
                // Enviar formulário de avaliação
                document.getElementById('form-avaliacao').addEventListener('submit', function(e) {
                    e.preventDefault();
                    submitAvaliacaoForm();
                });
                
                // Gerar PDF
                document.getElementById('btn-gerar-pdf').addEventListener('click', function() {
                    const pacienteId = document.getElementById('pacienteId').value;
                    window.open(`http://localhost:4567/gerar-pdf/${pacienteId}`, '_blank');
                });
            })
            .catch(error => {
                console.error('Erro ao carregar paciente:', error);
                contentDiv.innerHTML = `<p>Erro ao carregar dados do paciente. Tente novamente.</p>`;
            });
    }
    
    function submitPacienteForm() {
        const formData = {
            nome: document.getElementById('nome').value,
            nomeSocial: document.getElementById('nomeSocial').value,
            nomeMae: document.getElementById('nomeMae').value,
            dataNascimento: document.getElementById('dataNascimento').value,
            sexo: document.getElementById('sexo').value,
            peso: parseFloat(document.getElementById('peso').value),
            altura: parseInt(document.getElementById('altura').value),
            temProblemaSaude: document.getElementById('problemaSaudeSim').checked,
            problemasSaude: document.getElementById('problemaSaudeSim').checked ? 
                           document.getElementById('problemasSaude').value : null,
            temAlergia: document.getElementById('alergiaSim').checked,
            alergias: document.getElementById('alergiaSim').checked ? 
                     document.getElementById('alergias').value : null,
            temAntecedenteCirurgico: document.getElementById('antecedenteSim').checked,
            antecedentesCirurgicos: document.getElementById('antecedenteSim').checked ? 
                                  document.getElementById('antecedentesCirurgicos').value : null,
            cirurgiaoResponsavel: document.getElementById('cirurgiaoResponsavel').value,
            cirurgiaProposta: document.getElementById('cirurgiaProposta').value
        };
        
        // Calcular idade a partir da data de nascimento
        if (formData.dataNascimento) {
            const nascimento = new Date(formData.dataNascimento);
            const hoje = new Date();
            let idade = hoje.getFullYear() - nascimento.getFullYear();
            const mes = hoje.getMonth() - nascimento.getMonth();
            if (mes < 0 || (mes === 0 && hoje.getDate() < nascimento.getDate())) {
                idade--;
            }
            formData.idade = idade;
        }
        
        fetch('http://localhost:4567/pacientes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            alert('Dados do paciente salvos com sucesso!');
            loadMedicoDashboard(); // Redirecionar para a área do médico
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao salvar dados do paciente.');
        });
    }
    
    function submitAvaliacaoForm() {
        const formData = {
            pacienteId: document.getElementById('pacienteId').value,
            asa: document.getElementById('asa').value,
            mallampati: document.getElementById('mallampati').value,
            viaAerea: document.getElementById('viaAerea').value,
            examesComplementares: document.getElementById('examesComplementares').value,
            observacoes: document.getElementById('observacoes').value,
            anestesistaResponsavel: document.getElementById('anestesistaResponsavel').value
        };
        
        fetch('http://localhost:4567/avaliacoes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            alert('Avaliação anestésica salva com sucesso!');
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao salvar avaliação anestésica.');
        });
    }
});