package model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RpcResponse {
    private Object data;
    private Class<?> dataType;
    private String message;
    private Exception exception;
}
