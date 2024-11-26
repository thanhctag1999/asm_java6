package com.fpoly.java6.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Address;
import com.fpoly.java6.jpa.AddressJPA;
import com.fpoly.java6.model.dto.AddressDTO;
import com.fpoly.java6.model.mapper.AddressMapper;

@Service
public class GHNService {
	@Autowired
	AddressJPA addressJPA;
	
	@Value("${ghn.api.token}")
	private String apiToken;

	@Value("${ghn.api.url}")
	private String apiUrl;


	private final RestTemplate restTemplate;

	private AccountService accountService;

	 @Autowired
	    public GHNService(AccountService accountService) {
	        this.restTemplate = new RestTemplate();
			this.accountService = accountService;
	    }

	// Lấy danh sách tỉnh
	public Map<String, Object> getProvinces() {
		String url = apiUrl + "/shiip/public-api/master-data/province";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Token", apiToken);

		HttpEntity<Void> entity = new HttpEntity<>(headers);

		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
		return response.getBody();
	}

	// Lấy danh sách quận theo tỉnh
	public Map<String, Object> getDistricts(int provinceIdInt) {
		String url = apiUrl + "/shiip/public-api/master-data/district";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Token", apiToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("province_id", provinceIdInt);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
		return response.getBody();
	}

	// Lấy danh sách phường theo quận và phường
	public Map<String, Object> getWards(int districtId) {
		String url = apiUrl + "/shiip/public-api/master-data/ward";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Token", apiToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("district_id", districtId);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
		return response.getBody();
	}

	// fee ship
	public Map<String, Object> calculateShippingFee(int fromProvince, int fromDistrict, int fromWard, int toProvince,
            int toDistrict, int toWard, Double weight) {
    try {
        String apiUrl = "https://api-giaohangtietkiem.vn/calculate-fee?fromProvince=" + fromProvince + 
                        "&fromDistrict=" + fromDistrict + "&fromWard=" + fromWard + 
                        "&toProvince=" + toProvince + "&toDistrict=" + toDistrict + 
                        "&toWard=" + toWard + "&weight=" + weight;

        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody(); // Trả về kết quả khi thành công
        } else {
            throw new RuntimeException("Lỗi khi gọi API tính phí vận chuyển: " + response.getStatusCode());
        }
    } catch (Exception e) {
        e.printStackTrace();
        return Map.of("error", "Lỗi hệ thống khi tính phí vận chuyển: " + e.getMessage());
    }
}
	
	//save
	public AddressDTO saveAddress(AddressDTO addressDTO) {
	    // Fetch the Account object based on accountId
	    Account account = accountService.findById(addressDTO.getAccountId());
	    
	    // Convert AddressDTO to Address entity with the fetched Account object
	    Address address = AddressMapper.toEntity(addressDTO, account);
	    
	    // Save the Address entity to the database
	    Address savedAddress = addressJPA.save(address);
	    
	    // Return the saved Address as AddressDTO
	    return AddressMapper.toDTO(savedAddress);
	}

	public List<Address> findByAccountId(Integer accountId) {
	    return addressJPA.findByAccountId(accountId);  // Make sure you have this method in your JPA repository
	}


}