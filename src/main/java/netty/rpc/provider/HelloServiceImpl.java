package netty.rpc.provider;

import netty.rpc.api.HelloService;

/**
 * @Classname HelloServiceImpl
 * @Description TODO
 * @Date 2020/7/27 13:43
 * @Author Danrbo
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        if (msg != null) {
            return String.format("接收到客户端发来的信息【%s】", msg);
        } else {
            return "我们已收到客户端发来的信息";
        }
    }
}
