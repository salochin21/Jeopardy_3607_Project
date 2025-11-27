package jeopardy.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonLoader implements Strategy {
    private Question question;
    private JsonElement jsonElement;
    private QuestionBank system;
    private QuestionBank questionBank;

    public JsonLoader(String filePath) {
        system = new QuestionBank("Jeopardy System");
        
        try {
            jsonElement = readFromFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public final JsonElement readFromFile(String filePath) throws IOException{
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("Resource not found on classpath: " + filePath);
        }
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            jsonElement = new JsonParser().parse(reader);
            
        }catch (IOException e) {
            e.printStackTrace();
        } 
           
        return jsonElement;   
    }
    
    @Override
    public void loadQuestions() {
        JsonArray questionsArray = jsonElement.getAsJsonArray();
        
        for (JsonElement element : questionsArray) {
            JsonObject questionObj = element.getAsJsonObject();
            
            String category = questionObj.get("Category").getAsString();
            String questionText = questionObj.get("Question").getAsString();
            String answer = questionObj.get("CorrectAnswer").getAsString();
            int value = questionObj.get("Value").getAsInt();
            int i=0;
            JsonObject optionsArray = questionObj.get("Options").getAsJsonObject();
            String[] options = new String[optionsArray.size()];
            for (java.util.Map.Entry<String, JsonElement> opt : optionsArray.entrySet()) {
                        options[i++] = opt.getValue().getAsString();
            }

            question = new Question(category, questionText, answer, value, options);
            // Here you can add the question to a QuestionBank or process it as needed
            saveQuestions(question);
        }
    }
    
    
    public void saveQuestions(Question question) {
        // Implementation for saving questions to QuestionBank
        CategoryExists(question.getCategory());
        questionBank.addQuestion(question);
    }
    
    @Override
    public QuestionBank getSystem() {
        return system;
    }

    public void setquestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    //checks if category exists, if not create it
    public void CategoryExists(String categoryName) {
        if (!system.hasCategory(categoryName)) {
            QuestionBank newCategory = new QuestionBank(categoryName);
            system.addQuestion(newCategory);
            setquestionBank(newCategory); 
        }   else {
            // Category exists, retrieve it
            setquestionBank(system.getCategoryByName(categoryName));
        }
       
    }


    

    
}
