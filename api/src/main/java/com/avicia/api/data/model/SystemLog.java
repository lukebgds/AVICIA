package com.avicia.api.data.model;

import java.time.LocalDateTime;

import com.avicia.api.data.enumerate.TipoSystemLog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(schema = "seguranca", name = "log_auditoria")
@Getter
@Setter
@NoArgsConstructor
public class SystemLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_log", nullable = false)
    private Integer idLog;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_log")
    private TipoSystemLog tipoLog;

    @Column(name = "acao")
    private String acao;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "entidade_afetada")
    private String entidadeAfetada;

    @Column(name = "detalhes")
    private String detalhes;

}
