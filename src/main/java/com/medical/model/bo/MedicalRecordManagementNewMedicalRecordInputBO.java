package com.medical.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordManagementNewMedicalRecordInputBO {
  private String patientID;

  private String department;

  private String doctorName;

  private String registrationInfo;

  private String pastMedicalHistory;

  private String currentMedicalHistory;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(patientID);
    args.add(department);
    args.add(doctorName);
    args.add(registrationInfo);
    args.add(pastMedicalHistory);
    args.add(currentMedicalHistory);
    return args;
  }
}
