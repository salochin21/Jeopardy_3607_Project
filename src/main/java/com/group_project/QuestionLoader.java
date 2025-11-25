package com.group_project;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class QuestionLoader {
    
    // This class will handle loading and providing questions for the game
    QuestionBank system;
    QuestionBank qb;
    Question question;

    public QuestionLoader(QuestionBank questionBank) {
        // Constructor logic to initialize question database or source
        this.system = questionBank;
    }

    public void loadQuestionsFromFile(String filePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("Resource not found on classpath: " + filePath);
        }
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            JsonElement jsonElement = new JsonParser().parse(reader);
            JsonArray questionsArray = jsonElement.getAsJsonArray();
            //System.out.println(questionsArray);
            for (JsonElement element : questionsArray) {
                //System.out.println("adding question");
                JsonObject questionObject = element.getAsJsonObject();
                String category = questionObject.get("Category").getAsString();
                //System.out.println("Category: " + category);
                String questionText = questionObject.get("Question").getAsString();
                String answerText = questionObject.get("CorrectAnswer").getAsString();
                int value = questionObject.get("Value").getAsInt();
                String[] options = null;
                int i = 0;
                
                /* 
                if (questionObject.has("Options")) {
                    JsonArray optionsArray = questionObject.get("Options").getAsJsonArray();
                    options = new String[optionsArray.size()];
                    for (int i = 0; i < optionsArray.size(); i++) {
                        options[i] = optionsArray.get(i).getAsString();
                    }
                }*/
                if (questionObject.has("Options") && questionObject.get("Options").isJsonObject()) {
                    //System.out.println("Options:");
                    JsonObject opts = questionObject.get("Options").getAsJsonObject();
                    options = new String[opts.size()];
                    for (java.util.Map.Entry<String, JsonElement> opt : opts.entrySet()) {
                        //System.out.println("  " + opt.getKey() + ": " + opt.getValue());
                        options[i++] = opt.getValue().getAsString();

                    }
                }

                question = new Question(category,questionText, answerText, value, options);
                
                CategoryExists(category);
                qb.addQuestion(question);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


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

    public void setquestionBank(QuestionBank qb) {
        this.qb = qb;
        //System.out.println("Setting question bank to: " + qb.getName());
    }

    public void addCategory(QuestionBank category) {
        system.addQuestion(category);
    }

    public QuestionBank getSystem() {
        return system;
    }


    public static void main(String[] args) throws Exception {
        QuestionBank qb= new QuestionBank("System");
        
        QuestionLoader q = new QuestionLoader(qb);
        
        q.loadQuestionsFromFile("sample_game_JSON.json");
        
    }
}
