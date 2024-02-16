package com.solomon.nkwor.solomon.bank.Service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.solomon.nkwor.solomon.bank.Model.Transactions;
import com.solomon.nkwor.solomon.bank.Model.User;
import com.solomon.nkwor.solomon.bank.Repository.TransactionRepository;
import com.solomon.nkwor.solomon.bank.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {

    private TransactionRepository transactionRepository;

    private UserRepository userRepository;

    private static final String FILE ="C:\\Users\\SolomonNkwor\\OneDrive - Trium Limited\\Desktop\\BankStatement.pdf";

//    public BankStatement(TransactionRepository transactionRepository){
//        this.transactionRepository = transactionRepository;
//    }

    public List<Transactions> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        User user = userRepository.findByAccountNumber(accountNumber);

        String customerName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
        Rectangle statementSize = new Rectangle(PageSize.A4);

        Document document = new Document(statementSize);

        log.info("Setting size complete");

        OutputStream outputStream = new FileOutputStream(FILE);

        PdfWriter.getInstance(document, outputStream);

        document.open();

        PdfPTable bankInfoTable = new PdfPTable(1);

        PdfPCell bankName = new PdfPCell(new Phrase("Solomon Bank"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(20f);

        PdfPCell bankAddress = new PdfPCell(new Phrase("72, VI, Lagos, Nigeria"));
        bankAddress.setBorder(0);

        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);

        PdfPTable statementInfo = new PdfPTable(2);
        PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " + startDate));
        customerInfo.setBorder(0);

        PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
        statement.setBorder(0);

        PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));
        stopDate.setBorder(0);



        return transactionRepository.findAll().stream().filter(transactions -> transactions.getAccountNumber().equals(accountNumber))
                .filter(transactions -> transactions.getTransactionTime().isEqual(start)).filter(transactions -> transactions.getTransactionTime().isEqual(end)).toList();
    }

}
