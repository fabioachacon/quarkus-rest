package api.persistence.repositories;

import javax.enterprise.context.ApplicationScoped;

import api.persistence.entities.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

}
