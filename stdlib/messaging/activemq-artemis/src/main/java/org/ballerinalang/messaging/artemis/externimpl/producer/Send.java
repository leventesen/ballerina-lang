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
 *
 */

package org.ballerinalang.messaging.artemis.externimpl.producer;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.ClientProducer;
import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.CallableUnitCallback;
import org.ballerinalang.messaging.artemis.ArtemisConstants;
import org.ballerinalang.messaging.artemis.ArtemisUtils;
import org.ballerinalang.model.NativeCallableUnit;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;

/**
 * Extern function of the producer to send a message.
 *
 * @since 0.995
 */

@BallerinaFunction(
        orgName = ArtemisConstants.BALLERINA, packageName = ArtemisConstants.ARTEMIS,
        functionName = "externSend",
        receiver = @Receiver(type = TypeKind.OBJECT, structType = ArtemisConstants.PRODUCER_OBJ,
                             structPackage = ArtemisConstants.PROTOCOL_PACKAGE_ARTEMIS),
        args = {
                @Argument(name = "data", type = TypeKind.OBJECT, structType = ArtemisConstants.MESSAGE_OBJ)
        }
)
public class Send implements NativeCallableUnit {

    @Override
    public void execute(Context context, CallableUnitCallback callableUnitCallback) {
        try {
            @SuppressWarnings(ArtemisConstants.UNCHECKED)
            BMap<String, BValue> producerObj = (BMap<String, BValue>) context.getRefArgument(0);
            @SuppressWarnings(ArtemisConstants.UNCHECKED)
            BMap<String, BValue> data = (BMap<String, BValue>) context.getRefArgument(1);
            ClientProducer producer = (ClientProducer) producerObj.getNativeData(ArtemisConstants.ARTEMIS_PRODUCER);
            ClientMessage message = (ClientMessage) data.getNativeData(ArtemisConstants.ARTEMIS_MESSAGE);
            producer.send(message, message1 -> callableUnitCallback.notifySuccess());
        } catch (ActiveMQException e) {
            context.setReturnValues(ArtemisUtils.getError(context, e));
            callableUnitCallback.notifySuccess();
        }
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
