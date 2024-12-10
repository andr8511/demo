package com.example.demo;

import com.aspose.cells.PdfSaveOptions;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
public class PrintController {
    private static final Logger logger = LoggerFactory.getLogger(PrintController.class);
    private static final String PDF_PATH = "D:\\PdfVariable.pdf";
    private static final int DEFAULT_PRINT_SERVICE_INDEX = 2; // Adjust as needed

    @PostMapping("/print")
    public CompletableFuture<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("printer") String printerName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PrintService selectedPrinter = findPrinterByName(printerName);
                if (selectedPrinter == null) {
                    return "Принтер не найден.";
                }
                    saveFileAndPrint(file.getInputStream());
                return "Документ успешно отправлен на печать.";
            } catch (Exception e) {
                logger.error("Ошибка при попытке печати: ", e);
                return "Ошибка при попытке печати: " + e.getMessage();
            }
        });
    }

    private PrintService findPrinterByName(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printer : printServices) {
            if (printerName.contains(printer.toString())) {
                return printer;
            }
        }
        return null;
    }


    private void saveFileAndPrint(InputStream inputStream) throws Exception {
        com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook(inputStream);
        try {
            workbook.save(PDF_PATH, new PdfSaveOptions());
            printPdf(PDF_PATH);
        } catch (Exception e) {
            logger.error("Error while saving and printing: ", e);
            throw e;
        } finally {
            workbook.dispose();
        }
    }



    private void printPdf(String filePath) {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PrintService printService = getPrintService();
            printerJob.setPrintService(printService);
            PrintRequestAttributeSet attributes = createPrintAttributes();

            printDocumentInParts(document, printerJob, attributes);
        } catch (Exception e) {
            logger.error("Ошибка при печати PDF: ", e);
        }
    }

    private PrintService getPrintService() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        if (printServices.length > DEFAULT_PRINT_SERVICE_INDEX) {
            return printServices[DEFAULT_PRINT_SERVICE_INDEX];
        }
        throw new IllegalStateException("Нет подходящих сервисов печати.");
    }

    private PrintRequestAttributeSet createPrintAttributes() {
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        attributes.add(new JobName("MyPrintJob", null));
        attributes.add(MediaSizeName.ISO_A4);
        attributes.add(Sides.ONE_SIDED);
        attributes.add(new Copies(1));
        return attributes;
    }

    private void printDocumentInParts(PDDocument document, PrinterJob printerJob,
                                      PrintRequestAttributeSet attributes) {
        int totalPages = document.getNumberOfPages();
        for (int i = 0; i < totalPages; i++) {
            try {
                PDPage page = document.getPage(i);
                PDDocument singlePageDoc = new PDDocument();
                singlePageDoc.addPage(page);

                printerJob.setPageable(new PDFPageable(singlePageDoc));
                printerJob.print(attributes);
                singlePageDoc.close();
            } catch (Exception e) {
                logger.error("Ошибка при печати страницы {}: ", i + 1, e);
            }
        }
    }
}
