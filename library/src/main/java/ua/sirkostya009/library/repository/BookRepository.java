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
      "bool": {
        "must": {
          "match_all": {}
        },
        "filter": {
          "nested": {
            "path": "boughtBy",
            "query": {
              "match": {
                "boughtBy": "?0"
              }
            }
          }
        }
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

    @Query("""
    {
      "script": {
        "source": "ctx._source.boughtBy.add(?1);"
      },
      "query": {
        "term": {
          "id": {
            "value": "?0"
          }
        }
      }
    }
    """)
    void addBuyer(String bookId, String userId);
}
