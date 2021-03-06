/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.wso2.ballerinalang.compiler.util;

import org.wso2.ballerinalang.compiler.semantics.model.types.BArrayType;

/**
 * Enum to hold the state of a {@link BArrayType}.
 */
public enum BArrayState {
    CLOSED_SEALED((byte) 1),
    OPEN_SEALED((byte) 2),
    UNSEALED((byte) 3);

    byte value;

    BArrayState(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return this.value;
    }

}
