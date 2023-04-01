package ua.sirkostya009.library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import ua.sirkostya009.library.model.Book;
import ua.sirkostya009.library.repository.BookRepository;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableElasticsearchRepositories
@EnableMethodSecurity
public class LibraryMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(LibraryMicroservice.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookRepository repository) {
        return args -> {
            repository.deleteAll();

            final var minPages = 50;
            final var maxPages = minPages * 10;
            final var minWords = 50000;
            final var maxWords = 300000;

            var authors = List.of(
                    "Agatha Christie",
                    "J.K. Rowling",
                    "Stephen King",
                    "Dan Brown",
                    "George R.R. Martin",
                    "J.R.R. Tolkien",
                    "Jane Austen",
                    "Harper Lee",
                    "Mark Twain",
                    "Charles Dickens",
                    "William Shakespeare",
                    "Toni Morrison",
                    "Margaret Atwood",
                    "Maya Angelou",
                    "Gabriel García Márquez",
                    "Ernest Hemingway",
                    "F. Scott Fitzgerald",
                    "Virginia Woolf",
                    "Hermann Hesse",
                    "Leo Tolstoy",
                    "Fyodor Dostoevsky",
                    "Albert Camus",
                    "Franz Kafka",
                    "Gustave Flaubert",
                    "Emily Bronte",
                    "Charlotte Bronte",
                    "Jane Austen",
                    "Edgar Allan Poe",
                    "H.G. Wells",
                    "Jules Verne",
                    "H.P. Lovecraft",
                    "Neil Gaiman",
                    "Margaret Mitchell",
                    "Chimamanda Ngozi Adichie",
                    "Alice Walker",
                    "Zadie Smith",
                    "Don DeLillo",
                    "Thomas Pynchon",
                    "David Foster Wallace",
                    "J.D. Salinger",
                    "John Steinbeck",
                    "Ernest Gaines",
                    "Chinua Achebe",
                    "Octavia Butler",
                    "Ursula K. Le Guin"
            );
            var genres = List.of(
                    "Science Fiction",
                    "Mystery",
                    "Thriller",
                    "Romance",
                    "Fantasy",
                    "Historical Fiction",
                    "Young Adult",
                    "Horror",
                    "Biography",
                    "Memoir",
                    "Self-help",
                    "Business",
                    "Cooking",
                    "Travel",
                    "Humor",
                    "Poetry",
                    "Drama",
                    "Satire",
                    "Tragedy",
                    "Action and Adventure",
                    "Crime and Detective",
                    "Epistolary",
                    "Erotic",
                    "Fable",
                    "Fairy Tale",
                    "Graphic Novel",
                    "Magical Realism",
                    "Musical",
                    "Mythology",
                    "Paranormal",
                    "Philosophy",
                    "Political",
                    "Religious",
                    "Science",
                    "Social",
                    "Sports",
                    "Suspense",
                    "Urban",
                    "Western"
            );
            var titleWords = List.of(
                    "The",
                    "Secret",
                    "Shadow",
                    "Light",
                    "Darkness",
                    "King",
                    "Queen",
                    "Prince",
                    "Princess",
                    "Empire",
                    "Kingdom",
                    "Sword",
                    "Magic",
                    "Wizard",
                    "Witch",
                    "Dragon",
                    "Phoenix",
                    "Fire",
                    "Ice",
                    "Storm",
                    "Wind",
                    "Water",
                    "Earth",
                    "Sky",
                    "Moon",
                    "Sun",
                    "Star",
                    "Galaxy",
                    "Universe",
                    "Time",
                    "Space",
                    "Love",
                    "Hate",
                    "Friendship",
                    "Betrayal",
                    "War",
                    "Peace",
                    "Hope",
                    "Fear",
                    "Life",
                    "Death",
                    "Adventure",
                    "Mystery",
                    "Suspense",
                    "Horror",
                    "Thriller",
                    "Romance",
                    "Comedy",
                    "Drama",
                    "Action",
                    "Fantasy",
                    "Science Fiction",
                    "Historical Fiction"
            );
            var random = new Random();

            for (var i = 0; i < 20; ++i) {
                var authorsCount = random.nextInt(1, 3);
                var genresCount = random.nextInt(1, 3);
                var titleWordsCount = random.nextInt(2, 8);

                var bookAuthors = Stream.generate(() -> authors.get(random.nextInt(authors.size()))).limit(authorsCount).collect(Collectors.toSet());
                var bookGenres = Stream.generate(() -> genres.get(random.nextInt(genres.size()))).limit(genresCount).collect(Collectors.toSet());
                var bookPages = Stream.generate(() -> UUID.randomUUID().toString()).limit(random.nextInt(minPages, maxPages)).toList();
                var title = Stream.generate(() -> titleWords.get(random.nextInt(titleWords.size()))).limit(titleWordsCount).collect(Collectors.joining(" "));

                repository.save(Book.builder()
                                .title(title)
                                .pagesCount(random.nextInt(minPages, maxPages))
                                .wordsCount(random.nextInt(minWords, maxWords))
                                .authors(bookAuthors)
                                .genres(bookGenres)
                                .pages(bookPages)
                                .build());
            }
        };
    }

}
