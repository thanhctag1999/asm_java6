package com.fpoly.java6.model.dto;
import java.util.List;
import lombok.Data;

@Data
public class AccountDTO {
    private int id;
    private String name;
    private String phone;
    private boolean gender;
    private String email;
    private String image;
    private int role;
    private boolean isActived;
    private List<AddressDTO> addresses;

    // Constructor
    public AccountDTO(int id, String name, String phone, boolean gender, String email, String image, int role, boolean isActived, List<AddressDTO> addresses) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.image = image;
        this.role = role;
        this.isActived = isActived;
        this.addresses = addresses;
    }
}
