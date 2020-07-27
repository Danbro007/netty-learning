package netty.rpc.consumer;

import netty.rpc.api.HelloService;
import netty.rpc.netty.NettyClient;

import java.util.concurrent.TimeUnit;

/**
 * @Classname ClientBootstrap
 * @Description TODO
 * @Date 2020/7/27 14:46
 * @Author Danrbo
 */
public class ClientBootstrap {
    private final static String PROVIDER_NAME = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        NettyClient nettyClient = new NettyClient();
        HelloService helloService = (HelloService) nettyClient.getBean(HelloService.class, PROVIDER_NAME);
        for(;;){
            Thread.sleep(1000);
            String responseMsg = helloService.hello("Testing");
            System.out.println(String.format("调用结果为【%s】", responseMsg));
        }
    }
}



