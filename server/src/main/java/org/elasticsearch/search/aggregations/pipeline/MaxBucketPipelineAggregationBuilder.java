/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.search.aggregations.pipeline;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Map;

public class MaxBucketPipelineAggregationBuilder extends BucketMetricsPipelineAggregationBuilder<MaxBucketPipelineAggregationBuilder> {
    public static final String NAME = "max_bucket";

    public MaxBucketPipelineAggregationBuilder(String name, String bucketsPath) {
        super(name, NAME, new String[] { bucketsPath });
    }

    /**
     * Read from a stream.
     */
    public MaxBucketPipelineAggregationBuilder(StreamInput in) throws IOException {
        super(in, NAME);
    }

    @Override
    protected void innerWriteTo(StreamOutput out) throws IOException {
        // Do nothing, no extra state to write to stream
    }

    @Override
    protected PipelineAggregator createInternal(Map<String, Object> metadata) {
        return new MaxBucketPipelineAggregator(name, bucketsPaths, gapPolicy(), formatter(), metadata);
    }

    @Override
    protected XContentBuilder doXContentBody(XContentBuilder builder, Params params) throws IOException {
        return builder;
    }

    public static final PipelineAggregator.Parser PARSER = new BucketMetricsParser() {
        @Override
        protected MaxBucketPipelineAggregationBuilder buildFactory(String pipelineAggregatorName,
                String bucketsPath, Map<String, Object> params) {
            return new MaxBucketPipelineAggregationBuilder(pipelineAggregatorName, bucketsPath);
        }
    };

    @Override
    public String getWriteableName() {
        return NAME;
    }
}
