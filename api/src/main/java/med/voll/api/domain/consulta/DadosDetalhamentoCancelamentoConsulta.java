package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoCancelamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data, String motivoCancelamento) {

}
