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
public class MedicalRecordManagementQueryedicalRecordInputBO {
  private BigInteger medicalId;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(medicalId);
    return args;
  }
}
