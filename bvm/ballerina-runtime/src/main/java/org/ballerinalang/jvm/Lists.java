/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.ballerinalang.jvm;

import org.ballerinalang.jvm.types.BArrayType;
import org.ballerinalang.jvm.types.TypeTags;
import org.ballerinalang.jvm.values.ArrayValue;

import java.math.BigDecimal;

/**
 * Common utility methods used for List manipulation.
 *
 * @since 0.995.0
 */
public class Lists {

    public static Object get(ArrayValue array, int index) {
        if (array.getType().getTag() != TypeTags.ARRAY_TAG) {
            return array.getRefValue(index);
        }

        switch (((BArrayType) array.getType()).getElementType().getTag()) {
            case TypeTags.BOOLEAN_TAG:
                int i = array.getBoolean(index);
                return new Boolean(i != 0);
            case TypeTags.BYTE_TAG:
                return new Byte(array.getByte(index));
            case TypeTags.FLOAT_TAG:
                return new Float(array.getFloat(index));
            case TypeTags.DECIMAL_TAG:
                return new BigDecimal(array.getRefValue(index).toString());
            case TypeTags.INT_TAG:
                return new Integer((int) array.getInt(index));
            case TypeTags.STRING_TAG:
                return new String(array.getString(index));
            default:
                return array.getRefValue(index);
        }
    }

    public static void add(ArrayValue array, int index, Object refType) {
        if (array.getType().getTag() != TypeTags.ARRAY_TAG) {
            array.add(index, refType);
            return;
        }

        switch (((BArrayType) array.getType()).getElementType().getTag()) {
            case TypeTags.BOOLEAN_TAG:
                array.add(index, ((Boolean) refType).booleanValue() ? 1 : 0);
                return;
            case TypeTags.BYTE_TAG:
                array.add(index, ((Byte) refType).byteValue());
                return;
            case TypeTags.FLOAT_TAG:
                array.add(index, ((Double) refType).doubleValue());
                return;
            case TypeTags.INT_TAG:
                array.add(index, ((Long) refType).longValue());
                return;
            case TypeTags.STRING_TAG:
                array.add(index, (String) refType);
                return;
            default:
                array.add(index, refType);
        }
    }
}
