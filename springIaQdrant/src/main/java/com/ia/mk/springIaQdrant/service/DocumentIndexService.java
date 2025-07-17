package com.ia.mk.springIaQdrant.service;


import org.apache.tika.Tika;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class DocumentIndexService {

    private static final int CHUNK_SIZE = 500;  // Tokens max (ajustable)

    private final Tika tika;
    private final VectorStore vectorStore;

    public DocumentIndexService(Tika tika, VectorStore vectorStore) {
        this.tika = tika;
        this.vectorStore = vectorStore;
    }

    /**
     * Extraction, découpage optimisé par token, et indexation du document.
     */
    public String indexDocument(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String content;

        // 1. Extraction fiable du contenu avec Apache Tika
        try (InputStream stream = file.getInputStream()) {
            content = tika.parseToString(stream);
        } catch (Exception e) {
            throw new RuntimeException("Erreur extraction du fichier " + filename, e);
        }

        // 2. Découpage robuste basé sur tokens (optimisé pour les modèles LLM)
        List<Document> chunks = splitIntoChunks(content, filename);

        // 3. Indexation optimisée dans Qdrant (embedding auto par Spring AI)
        vectorStore.add(chunks);

        return String.format("%s indexé avec succès (%d chunks)", filename, chunks.size());
    }

    /**
     * Utilise TokenTextSplitter pour un découpage optimisé selon les modèles LLM.
     */
    private List<Document> splitIntoChunks(String text, String filename) {
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(CHUNK_SIZE)
                .build();

        // On crée un document d'entrée
        Document inputDoc = new Document(text, Map.of("filename", filename));

        // Utiliser split(Document)
        List<Document> chunks = splitter.split(inputDoc);

        // Ajouter index manuellement
        for (int i = 0; i < chunks.size(); i++) {
            chunks.get(i).getMetadata().put("chunkIndex", i);
        }

        return chunks;
    }
}