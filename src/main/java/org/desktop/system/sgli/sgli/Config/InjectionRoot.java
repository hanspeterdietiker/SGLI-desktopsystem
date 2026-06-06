package org.desktop.system.sgli.sgli.Config;

import org.desktop.system.sgli.sgli.Controller.HubViewController;
import org.desktop.system.sgli.sgli.Repository.ContractRepository;
import org.desktop.system.sgli.sgli.Repository.PaymentRepository;
import org.desktop.system.sgli.sgli.Services.ContractService;
import org.desktop.system.sgli.sgli.Services.LgpdAuditService;
import org.desktop.system.sgli.sgli.Services.LgpdExportService;
import org.desktop.system.sgli.sgli.Services.PaymentService;

public class InjectionRoot {

    public static HubViewController hubViewController() {
        return new HubViewController(
                new ContractService(new ContractRepository()),
                new PaymentService(new PaymentRepository()),
                new LgpdAuditService(new ContractRepository()),
                new LgpdExportService(new ContractRepository())
        );
    }
}
