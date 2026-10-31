package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import client.IngredientsClient;
import stellarburgers.model.Ingredient;
import stellarburgers.model.IngredientsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class IngredientStorage {
    private static final List<String> ingredientIds = new ArrayList<>();
    private static boolean isLoaded = false;

    public static void loadIngredients() {
        if (isLoaded) {
            return;
        }

        IngredientsClient client = new IngredientsClient();
        String responseBody = client.getIngredients().asString();

        ObjectMapper mapper = new ObjectMapper();
        try {
            // Парсим JSON в объект-обертку, а не сразу в массив
            IngredientsResponse response = mapper.readValue(responseBody, IngredientsResponse.class);

            // Проверяем, что сервер вернул успех и список не пустой
            if (response.isSuccess() && response.getIngredients() != null) {
                for (Ingredient ing : response.getIngredients()) {
                    ingredientIds.add(ing.getId());
                }
                isLoaded = true;
            } else {
                throw new RuntimeException("API Error: Server returned success=false or ingredients list is null. Body: " + responseBody);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse ingredients JSON. Body: " + responseBody, e);
        }
    }

    public static String getRandomIngredientId() {
        if (ingredientIds.isEmpty()) {
            loadIngredients();
        }
        Random random = new Random();
        return ingredientIds.get(random.nextInt(ingredientIds.size()));
    }

    public static List<String> getRandomIngredientIds(int count) {
        if (ingredientIds.isEmpty()) {
            loadIngredients();
        }
        if (count > ingredientIds.size()) {
            count = ingredientIds.size();
        }

        List<String> copy = new ArrayList<>(ingredientIds);
        Collections.shuffle(copy);
        return copy.subList(0, count);
    }
}