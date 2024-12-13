package ddt_config;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ExcelDataReader implements DataReader {
    private final ReaderConfig config;

    public ExcelDataReader(ReaderConfig config){
        this.config=config;
    }

    private XSSFWorkbook getWorkBook() throws InvalidFormatException, IOException {
        return new XSSFWorkbook(new File(config.getFileLocation()));
    }
    private XSSFSheet getSheet(XSSFWorkbook workBook) {
        return workBook.getSheet(config.getSheetName());
    }

    public Cell getColumnIndex(Row row) {
        return row.getCell(config.getColumnName());
    }

    private List<String> getHeaders(XSSFSheet sheet) {
        List<String> headers = new ArrayList<>();
        XSSFRow row = sheet.getRow(0);
        row.forEach(cell ->
            headers.add(cell.getStringCellValue())
        );
        return Collections.unmodifiableList(headers);
    }
    @Override
    public List<Map<String, String>> getAllRows() {
        List<Map<String,String>> data;
        try(XSSFWorkbook workbook=getWorkBook()){
            XSSFSheet sheet = getSheet(workbook);
            data = getData(sheet);
        } catch (Exception e) {
            Log.error("Not able to read the excel from location"+config.getFileName() +config.getFileLocation());
            e.printStackTrace();
            return Collections.emptyList();
        }
        return data;
    }

    private List<Map<String, String>> getData(XSSFSheet sheet) {
        List<Map<String, String>> data = new ArrayList<>();
        List<String> headers = getHeaders(sheet);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Map<String, String> rowMap = new HashedMap<>();
            XSSFRow row = sheet.getRow(i);
            forEachWithCounter(row, (index, cell) ->
                rowMap.put(headers.get(index), cell.getStringCellValue())
            );
            data.add(rowMap);
        }
        return Collections.unmodifiableList(data);
    }
    private Map<String, String> getData(XSSFSheet sheet, int rowIndex) {
        List<String> headers = getHeaders(sheet);
        Map<String, String> rowMap = new HashedMap<>();
        XSSFRow row = sheet.getRow(rowIndex);
        forEachWithCounter(row, (index, cell) ->
            rowMap.put(headers.get(index), cell.getStringCellValue())
        );
        forEachWithCounter(row, (i, j) -> {
        });
        return Collections.unmodifiableMap(rowMap);
    }

    @Override
    public Map<String, String> getASingleRow() {
        Map<String, String> data;
        try (XSSFWorkbook workBook = getWorkBook()) {
            XSSFSheet sheet = getSheet(workBook);
            data = getData(sheet, config.getIndex());
        } catch (Exception e) {
            Log.error("Not able to read the excel %s from location %s"+ config.getFileName()
                       + config.getFileLocation());

            return Collections.emptyMap();
        }
        return data;
    }

    @Override
    public List<String> getExcelDataWithRespectToColumn() {
        try (XSSFWorkbook workBook = getWorkBook()) {
            XSSFSheet sheet = getSheet(workBook);
            List<String> values = new ArrayList<>();
            for (Row row : sheet) {
                Cell cell = getColumnIndex(row);
                if (cell != null && (cell.getCellType() == CellType.STRING || (cell.getCellType() == CellType.FORMULA && cell.getCachedFormulaResultType() == CellType.STRING))) {
                    values.add(cell.getStringCellValue());
                }
            }
            return values;
        } catch (Exception exception) {
            Log.error("Not able to read the excel "+config.getFileName()+" from location "+config.getFileLocation());
        }
        return Collections.emptyList();
    }

    private void forEachWithCounter(Iterable<Cell> source, BiConsumer<Integer, Cell> biConsumer) {
        int i = 0;
        for (Cell cell : source) {
            biConsumer.accept(i, cell);
            i++;
        }
    }
}
