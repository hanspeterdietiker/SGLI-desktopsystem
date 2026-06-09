package org.desktop.system.sgli.sgli.Services;

import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Repository.ContractRepository;
import org.desktop.system.sgli.sgli.Utils.CpfUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LgpdAuditService {

    private final ContractRepository contractRepository;

    public LgpdAuditService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public AuditReport runAudit() {
        List<ContractModel> all = contractRepository.findAll();

        List<String> invalidCpfContracts = new ArrayList<>();
        int expiredCount = 0;
        long oldestDaysRetained = 0;

        for (ContractModel c : all) {
            if (!CpfUtils.isValid(c.getCpfLocatario()) || !CpfUtils.isValid(c.getCpfLocador())) {
                invalidCpfContracts.add(c.getId().toString());
            }
            if (c.getDateEnd() != null && c.getDateEnd().isBefore(LocalDate.now())) {
                expiredCount++;
                long days = ChronoUnit.DAYS.between(c.getDateEnd(), LocalDate.now());
                if (days > oldestDaysRetained) oldestDaysRetained = days;
            }
        }

        return new AuditReport(
            all.size(),
            invalidCpfContracts.size(),
            invalidCpfContracts,
            expiredCount,
            oldestDaysRetained
        );
    }

    public record AuditReport(
        int totalContracts,
        int invalidCpfCount,
        List<String> invalidCpfContracts,
        int expiredContractCount,
        long oldestExpiredDaysRetained
    ) {}
}
