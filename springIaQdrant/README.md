# 🔍 Recherche Sémantique avec Qdrant et Spring AI

Ce module permet d'effectuer une **recherche sémantique** dans une base vectorielle **Qdrant**, à l'aide d'un **LLM (OpenAI)** via **Spring AI**.

---

## 🚀 Fonctionnalités

- 📄 Indexation de documents texte (PDF, `.txt`, etc.)
- 🔍 Recherche sémantique sur les contenus vectorisés
- 🤖 Génération de réponse synthétique par LLM (OpenAI)
- 🧠 Utilisation d'embeddings et d'un modèle de langage via Spring AI
- 💾 Stockage vectoriel avec Qdrant

---

## ⚙️ Prérequis

- Java 21
- Maven 3+
- Docker & Docker Compose
- Clé API OpenAI (à définir dans `application.properties` ou `application.yml`)

---

## 🐳 Lancer Qdrant

Démarrer Qdrant en mode conteneur :

```bash
docker-compose -f docker-compose-qdrant.yaml up -d
```

Accéder à l'interface web de Qdrant :

➡️ [http://localhost:6333/dashboard](http://localhost:6333/dashboard)

---

## ▶️ Lancer l'application Spring Boot

Compilez et démarrez le projet :

```bash
mvn spring-boot:run
```

---

## 📥 Indexer un document texte

Vous pouvez indexer un fichier `.txt` ou `.pdf` dans la base vectorielle :

```bash
curl -X POST http://localhost:8080/api/vector/index -F file=@ia_société.txt
curl -X POST http://localhost:8080/api/vector/index -F file=@texte-factice.txt
```

---

## 🔍 Recherche sémantique (fragments de texte)

Rechercher des **fragments similaires** dans la base vectorielle :

```bash
curl --get "http://localhost:8080/api/vector/search" \
  --data-urlencode "q=Quel est le rêve de Jean" \
  --data-urlencode "topK=1"
```

---

## 🧠 Recherche avec génération par LLM

Envoyer une requête sémantique au LLM via un prompt personnalisé :

```bash
curl -X POST http://localhost:8080/api/vector/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "Quels sont les enjeux éthiques, sociaux et économiques liés à l’IA ?",
    "topK": 3,
    "promptTemplate": "Vous êtes un assistant expert en IA. Répondez de manière précise. Contexte :{context} Question :{question}"
}'
```

---

## 📁 Structure des endpoints

| Méthode | Endpoint                      | Description                                          |
|---------|-------------------------------|------------------------------------------------------|
| POST    | `/api/vector/index`          | Indexer un fichier dans Qdrant                      |
| GET     | `/api/vector/search`         | Recherche sémantique (fragments)                    |
| POST    | `/api/vector/ask`            | Recherche + génération de réponse par LLM           |

---

## ✏️ Exemple de prompt

```text
Vous êtes un assistant expert en IA. Répondez de manière précise.
Contexte : {context}
Question : {question}
```

---

## 🧪 Exemple d’usage

- Indexer un corpus juridique, scientifique ou professionnel
- Rechercher des réponses ciblées dans des documents PDF
- Construire un assistant intelligent pour les utilisateurs internes

---
