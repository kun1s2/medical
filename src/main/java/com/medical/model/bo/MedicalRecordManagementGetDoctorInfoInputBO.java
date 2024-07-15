package com.medical.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordManagementGetDoctorInfoInputBO {
  private String _id;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(_id);
    return args;
  }
}
