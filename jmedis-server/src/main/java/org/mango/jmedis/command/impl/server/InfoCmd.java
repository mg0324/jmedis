package org.mango.jmedis.command.impl.server;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.mango.jmedis.annotation.Cmd;
import org.mango.jmedis.core.JMedisClient;
import org.mango.jmedis.command.BaseCmd;
import org.mango.jmedis.core.config.ServerConf;
import org.mango.jmedis.response.CmdResponse;
import org.mango.jmedis.response.bean.Info;
import org.mango.jmedis.response.bean.InfoBlock;
import org.mango.jmedis.core.ClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回服务器信息
 * # server
 * # client
 * # cpu
 * # memory
 * ...
 * @Description info 命令
 * @Date 2021-10-30 09:28
 * @Created by mango
 */
@Cmd
public class InfoCmd extends BaseCmd<Info> {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    // sigar系统信号对象
    private Sigar sigar;

    public InfoCmd(){
        sigar = new Sigar();
    }

    @Override
    public CmdResponse<Info> execute(JMedisClient client, String[] param) {
        try {
            Info data = new Info();
            List<InfoBlock> blockList = new ArrayList<>();
            // server
            blockList.add(wrapServer());
            // clients
            blockList.add(wrapClients());
            data.setBlockList(blockList);
            return this.renderUseInfo(data);
        }catch (Exception e){
            log.error("execute info error,{}",e.getMessage(),e);
            return null;
        }
    }
    /**
     * 封装客户端信息
     * @return
     */
    private InfoBlock wrapClients() {
        InfoBlock block = new InfoBlock();
        block.setName("Clients");
        Map<String,String> props = new HashMap<>();
        props.put("connected_clients", ClientFactory.current().size()+"");
        block.setProps(props);
        return block;
    }

    /**
     * 封装服务端信息
     * @return
     */
    private InfoBlock wrapServer() throws SigarException {
        InfoBlock block = new InfoBlock();
        block.setName("Server");
        Map<String,String> props = new HashMap<>();
        props.put("jmedis_version", ServerConf.getConf().getVersion());
        props.put("jmedis_buffer_size",ServerConf.getConf().getBufferSize()+"KB");
        props.put("jmedis_dbsize",ServerConf.getConf().getDbSize()+"");
        props.put("os",System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version"));
        props.put("java_version",System.getProperty("java.version"));
        props.put("tcp_port",ServerConf.getConf().getPort()+"");
        props.put("process_id",sigar.getPid()+"");
        props.put("uptime",sigar.getUptime().getUptime()+"");
        props.put("config_file",ServerConf.getConf().getConfigFile());
        block.setProps(props);
        return block;
    }

    @Override
    public boolean expect(String[] param) {
        return this.sizeEq(param,0);
    }

}
/**
 * redis info命令：
 * # Server
 * redis_version:6.2.6
 * redis_git_sha1:00000000
 * redis_git_dirty:0
 * redis_build_id:1527eab61b27d3bf
 * redis_mode:standalone
 * os:Linux 5.10.25-linuxkit x86_64
 * arch_bits:64
 * multiplexing_api:epoll
 * atomicvar_api:atomic-builtin
 * gcc_version:10.2.1
 * process_id:20
 * process_supervised:no
 * run_id:5b539351a8ff802029822dc1e87ee88cfbebe245
 * tcp_port:6379
 * server_time_usec:1635561654292828
 * uptime_in_seconds:302810
 * uptime_in_days:3
 * hz:10
 * configured_hz:10
 * lru_clock:8171702
 * executable:/root/redis-server
 * config_file:
 * io_threads_active:0
 *
 * # Clients
 * connected_clients:1
 * cluster_connections:0
 * maxclients:10000
 * client_recent_max_input_buffer:16
 * client_recent_max_output_buffer:0
 * blocked_clients:0
 * tracking_clients:0
 * clients_in_timeout_table:0
 *
 * # Memory
 * used_memory:875128
 * used_memory_human:854.62K
 * used_memory_rss:7675904
 * used_memory_rss_human:7.32M
 * used_memory_peak:933136
 * used_memory_peak_human:911.27K
 * used_memory_peak_perc:93.78%
 * used_memory_overhead:830448
 * used_memory_startup:809880
 * used_memory_dataset:44680
 * used_memory_dataset_perc:68.48%
 * allocator_allocated:1092360
 * allocator_active:1359872
 * allocator_resident:3723264
 * total_system_memory:2084691968
 * total_system_memory_human:1.94G
 * used_memory_lua:37888
 * used_memory_lua_human:37.00K
 * used_memory_scripts:0
 * used_memory_scripts_human:0B
 * number_of_cached_scripts:0
 * maxmemory:0
 * maxmemory_human:0B
 * maxmemory_policy:noeviction
 * allocator_frag_ratio:1.24
 * allocator_frag_bytes:267512
 * allocator_rss_ratio:2.74
 * allocator_rss_bytes:2363392
 * rss_overhead_ratio:2.06
 * rss_overhead_bytes:3952640
 * mem_fragmentation_ratio:9.22
 * mem_fragmentation_bytes:6843544
 * mem_not_counted_for_evict:0
 * mem_replication_backlog:0
 * mem_clients_slaves:0
 * mem_clients_normal:20496
 * mem_aof_buffer:0
 * mem_allocator:jemalloc-5.1.0
 * active_defrag_running:0
 * lazyfree_pending_objects:0
 * lazyfreed_objects:0
 *
 * # Persistence
 * loading:0
 * current_cow_size:0
 * current_cow_size_age:0
 * current_fork_perc:0.00
 * current_save_keys_processed:0
 * current_save_keys_total:0
 * rdb_changes_since_last_save:0
 * rdb_bgsave_in_progress:0
 * rdb_last_save_time:1635556015
 * rdb_last_bgsave_status:ok
 * rdb_last_bgsave_time_sec:0
 * rdb_current_bgsave_time_sec:-1
 * rdb_last_cow_size:192512
 * aof_enabled:0
 * aof_rewrite_in_progress:0
 * aof_rewrite_scheduled:0
 * aof_last_rewrite_time_sec:-1
 * aof_current_rewrite_time_sec:-1
 * aof_last_bgrewrite_status:ok
 * aof_last_write_status:ok
 * aof_last_cow_size:0
 * module_fork_in_progress:0
 * module_fork_last_cow_size:0
 *
 * # Stats
 * total_connections_received:4
 * total_commands_processed:29
 * instantaneous_ops_per_sec:0
 * total_net_input_bytes:829
 * total_net_output_bytes:49936
 * instantaneous_input_kbps:0.00
 * instantaneous_output_kbps:0.00
 * rejected_connections:0
 * sync_full:0
 * sync_partial_ok:0
 * sync_partial_err:0
 * expired_keys:0
 * expired_stale_perc:0.00
 * expired_time_cap_reached_count:0
 * expire_cycle_cpu_milliseconds:9371
 * evicted_keys:0
 * keyspace_hits:1
 * keyspace_misses:0
 * pubsub_channels:0
 * pubsub_patterns:0
 * latest_fork_usec:7779
 * total_forks:2
 * migrate_cached_sockets:0
 * slave_expires_tracked_keys:0
 * active_defrag_hits:0
 * active_defrag_misses:0
 * active_defrag_key_hits:0
 * active_defrag_key_misses:0
 * tracking_total_keys:0
 * tracking_total_items:0
 * tracking_total_prefixes:0
 * unexpected_error_replies:0
 * total_error_replies:10
 * dump_payload_sanitizations:0
 * total_reads_processed:40
 * total_writes_processed:36
 * io_threaded_reads_processed:0
 * io_threaded_writes_processed:0
 *
 * # Replication
 * role:master
 * connected_slaves:0
 * master_failover_state:no-failover
 * master_replid:31040157716ef9b1e3b2076280e10cd8f71882bc
 * master_replid2:0000000000000000000000000000000000000000
 * master_repl_offset:0
 * second_repl_offset:-1
 * repl_backlog_active:0
 * repl_backlog_size:1048576
 * repl_backlog_first_byte_offset:0
 * repl_backlog_histlen:0
 *
 * # CPU
 * used_cpu_sys:184.211934
 * used_cpu_user:59.039037
 * used_cpu_sys_children:0.020874
 * used_cpu_user_children:0.015275
 * used_cpu_sys_main_thread:184.203551
 * used_cpu_user_main_thread:59.029254
 *
 * # Modules
 *
 * # Errorstats
 * errorstat_ERR:count=3
 * errorstat_NOAUTH:count=6
 * errorstat_WRONGPASS:count=1
 *
 * # Cluster
 * cluster_enabled:0
 *
 * # Keyspace
 * db0:keys=1,expires=0,avg_ttl=0
 */
