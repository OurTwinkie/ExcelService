package org.example.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Service
@Slf4j
public class ExcelService {
    public int findNthMax(String filePath, int n) {
        List<Integer> numbers = readNumbersFromExcel(filePath);
        return quickSelect(numbers, 0, numbers.size() - 1, n);
    }

    private List<Integer> readNumbersFromExcel(String filePath) {
        List<Integer> numbers = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    numbers.add((int) cell.getNumericCellValue());
                }
            }
        } catch (Exception e) {
            log.error("Ошибка при чтении Excel: {}", e.getMessage());
        }
        return numbers;
    }

    private int quickSelect(List<Integer> nums, int left, int right, int n) {
        if (left == right) return nums.get(left);
        int pivotIndex = partition(nums, left, right);
        int count = right - pivotIndex + 1;
        if (count == n) return nums.get(pivotIndex);
        return count > n ? quickSelect(nums, pivotIndex + 1, right, n)
                : quickSelect(nums, left, pivotIndex - 1, n - count);
    }

    private int partition(List<Integer> nums, int left, int right) {
        int pivot = nums.get(right);
        int i = left;
        for (int j = left; j < right; j++) {
            if (nums.get(j) <= pivot) {
                Collections.swap(nums, i, j);
                i++;
            }
        }
        Collections.swap(nums, i, right);
        return i;
    }
}
