package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Service
public class CancelamentoDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    public Consulta cancelar(DadosCancelamentoConsulta dados) {

        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informada não existe!");
        }
        if (dados.motivoCancelamento() == null) {
            throw new ValidacaoException("Favor Informar o motivo do cancelamento:  DESISTENCIA_PACIENTE,\n" +
                    "    CANCELAMENTO_MEDICO,\n" +
                    "    OUTROS");
        }
        var confirmacao = verificarAntecedencia(dados);
        if (confirmacao == 0){
            return null;
        }
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        return consulta;


    }

    private int verificarAntecedencia(DadosCancelamentoConsulta dados) {
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.add(Calendar.HOUR, 24);
        LocalDateTime dataVerificacao = LocalDateTime.ofInstant(dataCalendar.toInstant(), ZoneId.systemDefault());
        var confirmacao = consultaRepository.cancelar24HorasAntes(dados.idConsulta(), dados.motivoCancelamento(), dataVerificacao);
        return  confirmacao;

    }
}
