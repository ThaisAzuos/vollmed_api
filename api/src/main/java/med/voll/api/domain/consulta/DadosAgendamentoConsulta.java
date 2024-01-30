package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.paciente.DadosCadastroPaciente;

import java.time.LocalDateTime;
import java.util.Date;

public record DadosAgendamentoConsulta(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade) {
}
