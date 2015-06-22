/*
 * Copyright 2015 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec;

import io.netty.util.internal.ObjectUtil;

/**
 * Result of detecting a protocol.
 *
 * @param <T> the type of the protocol
 */
public final class ProtocolDetectionResult<T> {

    /**
     * The state of the current detection.
     */
    public enum DetectionState {
        /**
         * Need more data to detect the protocol.
         */
        NEEDS_MORE_DATA,
        /**
         * The data was invalid.
         */
        INVALID,

        /**
         * Protocol was detected,
         */
        DETECTED
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final ProtocolDetectionResult NEEDS_MORE_DATE =
            new ProtocolDetectionResult(DetectionState.NEEDS_MORE_DATA, null);
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final ProtocolDetectionResult INVALID =
            new ProtocolDetectionResult(DetectionState.INVALID, null);

    private final DetectionState state;
    private final T result;

    /**
     * Returns a {@link ProtocolDetectionResult} that signals that more data is needed to detect the protocol.
     */
    @SuppressWarnings("unchecked")
    public static <T> ProtocolDetectionResult<T> needsMoreData() {
        return NEEDS_MORE_DATE;
    }

    /**
     * Returns a {@link ProtocolDetectionResult} that signals the data was invalid for the protocol.
     */
    @SuppressWarnings("unchecked")
    public static <T> ProtocolDetectionResult<T> invalid() {
        return INVALID;
    }

    /**
     * Returns a {@link ProtocolDetectionResult} which holds the detected protocol.
     */
    @SuppressWarnings("unchecked")
    public static <T> ProtocolDetectionResult<T> detected(T protocol) {
        return new ProtocolDetectionResult<T>(DetectionState.DETECTED, ObjectUtil.checkNotNull(protocol, "protocol"));
    }

    private ProtocolDetectionResult(DetectionState state, T result) {
        this.state = state;
        this.result = result;
    }

    /**
     * Return the {@link DetectionState}. If the state is {@link DetectionState#DETECTED} you can retrieve the protocol
     * via {@link #detectedProtocol()}.
     */
    public DetectionState state() {
        return state;
    }

    /**
     * Returns the protocol if {@link #state()} returns {@link DetectionState#DETECTED}, otherwise {@code null}.
     */
    public T detectedProtocol() {
        return result;
    }
}
