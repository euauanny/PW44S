package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.ProductDTO;
import br.edu.utfpr.pb.pw44s.server.mapper.ProductMapper;
import br.edu.utfpr.pb.pw44s.server.model.Product;
import br.edu.utfpr.pb.pw44s.server.repository.ProductRepository;
import br.edu.utfpr.pb.pw44s.server.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    //get
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok(
                this.productService.findAll()
                        .stream()
                        .map(productMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) {
        Product product = this.productService.save(
                productMapper.toEntity(productDTO)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.productMapper.toDTO(product));
    }

    @PutMapping
    public ResponseEntity<ProductDTO> update(
            @RequestBody @Valid ProductDTO productDTO) {
        Product product= this.productService.save(
                productMapper.toEntity(productDTO)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productMapper.toDTO(product));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        Product product = this.productService.findById(id);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(this.productMapper.toDTO(product));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        Product product = this.productService.findById(id);
        if(product != null){
            this.productService.deleteById(id);
        }
    }

}

