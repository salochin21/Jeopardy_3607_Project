package jeopardy.example;

public class CSVLoader implements Strategy {
      private final QuestionBank system;
    private final String filePath; // resource path or file path

    public CSVLoader(String filePath) {
        this.filePath = filePath;
        this.system = new QuestionBank("Jeopardy System");
    }

    @Override
    public void loadQuestions() {
        List<String> lines = readAllLines(filePath);
        if (lines == null || lines.isEmpty()) {
            return;
        }

        String header = lines.get(0);
        String[] headers = splitCsvLine(header);
        Map<String, Integer> index = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            index.put(headers[i].trim().toLowerCase(), i);
        }

        for (int r = 1; r < lines.size(); r++) {
            String line = lines.get(r);
            if (line.trim().isEmpty()) continue;
            String[] cells = splitCsvLine(line);

            String category = getCell(cells, index, "category");
            String questionText = getCell(cells, index, "question");
            if (questionText.isEmpty()) questionText = getCell(cells, index, "question_text");
            String answer = getCell(cells, index, "correctanswer");
            if (answer.isEmpty()) answer = getCell(cells, index, "answer");
            String valueStr = getCell(cells, index, "value");
            if (valueStr.isEmpty()) valueStr = getCell(cells, index, "question_value");
            int value = 0;
            try { value = Integer.parseInt(valueStr.trim()); } catch (Exception e) { value = 0; }

            // Build options array
            List<String> opts = new ArrayList<>();
            // Check for single Options column
            String optsCell = getCell(cells, index, "options");
            if (!optsCell.isEmpty()) {
                String sep = optsCell.contains("|") ? "\\|" : ";";
                String[] parts = optsCell.split(sep);
                for (String p : parts) opts.add(p.trim());
            } else {
                // Look for OptionA, OptionB...
                for (char c = 'a'; c <= 'd'; c++) {
                    String key = "option" + Character.toUpperCase(c);
                    String v = getCell(cells, index, key);
                    if (!v.isEmpty()) opts.add(v);
                }
                // Also try lowercase optiona
                if (opts.isEmpty()) {
                    for (char c = 'a'; c <= 'd'; c++) {
                        String key = "option" + c;
                        String v = getCell(cells, index, key);
                        if (!v.isEmpty()) opts.add(v);
                    }
                }
            }

            String[] optionsArray = opts.isEmpty() ? new String[0] : opts.toArray(new String[0]);

            Question q = new Question(category, questionText, answer, value, optionsArray);
            
            
            
            // Add to category bank
            QuestionBank catBank = system.getCategoryByName(category);
            if (catBank == null) {
                catBank = new QuestionBank(category);
                system.addQuestion(catBank);
            }
            catBank.addQuestion(q);
        }
    }

    @Override
    public QuestionBank getSystem() {
        return system;
    }

    private List<String> readAllLines(String path) {
        // Try classpath resource first
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        try {
            if (is != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                    List<String> lines = new ArrayList<>();
                    String line;
                    while ((line = reader.readLine()) != null) lines.add(line);
                    return lines;
                }
            } else {
                // Try filesystem
                Path p = Paths.get(path);
                if (Files.exists(p)) {
                    return Files.readAllLines(p, StandardCharsets.UTF_8);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Splits a CSV line taking quoted commas into account.
     */
    private String[] splitCsvLine(String line) {
        if (line == null) return new String[0];
        List<String> cells = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                // toggle quote flag (handle double quotes as escaped quotes)
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // escaped quote, append one and skip next
                    cur.append('"');
                    i++; // skip next quote
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                cells.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        cells.add(cur.toString());
        return cells.toArray(new String[0]);
    }

    private String getCell(String[] cells, Map<String,Integer> indexMap, String key) {
        Integer i = indexMap.get(key.toLowerCase());
        if (i == null) return "";
        if (i < 0 || i >= cells.length) return "";
        String v = cells[i].trim();
        if (v.startsWith("\"") && v.endsWith("\"")) {
            v = v.substring(1, v.length()-1);
        }
        return v;
    }
    
}

