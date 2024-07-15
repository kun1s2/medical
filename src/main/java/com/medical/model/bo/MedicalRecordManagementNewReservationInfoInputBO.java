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
public class MedicalRecordManagementNewReservationInfoInputBO {
  private String time;

  private String pm;

  private BigInteger reservationTime;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(time);
    args.add(pm);
    args.add(reservationTime);
    return args;
  }
}
