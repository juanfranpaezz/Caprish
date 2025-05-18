package Caprish.Service.imp.admin;

import Caprish.Model.imp.admin.ClientReport;
import Caprish.Repository.interfaces.admin.ClientReportRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;


@Service
public class ClientReportService extends MyObjectGenericService<ClientReport> {
    protected ClientReportService(ClientReportRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<ClientReport> getEntityClass() {
        return ClientReport.class;
    }
}