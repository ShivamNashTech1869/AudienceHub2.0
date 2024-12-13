package resources;

import util.ExcelReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.Log;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataDriven {

    public static ArrayList<String> getTestData(String sheetName,String testCase) throws IOException {
        ArrayList<String> arrayList= new ArrayList<>();
        FileInputStream file = new FileInputStream("src/main/java/resources/externalData/credsData.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        int sheetNumber=workbook.getNumberOfSheets();
        for(int i=0;i<sheetNumber;i++){
            if(workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row first = rows.next();
                Iterator<Cell> cell = first.cellIterator();
                int r = 0;
                int c = 0;
                while (cell.hasNext()) {
                    Cell value = cell.next();
                    if (value.getStringCellValue().equalsIgnoreCase("Test Cases")) {
                        c = r;
                    }
                    r++;
                }
                while (rows.hasNext()) {
                    Row r2 = rows.next();
                    if (r2.getCell(c) != null) {
                        if (r2.getCell(c).getStringCellValue().equalsIgnoreCase(testCase)) {
                            Iterator<Cell> cv = r2.cellIterator();
                            while (cv.hasNext()) {
                                Cell cell2 = cv.next();
                                if (cell2.getCellType() == CellType.STRING) {
                                    arrayList.add(cell2.getStringCellValue());
                                } else {
                                    arrayList.add(NumberToTextConverter.toText(cell2.getNumericCellValue()));
                                }
                            }
                        }
                    }

                }
            }
            }
        return arrayList;
        }


    public static String readExcelColumnCucumber(String columnName, String sheetName, int rowNumber) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader();
        List<Map<String,String>> testData =
                reader.getData("src/main/java/resources/externalData/searchKeys.xlsx", sheetName);
        String rowId = testData.get(rowNumber).get(columnName);
        Log.info("id : " + rowId);
        return rowId;
    }

    public static ArrayList<String> getPromoCode(String sheetName,String testCase) throws IOException {
        ArrayList<String> arrayList= new ArrayList<>();
        FileInputStream file = new FileInputStream("src/main/java/resources/externalData/ddt_data.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        int sheetNumber=workbook.getNumberOfSheets();
        for(int i=0;i<sheetNumber;i++){
            if(workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row first = rows.next();
                Iterator<Cell> cell = first.cellIterator();
                int r = 0;
                int c = 0;
                while (cell.hasNext()) {
                    Cell value = cell.next();
                    if (value.getStringCellValue().equalsIgnoreCase("Test Cases")) {
                        c = r;
                    }
                    r++;
                }
                while (rows.hasNext()) {
                    Row r2 = rows.next();
                    if (r2.getCell(c) != null) {
                        if (r2.getCell(c).getStringCellValue().equalsIgnoreCase(testCase)) {
                            Iterator<Cell> cv = r2.cellIterator();
                            while (cv.hasNext()) {
                                Cell cell2 = cv.next();
                                if (cell2.getCellType() == CellType.STRING) {
                                    arrayList.add(cell2.getStringCellValue());
                                } else {
                                    arrayList.add(NumberToTextConverter.toText(cell2.getNumericCellValue()));
                                }
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public static ArrayList<String> getProduct(String sheetName,String testCase) throws IOException {
        ArrayList<String> arrayList= new ArrayList<>();
        FileInputStream file = new FileInputStream("src/main/java/resources/externalData/ddt_data.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        int sheetNumber=workbook.getNumberOfSheets();
        for(int i=0;i<sheetNumber;i++){
            if(workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row first = rows.next();
                Iterator<Cell> cell = first.cellIterator();
                int r = 0;
                int c = 0;
                while (cell.hasNext()) {
                    Cell value = cell.next();
                    if (value.getStringCellValue().equalsIgnoreCase("Test Cases")) {
                        c = r;
                    }
                    r++;
                }
                while (rows.hasNext()) {
                    Row r2 = rows.next();
                    if (r2.getCell(c) != null) {
                        if (r2.getCell(c).getStringCellValue().equalsIgnoreCase(testCase)) {
                            Iterator<Cell> cv = r2.cellIterator();
                            while (cv.hasNext()) {
                                Cell cell2 = cv.next();
                                if (cell2.getCellType() == CellType.STRING) {
                                    arrayList.add(cell2.getStringCellValue());
                                } else {
                                    arrayList.add(NumberToTextConverter.toText(cell2.getNumericCellValue()));
                                }
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

}


