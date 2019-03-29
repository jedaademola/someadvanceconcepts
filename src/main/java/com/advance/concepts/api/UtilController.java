package com.advance.concepts.api;

import com.advance.concepts.model.Result;
import com.advance.concepts.util.ExtractFieldFromJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/util")
public class UtilController {

    @Autowired
    private ExtractFieldFromJson extractFieldFromJson;

    /* @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
     public Result getById() {
        // public Result getById(@PathVariable("id") String id) {
         return extractFieldFromJson.extractJson("");

     }
 */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
    public Result getById(@PathVariable("id") String id) {
        return extractFieldFromJson.extractJson2(id);

    }
}
