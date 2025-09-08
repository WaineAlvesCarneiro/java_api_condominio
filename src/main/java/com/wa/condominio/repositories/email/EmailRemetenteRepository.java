package com.wa.condominio.repositories.email;

import com.wa.condominio.model.email.EmailRemetente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRemetenteRepository extends JpaRepository<EmailRemetente, Long> {
}