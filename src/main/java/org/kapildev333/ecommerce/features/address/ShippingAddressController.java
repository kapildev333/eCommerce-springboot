package org.kapildev333.ecommerce.features.address;

import org.kapildev333.ecommerce.models.ShippingAddressRequestModel;
import org.kapildev333.ecommerce.utils.CommonApiResponse;
import org.kapildev333.ecommerce.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping-addresses")
public class ShippingAddressController {

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping(value = "/v1/getAllAddresses")
    public ResponseEntity<CommonApiResponse<List<ShippingAddress>>> getAllShippingAddresses() {
        String userEmail = jwtUtil.extractEmail(jwtUtil.extractTokenFromRequest());
        CommonApiResponse<List<ShippingAddress>> response = new CommonApiResponse<>(true, "All shipping addresses fetched successfully", shippingAddressService.getAllShippingAddressesByUserEmail(userEmail));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/v1/getAddressById/{id}")
    public ResponseEntity<CommonApiResponse<ShippingAddress>> getShippingAddressById(@PathVariable Long id) {
        String userEmail = jwtUtil.extractEmail(jwtUtil.extractTokenFromRequest());

        ShippingAddress shippingAddress = shippingAddressService.getShippingAddressByIdAndUserEmail(id, userEmail);
        if (shippingAddress != null) {
            return ResponseEntity.ok(new CommonApiResponse<>(true, "Shipping address fetched successfully", shippingAddress));
        }
        return ResponseEntity.status(404).body(new CommonApiResponse<>(false, "Shipping address not found", null));
    }

    @PostMapping(value = "/v1/createAddress")
    public ResponseEntity<CommonApiResponse<ShippingAddress>> createShippingAddress(@RequestBody ShippingAddressRequestModel shippingAddressRequestModel) {
        String userEmail = jwtUtil.extractEmail(jwtUtil.extractTokenFromRequest());

        ShippingAddress newShippingAddress = shippingAddressService.createShippingAddress(ShippingAddressRequestModel.getShippingAddress(shippingAddressRequestModel), userEmail);
        return ResponseEntity.ok(new CommonApiResponse<>(true, "Shipping address created successfully", newShippingAddress));
    }

    @PutMapping("/v1/updateAddress/{id}")
    public ResponseEntity<CommonApiResponse<ShippingAddress>> updateShippingAddress(@PathVariable Long id, @RequestBody ShippingAddressRequestModel shippingAddressRequestModel) {
        String userEmail = jwtUtil.extractEmail(jwtUtil.extractTokenFromRequest());

        ShippingAddress updatedShippingAddress = shippingAddressService.updateShippingAddress(id, ShippingAddressRequestModel.getShippingAddress(shippingAddressRequestModel), userEmail);
        if (updatedShippingAddress != null) {
            return ResponseEntity.ok(new CommonApiResponse<>(true, "Shipping address updated successfully", updatedShippingAddress));
        }
        return ResponseEntity.status(404).body(new CommonApiResponse<>(false, "Shipping address not found", null));
    }

    @DeleteMapping("/v1/deleteAddress/{id}")
    public ResponseEntity<CommonApiResponse<Void>> deleteShippingAddress(@PathVariable Long id) {
        String userEmail = jwtUtil.extractEmail(jwtUtil.extractTokenFromRequest());

        shippingAddressService.deleteShippingAddress(id, userEmail);
        return ResponseEntity.ok(new CommonApiResponse<>(true, "Shipping address deleted successfully", null));
    }
}
