package h1r0ku.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column (name = "product_name", nullable = false)
    private String productName;

    @Column (name = "description")
    private String description;

    @Column(name = "price", columnDefinition = "Decimal(10, 2) default '0.00'", nullable = false)
    private BigDecimal price = BigDecimal.valueOf(0);

    @Column(name = "quantity", columnDefinition = "Integer default 0", nullable = false)
    private Integer quantity = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;
    @Column(name = "average_start", columnDefinition = "Real(3,2) default '0.00", nullable = false)
    private Float averageStar = 0.00f;

    @Column(name = "orders_count", columnDefinition = "Integer default 0", nullable = false)
    private Integer ordersCount = 0;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}