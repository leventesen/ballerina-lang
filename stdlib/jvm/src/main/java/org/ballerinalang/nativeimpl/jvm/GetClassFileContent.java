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
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.nativeimpl.jvm;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.model.values.BValueArray;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.objectweb.asm.ClassWriter;

import static org.ballerinalang.model.types.TypeKind.ARRAY;
import static org.ballerinalang.model.types.TypeKind.BYTE;

/**
 * Native class for jvm java class byte code creation.
 */
@BallerinaFunction(
        orgName = "ballerina", packageName = "jvm",
        functionName = "getClassFileContent",
        returnType = @ReturnType(type = ARRAY, elementType = BYTE)
)
public class GetClassFileContent extends BlockingNativeCallableUnit {

    @Override
    public void execute(Context context) {
        ClassWriter classWriter = ASMCodeGenerator.getInstance().getClassWriter();
        context.setReturnValues(new BValueArray(classWriter.toByteArray()));
        ASMCodeGenerator.getInstance().clean();
    }
}

