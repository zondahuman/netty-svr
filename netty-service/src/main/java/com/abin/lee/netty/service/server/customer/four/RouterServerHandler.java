package com.abin.lee.netty.service.server.customer.four;


import com.abin.lee.netty.model.proto.MsgHeaderProto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RouterServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(RouterServerHandler.class);
    private static final AttributeKey<ByteBuf> cumulationKey = AttributeKey.valueOf("cululation");

    private final ByteToMessageDecoder.Cumulator MERGE_CUMULATOR = new ByteToMessageDecoder.Cumulator() {
        @Override
        public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
            ByteBuf buffer;
            if (cumulation.writerIndex() > cumulation.maxCapacity() - in.readableBytes() || cumulation.refCnt() > 1) {
                buffer = expandCumulation(alloc, cumulation, in.readableBytes());
            } else {
                buffer = cumulation;
            }
            buffer.writeBytes(in);
            in.release();
            return buffer;
        }
    };

    private ByteBuf expandCumulation(ByteBufAllocator alloc, ByteBuf cumulation, int readable) {
        ByteBuf oldCumulation = cumulation;
        cumulation = alloc.buffer(oldCumulation.readableBytes() + readable);
        cumulation.writeBytes(oldCumulation);
        oldCumulation.release();
        return cumulation;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof ByteBuf) {
            Attribute<ByteBuf> cumulationAttr = ctx.channel().attr(cumulationKey);
            ByteBuf cumulationValue = cumulationAttr.get();
            ByteBuf in = (ByteBuf) msg;
            try {
                boolean first = cumulationValue == null;
                if (first) {
                    boolean notLessThan4 = false;
                    try {
                        if (in.readableBytes() >= 4) {
                            in.markReaderIndex();
                            int totalLen = in.readInt();
                            if (totalLen > 1 * 1024 * 1024)// 1m
                            {
                                logger.warn("C2GRouter--->Message too big!!" + totalLen + "bytes");
                                in.release();
                                ctx.close();
                                return;
                            }
                            in.resetReaderIndex();
                            ByteBuf container = PooledByteBufAllocator.DEFAULT.buffer(totalLen + 4);
                            container.writeBytes(in);
                            cumulationValue = container;
                            notLessThan4 = true;
                        } else {
                            cumulationValue = in;
                        }
                    } finally {
                        if (notLessThan4) {
                            in.release();
                        }
                    }
                } else {
                    cumulationValue = MERGE_CUMULATOR.cumulate(ctx.alloc(), cumulationValue, in);
                }

                // 保存合并结果
                cumulationAttr.set(cumulationValue);
                while (cumulationValue.readableBytes() >= 4) {
                    cumulationValue.markReaderIndex();
                    int totalLen = cumulationValue.readInt();
                    if (cumulationValue.readableBytes() < totalLen) {
                        cumulationValue.resetReaderIndex();
                        logger.warn("C2GRouter--->Package is not completed!!");
                        break;
                    }
                    // 解析头
                    int headerLen = cumulationValue.readInt();
                    byte[] headerBytes = new byte[headerLen];
                    cumulationValue.readBytes(headerBytes);
                    MsgHeaderProto.MsgHeader msgHeader = MsgHeaderProto.MsgHeader.parseFrom(headerBytes);
                    // 解析消息体
                    byte[] bodyBytes = new byte[totalLen - 4 - headerLen];
                    cumulationValue.readBytes(bodyBytes);
                    // 封装成request


                }
            } finally {
                // 如果没有可读数据释放buffer
                if (cumulationValue != null && !cumulationValue.isReadable()) {
                    cumulationValue.release();
                    cumulationValue = null;
                    cumulationAttr.set(null);
                }
            }
        }


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
