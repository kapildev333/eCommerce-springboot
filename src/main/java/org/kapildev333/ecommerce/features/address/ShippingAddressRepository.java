package org.kapildev333.ecommerce.features.address;

import org.kapildev333.ecommerce.features.authentications.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    List<ShippingAddress> findByUser(User user);
    Optional<ShippingAddress> findByIdAndUser(Long id, User user);
}