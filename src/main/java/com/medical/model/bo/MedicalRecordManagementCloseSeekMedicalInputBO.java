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
public class MedicalRecordManagementCloseSeekMedicalInputBO {
  private String patientID;

  private BigInteger id;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(patientID);
    args.add(id);
    return args;
  }
}
