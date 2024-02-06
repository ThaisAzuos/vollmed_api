package med.voll.api.domain.consulta;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Modifying
    @Query("""
            update Consulta c
            set
            c.ativa = false,
            c.motivoCancelamento = :motivoCancelamento
            where
            c.id = :idConsulta
            and
            c.data > :dataVerificacao
            """)
    int cancelar24HorasAntes(Long idConsulta, String motivoCancelamento, LocalDateTime dataVerificacao);

     Boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
