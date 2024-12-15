// ExpenseController.java
package com.proiecte.expense.controller;

import com.proiecte.expense.model.Expense;
import com.proiecte.expense.service.ExpenseService;
import com.proiecte.expense.service.PdfService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final PdfService pdfService;

    public ExpenseController(ExpenseService expenseService, PdfService pdfService) {
        this.expenseService = expenseService;
        this.pdfService = pdfService;
    }

    @GetMapping
    public String getAllExpenses(Model model) {
        model.addAttribute("expenses", expenseService.findAll());
        return "expenses";
    }

    @GetMapping("/new")
    public String showAddExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "add-expense";
    }

    @PostMapping
    public String saveExpense(@ModelAttribute Expense expense) {
        expenseService.save(expense);
        return "redirect:/expenses";
    }

    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteById(id);
        return "redirect:/expenses";
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<InputStreamResource> exportExpensesToPdf() {
        List<Expense> expenses = expenseService.findAll();
        ByteArrayInputStream bis = pdfService.generateExpensesPdf(expenses);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=expenses.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
