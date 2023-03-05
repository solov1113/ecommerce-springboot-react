package h1r0ku.entity;

import h1r0ku.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", columnDefinition = "VARCHAR(20) default 'PENDING'", nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Column(name = "order_description")
    private String orderDescription;

    @Column(name = "total_price", columnDefinition = "Decimal(10,2) default '0.00'", nullable = false)
    private BigDecimal totalPrice = new BigDecimal(0);

//    add tax discount

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public void increaseTotalPrice(BigDecimal value) {
        setTotalPrice(this.totalPrice.add(value));
    }

    public void addItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public void removeItem(Long itemId) {
        this.orderItems.removeIf(item -> item.getId().equals(itemId));
    }

}
