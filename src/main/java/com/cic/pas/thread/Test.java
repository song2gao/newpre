//package com.cic.pas.thread;
//
//public class Test {
//    public void test(){
//        connector = new NioSocketConnector();  //创建连接客户端
//        connector.setConnectTimeoutMillis(30000); //设置连接超时
////      断线重连回调拦截器
//        connector.getFilterChain().addFirst("reconnection", new IoFilterAdapter() {
//            @Override
//            public void sessionClosed(NextFilter nextFilter, IoSession ioSession) throws Exception {
//                for(;;){
//                    try{
//                        Thread.sleep(3000);
//                        ConnectFuture future = connector.connect();
//                        future.awaitUninterruptibly();// 等待连接创建成功
//                        session = future.getSession();// 获取会话
//                        if(session.isConnected()){
//                            logger.info("断线重连["+ connector.getDefaultRemoteAddress().getHostName() +":"+ connector.getDefaultRemoteAddress().getPort()+"]成功");
//                            break;
//                        }
//                    }catch(Exception ex){
//                        logger.info("重连服务器登录失败,3秒再连接一次:" + ex.getMessage());
//                    }
//                }
//            }
//        });
//
//        connector.getFilterChain().addLast("mdc", new MdcInjectionFilter());
//        TextLineCodecFactory factory = new TextLineCodecFactory(Charset.forName(encoding), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue());
//        factory.setDecoderMaxLineLength(10240);
//        factory.setEncoderMaxLineLength(10240);
//        //加入解码器
//        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(factory));
//
//        connector.getSessionConfig().setReceiveBufferSize(10240);   // 设置接收缓冲区的大小
//        connector.getSessionConfig().setSendBufferSize(10240);// 设置输出缓冲区的大小
//
//        connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30000);  //读写都空闲时间:30秒
//        connector.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 40000);//读(接收通道)空闲时间:40秒
//        connector.getSessionConfig().setIdleTime(IdleStatus.WRITER_IDLE, 50000);//写(发送通道)空闲时间:50秒
//
//        //添加处理器
//        connector.setHandler(new IoHandler());
//
//        connector.setDefaultRemoteAddress(new InetSocketAddress(host, port));// 设置默认访问地址
//        for (;;) {
//            try {
//                ConnectFuture future = connector.connect();
//                // 等待连接创建成功
//                future.awaitUninterruptibly();
//                // 获取会话
//                session = future.getSession();
//                logger.error("连接服务端" + host + ":" + port + "[成功]" + ",,时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//                break;
//            } catch (RuntimeIoException e) {
//                System.out.println("连接服务端" + host + ":" + port + "失败" + ",,时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ", 连接MSG异常,请检查MSG端口、IP是否正确,MSG服务是否启动,异常内容:" + e.getMessage());
//                logger.error("连接服务端" + host + ":" + port + "失败" + ",,时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ", 连接MSG异常,请检查MSG端口、IP是否正确,MSG服务是否启动,异常内容:" + e.getMessage(), e);
//                Thread.sleep(5000);// 连接失败后,重连10次,间隔30s
//            }
//        }
//    }
//}
