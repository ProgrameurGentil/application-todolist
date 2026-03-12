package com.example.note_haie.model

data class Joke(
    val question: String,
    val response: String,
    val author: String
)

val jokes = listOf(
    Joke(question = "Comment appelle t on un serpent travaillant à l'éducation national ?", response = "EduPython", author = "Henzo RENET"),
    Joke(question = "Quels sont les aliments que les geeks aiment bien manger ?", response = "Les aliments BIOS", author = "Henzo RENET"),
    Joke(question = "Quelle est la lettre qui est tjrs trempé ?", response = "L'O", author = "Henzo RENET"),
    Joke(question = "Comment appelle t on un chat qui parle ", response = "Un chatmot (chameau)", author = "Henzo RENET"),
    Joke(question = "Comment appelle t on un mouton idiot ?", response = "Un mouton béééééééééééééééte", author = "Henzo RENET"),
    Joke(question = "Quel est l'animal le plus motivé ?", response = "Le ver deterre (déter)", author = "Henzo RENET"),
    Joke(question = "Quel fromage travail pour les forces de l'ordre ?", response = "Le brie (B.R.I)", author = "Henzo RENET"),
    Joke(question = "Quel marque de matèriel audio travaillait comme un service déconcentré de l'État Français ?", response = "D.A.S (ddass) (DAS est une marque d'haut parleur)", author = "Henzo RENET"),
    Joke(question = "Comment font deux couteaux pour jouer en multijoueur sur un jeu vidéo ?", response = "Ils jouent en lame (LAN)", author = "Henzo RENET"),
    Joke(question = "Qu'est ce que met un informaticien bas niveau quand il a froid ?", response = "Une C# (une écharpe)", author = "Henzo RENET"),
    Joke(question = "Comment appelle-t-on une petite montagne en informatique ?", response = "Une kotlin (une colline)", author = "Henzo RENET"),
    Joke(question = "Qu'est ce qui n'est vraiment pas lourd ?", response = "Une palourde", author = "Henzo RENET"),
)

