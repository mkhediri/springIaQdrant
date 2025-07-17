package com.ia.mk.springIaQdrant.controller;

import com.ia.mk.springIaQdrant.service.QuestionAnsweringService;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Ce contrôleur expose un endpoint permettant de poser une question en langage naturel.
 * Il réalise une recherche sémantique dans la base vectorielle (Qdrant), assemble les résultats obtenus
 * en un contexte, puis interroge un modèle OpenAI via un template de prompt dynamique afin d'obtenir
 * une réponse synthétisée en langage naturel.
 *
 * Endpoint : POST /api/vector/ask
 *
 * Payload JSON attendu :
 * {
 *   "question": "Votre question en langage naturel",
 *   "topK": 3,  // Nombre de résultats sémantiques (facultatif, défaut=4)
 *   "promptTemplate": "Votre modèle de prompt avec placeholders {context} et {question}" (facultatif)
 * }
 *
 * Retour : réponse générée par le modèle en texte brut.
 */
@RestController
@RequestMapping("/api/vector")
public class QaController {

    @Value("classpath:templates/default-prompt.st")
    private Resource defaultPrompt;

    private final QuestionAnsweringService qaService;

    public QaController(QuestionAnsweringService qaService) {
        this.qaService = qaService;
    }

    @PostMapping(path = "/ask")
    public ResponseEntity<String> ask(@RequestBody Map<String, Object> payload) {
        String question       = (String) payload.get("question");
        int    topK           = (int)    payload.getOrDefault("topK", 4);

        String promptTemplate = (String) payload.getOrDefault(
                "promptTemplate", new PromptTemplate(defaultPrompt)
        );

        String answer = qaService.answer(question, topK, promptTemplate);
        return ResponseEntity.ok(answer);
    }
}