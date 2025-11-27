package jeopardy.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private Summary summary;
    private ReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        summary = new Summary();
        
        // Sample game data
        summary.recordTurn("Alice", "Variables & Data Types", 100,
            "Which of the following declares an integer variable in C++?", "A", true, 100, 100);
        summary.recordTurn("Bob", "Control Structures", 200,
            "Which loop always executes at least once?", "C", true, 200, 200);
        summary.recordTurn("Alice", "Functions", 300,
            "What is the return type of int add(int a, int b)?", "A", true, 300, 400);
        summary.recordTurn("Bob", "Arrays", 400,
            "How many elements in int arr[3][4];?", "D", false, -400, -200);
        
        reportGenerator = new ReportGenerator(summary);
    }

    @Test
    public void testGenerateTextReport() throws IOException {
        String fileName = "game_summary_report.txt";
        reportGenerator.generateTextReport(fileName);
        
        // Verify file was created
        assert Files.exists(Paths.get(fileName));
        
        // Print content for verification
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        System.out.println(content);
    }

    @Test
    public void testGenerateCSVReport() throws IOException {
        String fileName = "game_summary_report.csv";
        reportGenerator.generateCSVReport(fileName);
        
        // Verify file was created
        assert Files.exists(Paths.get(fileName));
        
        // Print content for verification
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        System.out.println(content);
    }

    @Test
    public void testGenerateDocxReport() throws IOException {
        String fileName = "game_summary_report.docx.txt";
        reportGenerator.generateDocxPlainText(fileName);
        
        // Verify file was created
        assert Files.exists(Paths.get(fileName));
    }

    @Test
    public void testGeneratePdfReport() throws IOException {
        String fileName = "game_summary_report.pdf.txt";
        reportGenerator.generatePdfPlainText(fileName);
        
        // Verify file was created
        assert Files.exists(Paths.get(fileName));
    }
}
