package com.restaurantwebsite.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Table(name = "pickup_order_items")
@Data
@NoArgsConstructor
public class PickupOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_order_id", nullable = false)
    private PickupOrder pickupOrder;

    // Nullable: if the linked website MenuItem is later deleted, this order
    // history row survives intact (the snapshot fields below still show
    // what was ordered).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    // Snapshots taken at order time, so this row remains accurate even if
    // the menu item's name/price changes (or the item is removed) afterward.
    @Column(nullable = false)
    private String itemNameSnapshot;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPriceSnapshot;

    @Column(nullable = false)
    private int quantity;
}