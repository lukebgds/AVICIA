package com.avicia.api.configuration;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import com.avicia.api.model.Role;
import com.avicia.api.model.Usuario;
import com.avicia.api.repository.RoleRepository;
import com.avicia.api.repository.UsuarioRepository;

@Configuration
public class AdminInitializerConfiguration {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public CommandLineRunner criarSystemAdmin(UsuarioRepository usuarioRepository) {
        return args -> {

            String adminNome = "system.admin";
            boolean exists = usuarioRepository.findByIdUsuario(101000001).isPresent();

            if (!exists) {

                Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(
                    16,   // saltLength
                    32,   // hashLength
                    1,    // parallelism
                    65536, // memory (KB)
                    3     // iterations
                );

                String senhaHash = encoder.encode("Avicia@2025");
                Usuario admin = new Usuario();

                admin.setIdUsuario(101000001);
                
                Role role = roleRepository.findById(101)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));
                admin.setIdRole(role);
                
                admin.setNome(adminNome);
                admin.setCpf("00000000000");
                admin.setDataNascimento(LocalDate.of(2025, 9, 25));
                admin.setSexo("N/A");
                admin.setEstadoCivil("N/A");
                admin.setEmail("admin@avicia.local");
                admin.setTelefone("000000000");
                admin.setEndereco("N/A");
                admin.setSenhaHash(senhaHash);
                admin.setDataCriacao(LocalDate.now());
                admin.setMfaHabilitado(false);
                admin.setAtivo(true);

                usuarioRepository.save(admin);

                System.out.println(" ======= Usuário system.admin criado com sucesso! ======= ");

            } else {

                System.out.println(" ======= Usuário system.admin já existe, ignorando criação. ====== ");
            }
        };
    }

}
