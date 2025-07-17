package com.ia.mk.springIaQdrant.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemanticSearchService {

    private final VectorStore vectorStore;

    public SemanticSearchService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /**
     * Recherche les topK documents les plus similaires à la requête.
     *
     * @param query la requête utilisateur (texte)
     * @param topK  nombre maximum de résultats à renvoyer
     * @return liste de Documents (contenu + métadonnées)
     */
    public List<Document> search(String query, int topK) {
        // Construction d'une SearchRequest pour régler topK et seuil
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(topK)
                .build();

        // La méthode similaritySearch effectue un embedding de la query
        // puis interroge Qdrant pour retourner les documents les plus proches.
        return vectorStore.similaritySearch(request);
    }
}