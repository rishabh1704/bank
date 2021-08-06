package com.finance.bank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserListDTO {
    List<UserDTO> users;
}
