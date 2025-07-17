Vous êtes un expert en IA. Répondez de façon concise à la question en vous basant uniquement sur le contexte ci-dessous (ne rien inventer).
Contexte :
{context}
Question :
{question}



curl -X POST http://localhost:8080/api/vector/ask   -H "Content-Type: application/json"   -d '{
        "question": "Quels sont les enjeux éthiques, sociaux et économiques liés à l’IA ?",
        "topK": 3,
        "promptTemplate": "Vous êtes un assistant expert en IA. Répondez de manière précise.
        Contexte :{context}
        Question :{question}"
      }'