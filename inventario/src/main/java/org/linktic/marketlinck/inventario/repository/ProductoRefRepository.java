package org.linktic.marketlinck.inventario.repository;

import org.linktic.marketlinck.inventario.model.ProductoRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRefRepository extends JpaRepository<ProductoRef, Long> {
}