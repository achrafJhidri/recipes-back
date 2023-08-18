package com.example.recipes.controller;

import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController()
@CrossOrigin(origins = "http://localhost:4200")
public class RecipesController {

    private final List<Recipe> recipes;

    public RecipesController() {
        recipes = new ArrayList<>();
        recipes.add(new Recipe("pancakes","Fluffy pancakes","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQK62GSZlxhbBCTGP1ldw97wXC4qwa1o2QwbQ&usqp=CAU",
                        List.of(
                                new Ingredient("basla",2),
                                new Ingredient("touma",2)
                        )));
        recipes.add(new Recipe("beghrir","beghrir mt9oub mziaan",
                "https://static.wixstatic.com/media/94e2e7_8ce40d0ac7dd4c3889f6513b7047af63~mv2.jpg/v1/fill/w_640,h_424,al_c,q_80,usm_0.66_1.00_0.01,enc_auto/94e2e7_8ce40d0ac7dd4c3889f6513b7047af63~mv2.jpg",
                List.of(
                        new Ingredient("skenjbir",2),
                        new Ingredient("della7",2)
                )));

    }
    @GetMapping(value = "/recipes")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Object> getRecipes(Principal principal)  {
        return ResponseEntity.ok().body(recipes);
    }
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping(value = "/recipes/{index}")
    public Recipe getRecipe(@PathVariable int index){
        return recipes.get(index);
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping(value = "/recipes")
    public Recipe addRecipe(@RequestBody Recipe recipe){
        recipes.add(recipe);
        return recipe;
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PutMapping(value = "/recipes/{index}")
    public Recipe editRecipe(@PathVariable int index ,@RequestBody Recipe recipe){
        recipes.remove(index);
        recipes.add(index,recipe);
        return recipe;
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @DeleteMapping(value = "/recipes/{index}")
    public void deleteRecipe(@PathVariable int index){
        recipes.remove(index);
    }
}
