package com.arti.inventory.middleware;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PdfService {

    @Autowired
    QRCodeService qrCodeService;

    public byte[] fillPdfForm(Map<String, String> fieldValues) throws IOException {
        // Load the existing PDF document
        PdfReader reader = new PdfReader("pdf/fiche-de-mission.pdf");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        // Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        Document document = new Document(pdfDoc);

        // Access the form fields
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

        // Fill the form fields
        for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
            form.getField(entry.getKey()).setValue(entry.getValue());
        }

        // Generate QR code image
        //generateQrCode(fieldValues, document);

        // Close the document
        form.flattenFields();
        document.close();

        return outputStream.toByteArray();
    }

    @SuppressWarnings("unused")
    private void generateQrCode(Map<String, String> fieldValues, Document document) {
        String qrText = fieldValues.get("ticketId");
        if (qrText != null && !qrText.isEmpty()) {
            byte[] qrCodeImage = qrCodeService.generateQRCodeImage(qrText, 130, 130);

            if (qrCodeImage != null) {
                Image qrImage = new Image(ImageDataFactory.create(qrCodeImage));
                qrImage.setFixedPosition(63, 53);
                document.add(qrImage);
            }
        }
    }

    public byte[] combinePdfFiles(List<byte[]> pdfFiles) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(outputStream);
                PdfDocument pdfDoc = new PdfDocument(writer)) {

            for (byte[] pdfFile : pdfFiles) {
                PdfDocument sourcePdf = new PdfDocument(new PdfReader(new ByteArrayInputStream(pdfFile)));
                sourcePdf.copyPagesTo(1, sourcePdf.getNumberOfPages(), pdfDoc);
                sourcePdf.close();
            }
        }

        return outputStream.toByteArray();
    }
}
