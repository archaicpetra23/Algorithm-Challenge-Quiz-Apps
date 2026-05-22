# AGENT.md — Algorithm Challenge Quiz

> File ini dibuat untuk memudahkan agent AI lain memahami konteks proyek ini tanpa perlu membaca semua kode dari awal.

## Deskripsi Proyek

**Algorithm Challenge Quiz** adalah aplikasi edukasi interaktif berbasis **JavaFX** yang membantu pengguna memahami algoritma dan struktur data melalui:
- Quiz multiple choice berjenjang (Easy/Medium/Hard)
- Visualisasi Sorting (Bubble/Selection/Insertion Sort)
- Visualisasi Graph Traversal (BFS & DFS)
- Visualisasi Stack & Queue (push/pop interaktif)
- Drag-and-Drop challenge (susun langkah algoritma)
- Leaderboard tersimpan di SQLite

## Tech Stack

| Komponen | Teknologi |
|---|---|
| Language | Java 21+ |
| GUI | JavaFX (FXML + CSS) |
| Database | SQLite via Hibernate ORM |
| Build | Maven 3.9 |

## Cara Menjalankan

```bash
cd "/home/razan/Documents/Algorithm Challenge Quiz"
mvn javafx:run
```

## Struktur Penting

```
src/main/java/com/algorithmquiz/
├── App.java                     ← Entry point, navigasi antar scene
├── model/                       ← Entity JPA (Question, LeaderboardEntry, Player)
├── database/DatabaseManager.java← Hibernate SessionFactory
├── repository/                  ← CRUD via Hibernate
├── factory/QuestionFactory.java ← Factory Pattern
├── strategy/                    ← Strategy Pattern (MultipleChoice, DragDrop)
├── manager/                     ← SRP: QuizManager, ScoreManager, TimerManager
├── controller/                  ← JavaFX Controllers (6 screen)
└── util/DataSeeder.java         ← Seed 30 soal ke DB

src/main/resources/
├── fxml/          ← 6 file FXML (main-menu, setup, quiz, result, leaderboard, learn)
└── css/app.css    ← Semua style JavaFX
```

## Screen / Flow

```
main-menu → setup → quiz → result → leaderboard
                       ↓
                     learn (tab: sorting/graph/structure/dragdrop)
```

## Database Schema (SQLite)

**Tabel `questions`**: id, level, type, question_text, option_a–d, correct_answer, explanation, category, time_limit

**Tabel `leaderboard`**: id, player_name, score, level, play_date, correct_count, total_questions

## Design Pattern

- **Factory**: `QuestionFactory` — membuat object Question
- **Strategy**: `QuizStrategy` → `MultipleChoiceStrategy`, `DragDropStrategy`
- **SRP**: Kelas terpisah untuk Quiz, Score, Timer management

## Color Palette

- Background: `#F5EEEE`
- Accent (Red): `#C62828`
- Text: `#000000` / `#3D3535`
- Border: `#E8E0E0`

## Status Saat Ini

- ✅ Semua file Java selesai dibuat
- ✅ Semua FXML selesai dibuat
- ✅ CSS selesai
- ✅ 30 soal (10 Easy + 10 Medium + 10 Hard)
- ✅ Module-info.java selesai

## Jika Ada Bug / Error

1. Pastikan Java 21+ dan Maven 3.9+ terinstall
2. Jalankan `mvn dependency:resolve` untuk download deps
3. Database SQLite dibuat otomatis di direktori project (`algorithm_quiz.db`)
4. Soal di-seed otomatis saat pertama kali jalan

## Kontak Developer

Proyek UAS — dibuat dengan Antigravity AI Agent
