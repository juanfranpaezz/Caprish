package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Credential;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ClientService
        extends MyObjectGenericService<Client, ClientRepository, ClientService> {


    public ClientService(ClientRepository repo) {
        super(repo);
    }

    @Override
    protected void verifySpecificAttributes(Client entity) {}

    public Long getIdByCredentialId(Long id){
        return repository.findIdByCredential_Id(id);
    }

    public Client getByCredentialId(Long id){
        return repository.findByCredential_Id(id);
    }

    public Optional<Client> findByCredential(Credential credential) {
        return repository.findByCredential(credential);
    }
    public Client findByCredentialId(Long credentialId) {
        return repository.findByCredential_Id(credentialId);
    }


}
