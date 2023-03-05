package h1r0ku.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "src")
    private String src;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductImage(String src, Product product) {
        this.src = src;
        this.product = product;
    }
}
