package ZookeeperDemo.chapter05Demo;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class Zookeeper_Constructor_Usage_With_SID_PASSWD implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	public static void main(String[] args) throws Exception {
		ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD());
		connectedSemaphore.await();
		long sessionId = zookeeper.getSessionId();
		byte[] passwd = zookeeper.getSessionPasswd();
		
		zookeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD()	, 1l, "test".getBytes());
		
		zookeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD()	, sessionId, passwd);
		
		Thread.sleep(Integer.MAX_VALUE);
	}
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event: "+ event);
		if(KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}

	}

}
