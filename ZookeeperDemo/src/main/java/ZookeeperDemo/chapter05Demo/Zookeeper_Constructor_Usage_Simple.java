package ZookeeperDemo.chapter05Demo;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class Zookeeper_Constructor_Usage_Simple implements Watcher {

	public static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	public static void main(String[] args) throws Exception {
		ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Zookeeper_Constructor_Usage_Simple());
		
		System.out.println(zookeeper.getState());
		connectedSemaphore.await();
		System.out.println("ZooKeeper session established.");
	}
	
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event: " + event);
		if(KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}

	}

}
