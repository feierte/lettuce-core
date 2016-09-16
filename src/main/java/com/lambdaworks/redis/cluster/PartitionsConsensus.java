package com.lambdaworks.redis.cluster;

import java.util.Map;

import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.cluster.models.partitions.Partitions;

/**
 * Consensus API to decide on the {@link com.lambdaworks.redis.cluster.models.partitions.Partitions topology view} to be used by
 * {@link RedisClusterClient}.
 * <p>
 * {@link PartitionsConsensus} takes the current {@link Partitions} and a {@link java.util.Map} of newly retrieved
 * {@link Partitions} to determine a view that shall be used. Implementing classes may reuse {@link Partitions} from input
 * arguments or construct a new {@link Partitions} object.
 * 
 * @author Mark Paluch
 * @since 4.2
 * @see com.lambdaworks.redis.cluster.models.partitions.Partitions
 * @see RedisClusterClient
 */
abstract class PartitionsConsensus {

    /**
     * Consensus algorithm to select a partition containing the most previously known nodes.
     */
    public static final PartitionsConsensus KNOWN_MAJORITY = new PartitionsConsensusImpl.KnownMajority();

    /**
     * Consensus algorithm to select a topology view containing the most active nodes.
     */
    public static final PartitionsConsensus HEALTHY_MAJORITY = new PartitionsConsensusImpl.HealthyMajority();

    /**
     * Determine the {@link Partitions} to be used by {@link RedisClusterClient}.
     * 
     * @param current the currently used topology view, must not be {@literal null}.
     * @param topologyViews the newly retrieved views, must not be {@literal null}.
     * @return the resulting {@link Partitions} to be used by {@link RedisClusterClient}.
     */
    abstract Partitions getPartitions(Partitions current, Map<RedisURI, Partitions> topologyViews);
}
