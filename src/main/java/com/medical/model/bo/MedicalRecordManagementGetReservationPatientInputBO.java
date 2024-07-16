package com.medical.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordManagementGetReservationPatientInputBO {
  private List<String> patientIds;

  private String doctorId;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(patientIds);
    args.add(doctorId);
    return args;
  }
}
