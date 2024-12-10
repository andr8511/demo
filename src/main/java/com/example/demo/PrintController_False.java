//package com.example.demo;
//
//import com.aspose.cells.PdfSaveOptions;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.printing.PDFPageable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.print.*;
//import javax.print.attribute.HashPrintRequestAttributeSet;
//import javax.print.attribute.PrintRequestAttributeSet;
//import javax.print.attribute.standard.Copies;
//import javax.print.attribute.standard.JobName;
//import javax.print.attribute.standard.MediaSizeName;
//import javax.print.attribute.standard.Sides;
//import java.awt.print.PrinterException;
//import java.awt.print.PrinterJob;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Iterator;
//
//
//@RestController
//public class PrintController_False {
//
//    @PostMapping("/print")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file,
//                                   @RequestParam("printer") String printerName) {
//        try {
//            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
//            PrintService selectedPrinter = null;
//
//            for (PrintService printer : printServices) {
//                if (printerName.contains(printer.toString())) {
//                    selectedPrinter = printer;
//                    break;
//                }
//            }
//
//            if (selectedPrinter == null) {
//                return "Принтер не найден.";
//            }
//
//            // Создаем документ для печати
//            try (InputStream inputStream = file.getInputStream()) {
//                com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook(inputStream);
//
//                String pdfPath = "D:\\PdfVariable.pdf";
//                PdfSaveOptions options = new PdfSaveOptions();
//                workbook.save(pdfPath, options);
//                printPdf(pdfPath);
//            }
//            return "Документ успешно отправлен на печать.";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Ошибка при попытке печати: " + e.getMessage();
//        }
//    }
//
//
//    private static void printPdf(String filePath) {
//        try {
//            PDDocument document = PDDocument.load(new File(filePath));
//            PrinterJob printerJob = PrinterJob.getPrinterJob();
//            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null,  null);
//            //TODO !!!!!!!!!!
//            int printIndex = 2;
//            PrintService printService = printServices[printIndex];
//            printerJob.setPrintService(printService);
//            PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
//            attributes.add(new JobName("MyPrintJob",null));
//            attributes.add(MediaSizeName.ISO_A4);
//            attributes.add(Sides.ONE_SIDED);
//            attributes.add(new Copies(1));
//            PDDocument landscapeDoc = new PDDocument();
//            Iterator iterator = document.getPages().iterator();
//
//            while(iterator.hasNext()) {
//                PDPage page = (PDPage) iterator.next();
////                PDRectangle pageSize = page.getMediaBox();
////                if (pageSize.getWidth() > pageSize.getHeight()) {
//                    landscapeDoc.addPage(page);
////                }
//            }
//
//            printerJob.setPageable(new PDFPageable(landscapeDoc));
//            printerJob.print(attributes);
//            document.close();
//        } catch (PrinterException | IOException var11) {
//            var11.printStackTrace();
//        }
//
//    }
//}

