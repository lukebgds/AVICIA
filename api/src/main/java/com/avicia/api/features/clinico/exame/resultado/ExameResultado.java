package com.avicia.api.features.clinico.exame.resultado;

import java.time.LocalDate;
import com.avicia.api.features.clinico.exame.solicitado.ExameSolicitado;
import com.avicia.api.features.profissional.ProfissionalSaude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "clinico", name = "resultado_exame")
@Getter
@Setter
@NoArgsConstructor
public class ExameResultado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_resultado_exame")
    private Integer idResultado;

    @ManyToOne
    @JoinColumn(name = "id_exame_solicitado")
    private ExameSolicitado exameSolicitado;

    @Column(name = "data_resultado")
    private LocalDate dataResultado;

    @Column(name = "laudo")
    private String laudo;

    @Column(name = "arquivo_resultado")
    private String arquivoResultado;

    @Column(name = "observacoes")
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "assinado_por")
    private ProfissionalSaude assinadoPor;

    @Column(name = "status")
    private String status;

}
