package com.fpoly.java6.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java6.entities.Address;
import com.fpoly.java6.jpa.AddressJPA;
import com.fpoly.java6.model.dto.AddressDTO;
import com.fpoly.java6.model.mapper.AddressMapper;
import com.fpoly.java6.service.GHNService;

@RestController
@RequestMapping("/api/locations")
public class GHNController {

	private final GHNService ghnService;

	private AddressJPA addressJPA;

	@Autowired
	public GHNController(GHNService ghnService, AddressJPA addressJPA) {
	    this.ghnService = ghnService;
	    this.addressJPA = addressJPA;
	}

	// Lấy danh sách tỉnh
	@GetMapping("/provinces")
	public ResponseEntity<?> getProvinces() {
		return ResponseEntity.ok(ghnService.getProvinces());
	}

	// Lấy danh sách quận theo tỉnh đã chọn
	@GetMapping("/districts")
	public ResponseEntity<?> getDistricts(@RequestParam String provinceId) {
		try {
			// Chuyển provinceId sang kiểu int
			int provinceIdInt = Integer.parseInt(provinceId);

			// Gọi service để lấy danh sách quận
			return ResponseEntity.ok(ghnService.getDistricts(provinceIdInt));
		} catch (NumberFormatException e) {
			// Xử lý lỗi nếu provinceId không thể chuyển thành số nguyên
			return ResponseEntity.badRequest().body("Province ID không hợp lệ");
		}
	}

	@GetMapping("/wards")
	public ResponseEntity<?> getWards(@RequestParam String districtId) {
		try {
			// Chuyển districtId sang kiểu int
			int districtIdInt = Integer.parseInt(districtId);

			// Gọi service để lấy danh sách phường theo districtId
			return ResponseEntity.ok(ghnService.getWards(districtIdInt));
		} catch (NumberFormatException e) {
			// Xử lý lỗi nếu districtId không thể chuyển thành số nguyên
			return ResponseEntity.badRequest().body("District ID không hợp lệ");
		}
	}

	@PostMapping("/calculate-fee")
	public ResponseEntity<Map<String, Object>> calculateShippingFee(@RequestParam int fromProvince,
			@RequestParam int fromDistrict, @RequestParam int fromWard, @RequestParam int toProvince,
			@RequestParam int toDistrict, @RequestParam int toWard, @RequestParam(required = false) Double weight) {

		try {
			Map<String, Object> shippingFeeResponse = ghnService.calculateShippingFee(fromProvince, fromDistrict,
					fromWard, toProvince, toDistrict, toWard, weight);

			return ResponseEntity.ok(shippingFeeResponse);
		} catch (Exception e) {
			e.printStackTrace();
			// In thêm thông tin lỗi chi tiết vào log
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Có lỗi xảy ra trong quá trình tính phí vận chuyển: " + e.getMessage()));
		}
	}

	@GetMapping("/addresses")
	public ResponseEntity<List<AddressDTO>> getAddressesByAccountId(@RequestParam int accountId) {
		// Assuming you have a service method that gets addresses for a specific account
		List<Address> addresses = ghnService.findByAccountId(accountId);

		if (addresses.isEmpty()) {
			return ResponseEntity.noContent().build(); // Return 204 if no addresses found
		}

		// Convert addresses to AddressDTO and return
		List<AddressDTO> addressDTOs = addresses.stream().map(AddressMapper::toDTO).collect(Collectors.toList());

		return ResponseEntity.ok(addressDTOs);
	}

	// Example of returning a valid JSON response in a Spring controller
	@PostMapping("/save-address")
	public ResponseEntity<?> saveAddress(@RequestBody AddressDTO addressDTO) {
		try {
			ghnService.saveAddress(addressDTO);
			return ResponseEntity.ok("{\"message\": \"Address saved successfully\"}");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"An error occurred while saving the address\"}");
		}
	}

	@DeleteMapping("/delete-address/{addressId}")
	public ResponseEntity<?> deleteAddress(@PathVariable int addressId, @RequestParam(required = false) Integer accountId) {
	    try {
	        // Kiểm tra nếu accountId không hợp lệ
	        if (accountId == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("accountId không hợp lệ.");
	        }

	        // Kiểm tra nếu địa chỉ tồn tại
	        Optional<Address> addressOptional = addressJPA.findById(addressId);
	        if (!addressOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Địa chỉ không tồn tại.");
	        }

	        Address address = addressOptional.get();

	        // Kiểm tra nếu tài khoản không sở hữu địa chỉ này
	        if (address.getAccount().getId() != accountId) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Địa chỉ không thuộc tài khoản này.");
	        }

	        // Tiến hành xóa địa chỉ
	        addressJPA.delete(address);

	        return ResponseEntity.status(HttpStatus.OK).body("Địa chỉ đã được xóa thành công.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa địa chỉ: " + e.getMessage());
	    }
	}






	@PostMapping("/set-default-address/{id}")
	public ResponseEntity<Void> setDefaultAddress(@PathVariable int id) {
		// Đặt tất cả địa chỉ không là mặc định
		List<Address> addresses = addressJPA.findAll();
		addresses.forEach(address -> {
			address.setActived(false);
			addressJPA.save(address);
		});

		// Đặt địa chỉ được chọn làm mặc định
		Address address = addressJPA.findById(id).orElseThrow(() -> new RuntimeException("Địa chỉ không tồn tại"));
		address.setActived(true);
		addressJPA.save(address);

		return ResponseEntity.ok().build();
	}

}
