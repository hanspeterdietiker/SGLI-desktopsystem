package org.desktop.system.sgli.sgli.Config;

import org.desktop.system.sgli.sgli.Controller.HubViewController;
import org.desktop.system.sgli.sgli.Repository.ContractRepository;
import org.desktop.system.sgli.sgli.Repository.PaymentRepository;
import org.desktop.system.sgli.sgli.Services.ContractService;
import org.desktop.system.sgli.sgli.Services.PaymentService;

public class InjectionRoot {

    public static HubViewController hubViewController() {
        return new HubViewController(
                new ContractService(new ContractRepository()),
                new PaymentService(new PaymentRepository())
        );
    }
}
