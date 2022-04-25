package br.com.saks.biblioteca.controller;

import br.com.saks.biblioteca.model.Livro;
import br.com.saks.biblioteca.repository.LivroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
public class LivroController {
    
    @Autowired
    private LivroRepository livroRepository;
    
    @GetMapping
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }
    
    @GetMapping(value="/{id}")
    public Optional<Livro> listarPeloId(@PathVariable Long id) {
        return livroRepository.findById(id);
    }
    
    @PostMapping
    public Livro adicionar(@RequestBody Livro livro) {
        return livroRepository.save(livro);
    }
    
    @PutMapping(value="/{id}")
    public ResponseEntity editar(@PathVariable Long id, @RequestBody Livro livro) {
        return livroRepository.findById(id)
                .map(record -> {
                    record.setTitulo(livro.getTitulo());
                    Livro livroUpdated = livroRepository.save(record);
                    return ResponseEntity.ok().body(livroUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping(value="/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(record-> {
                    livroRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
