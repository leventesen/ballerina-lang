/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.jvm.values;

import org.ballerinalang.jvm.types.BType;
import org.ballerinalang.jvm.util.exceptions.BLangFreezeException;
import org.ballerinalang.jvm.util.exceptions.BallerinaException;
import org.ballerinalang.jvm.values.freeze.State;
import org.ballerinalang.jvm.values.freeze.Status;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Interface to be implemented by all the reference types.
 * 
 * @since 0.995.0
 */
public interface RefValue {

    BType getType();

    void stamp(BType type);

    Object copy(Map<Object, Object> refs);

    /**
     * Method to retrieve if the {@link RefValue} is frozen, if applicable. Compile time checks ensure
     * that the check is only possible on structured basic types.
     *
     * @return Flag indicating whether the value is frozen or not
     */
    default boolean isFrozen() {
        return false;
    }

    /**
     * Method to attempt freezing a {@link RefValue}, to disallow further modification.
     *
     * @param freezeStatus the {@link Status} instance to keep track of the
     *            freeze result of this attempt
     */
    default void attemptFreeze(Status freezeStatus) {
        throw new BLangFreezeException("freeze not allowed on '" + getType() + "'");
    }

    /**
     * Freeze the value. If freeze is successful, the status is updated to frozen and
     * the same value itself will return. If the freezing fails, frozen status of the
     * value and its constituents is set to false, and an error is returned.
     * 
     * @return if freeze is successful, same value is returned. Else an error is returned
     */
    default Object freeze() {
        Status freezeStatus = new Status(State.MID_FREEZE);
        try {
            // if freeze is successful, set the status as frozen and the value itself as the return value
            attemptFreeze(freezeStatus);
            freezeStatus.setFrozen();
            return this;
        } catch (BLangFreezeException e) {
            // if freeze is unsuccessful due to an invalid value, set the frozen status of the value and its
            // constituents to false, and return an error
            freezeStatus.setUnfrozen();

            // TODO: return an error value
            return null;
        } catch (BallerinaException e) {
            // if freeze is unsuccessful due to concurrent freeze attempts, set the frozen status of the value
            // and its constituents to false, and panic
            freezeStatus.setUnfrozen();
            throw e;
        }
    }

    /**
     * Default serialize implementation for {@link RefValue}.
     *
     * @param outputStream Represent the output stream that the data will be written to.
     */
    default void serialize(OutputStream outputStream) {
        try {
            outputStream.write(this.toString().getBytes(Charset.defaultCharset()));
        } catch (IOException e) {
            throw new BallerinaException("error occurred while serializing data", e);
        }
    }
}
