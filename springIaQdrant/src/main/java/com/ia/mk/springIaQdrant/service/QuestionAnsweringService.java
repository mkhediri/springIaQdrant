package com.ia.mk.springIaQdrant.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionAnsweringService {

    private final VectorStore vectorStore;
    private final ChatModel   chatModel;

    public QuestionAnsweringService(VectorStore vectorStore,
                                    ChatModel chatModel) {
        this.vectorStore = vectorStore;
        this.chatModel   = chatModel;
    }

    public String answer(String question, int topK, String promptTemplate) {
        // 1. Retrieval
        List<Document> fragments = vectorStore.similaritySearch(
                SearchRequest.builder().query(question).topK(topK).build()
        );

        // 2. Assemble context
        String context = fragments.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n---\n\n"));

        // 3. Build Prompt
        PromptTemplate tpl = new PromptTemplate(promptTemplate);
        Prompt prompt      = tpl.create(Map.of("context", context, "question", question));

        // 4. Call OpenAI
        ChatResponse response = chatModel.call(prompt);

        // 5. Return answer
        return response.getResult().getOutput().getText().trim();
    }
}
