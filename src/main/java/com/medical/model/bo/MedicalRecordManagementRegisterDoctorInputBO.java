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
public class MedicalRecordManagementRegisterDoctorInputBO {
  private String account;

  private String doctorName;

  private String gender;

  private BigInteger age;

  private String deptName;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(account);
    args.add(doctorName);
    args.add(gender);
    args.add(age);
    args.add(deptName);
    return args;
  }
}
