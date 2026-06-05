package org.desktop.system.sgli.sgli.Services;

import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Repository.ContractRepository;
import org.desktop.system.sgli.sgli.Utils.LgpdAuditLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LgpdExportService {

    private final ContractRepository contractRepository;

    public LgpdExportService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Path exportPersonalDataJson() throws IOException {
        List<ContractModel> contracts = contractRepository.findAll();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path dest = Paths.get(System.getProperty("user.home"), "Downloads",
                              "SGLI_DadosPessoais_" + timestamp + ".json");

        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < contracts.size(); i++) {
            ContractModel c = contracts.get(i);
            sb.append("  {\n")
              .append("    \"id\": \"").append(c.getId()).append("\",\n")
              .append("    \"nomeLocador\": \"").append(escape(c.getNameLocador())).append("\",\n")
              .append("    \"cpfLocador\": \"").append(escape(c.getCpfLocador())).append("\",\n")
              .append("    \"nomeLocatario\": \"").append(escape(c.getNameLocatario())).append("\",\n")
              .append("    \"cpfLocatario\": \"").append(escape(c.getCpfLocatario())).append("\",\n")
              .append("    \"dataInicio\": \"").append(c.getDateInit()).append("\",\n")
              .append("    \"dataFim\": \"").append(c.getDateEnd()).append("\"\n")
              .append("  }").append(i < contracts.size() - 1 ? "," : "").append("\n");
        }
        sb.append("]");

        Files.writeString(dest, sb.toString());
        LgpdAuditLogger.logExport("DadosPessoais_JSON", contracts.size());
        return dest;
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
