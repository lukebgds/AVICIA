package com.avicia.api.data.model;

import java.util.List;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "seguranca", name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @Column(name = "id_role")
    private Integer idRole;

    @Column(name = "nome", nullable = false)
    private String nome;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "permissoes", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> permissoes;

    // Pode ser que não seja TEXT
    @Column(name = "descricao", columnDefinition = "text")
    private String descricao; 

    @OneToMany(mappedBy = "idRole")
    private List<Usuario> usuarios;

}
