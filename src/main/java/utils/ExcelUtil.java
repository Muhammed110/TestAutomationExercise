package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ExcelUtil {
    private static final String FILE_PATH = "./data.xlsx"; // File in project root

    public static void saveCredentials(String email, String password) {
        File file = new File(FILE_PATH);
        Workbook workbook;
        Sheet sheet;

        try {
            if (file.exists() && Files.size(Paths.get(FILE_PATH)) > 0) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                    sheet = workbook.getSheetAt(0);
                }
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Credentials");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Email");
                header.createCell(1).setCellValue("Password");
            }

            int rowCount = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue(email);
            row.createCell(1).setCellValue(password);

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
            workbook.close();

            System.out.println("✅ Credentials saved: " + email + " | " + password);
        } catch (IOException e) {
            System.out.println("❌ Error saving credentials: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Map<String, String> getRandomRow(String sheetName, int startRow) {
        Map<String, String> rowData = new HashMap<>();
        File file = new File(FILE_PATH);

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("❌ Sheet '" + sheetName + "' not found in Excel file!");
            }

            int rowCount = sheet.getLastRowNum();
            if (rowCount < startRow) {
                throw new RuntimeException("❌ Not enough data rows in the sheet!");
            }

            // ✅ Get a random row (starting from `startRow` to skip headers)
            int randomRowIndex = new Random().nextInt(rowCount - startRow + 1) + startRow;
            Row randomRow = sheet.getRow(randomRowIndex);
            Row headerRow = sheet.getRow(0);

            if (randomRow != null && headerRow != null) {
                for (int i = 0; i < randomRow.getLastCellNum(); i++) {
                    String columnName = headerRow.getCell(i).getStringCellValue();
                    String cellValue = new DataFormatter().formatCellValue(randomRow.getCell(i));
                    rowData.put(columnName, cellValue);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        }
        return rowData;
    }

}