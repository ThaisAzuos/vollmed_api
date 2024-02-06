package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private CancelamentoDeConsultas cancelamento;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var consulta = agenda.agendar(dados);
        return  ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData()));
    }

    @PutMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){

        var consulta = cancelamento.cancelar(dados);
        if (consulta == null){
            return ResponseEntity.badRequest().body("Não foi possível prosseguir com o cancelamento do agendamento!");
        }
        return ResponseEntity.ok(new DadosDetalhamentoCancelamentoConsulta(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData(), consulta.getMotivoCancelamento()));
    }
}
