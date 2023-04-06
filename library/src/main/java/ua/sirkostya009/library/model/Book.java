package ua.sirkostya009.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "books")
public class Book {
    @Id
    private String id;
    @Field(type = FieldType.Search_As_You_Type)
    private String title;
    @Field(type = FieldType.Keyword)
    private Set<String> authors;
    @Field(type = FieldType.Keyword)
    private Set<String> genres;
    @Field(type = FieldType.Text)
    private List<String> pages;
    @Field(type = FieldType.Integer)
    private Integer pagesCount;
    @Field(type = FieldType.Integer)
    private Integer wordsCount;
    @Field(type = FieldType.Nested)
    private Set<String> boughtBy;
}
