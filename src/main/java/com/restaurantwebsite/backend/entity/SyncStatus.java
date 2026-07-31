package com.restaurantwebsite.backend.entity;

// Tracks whether a customer-submitted reservation/pickup order has been
// successfully relayed to the POS backend yet. Kept as PENDING immediately
// after the customer submits, then updated right after the sync attempt —
// so a temporary network issue with the POS backend never loses the
// customer's request, just leaves it flagged for retry.
public enum SyncStatus {
    PENDING,
    SYNCED,
    FAILED
}