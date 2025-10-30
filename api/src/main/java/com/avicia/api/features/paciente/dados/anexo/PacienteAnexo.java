package com.avicia.api.features.paciente.dados.anexo;

import java.time.LocalDateTime;

import com.avicia.api.features.paciente.Paciente;

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
@Table(schema = "cadastro", name = "paciente_anexo")
@Getter
@Setter
@NoArgsConstructor
public class PacienteAnexo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_paciente_anexo")
    private Integer idAnexo;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "url_arquivo")
    private String urlArquivo;

    @Column(name = "data_upload")
    private LocalDateTime dataUpload;
}
