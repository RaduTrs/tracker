// PdfService.java
package com.proiecte.expense.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.proiecte.expense.model.Expense;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public ByteArrayInputStream generateExpensesPdf(List<Expense> expenses) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("List of Expenses"));

            for (Expense expense : expenses) {
                document.add(new Paragraph(
                        "Title: " + expense.getTitle() + 
                        ", Amount: " + expense.getAmount() + 
                        ", Category: " + expense.getCategory() + 
                        ", Date: " + expense.getDate()));
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
