package com.hotactress.hot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by shubhamagrawal on 06/07/18.
 */


@Data @AllArgsConstructor @NoArgsConstructor
public class Conv {
   public String id;
   public boolean seen;
   public long timestamp;
}
