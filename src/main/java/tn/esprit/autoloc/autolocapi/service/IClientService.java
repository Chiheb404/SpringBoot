package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.Client;

public interface IClientService {

    Client getById(Long id);

    List<Client> getAll();

    Client add(Client client);

    Client update(Long id, Client client);

    void delete(Long id);

    default Client findById(Long id) {
        return getById(id);
    }

    default List<Client> findAll() {
        return getAll();
    }

    default Client create(Client client) {
        return add(client);
    }

    default void deleteById(Long id) {
        delete(id);
    }
}
