package com.ia.mk.springIaQdrant.controller;

import com.ia.mk.springIaQdrant.service.SemanticSearchService;
import org.springframework.ai.document.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Ce contrôleur permet d'effectuer des recherches sémantiques directes sur la base vectorielle (Qdrant).
 * Il retourne les fragments de documents les plus pertinents par rapport à une requête fournie.
 *
 * Endpoint : GET /api/vector/search
 *
 * Paramètres de la requête :
 * - q : Requête en texte naturel pour effectuer la recherche sémantique (obligatoire)
 * - topK : Nombre maximum de résultats retournés (facultatif, par défaut 4)
 *
 * Retour : Liste de fragments (Documents) triés par pertinence avec leurs métadonnées.
 */
@RestController
@RequestMapping("/api/vector")
public class SemanticSearchController {

    private final SemanticSearchService searchService;

    public SemanticSearchController(SemanticSearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * GET  /api/vector/search
     * @param q    texte de la requête (paramètre 'q')
     * @param topK facultatif, nombre de résultats max (défaut = 4)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Document>> search(
            @RequestParam("q") String q,
            @RequestParam(value = "topK", required = false, defaultValue = "4") int topK) {

        List<Document> results = searchService.search(q, topK);
        return ResponseEntity.ok(results);
    }
}