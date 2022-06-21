package io.corona.coronavirustracker.controllers;

import io.corona.coronavirustracker.services.CoronaVirusDataService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;


@Controller
public class ReportController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/report")
    public ResponseEntity<StreamingResponseBody> excel() {
        Workbook workbook = new XSSFWorkbook();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        CreationHelper creationHelper = workbook.getCreationHelper();
        int day = calendar.get(Calendar.DATE) - 1;
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        File[] files = new File("coronaData").listFiles();
        if (files != null) {
            for (File f : files) {
                System.out.println(f.getName());
            }
        }

        Sheet sheet = workbook.createSheet(day + "." + month + "." + year);

        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setItalic(true);
        headerStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerRow.setHeight((short) 600);

        CellStyle positiveStyle = workbook.createCellStyle();
        Font positiveFont = workbook.createFont();
        positiveFont.setBold(true);
        positiveFont.setItalic(true);
        positiveFont.setColor(IndexedColors.WHITE.getIndex());
        positiveStyle.setFont(positiveFont);
        positiveStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        positiveStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle negativeStyle = workbook.createCellStyle();
        Font negativeFont = workbook.createFont();
        negativeFont.setBold(true);
        negativeFont.setItalic(true);
        negativeFont.setColor(IndexedColors.WHITE.getIndex());
        negativeStyle.setFont(negativeFont);
        negativeStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        negativeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Cell cellLocation = headerRow.createCell(0);
        cellLocation.setCellValue("Štát");
        Cell cellTotalCases = headerRow.createCell(1);
        cellTotalCases.setCellValue("Počet nakazených");
        Cell cellNewCases = headerRow.createCell(2);
        cellNewCases.setCellValue("Nové prípady");
        Cell cellTotalDeaths = headerRow.createCell(3);
        cellTotalDeaths.setCellValue("Počet úmrti");
        Cell cellNewDeaths = headerRow.createCell(4);
        cellNewDeaths.setCellValue("Nové úmrtia");
        Cell cellNewTests = headerRow.createCell(5);
        cellNewTests.setCellValue("Počet testov");

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            headerRow.getCell(i).setCellStyle(headerStyle);
        }

//        int rowNum = 1;
//        for (LocationStats locationStat : coronaVirusDataService.getAllStats()) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(locationStat.getState());
//            row.createCell(1).setCellValue(locationStat.getTotalCases());
//            row.createCell(2).setCellValue(locationStat.getTotalRecovered());
//            row.createCell(3).setCellValue(locationStat.getTotalDeaths());
//            row.createCell(4).setCellValue(locationStat.getDiffCases());
//            row.createCell(5).setCellValue(locationStat.getDiffDeaths());
//        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"coronadata" + "_" + day + "_" + month + "_" + year + ".xlsx\"")
                .body(workbook::write);
    }
}
