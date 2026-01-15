package br.com.saasbarber.repository;

import br.com.saasbarber.domain.model.Barbearia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BarbeariaRepository implements PanacheRepository<Barbearia> {
}
