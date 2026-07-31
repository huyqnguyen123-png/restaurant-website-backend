package com.restaurantwebsite.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pickup_orders")
@Data
@NoArgsConstructor
public class PickupOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    // Optional — useful for the restaurant to call the customer if needed,
    // but not part of the minimum required fields.
    private String phone;

    @Column(nullable = false)
    private LocalDateTime pickupTime;

    // Snapshot of the order total at submission time (sum of each item's
    // unitPriceSnapshot * quantity) — not recalculated later even if POS
    // prices change afterward.
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SyncStatus syncStatus = SyncStatus.PENDING;

    // The order id this was assigned once created in the POS backend
    // (null until successfully synced).
    private Long posOrderId;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pickupOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PickupOrderItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}