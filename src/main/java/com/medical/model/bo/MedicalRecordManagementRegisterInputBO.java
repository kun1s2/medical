package com.medical.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordManagementRegisterInputBO {
  private String account;

  private String name;

  private String gender;

  private BigInteger age;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(account);
    args.add(name);
    args.add(gender);
    args.add(age);
    return args;
  }
}
