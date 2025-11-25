package com.group_project;

public class QuestionTableDisplay {
    private static final String[] HEADERS = new String[] {
		"Variables & Data Types",
		"Control Structures",
		"Functions",
		"Arrays",
		"File Handling"
	};
    
    public void displayTable() {
        System.out.printf("%-25s %-25s %-25s %-25s %-25s%n", (Object[]) HEADERS);
        for (int i = 0; i < 5; i++) {
            for (String header : HEADERS) {
                System.out.printf("%-25s", "" + ((i + 1) * 100));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
		QuestionTableDisplay d = new QuestionTableDisplay();
		d.displayTable();
	}
}
