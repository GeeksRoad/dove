package zg.dove.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import zg.dove.filter.IFilter;
import zg.dove.route.IRoute;


public class NetChannelInitializer extends ChannelInitializer {

    private final NetChannelHandler netChannelHandler;

    public NetChannelInitializer(IFilter filter, IRoute route) {
        this.netChannelHandler = new NetChannelHandler(filter, route);
    }

    @Override
    protected void initChannel(Channel ch) {
        ch.pipeline()
                .addLast("idle", new IdleStateHandler(NetChannelConfig.READDER_IDLE_TIME, NetChannelConfig.WRITER_IDLE_TIME,
                        NetChannelConfig.ALL_IDLE_TIME))
                .addLast("codec", new NetChannelCodec())
                .addLast("channel", this.netChannelHandler);
    }
}
