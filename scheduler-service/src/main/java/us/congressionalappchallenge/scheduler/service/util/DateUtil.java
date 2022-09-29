package us.congressionalappchallenge.scheduler.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
  public static Date convert(String input) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
      return sdf.parse(input);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
