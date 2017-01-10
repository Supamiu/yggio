import net.mmoserver.common.Session;
import net.mmoserver.packet.Packet;
import net.mmoserver.packet.PacketOpcode;
import net.mmoserver.tcp.TcpServer;
import org.junit.Before;
import org.junit.Test;
import tools.DataSender;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;

/**
 * Created by Miu on 09/01/2017.
 */
public class TcpServerTest {

    private TcpServer server;

    private Socket client;

    @Before
    public void before() throws IOException, InterruptedException {
        this.server = new TcpServer(58008, false);
        //Waiting few millis before connecting client (server is async, so it will finish init process).
        Thread.sleep(100);
        this.client = new Socket("127.0.0.1", 58008);
        this.client.setTcpNoDelay(true);
    }

    @Test
    public void test() throws IOException, InterruptedException {
        Packet mockPacket = mock(MockPacket.class);
        Packet.add(mockPacket);

        assertEquals(1, Packet.getPackets().size());

        DataSender.send(this.client, 10, "Foo");

        Thread.sleep(10);

        assertEquals(1, mockingDetails(mockPacket).getInvocations().size());
    }
}

@PacketOpcode(10)
class MockPacket extends Packet {

    @Override
    public void decodeTcp(Session session) throws IOException {
    }

    @Override
    public void decodeUdp(Session session, ByteBuffer udpBuffer) throws IOException {
    }
}