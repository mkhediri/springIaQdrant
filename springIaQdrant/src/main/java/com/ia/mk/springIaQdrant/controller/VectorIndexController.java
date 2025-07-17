package com.ia.mk.springIaQdrant.controller;

import com.ia.mk.springIaQdrant.service.DocumentIndexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Ce contrôleur expose un endpoint permettant d'indexer des fichiers (par exemple PDF ou texte)
 * dans la base vectorielle Qdrant. Il extrait le contenu textuel du fichier fourni,
 * le découpe en plusieurs fragments (chunks) et génère automatiquement les embeddings
 * correspondants pour les stocker dans Qdrant via Spring AI.
 *
 * Endpoint : POST /api/vector/index
 *
 * Paramètres :
 * - file : Fichier multipart à indexer (format texte, PDF, etc.)
 *
 * Réponse :
 * {
 *   "filename": "nom-original-du-fichier",
 *   "status": "indexed"
 * }
 */

@RestController
@RequestMapping("/api/vector")
public class VectorIndexController {

    private final DocumentIndexService indexService;

    public VectorIndexController(DocumentIndexService indexService) {
        this.indexService = indexService;
    }

    /**
     * POST /api/vector/index
     * Form-data: file (MultipartFile)
     * → indexe le document dans Qdrant
     */
    @PostMapping("/index")
    public ResponseEntity<Map<String,String>> uploadAndIndex(
            @RequestParam("file") MultipartFile file) {
        String filename = indexService.indexDocument(file);
        return ResponseEntity.ok(
            Map.of("filename", filename, "status", "indexed"));
    }
}
