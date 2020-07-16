package netty.tcp.protocoltcp;

import lombok.Builder;
import lombok.Data;

/**
 * @Classname MessageProtocol
 * @Description TODO
 * @Date 2020/7/16 20:29
 * @Author Danrbo
 */
@Data
@Builder
public class MessageProtocol {
    private int length;
    private byte[] content;
}
