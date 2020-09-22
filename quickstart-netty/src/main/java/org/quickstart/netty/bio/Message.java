package org.quickstart.netty.bio;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>描述: [功能描述] </p >
 *
 * @author yangzl
 * @version v1.0
 * @date 2020/9/16 11:14
 */
@Data
@AllArgsConstructor
public class Message implements Serializable {
  private String content;
}