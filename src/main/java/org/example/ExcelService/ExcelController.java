package org.example.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
@Tag(name = "Excel Controller", description = "Обрабатывает xlsx-файлы и ищет N-ый максимум")
public class ExcelController {
    private final ExcelService excelService;

    @Operation(summary = "Получить N-ое максимальное число из файла")
    @GetMapping("/find-max")
    public int findNthMax(@RequestParam String filePath, @RequestParam int n) {
        return excelService.findNthMax(filePath, n);
    }
}