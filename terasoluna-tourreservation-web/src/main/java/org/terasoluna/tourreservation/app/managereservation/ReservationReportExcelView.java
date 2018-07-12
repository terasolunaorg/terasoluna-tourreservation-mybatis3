package org.terasoluna.tourreservation.app.managereservation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

@Component
public class ReservationReportExcelView extends AbstractXlsxView {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            ReservationReportExcelView.class);

    private static final String LINE_SEPARATOR = System.getProperty(
            "line.separator");

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");

    @Override
    protected Workbook createWorkbook(Map<String, Object> model,
            HttpServletRequest request) {
        String excelTemplateFilePath = this.getServletContext().getRealPath(
                "/WEB-INF/reports/reservationReportExcel.xlsx");
        File excelTemplateFile = new File(excelTemplateFilePath);
        Workbook workbook = null;
        try (InputStream is = new ByteArrayInputStream(Files.readAllBytes(
                excelTemplateFile.toPath()));) {
            workbook = WorkbookFactory.create(is);
        } catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
            LOGGER.error("create workbook error", e);
        }
        return workbook;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        DownloadFileOutput downloadExcelOutput = (DownloadFileOutput) model.get(
                "downloadExcelOutput");

        String fileName = (String) model.get("fileName");
        String encodedFilename = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Disposition",
                "attachment; filename*=UTF-8''" + encodedFilename);

        Sheet sheet = workbook.getSheet("ReservationReport");

        // Define style so that line breaks are displayed correctly.
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Head
        getCell(sheet, 1, 23).setCellValue(DATE_FORMAT.format(
                downloadExcelOutput.getPrintDay()));
        getCell(sheet, 4, 4).setCellValue(downloadExcelOutput.getReserveNo());
        getCell(sheet, 6, 1).setCellValue(downloadExcelOutput.getCustomerName()
                + "　様");
        getCell(sheet, 5, 18).setCellValue(downloadExcelOutput
                .getReferenceName());
        getCell(sheet, 6, 20).setCellValue(downloadExcelOutput
                .getReferenceEmail());
        getCell(sheet, 7, 20).setCellValue(downloadExcelOutput
                .getReferenceTel());

        // Reservation details
        getCell(sheet, 9, 6).setCellValue(downloadExcelOutput.getTourName());
        getCell(sheet, 10, 6).setCellValue(DATE_FORMAT.format(
                downloadExcelOutput.getDepDay()));
        getCell(sheet, 11, 6).setCellValue(downloadExcelOutput.getArrName());
        getCell(sheet, 12, 6).setCellValue(downloadExcelOutput.getConductor());
        getCell(sheet, 13, 6).setCellValue(downloadExcelOutput.getAccomName());
        getCell(sheet, 14, 6).setCellValue(downloadExcelOutput.getTourAbs());
        getCell(sheet, 14, 6).setCellStyle(style);
        getCell(sheet, 9, 21).setCellValue(DATE_FORMAT.format(
                downloadExcelOutput.getReservedDay()));
        getCell(sheet, 10, 21).setCellValue(downloadExcelOutput.getTourDays());
        getCell(sheet, 11, 21).setCellValue(downloadExcelOutput.getDepName());
        getCell(sheet, 13, 21).setCellValue(downloadExcelOutput.getAccomTel());

        // Representative information
        getCell(sheet, 17, 6).setCellValue(downloadExcelOutput
                .getCustomerCode());
        getCell(sheet, 18, 6).setCellValue(downloadExcelOutput
                .getCustomerKana());
        getCell(sheet, 19, 6).setCellValue(downloadExcelOutput
                .getCustomerName());
        getCell(sheet, 20, 6).setCellValue(DATE_FORMAT.format(
                downloadExcelOutput.getCustomerBirth()));
        getCell(sheet, 21, 6).setCellValue(downloadExcelOutput
                .getCustomerJob());
        getCell(sheet, 22, 6).setCellValue(downloadExcelOutput
                .getCustomerMail());
        getCell(sheet, 23, 6).setCellValue(downloadExcelOutput
                .getCustomerTel());
        getCell(sheet, 24, 6).setCellValue(downloadExcelOutput
                .getCustomerPost());
        getCell(sheet, 25, 6).setCellValue(downloadExcelOutput
                .getCustomerAdd());

        // Bill statement
        getCell(sheet, 29, 6).setCellValue(downloadExcelOutput
                .getAdultUnitPrice() + "　円");
        getCell(sheet, 30, 6).setCellValue(downloadExcelOutput
                .getChildUnitPrice() + "　円");
        getCell(sheet, 29, 14).setCellValue(downloadExcelOutput.getAdultCount()
                + "　人");
        getCell(sheet, 30, 14).setCellValue(downloadExcelOutput.getChildCount()
                + "　人");
        getCell(sheet, 29, 20).setCellValue(downloadExcelOutput.getAdultPrice()
                + "　円");
        getCell(sheet, 30, 20).setCellValue(downloadExcelOutput.getChildPrice()
                + "　円");
        getCell(sheet, 31, 20).setCellValue(downloadExcelOutput.getSumPrice()
                + "　円");

        // Notices
        getCell(sheet, 34, 6).setCellValue(downloadExcelOutput.getRemarks());
        getCell(sheet, 34, 6).setCellStyle(style);

        // Payment information
        getCell(sheet, 37, 6).setCellValue(downloadExcelOutput
                .getPaymentMethod());
        getCell(sheet, 38, 6).setCellValue(downloadExcelOutput
                .getPaymentCompanyName() + LINE_SEPARATOR + downloadExcelOutput
                        .getPaymentAccount());
        getCell(sheet, 38, 6).setCellStyle(style);
        getCell(sheet, 39, 6).setCellValue(downloadExcelOutput
                .getPaymentTimeLimit());

    }

    private Cell getCell(Sheet sheet, int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        return row.getCell(colIndex);
    }

}
