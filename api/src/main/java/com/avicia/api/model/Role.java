package com.avicia.api.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "seguranca", name = "role")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer idRole;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "permissoes", columnDefinition = "jsonb", nullable = false)
    private String permissoes;

    // Pode ser que não seja TEXT
    @Column(name = "descricao", columnDefinition = "text")
    private String descricao; 

    @OneToMany(mappedBy = "idRole")
    private List<Usuario> usuarios;

}
