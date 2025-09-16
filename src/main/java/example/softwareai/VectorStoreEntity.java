package example.softwareai;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "vector_store")
@NoArgsConstructor
@AllArgsConstructor
public class VectorStoreEntity {

    @Id
    private UUID id;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "jsonb")
    private String metadata;

}
