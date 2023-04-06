package ua.sirkostya009.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.sirkostya009.library.model.Book;

import java.util.Optional;

public interface BookRepository extends ElasticsearchRepository<Book, String> {
    @Query("""
    {
      "nested": {
        "path": "boughtBy",
        "query": {
          "match": {
            "boughtBy": "?0"
          }
        },
        "score_mode": "none"
      }
    }
    """)
    Page<Book> findBoughtBy(String id, Pageable pageable);

    @Query("""
    {
      "script": {
        "source": "return doc['pages'][?1];"
      },
      "query": {
        "bool": {
          "must": [
            {
              "term": {
                "id": {
                  "value": "?0"
                }
              }
            },
            {
              "range": {
                "pagesCount": {
                  "gte": ?1
                }
              }
            }
          ]
        }
      }
    }
    """)
    Optional<String> findSinglePage(String bookId, int pageIndex);

    /**
     * Adds a uid to boughtBy property. Make sure you have elasticsearch scripting enabled.
     * @param bookId book's id to add a buyer to
     * @param userId user id of the buyer
     */
    @Query("""
    {
      "script": {
        "source": "if (ctx._source.boughtBy == null) { ctx._source.boughtBy = [buyer] } else { ctx._source.boughtBy.add(buyer) }",
        "lang": "painless",
        "params": {
          "buyer": "?1"
        }
      },
      "query": {
        "match": {
          "id": {
            "query": "?0"
          }
        }
      }
    }
    """)
    void addBuyer(String bookId, String userId);
}
