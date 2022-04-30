package com.backend.backend.controller;

import com.backend.backend.entity.Editor;
import com.backend.backend.repository.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/editor")
public class EditorController {
    @Autowired
    private EditorRepository editorRepository;

    @GetMapping("all")
    public List<Editor> getAllEditors() {
        return editorRepository.getAllEditors();
    }

    @PostMapping("all")
    public void addNewEditor(@RequestBody int user_id) {
        editorRepository.addNewEditor(user_id);
    }

    @GetMapping("{user_id}")
    public Editor findEditorWithId(@PathVariable int user_id) {
        return editorRepository.findEditorWithId(user_id);
    }

}
