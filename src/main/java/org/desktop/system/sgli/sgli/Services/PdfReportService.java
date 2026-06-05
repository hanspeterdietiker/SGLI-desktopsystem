package org.desktop.system.sgli.sgli.Services;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;
import org.desktop.system.sgli.sgli.Utils.AlertAction;
import org.desktop.system.sgli.sgli.Utils.CpfUtils;
import org.desktop.system.sgli.sgli.Utils.DateFormatterUtils;
import org.desktop.system.sgli.sgli.Utils.DecimalFormatterUtils;
import org.desktop.system.sgli.sgli.Utils.LgpdAuditLoggerUtils;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class PdfReportService {


    private PdfReportService() {
        throw new UnsupportedOperationException("Esta classe é de serviço e não deve ser instanciada");
    }

    public static void exportContractsReport(List<ContractModel> contractsList) {
        Document doc = new Document();
        try {
            String downloadsPath = System.getProperty("user.home") + "\\Downloads\\Relatorio_Contratos.pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(downloadsPath));
            doc.open();

            Paragraph title = new Paragraph("Relatório de Contratos");
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph("\n"));

            Paragraph dataCreated = new Paragraph("Data de Criação: " + LocalDate.now().format(DateFormatterUtils.dateFormatter));
            dataCreated.setAlignment(Element.ALIGN_RIGHT);
            doc.add(dataCreated);
            doc.add(new Paragraph("\n"));

            if (contractsList.isEmpty()) {
                doc.add(new Paragraph("Nenhum contrato registrado."));
            } else {
                for (ContractModel contract : contractsList) {
                    doc.add(new Paragraph("Nome Locador: " + contract.getNameLocador()));
                    doc.add(new Paragraph("CPF Locador: " + CpfUtils.mask(contract.getCpfLocador())));
                    doc.add(new Paragraph("Nome Locatario: " + contract.getNameLocatario()));
                    doc.add(new Paragraph("CPF Locatário: " + CpfUtils.mask(contract.getCpfLocatario())));
                    doc.add(new Paragraph("Tipo do Contrato: " + contract.getContractType().getLabel()));
                    doc.add(new Paragraph("Valor Condominio R$ " + DecimalFormatterUtils.decimalFormat.format(contract.getValorCond())));
                    doc.add(new Paragraph("Valor Aluguel R$ " + DecimalFormatterUtils.decimalFormat.format(contract.getValorAlug())));
                    doc.add(new Paragraph("Valor Iptu R$ " + DecimalFormatterUtils.decimalFormat.format(contract.getValorIptu())));
                    doc.add(new Paragraph("Data Início: " + contract.getDateInit().format(DateFormatterUtils.dateFormatter)));
                    doc.add(new Paragraph("Data Fim: " + contract.getDateEnd().format(DateFormatterUtils.dateFormatter)));
                    doc.add(new Paragraph("\n"));
                }
            }

            LgpdAuditLoggerUtils.logExport("Relatorio_Contratos", contractsList.size());
            doc.close();
            AlertAction.showAlert("Sucesso", "PDF de Contratos salvo em: " + downloadsPath);
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao exportar relatório de Contratos: " + e.getMessage());
            if (doc.isOpen()) {
                doc.close();
            }
        }
    }

    public static void exportPaymentsReport(List<PaymentModel> paymentsList) {
        Document doc = new Document();
        try {
            String downloadsPath = System.getProperty("user.home") + "\\Downloads\\Relatorio_Pagamentos.pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(downloadsPath));
            doc.open();

            Paragraph title = new Paragraph("Relatório de Pagamentos");
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph("\n"));

            Paragraph dataCreated = new Paragraph("Data de Criação: " + LocalDate.now().format(DateFormatterUtils.dateFormatter));
            dataCreated.setAlignment(Element.ALIGN_RIGHT);
            doc.add(dataCreated);
            doc.add(new Paragraph("\n"));

            if (paymentsList.isEmpty()) {
                doc.add(new Paragraph("Nenhum Pagamento registrado."));
            } else {
                for (PaymentModel payment : paymentsList) {
                    doc.add(new Paragraph("Locatario: " + payment.getContract().getNameLocatario()));
                    doc.add(new Paragraph("CPF Locatario: " + CpfUtils.mask(payment.getContract().getCpfLocatario())));
                    doc.add(new Paragraph("Mes de Referencia: " + payment.getMonthRef().format(DateFormatterUtils.dateFormatter)));
                    doc.add(new Paragraph("Valor Base R$ " + DecimalFormatterUtils.decimalFormat.format(payment.getValorBase())));
                    doc.add(new Paragraph("\n"));
                }
            }

            LgpdAuditLoggerUtils.logExport("Relatorio_Pagamentos", paymentsList.size());
            doc.close();
            AlertAction.showAlert("Sucesso", "PDF de Pagamentos salvo em: " + downloadsPath);
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao exportar relatório de Pagamentos: " + e.getMessage());
            if (doc.isOpen()) {
                doc.close();
            }
        }
    }
}