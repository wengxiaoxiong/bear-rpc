package model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object data;
    private Class<?> dataType;
    private String message;
    private Exception exception;
}
