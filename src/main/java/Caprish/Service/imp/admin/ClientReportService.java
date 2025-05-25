package Caprish.Service.imp.admin;

import Caprish.Model.imp.admin.ClientReport;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.admin.ClientReportRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;


@Service
public class ClientReportService extends MyObjectGenericService<ClientReport, ClientReportRepository, ClientReportService> {
    protected ClientReportService(ClientReportRepository childRepository) {
        super(childRepository);
    }

        @Override
            protected void verifySpecificAttributes(ClientReport entity) {

            }
}

