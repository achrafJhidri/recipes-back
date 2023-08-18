package com.example.recipes.controller;

import com.example.recipes.model.Ingredient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

@RestController()
@CrossOrigin(origins = "http://localhost:5000")
public class IngredientController {
    private final List<Ingredient> ingredients ;

    public IngredientController() {
        this.ingredients = new ArrayList<>();
        this.ingredients.add(new Ingredient("basla",2));
        this.ingredients.add(new Ingredient("tofa7a",10));
        this.ingredients.add(new Ingredient("sberdila",11));
        this.ingredients.add(new Ingredient("kojak",12));
        this.ingredients.add(new Ingredient("n3ala",10));
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/ingredients")
    public List<Ingredient> getIngredients(){
        return ingredients;
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/ingredients")
    public void addIngredient(@RequestBody Ingredient ingredient ){

        var ingredientToEdit = ingredients.stream().filter(ingredient1 -> ingredient1.name().equalsIgnoreCase(ingredient.name())).findFirst();
        if(ingredientToEdit.isPresent()){
            var old = ingredientToEdit.get();
            var newOne = new Ingredient(old.name(),old.amount()+ingredient.amount());
            ingredients.remove(old);
            ingredients.add(newOne);
        }else{
            ingredients.add(ingredient);
        }
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/ingredients/list")
    public void addIngredients(@RequestBody List<Ingredient> ingredients ){
        ingredients.forEach(this::addIngredient);
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PutMapping("/ingredients/{index}")
    public void addIngredient(@PathVariable int index,@RequestBody Ingredient ingredient ){
       ingredients.remove(index);
       ingredients.add(ingredient);
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @DeleteMapping(value = "/ingredients/{index}")
    public void deleteRecipe(@PathVariable int index){
        ingredients.remove(index);
    }
}
