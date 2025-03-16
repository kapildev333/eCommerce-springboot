package org.kapildev333.ecommerce.features.address;


import org.kapildev333.ecommerce.features.authentications.User;
import org.kapildev333.ecommerce.features.authentications.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingAddressService {

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ShippingAddress> getAllShippingAddressesByUserEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        return shippingAddressRepository.findByUser(user);
    }

    public ShippingAddress getShippingAddressByIdAndUserEmail(Long id, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        return shippingAddressRepository.findByIdAndUser(id, user).orElse(null);
    }

    public ShippingAddress createShippingAddress(ShippingAddress shippingAddress, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        shippingAddress.setUser(user);
        return shippingAddressRepository.save(shippingAddress);
    }

    public ShippingAddress updateShippingAddress(Long id, ShippingAddress shippingAddressDetails, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        return shippingAddressRepository.findByIdAndUser(id, user)
                .map(shippingAddress -> {
                    shippingAddress.setType(shippingAddressDetails.getType());
                    shippingAddress.setStreet(shippingAddressDetails.getStreet());
                    shippingAddress.setCity(shippingAddressDetails.getCity());
                    shippingAddress.setState(shippingAddressDetails.getState());
                    shippingAddress.setPostalCode(shippingAddressDetails.getPostalCode());
                    shippingAddress.setCountry(shippingAddressDetails.getCountry());
                    return shippingAddressRepository.save(shippingAddress);
                })
                .orElse(null);
    }

    public boolean deleteShippingAddress(Long id, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        return shippingAddressRepository.findByIdAndUser(id, user)
                .map(shippingAddress -> {
                    shippingAddressRepository.delete(shippingAddress);
                    return true;
                })
                .orElse(false);
    }
}